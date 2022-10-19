package com.fov.sermons.di

import com.fov.domain.cache.Cache
import com.fov.domain.database.daos.DownloadedAlbumsDao
import com.fov.domain.database.daos.DownloadedSongsDao
import com.fov.domain.interactors.music.MusicInteractor
import com.fov.domain.interactors.music.StoredMusicInteractor
import com.fov.domain.interactors.news.NewsInteractor
import com.fov.domain.interactors.video.VideoInteractor
import com.fov.domain.remote.apollo.music.ApolloMusicService
import com.fov.domain.remote.music.*
import com.fov.domain.remote.news.NewsKtorRemote
import com.fov.domain.remote.news.NewsKtorService
import com.fov.domain.remote.news.NewsRemote
import com.fov.domain.remote.video.VideoKtorRemote
import com.fov.domain.remote.video.VideoKtorService
import com.fov.domain.remote.video.VideoRemote
import com.fov.domain.repositories.music.*
import com.fov.domain.repositories.news.NewsRepository
import com.fov.domain.repositories.news.NewsRepositoryImpl
import com.fov.domain.repositories.video.VideoRepository
import com.fov.domain.repositories.video.VideoRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import io.ktor.client.*

@Module
@InstallIn(ViewModelComponent::class)
object SermonsModule {
    @Provides
    fun providesMusicInteractor(
        repository: SermonRepository,
        graphQLService : ApolloMusicService
    ): MusicInteractor = MusicInteractor(repository,graphQLService)

    @Provides
    fun providesNewsInteractor(
        repository: NewsRepository
    ): NewsInteractor = NewsInteractor(repository)

    @Provides
    fun providesVideoInteractor(
        repository: VideoRepository
    ) : VideoInteractor = VideoInteractor(repository)

    @Provides
    fun providesStoredSermonRepository(
        downloadedSongsDao: DownloadedSongsDao,
        downloadedAlbumsDao: DownloadedAlbumsDao,
        client: HttpClient
    ) : StoredSermonRepository = StoredSermonRepositoryImpl(downloadedSongsDao,downloadedAlbumsDao,client)


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
    fun providesNewsRepository(
        remote: NewsRemote
    ): NewsRepository = NewsRepositoryImpl(remote)

    @Provides
    fun providesVideoRepository(
        remote: VideoRemote
    ) : VideoRepository  = VideoRepositoryImpl(remote)


    @Provides
    fun providesMusicRemote(
        musicService : MusicKtorService
    ): MusicRemote = MusicKtorRemote(
        musicService
    )
    @Provides
    fun providesVideoRemote(
        videoService : VideoKtorService
    ) : VideoRemote = VideoKtorRemote(
        videoKtorService = videoService
    )
    @Provides
    fun providesNewsRemote(
        newsKtorService: NewsKtorService
    ) : NewsRemote = NewsKtorRemote(
        newsKtorService = newsKtorService
    )

}