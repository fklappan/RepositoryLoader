package de.fklappan.app.repositoryloader.domain.usecases

import de.fklappan.app.repositoryloader.domain.DataRepository
import de.fklappan.app.repositoryloader.domain.LOG_TAG
import de.fklappan.app.repositoryloader.domain.Logger
import de.fklappan.app.repositoryloader.domain.models.RepositoryDomainModel
import de.fklappan.app.repositoryloader.domain.usecases.common.InputlessUseCase
import io.reactivex.Single

/**
 * UseCase to fetch (GitHub) repositories from our data repository.
 * Has no input parameter except the injection of the repository class.
 * UseCase returns a list of RepositoryDomainModel.
 *
 * @param repository    the repository implementation
 */
class GetRepositoriesUseCase(private val repository: DataRepository,
                             private val logger: Logger)
: InputlessUseCase<List<RepositoryDomainModel>> {

    override fun execute(): Single<List<RepositoryDomainModel>> {
        return Single.create{
            val repositoryList = repository.getRepositories()
            val bookmarkList = repository.getBookmarks()
            logger.d(LOG_TAG, "Queried ${repositoryList.size} repositories and ${bookmarkList.size} bookmarks")

            // transform list to map because we are going to search for a lot of identifiers and
            // really dont want to iterate the list again every time
            val bookmarkMap = HashMap<Int, Boolean>()
            for (bookmark in bookmarkList) {
                bookmarkMap[bookmark.repositoryId] = bookmark.bookmark
            }

            for (repository in repositoryList) {
                if (bookmarkMap.containsKey(repository.repositoryId)) {
                    logger.d(LOG_TAG, "Found bookmark for repository: ${repository.repositoryId}")
                    repository.bookmark = bookmarkMap[repository.repositoryId]!!
                } else {
                    logger.d(LOG_TAG, "No bookmark found for repository: ${repository.repositoryId} - assuming not bookmarked")
                    repository.bookmark = false
                }
            }

            it.onSuccess(repositoryList)
        }
    }
}