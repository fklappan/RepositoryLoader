package de.fklappan.app.repositoryloader.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import de.fklappan.app.repositoryloader.common.AndroidLoggerImpl
import de.fklappan.app.repositoryloader.data.AppDatabase
import de.fklappan.app.repositoryloader.domain.Logger

/**
 * Dagger module to provide application wide properties.
 */
@Module
class ApplicationModule(private var application: Application) {

    @Provides
    fun provideApplication() : Application = application

    @Provides
    fun provideApplicationContext() : Context = application.applicationContext

    @Provides
    fun provideLogger() : Logger = AndroidLoggerImpl()

    @Provides
    fun provideDatabase(context: Context) : AppDatabase = AppDatabase.getInstance(context)

}