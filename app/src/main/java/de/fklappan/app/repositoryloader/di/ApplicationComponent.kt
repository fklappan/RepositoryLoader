package de.fklappan.app.repositoryloader.di

import dagger.Component

@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun newControllerComponent(module: ControllerModule) : ControllerComponent
}