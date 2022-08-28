package com.fov.sermons.states

import androidx.paging.PagingData
import com.fov.domain.database.models.RecentActivity
import com.fov.sermons.models.*
import com.fov.sermons.models.*
import com.fov.sermons.utils.MediaPlayback
import com.google.android.exoplayer2.ExoPlayer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class MusicState(
    val isLoading: Boolean = false,
    val currentSong : Song? = null,
    val isSongStarted : Boolean = false,
    val songPlayBackProgress: Float = 0.0f,
    val currentLength: String = "",
    val topSongsPaged : Flow<PagingData<Song>>? = null,
    val forYouPaged : Flow<PagingData<Song>>? = null,
    val topAlbumsPaged : Flow<PagingData<Album>>? = null,
    val errorMessage : String? = "",
    val recentSongSearch : Flow<PagingData<Song>> = flowOf(PagingData.from(emptyList())),
    val searchSongText : String = "",
    val newReleases : Flow<PagingData<Song>>? = null,
    val genres :  List<Genre>  = emptyList(),
    val genreData: GenreData? = null,
    val newSongs :  Flow<PagingData<Song>>  = flowOf(PagingData.from(emptyList())),
    val newAlbums :  Flow<PagingData<Album>>  = flowOf(PagingData.from(emptyList())),
    val nowPlaying : List<Song> = emptyList(),
    val selectedGenre: Genre? = null,
    val showingSong : Boolean = false,
    val showingAlbum : Boolean = false,
    val artistSongs :  Flow<PagingData<Song>>  = flowOf(PagingData.from(emptyList())),
    val recentActivities :  Flow<PagingData<RecentActivity>>  = flowOf(PagingData.from(emptyList())),
    val selectedSong : Song? = null,
    val selectedAlbum : Album? = null,
    val likedMusicData: LikedMusicData? = null,
    val isPlayerMinimized : Boolean = true,
    val mediaPlayBack : MediaPlayback? = null,
    val player : ExoPlayer? = null,
    val videos :  Flow<PagingData<Video>>  = flowOf(PagingData.from(emptyList())),

) {
    companion object {
        fun initialise(): MusicState = MusicState()
    }
    fun build(block: Builder.() -> Unit) = Builder(this).apply(block).build()
    class Builder(private val state: MusicState) {
        var loading = state.isLoading
        var currentSong = state.currentSong
        var songStarted = state.isSongStarted
        var songPlayBackProgress = state.songPlayBackProgress
        var currentLength = state.currentLength
        var topSongsPaged = state.topSongsPaged
        var forYouPaged = state.forYouPaged
        var topAlbumsPaged = state.topAlbumsPaged
        var error = state.errorMessage
        var recentSongSearch = state.recentSongSearch
        var searchSongText = state.searchSongText
        var newReleases = state.newReleases
        var genres = state.genres
        var genreData = state.genreData
        var newSongs = state.newSongs
        var newAlbums = state.newAlbums
        var nowPlaying = state.nowPlaying
        var selectedGenre = state.selectedGenre
        var showingSong = state.showingSong
        var showingAlbum = state.showingAlbum
        var artistSongs = state.artistSongs
        var recentActivities = state.recentActivities
        var selectedSong = state.selectedSong
        var selectedAlbum = state.selectedAlbum
        var likedMusicData = state.likedMusicData
        var isPlayerMinimized = state.isPlayerMinimized
        var mediaPlayBack = state.mediaPlayBack
        var player = state.player
        var videos = state.videos


        fun build(): MusicState {
            return MusicState(
                loading,
                currentSong,
                songStarted,
                songPlayBackProgress,
                currentLength,
                topSongsPaged,
                forYouPaged,
                topAlbumsPaged,
                error,
                recentSongSearch,
                searchSongText,
                newReleases,
                genres,
                genreData,
                newSongs,
                newAlbums,
                nowPlaying,
                selectedGenre,
                showingSong,
                showingAlbum,
                artistSongs,
                recentActivities,
                selectedSong,
                selectedAlbum,
                likedMusicData,
                isPlayerMinimized,
                mediaPlayBack,
                player,
                videos

            )
        }


    }
}