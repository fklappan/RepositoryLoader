package de.fklappan.app.repositoryloader.ui.overviewrepository

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import de.fklappan.app.repositoryloader.R
import de.fklappan.app.repositoryloader.common.BaseFragment
import de.fklappan.app.repositoryloader.common.ViewModelFactory
import de.fklappan.app.repositoryloader.domain.LOG_TAG
import de.fklappan.app.repositoryloader.ui.detailviewrepository.EXTRA_REPOSITORY_BOOKMARK
import de.fklappan.app.repositoryloader.ui.detailviewrepository.EXTRA_REPOSITORY_ID
import de.fklappan.app.repositoryloader.ui.detailviewrepository.EXTRA_REPOSITORY_NAME
import de.fklappan.app.repositoryloader.ui.detailviewrepository.EXTRA_REPOSITORY_STARGAZERS_COUNT
import kotlinx.android.synthetic.main.overview.*
import javax.inject.Inject

/**
 * Fetches a list of GitHub repositories through an call to the domain module and shows it in a
 * RecyclerView. Items can be clicked which opens a detail fragment.
 */
class OverviewRepositoryFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModelOverviewRepository: OverviewRepositoryViewModel
    private lateinit var overviewRepositoryAdapter: OverviewRepositoryAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(LOG_TAG, "onCreateView")
        return inflater.inflate(R.layout.overview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(LOG_TAG, "onViewCreated")
        getInjector().inject(this)
        super.onViewCreated(view, savedInstanceState)

        initFragment()
        initViewModels()
        observeViewModels()
        fetchData()
    }

    private fun initFragment() {
        Log.d(LOG_TAG, "initFragment")
        getAppBarHeader().setHeaderText(R.string.caption_repository_overview)
        overviewRepositoryAdapter = OverviewRepositoryAdapter { onRepositoryClicked(it) }
        recyclerViewRepositories.adapter = overviewRepositoryAdapter
    }

    private fun onRepositoryClicked(clickedRepository: RepositoryGuiModel) {
        Log.d(LOG_TAG, "onRepositoryClicked - id: ${clickedRepository.repositoryId}")
        val bundle = bundleOf(
            EXTRA_REPOSITORY_ID to clickedRepository.repositoryId,
            EXTRA_REPOSITORY_NAME to clickedRepository.repositoryName,
            EXTRA_REPOSITORY_STARGAZERS_COUNT to clickedRepository.stargazersCount,
            EXTRA_REPOSITORY_BOOKMARK to clickedRepository.bookmark
        )

        Navigation.findNavController(view!!)
            .navigate(R.id.action_overviewFragment_to_detailviewFragment, bundle)
    }

    private fun initViewModels() {
        Log.d(LOG_TAG, "initViewModels")
        viewModelOverviewRepository = ViewModelProviders.of(this, viewModelFactory)
            .get(OverviewRepositoryViewModel::class.java)
    }

    private fun observeViewModels() {
        Log.d(LOG_TAG, "observeViewModels")
        viewModelOverviewRepository.repositoryState.observe(
            this,
            Observer { state -> renderState(state) })
    }

    private fun fetchData() {
        Log.d(LOG_TAG, "fetchData")
        viewModelOverviewRepository.loadRepositories()
    }

    private fun renderState(state: OverviewRepositoryState) {
        Log.d(LOG_TAG, "renderState")
        when(state) {
            is OverviewRepositoryState.Loading -> showLoading()
            is OverviewRepositoryState.Error -> showError(state.message)
            is OverviewRepositoryState.RepositoryDetails -> showResult(state.repositoryList.toMutableList())
        }
    }

    private fun showLoading() {
        Log.d(LOG_TAG, "showLoading")
        recyclerViewRepositories.visibility = View.INVISIBLE
        textViewError.visibility = View.INVISIBLE
        progressBar.visibility = View.VISIBLE
    }

    private fun stopLoading() {
        Log.d(LOG_TAG, "stopLoading")
        progressBar.visibility = View.INVISIBLE
    }

    private fun showResult(resultList: MutableList<RepositoryGuiModel>) {
        Log.d(LOG_TAG, "showResult: " + resultList.size)
        stopLoading()
        textViewError.visibility = View.GONE
        recyclerViewRepositories.visibility = View.VISIBLE
        overviewRepositoryAdapter.items = resultList
    }

    private fun showError(errorMessage: String) {
        Log.e(LOG_TAG, "showError: $errorMessage")
        stopLoading()
        textViewError.visibility = View.VISIBLE
        textViewError.text = errorMessage
        recyclerViewRepositories.visibility = View.GONE
    }
}