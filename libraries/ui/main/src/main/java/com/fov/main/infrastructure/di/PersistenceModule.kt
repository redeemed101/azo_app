package com.example.fidarrappcompose.infrastructure.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.fidarrappcompose.R
import com.example.fidarrappcompose.infrastructure.persistence.AppDatabase
import com.fidarr.core.di.Preferences
import com.fidarr.domain.cache.Cache
import com.fidarr.domain.cache.CacheTest
import com.fidarr.domain.cache.RoomCache
import com.fidarr.domain.database.daos.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule  {
    @Provides
    @Singleton
    fun provideAppDatabase(application: Application): AppDatabase {
        return AppDatabase
            .createDatabase(
                application,
                application.getString(R.string.database)
            )
        /*return Room
            .databaseBuilder(
                application,
                AppDatabase::class.java,
                application.getString(R.string.database)
            )
            .fallbackToDestructiveMigration()
            .build()*/
    }
    @Provides
    @Singleton
    fun provideUserDao(appDatabase: AppDatabase): UserDao {
        return appDatabase.userDao()
    }

    @Provides
    @Singleton
    fun provideDownloadedPlaylistsDao(appDatabase: AppDatabase): DownloadedPlaylistDao {
        return appDatabase.downloadedPlaylistsDao()
    }
    @Provides
    @Singleton
    fun provideDownloadedSongsDao(appDatabase: AppDatabase): DownloadedSongsDao {
        return appDatabase.downloadedSongsDao()
    }

    @Provides
    @Singleton
    fun provideDownloadedAlbumDao(appDatabase: AppDatabase): DownloadedAlbumsDao {
        return appDatabase.downloadedAlbumsDao()
    }

    @Provides
    @Singleton
    fun provideRecentActivityDao(appDatabase: AppDatabase): RecentActivityDao {
        return appDatabase.recentActivityDao()
    }

    @Provides
    @Singleton
    fun provideRecentSongSearchDao(appDatabase: AppDatabase): RecentSongSearchDao {
        return appDatabase.recentSongSearchDao()
    }
    @Provides
    @Singleton
    fun provideRecentUserSearchDao(appDatabase: AppDatabase): RecentUserSearchDao {
        return appDatabase.recentUserSearchDao()
    }

    @Provides
    @Singleton
    fun provideCache(
        userSearchDao: RecentUserSearchDao,
        songSearchDao: RecentSongSearchDao,
        recentActivityDao: RecentActivityDao
    ) : Cache {
        return CacheTest(
            songSearchDao,
            userSearchDao,
            recentActivityDao
        )
    }

    @Provides
    @Singleton
    fun provideUserPreferences(@ApplicationContext context: Context): Preferences {

        return Preferences(context)
    }

}