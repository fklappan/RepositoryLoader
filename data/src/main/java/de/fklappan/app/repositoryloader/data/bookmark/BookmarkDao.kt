package de.fklappan.app.repositoryloader.data.bookmark

import androidx.room.*

/**
 * Room DAO implementation for bookmarks
 */
@Dao
interface BookmarkDao{

    @Query("SELECT * FROM bookmark")
    fun getBookmarkList(): List<BookmarkDataModel>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertBookmark(bookmarkDataModel: BookmarkDataModel)

    @Update
    fun updateBookmark(bookmarkDataModel: BookmarkDataModel)

    @Query("SELECT * FROM bookmark WHERE repository_id = :repositoryId")
    fun getBookmarkForRepository(repositoryId: Int): BookmarkDataModel

    @Query("DELETE FROM bookmark WHERE repository_id = :repositoryId")
    fun deleteBookmarkForRepository(repositoryId: Int)
}