package de.fklappan.app.repositoryloader.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import de.fklappan.app.repositoryloader.data.bookmark.BookmarkDao
import de.fklappan.app.repositoryloader.data.bookmark.BookmarkDataModel

@Database(entities = [BookmarkDataModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookmarkDao(): BookmarkDao

    companion object {

        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
                }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                    AppDatabase::class.java, "repositoryloader.db")
                    .allowMainThreadQueries()
                    .build()
    }





}