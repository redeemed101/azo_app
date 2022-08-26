package com.fov.sermons.di

import com.fov.domain.cache.Cache
import com.fov.domain.database.daos.DownloadedAlbumsDao
import com.fov.domain.database.daos.DownloadedSongsDao
import com.fov.domain.interactors.music.MusicInteractor
import com.fov.domain.interactors.music.StoredMusicInteractor
import com.fov.domain.remote.apollo.music.ApolloMusicService
import com.fov.domain.remote.music.*
import com.fov.domain.repositories.music.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object SermonsModule {
    @Provides
    fun providesMusicInteractor(
        repository: SermonRepository,
        graphQLService : ApolloMusicService
    ): MusicInteractor = MusicInteractor(repository,graphQLService)

    @Provides
    fun providesStoredSermonRepository(
        downloadedSongsDao: DownloadedSongsDao,
        downloadedAlbumsDao: DownloadedAlbumsDao
    ) : StoredSermonRepository = StoredSermonRepositoryMockImpl()


    @Provides
    fun providesStoredMusicInteractor(
        storedSermonRepository: StoredSermonRepository
    ): StoredMusicInteractor = StoredMusicInteractor(storedSermonRepository)


    @Provides
    fun providesMusicRepository(
        remote: MusicRemote,
        cache: Cache
    ): SermonRepository = SermonRepositoryImpl(remote, cache)


    @Provides
    fun providesMusicRemote(
        musicService : MusicKtorService
    ): MusicRemote = MusicKtorRemote(
        musicService
    )

}