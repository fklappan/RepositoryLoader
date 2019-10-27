package de.fklappan.app.repositoryloader.ui.detailviewrepository

import de.fklappan.app.repositoryloader.ui.overviewrepository.RepositoryGuiModel

/**
 * Represents the current state of the detailview fragment.
 */
sealed class DetailviewRepositoryState {
    data class Error(val message: String) : DetailviewRepositoryState()
    data class RepositoryDetails(val repositoryDetails : RepositoryGuiModel) : DetailviewRepositoryState()
}
