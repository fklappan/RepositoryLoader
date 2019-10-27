package de.fklappan.app.repositoryloader.ui.overviewrepository

/**
 * Data class for representing a single Repository within the App module. The different models from
 * the layers can be converted with the GuiModelMapper
 */
data class RepositoryGuiModel(
    var repositoryId: Int,
    var repositoryName: String,
    var stargazersCount: Int,
    var bookmark: Boolean
)