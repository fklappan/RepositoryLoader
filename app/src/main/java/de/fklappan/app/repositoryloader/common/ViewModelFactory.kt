package de.fklappan.app.repositoryloader.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import de.fklappan.app.repositoryloader.ui.detailviewrepository.DetailviewRepositoryViewModel
import de.fklappan.app.repositoryloader.ui.overviewrepository.OverviewRepositoryViewModel
import io.reactivex.Scheduler

/**
 * Custom ViewModel factory to inject the parameters upon construction. This class is injected by
 * dagger. There should be no need to create it otherwise.
 *
 * @param guiModelMapper        the model mapper from the App module
 * @param useCasesFactory       the UseCasesFactory
 * @param schedulerIo           Scheduler for the work thread
 * @param schedulerMainThread   Scheduler for the Android main thread
 */
class ViewModelFactory(private val guiModelMapper: GuiModelMapper,
                       private val useCasesFactory: UseCasesFactory,
                       private val schedulerIo : Scheduler,
                       private val schedulerMainThread: Scheduler)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OverviewRepositoryViewModel::class.java)) {
            return OverviewRepositoryViewModel(useCasesFactory, schedulerIo, schedulerMainThread, guiModelMapper) as T
        }
        if (modelClass.isAssignableFrom(DetailviewRepositoryViewModel::class.java)) {
            return DetailviewRepositoryViewModel(useCasesFactory, schedulerIo, schedulerMainThread, guiModelMapper) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}