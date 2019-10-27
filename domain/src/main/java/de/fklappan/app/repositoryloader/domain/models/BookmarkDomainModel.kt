package de.fklappan.app.repositoryloader.domain.models

/**
 * The domain model representing an bookmark
 */
data class BookmarkDomainModel(
    var bookmarkId: Int,
    var repositoryId: Int,
    var bookmark: Boolean
)