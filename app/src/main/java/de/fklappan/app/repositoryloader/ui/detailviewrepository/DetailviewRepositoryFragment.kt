package de.fklappan.app.repositoryloader.ui.detailviewrepository

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import de.fklappan.app.repositoryloader.R
import de.fklappan.app.repositoryloader.common.BaseFragment
import de.fklappan.app.repositoryloader.common.ViewModelFactory
import de.fklappan.app.repositoryloader.domain.LOG_TAG
import de.fklappan.app.repositoryloader.ui.overviewrepository.RepositoryGuiModel
import kotlinx.android.synthetic.main.detailview_repository.*
import kotlinx.android.synthetic.main.list_item_repository.*
import javax.inject.Inject

/**
 * Shows detailed information about a passed repository.
 *
 * Needs bundle arguments:
 * EXTRA_REPOSITORY_ID as Int                   the GitHub repository ID
 * EXTRA_REPOSITORY_NAME as String              the name of the repository which is shown
 * EXTRA_REPOSITORY_STARGAZERS_COUNT as Int     the count of the stargazers given on GitHub
 * EXTRA_REPOSITORY_BOOKMARK as Boolean         whether or not the repository is bookmarked
 *
 * Throws a IllegalStateException if the argument bundle is not provided.
 */
const val EXTRA_REPOSITORY_NAME = "EXTRA_REPOSITORY_NAME"
const val EXTRA_REPOSITORY_ID = "EXTRA_REPOSITORY_ID"
const val EXTRA_REPOSITORY_STARGAZERS_COUNT = "EXTRA_REPOSITORY_STARGAZERS_COUNT"
const val EXTRA_REPOSITORY_BOOKMARK = "EXTRA_REPOSITORY_BOOKMARK"

class DetailviewRepositoryFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModelDetail: DetailviewRepositoryViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(LOG_TAG, "onCreateView")
        return inflater.inflate(R.layout.detailview_repository, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(LOG_TAG, "onViewCreated")
        getInjector().inject(this)
        super.onViewCreated(view, savedInstanceState)

        initFragment()
        initViewModels()
        observeViewModels()
        checkArguments()
    }

    private fun checkArguments() {
        Log.d(LOG_TAG, "checkArguments")
        if (requireArguments().getInt(EXTRA_REPOSITORY_ID, -1) < 0) {
            throw IllegalStateException("No repository ID provided. Did you pass an Int argument bundle containing key EXTRA_REPOSITORY_ID?")
        }
        if (requireArguments().getString(EXTRA_REPOSITORY_NAME, "").isEmpty()) {
            throw IllegalStateException("No repository name provided. Did you pass an String argument bundle containing key EXTRA_REPOSITORY_NAME?")
        }
        if (requireArguments().getInt(EXTRA_REPOSITORY_STARGAZERS_COUNT, -1) < 0) {
            throw IllegalStateException("No stargazer count provided. Did you pass an Int argument bundle containing key EXTRA_REPOSITORY_STARGAZERS_COUNT?")
        }
        Log.d(LOG_TAG, "argument check successful")
        // it makes no sense to check for a bool because both possible values could be real values
    }

    private fun initFragment() {
        Log.d(LOG_TAG, "initFragment")
        getAppBarHeader().setHeaderText(R.string.caption_repository_details)
        imageViewBookmark.setOnClickListener(this::onClickBookmark)
    }

    private fun initViewModels() {
        Log.d(LOG_TAG, "initViewModels")
        viewModelDetail = ViewModelProviders.of(this, viewModelFactory)
            .get(DetailviewRepositoryViewModel::class.java)

        viewModelDetail.init(
            requireArguments().getInt(EXTRA_REPOSITORY_ID, 0),
            requireArguments().getString(EXTRA_REPOSITORY_NAME, "not available"),
            requireArguments().getInt(EXTRA_REPOSITORY_STARGAZERS_COUNT),
            requireArguments().getBoolean(EXTRA_REPOSITORY_BOOKMARK, false))
    }

    private fun observeViewModels() {
        Log.d(LOG_TAG, "observeViewModels")
        viewModelDetail.state.observe(
            this,
            Observer { state -> renderState(state) })
    }

    private fun renderState(state: DetailviewRepositoryState) {
        Log.d(LOG_TAG, "renderState")
        when(state) {
            is DetailviewRepositoryState.Error -> showError(state.message)
            is DetailviewRepositoryState.RepositoryDetails -> showData(state.repositoryDetails)
        }
    }

    private fun showError(errorMessage: String) {
        Log.e(LOG_TAG, "showError: $errorMessage")
        textViewError.visibility = View.VISIBLE
        layoutData.visibility = View.GONE
        textViewError.text = errorMessage
    }

    private fun showData(repositoryGuiModel : RepositoryGuiModel) {
        Log.d(LOG_TAG, "showData: ${repositoryGuiModel.repositoryName}")
        textViewError.visibility = View.GONE
        layoutData.visibility = View.VISIBLE

        textViewRepositoryName.text = repositoryGuiModel.repositoryName
        textViewStargazersCount.text = repositoryGuiModel.stargazersCount.toString()
        if (repositoryGuiModel.bookmark) {
            imageViewBookmark.setImageResource(R.drawable.bookmark)
            imageViewBookmark.imageTintList = ColorStateList.valueOf(requireContext().getColor(R.color.colorAccent))
        } else {
            imageViewBookmark.setImageResource(R.drawable.unbookmark)
            imageViewBookmark.imageTintList = ColorStateList.valueOf(requireContext().getColor(R.color.gray))
        }
        imageViewBookmark.isSelected = repositoryGuiModel.bookmark
    }

    private fun onClickBookmark(view: View) {
        Log.d(LOG_TAG, "onClickBookmark")
        viewModelDetail.bookmarkClicked()
        if (!imageViewBookmark.isSelected) {
            playBookmarkAnimation()
        }
        imageViewBookmark.isSelected = !imageViewBookmark.isSelected
    }

    private fun playBookmarkAnimation() {
        Log.d(LOG_TAG, "playBookmarkAnimation")
        val growFactor = 2.0f
        // double scale the button for 200 ms and return back to normal size
        val animScaleX = ObjectAnimator.ofFloat(imageViewBookmark, "scaleX", 1.0f, growFactor)
        with(animScaleX) {
            repeatCount = 1
            repeatMode = ValueAnimator.REVERSE
            duration = 200
        }

        val animScaleY = ObjectAnimator.ofFloat(imageViewBookmark, "scaleY", 1.0f, growFactor)
        with(animScaleY) {
            repeatCount = 1
            repeatMode = ValueAnimator.REVERSE
            duration = 200
        }

        val growAnimator = AnimatorSet()
        growAnimator.play(animScaleX).with(animScaleY)
        growAnimator.start()
    }
}