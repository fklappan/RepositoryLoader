package de.fklappan.app.repositoryloader.di

import dagger.Subcomponent
import de.fklappan.app.repositoryloader.ui.detailviewrepository.DetailviewRepositoryFragment
import de.fklappan.app.repositoryloader.ui.overviewrepository.OverviewRepositoryFragment

@Subcomponent(modules = [
    ControllerModule::class,
    SchedulersModule::class])
interface ControllerComponent {

    fun inject(fragment: OverviewRepositoryFragment)
    fun inject(fragment: DetailviewRepositoryFragment)
}