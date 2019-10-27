package de.fklappan.app.repositoryloader.data

import de.fklappan.app.repositoryloader.data.bookmark.BookmarkDataModel
import de.fklappan.app.repositoryloader.data.repository.RepositoryDataModel
import de.fklappan.app.repositoryloader.domain.models.BookmarkDomainModel
import de.fklappan.app.repositoryloader.domain.models.RepositoryDomainModel

/**
 * Helper class to map between data and domain models
 */
class ModelMapper {

    fun mapDataToDomain(dataModel: RepositoryDataModel) = RepositoryDomainModel(dataModel.repositoryId, dataModel.repositoryName, dataModel.stargazersCount, false)
    fun mapDataToDomain(dataModel: BookmarkDataModel) = BookmarkDomainModel(dataModel.bookmarkId, dataModel.repositoryId, dataModel.bookmark)
    fun mapDomainToData(domainModel: BookmarkDomainModel) = BookmarkDataModel(domainModel.bookmarkId, domainModel.repositoryId, domainModel.bookmark)
}