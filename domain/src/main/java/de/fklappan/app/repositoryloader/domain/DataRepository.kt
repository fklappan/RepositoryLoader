package de.fklappan.app.repositoryloader.domain

import de.fklappan.app.repositoryloader.domain.models.BookmarkDomainModel
import de.fklappan.app.repositoryloader.domain.models.RepositoryDomainModel

/**
 * Definition of OS independent repository interface. Should be implemented by an OS dependent module
 */
interface DataRepository {

    fun getRepositories(): List<RepositoryDomainModel>

    fun addBookmark(bookmarkDomainModel: BookmarkDomainModel)

    fun getBookmarks() : List<BookmarkDomainModel>

    fun hasBookmark(repositoryId: Int) : Boolean

    fun deleteBookmark(repositoryId: Int)
}