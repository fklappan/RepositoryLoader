package de.fklappan.app.repositoryloader.ui.overviewrepository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import de.fklappan.app.repositoryloader.common.GuiModelMapper
import de.fklappan.app.repositoryloader.common.RxViewModel
import de.fklappan.app.repositoryloader.common.UseCasesFactory
import de.fklappan.app.repositoryloader.domain.LOG_TAG
import de.fklappan.app.repositoryloader.domain.models.RepositoryDomainModel
import io.reactivex.Scheduler

/**
 * ViewModel for the overview fragment handling the loading of the data
 * @param useCasesFactory       the factory class to create UseCase objects.
 * @param schedulerIo           the IO scheduler to load work off to another thread.
 * @param schedulerGuiThread    the scheduler to access the Android GUI thread.
 * @param modelMapper           the model mapper class to map between GUI and domain models.
 */
class OverviewRepositoryViewModel(private val useCasesFactory: UseCasesFactory,
                                  private val schedulerIo: Scheduler,
                                  private val schedulerGuiThread: Scheduler,
                                  private val modelMapper: GuiModelMapper)
: RxViewModel() {

    private val _state = MutableLiveData<OverviewRepositoryState>()
    // expose read only workoutState
    val repositoryState: LiveData<OverviewRepositoryState>
        get() = _state

    /**
     * Loads all (GitHub) repositories. They will be exposed via the state LiveData of the ViewModel
      */
    fun loadRepositories() {
        Log.d(LOG_TAG, "loadRepositories")
        addDisposable(useCasesFactory.createGetRepositoriesUseCase().execute()
            .subscribeOn(schedulerIo)
            .observeOn(schedulerGuiThread)
            .doOnSubscribe{ _state.value = OverviewRepositoryState.Loading}
            .subscribe(
                this::handleSuccess,
                this::handleError
            ))
    }

    private fun handleSuccess(repositoryList: List<RepositoryDomainModel>) {
        Log.d(LOG_TAG, "loaded ${repositoryList.size} repositories")
        // map domain model to gui model
        val guiModelList = ArrayList<RepositoryGuiModel>()
        for (domainRepository in repositoryList) {
            guiModelList.add(modelMapper.mapDomainToGui(domainRepository))
        }
        _state.value = OverviewRepositoryState.RepositoryDetails(guiModelList)
    }

    private fun handleError(error: Throwable) {
        Log.e(LOG_TAG, "Error while executing request", error)
        _state.value = OverviewRepositoryState.Error(error.localizedMessage)
    }
}