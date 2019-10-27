package de.fklappan.app.repositoryloader.di

import android.content.Context
import dagger.Module
import dagger.Provides
import de.fklappan.app.repositoryloader.common.GuiModelMapper
import de.fklappan.app.repositoryloader.common.UseCasesFactory
import de.fklappan.app.repositoryloader.common.UseCasesFactoryImpl
import de.fklappan.app.repositoryloader.common.ViewModelFactory
import de.fklappan.app.repositoryloader.data.AppDatabase
import de.fklappan.app.repositoryloader.data.DataRepositoryImpl
import de.fklappan.app.repositoryloader.data.GitHubSquareService
import de.fklappan.app.repositoryloader.domain.DataRepository
import de.fklappan.app.repositoryloader.domain.Logger
import io.reactivex.Scheduler
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

/**
 * Dagger module to provide specific UI controller properties (for fragments/activities)
 */
@Module
class ControllerModule(private val context: Context) {

    @Provides
    fun provideContext() = context

    @Provides
    fun provideRepository(appDatabase: AppDatabase, gitHubSquareService: GitHubSquareService) : DataRepository {
        return DataRepositoryImpl(appDatabase.bookmarkDao(), gitHubSquareService)
    }

    @Provides
    fun provideUseCasesFactory(repository: DataRepository, logger: Logger) : UseCasesFactory {
        return UseCasesFactoryImpl(repository, logger)
    }

    @Provides
    fun provideViewModelFactory(guiModelMapper: GuiModelMapper,
                                useCasesFactory: UseCasesFactory,
                                @Named(SCHEDULER_IO) schedulerIo: Scheduler,
                                @Named(SCHEDULER_MAINTHREAD) schedulerMainThread: Scheduler
    ): ViewModelFactory {
        return ViewModelFactory(guiModelMapper, useCasesFactory, schedulerIo, schedulerMainThread)
    }

    @Provides
    fun provideGitHubSquareService() : GitHubSquareService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(GitHubSquareService::class.java)
    }
}