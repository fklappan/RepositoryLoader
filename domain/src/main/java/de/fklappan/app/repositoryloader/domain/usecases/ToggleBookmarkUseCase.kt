package de.fklappan.app.repositoryloader.domain.usecases

import de.fklappan.app.repositoryloader.domain.DataRepository
import de.fklappan.app.repositoryloader.domain.LOG_TAG
import de.fklappan.app.repositoryloader.domain.Logger
import de.fklappan.app.repositoryloader.domain.models.BookmarkDomainModel
import de.fklappan.app.repositoryloader.domain.models.RepositoryDomainModel
import de.fklappan.app.repositoryloader.domain.usecases.common.UseCase
import io.reactivex.Single

/**
 * UseCase class to toggle the bookmark for a repository. If a bookmark is removed, it is also
 * deleted from the database to prevent garbage in the database.
 */
class ToggleBookmarkUseCase(private val repository: DataRepository,
                            private val logger: Logger
) : UseCase<RepositoryDomainModel, RepositoryDomainModel> {

    override fun execute(param: RepositoryDomainModel): Single<RepositoryDomainModel> {
        return Single.create<RepositoryDomainModel>
        {

            if (repository.hasBookmark(param.repositoryId)) {
                logger.d(LOG_TAG, "Removing bookmark for repository: ${param.repositoryId}")
                repository.deleteBookmark(param.repositoryId)
                param.bookmark = false
            } else {
                // first time bookmark toggle is always "activate bookmark"
                logger.d(LOG_TAG, "Creating bookmark for repository: ${param.repositoryId}")
                repository.addBookmark(BookmarkDomainModel(0,param.repositoryId,true))
                param.bookmark = true
            }

            it.onSuccess(param)
        }
    }
}