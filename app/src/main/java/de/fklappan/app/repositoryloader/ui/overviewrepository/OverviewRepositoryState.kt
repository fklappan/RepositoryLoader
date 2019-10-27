package de.fklappan.app.repositoryloader.ui.overviewrepository

/**
 * Represents the current state of the overview fragment.
 */
sealed class OverviewRepositoryState {
    object Loading : OverviewRepositoryState()
    data class Error(val message: String) : OverviewRepositoryState()
    data class RepositoryDetails(val repositoryList : List<RepositoryGuiModel>) : OverviewRepositoryState()
}
