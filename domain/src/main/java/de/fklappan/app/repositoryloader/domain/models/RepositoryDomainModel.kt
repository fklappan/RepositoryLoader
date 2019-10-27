package de.fklappan.app.repositoryloader.domain.models

/**
 * The domain model representing a repository
 */
data class RepositoryDomainModel(
    var repositoryId: Int,
    var repositoryName: String,
    var stargazersCount: Int,
    var bookmark: Boolean)