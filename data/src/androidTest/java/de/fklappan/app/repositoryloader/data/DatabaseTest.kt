package de.fklappan.app.repositoryloader.data

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import de.fklappan.app.repositoryloader.data.bookmark.BookmarkDataModel
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@RunWith(AndroidJUnit4::class)
open class DatabaseTest {

    private lateinit var database: AppDatabase

    @Before
    fun initDb() {
        database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().context, AppDatabase::class.java).build()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun addBookmarkExpectListSizeOne() {
        // given
        val bookmark = BookmarkDataModel(0, 2, true)

        // when
        database.bookmarkDao().insertBookmark(bookmark)

        // then
        assertEquals(1, database.bookmarkDao().getBookmarkList().size, "List should have one element")
    }

    @Test
    fun addAndDeleteBookmarkExpectDeleted() {
        // given
        val bookmark = BookmarkDataModel(0, 2, true)

        // when
        database.bookmarkDao().insertBookmark(bookmark)
        val bookmarkBeforeDelete = database.bookmarkDao().getBookmarkForRepository(2)
        database.bookmarkDao().deleteBookmarkForRepository(2)

        // then
        assertNotNull(bookmarkBeforeDelete)
        assertNull(database.bookmarkDao().getBookmarkForRepository(2))
    }

    @Test
    fun noBookmarkExpectNull() {
        assertNull(database.bookmarkDao().getBookmarkForRepository(2))
    }
}