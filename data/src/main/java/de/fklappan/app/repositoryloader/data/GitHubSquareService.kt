package de.fklappan.app.repositoryloader.data

import de.fklappan.app.repositoryloader.data.repository.RepositoryDataModel
import retrofit2.Call
import retrofit2.http.GET

/**
 * Service definition for retrieving the Square Inc. public repositories hosted on GitHub
 */
interface GitHubSquareService {
    @GET("orgs/square/repos")
    fun getRepositoriesCall() : Call<List<RepositoryDataModel>>
}