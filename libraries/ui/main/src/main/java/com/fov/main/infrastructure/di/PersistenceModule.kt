package com.fov.main.infrastructure.di

import android.app.Application
import android.content.Context
import com.fov.core.di.Preferences
import com.fov.domain.cache.Cache
import com.fov.domain.cache.CacheTest
import com.fov.domain.database.daos.*
import com.fov.main.infrastructure.persistence.AppDatabase
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
                application.getString(com.fov.common_ui.R.string.database)
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
    fun provideCache(
        songSearchDao: RecentSongSearchDao,
        recentActivityDao: RecentActivityDao
    ) : Cache {
        return CacheTest(
            songSearchDao,
            recentActivityDao
        )
    }

    @Provides
    @Singleton
    fun provideUserPreferences(@ApplicationContext context: Context): Preferences {

        return Preferences(context)
    }

}