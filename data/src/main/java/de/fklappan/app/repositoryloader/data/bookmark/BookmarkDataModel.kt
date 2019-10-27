package de.fklappan.app.repositoryloader.data.bookmark

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Data model for bookmarks, using Room annotations
 */
@Entity(tableName = "bookmark")
data class BookmarkDataModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "bookmark_id", index = true) var bookmarkId: Int,
    @ColumnInfo(name = "repository_id") var repositoryId: Int,
    @ColumnInfo(name = "bookmark") var bookmark: Boolean
)