package com.fov.main.infrastructure.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fov.domain.database.daos.*
import com.fov.domain.database.migrations.MIGRATION_3_4
import com.fov.domain.database.migrations.MIGRATION_4_5
import com.fov.domain.database.models.*

@Database(entities = [User::class,RecentUserSearch::class,RecentSongSearch::class,
    RecentActivity::class, DownloadedSong::class, DownloadedAlbum::class],
    version = 5 , exportSchema = true,
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun recentSongSearchDao() : RecentSongSearchDao
    abstract fun recentActivityDao() : RecentActivityDao
    abstract  fun downloadedSongsDao() : DownloadedSongsDao
    abstract  fun downloadedAlbumsDao() : DownloadedAlbumsDao

    companion object {
        fun createDatabase(appContext: Context, databaseName : String): AppDatabase {
            return Room.databaseBuilder(
                appContext,
                AppDatabase::class.java,
                databaseName
            )
                .fallbackToDestructiveMigration()
                .addMigrations(MIGRATION_3_4, MIGRATION_4_5)
                .build()
        }
    }
}