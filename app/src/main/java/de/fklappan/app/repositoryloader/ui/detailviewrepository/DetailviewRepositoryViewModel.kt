package de.fklappan.app.repositoryloader.ui.detailviewrepository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import de.fklappan.app.repositoryloader.common.GuiModelMapper
import de.fklappan.app.repositoryloader.common.RxViewModel
import de.fklappan.app.repositoryloader.common.UseCasesFactory
import de.fklappan.app.repositoryloader.domain.LOG_TAG
import de.fklappan.app.repositoryloader.domain.models.RepositoryDomainModel
import de.fklappan.app.repositoryloader.ui.overviewrepository.RepositoryGuiModel
import io.reactivex.Scheduler

/**
 * ViewModel for the detail view fragment handling the logic for a user input or data retrieval.
 * @param useCasesFactory       the factory class to create UseCase objects.
 * @param schedulerIo           the IO scheduler to load work off to another thread.
 * @param schedulerGuiThread    the scheduler to access the Android GUI thread.
 * @param modelMapper           the model mapper class to map between GUI and domain models.
 */
class DetailviewRepositoryViewModel(private val useCasesFactory: UseCasesFactory,
                                    private val schedulerIo: Scheduler,
                                    private val schedulerGuiThread: Scheduler,
                                    private val modelMapper: GuiModelMapper)
: RxViewModel() {

    private lateinit var currentRepositoryGuiModel: RepositoryGuiModel
    private val _state = MutableLiveData<DetailviewRepositoryState >()
    // expose read only workoutState
    val state: LiveData<DetailviewRepositoryState >
        get() = _state

    /**
     * Initialises the ViewModel with the repository data
     */
    fun init(repositoryId: Int, repositoryName: String, stargazersCount: Int, bookmark: Boolean) {
        Log.d(LOG_TAG, "init")
        currentRepositoryGuiModel = RepositoryGuiModel(repositoryId, repositoryName, stargazersCount, bookmark)
        _state.value = DetailviewRepositoryState.RepositoryDetails(currentRepositoryGuiModel)
    }

    /**
     * Notifies the ViewModel about a bookmark action started by the user.
     */
    fun bookmarkClicked() {
        Log.d(LOG_TAG, "bookmarkClicked")
        addDisposable(useCasesFactory.createToggleBookmarkUseCase().execute(modelMapper.mapGuiToDomain(currentRepositoryGuiModel))
            .subscribeOn(schedulerIo)
            .observeOn(schedulerGuiThread)
            .subscribe(
                this::handleSuccess,
                this::handleError
            )
        )
    }

    private fun handleSuccess(repositoryDomainModel: RepositoryDomainModel) {
        Log.d(LOG_TAG, "handleSuccess")
        // map model and trigger new state event
        currentRepositoryGuiModel = modelMapper.mapDomainToGui(repositoryDomainModel)
        _state.value = DetailviewRepositoryState.RepositoryDetails(currentRepositoryGuiModel)
    }

    private fun handleError(error: Throwable) {
        Log.e(LOG_TAG, "Error while executing request", error)
        _state.value = DetailviewRepositoryState.Error(error.localizedMessage)
    }
}