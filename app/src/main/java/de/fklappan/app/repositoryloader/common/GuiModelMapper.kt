package de.fklappan.app.repositoryloader.common

import de.fklappan.app.repositoryloader.domain.models.RepositoryDomainModel
import de.fklappan.app.repositoryloader.ui.overviewrepository.RepositoryGuiModel
import javax.inject.Inject

/**
 * Helper class to map data classes between the connected layers.
 */
class GuiModelMapper @Inject constructor(){

    fun mapDomainToGui(domainModel: RepositoryDomainModel) : RepositoryGuiModel = RepositoryGuiModel(domainModel.repositoryId, domainModel.repositoryName, domainModel.stargazersCount, domainModel.bookmark)
    fun mapGuiToDomain(guiModel: RepositoryGuiModel) : RepositoryDomainModel = RepositoryDomainModel(guiModel.repositoryId, guiModel.repositoryName, guiModel.stargazersCount, guiModel.bookmark)
}