package de.fklappan.app.repositoryloader.data.repository

import com.google.gson.annotations.SerializedName

/**
 * This class represents the data model for a github repository.
 */
data class RepositoryDataModel(

    /**
     * github internal ID
     */
    @SerializedName("id")
    var repositoryId: Int,

    @SerializedName("name")
    var repositoryName: String,
    /**
     * how many stars the repository has
     */
    @SerializedName("stargazers_count")
    var stargazersCount: Int
)