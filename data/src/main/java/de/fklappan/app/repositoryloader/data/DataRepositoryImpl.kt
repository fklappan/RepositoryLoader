package de.fklappan.app.repositoryloader.data

import android.util.Log
import de.fklappan.app.repositoryloader.data.bookmark.BookmarkDao
import de.fklappan.app.repositoryloader.domain.DataRepository
import de.fklappan.app.repositoryloader.domain.LOG_TAG
import de.fklappan.app.repositoryloader.domain.models.BookmarkDomainModel
import de.fklappan.app.repositoryloader.domain.models.RepositoryDomainModel

/**
 * Concrete implementation of the OS independent repository interface from the domain module.
 * This is an Android specific implementation as we are using the Android database wrapper Room.
 * @param bookmarkDao               reference to the Room data access object for bookmarks
 * @param gitHubSquareService       the retrofit service to request the GitHub repositories
 */
class DataRepositoryImpl(
    private val bookmarkDao: BookmarkDao,
    private val gitHubSquareService: GitHubSquareService
) : DataRepository {

    private val modelMapper = ModelMapper()

    override fun getRepositories(): List<RepositoryDomainModel> {
        Log.d(LOG_TAG, "getRepositories")

        // map the list of data models to a list of domain models
        val domainModelList = ArrayList<RepositoryDomainModel>()
        val call = gitHubSquareService.getRepositoriesCall()
        val response = call.execute()
        if (response.isSuccessful) {
            for(dataModel in response.body()!!) {
                domainModelList.add(modelMapper.mapDataToDomain(dataModel))
            }
        }
        Log.d(LOG_TAG, "returning ${domainModelList.size} repositories")
        return domainModelList
    }

    override fun getBookmarks(): List<BookmarkDomainModel> {
        Log.d(LOG_TAG, "getBookmarks")

        val domainModelList = ArrayList<BookmarkDomainModel>()
        for(dataModel in bookmarkDao.getBookmarkList()) {
            domainModelList.add(modelMapper.mapDataToDomain(dataModel))
        }
        Log.d(LOG_TAG, "returning ${domainModelList.size} bookmarks")
        return domainModelList
    }

    override fun hasBookmark(repositoryId: Int): Boolean {
        Log.d(LOG_TAG, "hasBookmark for repository id $repositoryId")
        return bookmarkDao.getBookmarkForRepository(repositoryId) != null
    }

    override fun addBookmark(bookmarkDomainModel: BookmarkDomainModel) {
        Log.d(LOG_TAG, "addBookmark for repository id ${bookmarkDomainModel.repositoryId}")
        bookmarkDao.insertBookmark(modelMapper.mapDomainToData(bookmarkDomainModel))
    }

    override fun deleteBookmark(repositoryId: Int) {
        Log.d(LOG_TAG, "deleteBookmark for repository id $repositoryId")
        bookmarkDao.deleteBookmarkForRepository(repositoryId)
    }
}