package de.fklappan.app.repositoryloader.common

import de.fklappan.app.repositoryloader.domain.usecases.GetRepositoriesUseCase
import de.fklappan.app.repositoryloader.domain.usecases.ToggleBookmarkUseCase

/**
 * Factory for creation of UseCase instances.
 */
interface UseCasesFactory {
    fun createGetRepositoriesUseCase() : GetRepositoriesUseCase
    fun createToggleBookmarkUseCase() : ToggleBookmarkUseCase
}