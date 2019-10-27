package de.fklappan.app.repositoryloader

import android.app.Application
import de.fklappan.app.repositoryloader.di.ApplicationComponent
import de.fklappan.app.repositoryloader.di.ApplicationModule
import de.fklappan.app.repositoryloader.di.DaggerApplicationComponent

/**
 * Custom Android application class to initialise Dependency Injection stuff on app startup.
 */
class RepositoryLoaderApplication : Application() {

    private lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        applicationComponent = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }

    fun getApplicationComponent(): ApplicationComponent = applicationComponent
}