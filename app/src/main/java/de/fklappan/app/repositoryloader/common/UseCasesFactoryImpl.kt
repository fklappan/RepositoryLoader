package de.fklappan.app.repositoryloader.common

import de.fklappan.app.repositoryloader.domain.DataRepository
import de.fklappan.app.repositoryloader.domain.Logger
import de.fklappan.app.repositoryloader.domain.usecases.GetRepositoriesUseCase
import de.fklappan.app.repositoryloader.domain.usecases.ToggleBookmarkUseCase

/**
 * Implementation of the UseCasesFactory. Methods simply create and return the requested UseCase instance.
 * @param repository        the repository implementation
 * @param logger            the logger instance
 */
class UseCasesFactoryImpl(private val repository: DataRepository,
                          private val logger: Logger)
    : UseCasesFactory {

    override fun createToggleBookmarkUseCase(): ToggleBookmarkUseCase = ToggleBookmarkUseCase(repository, logger)
    override fun createGetRepositoriesUseCase(): GetRepositoriesUseCase = GetRepositoriesUseCase(repository, logger)
}