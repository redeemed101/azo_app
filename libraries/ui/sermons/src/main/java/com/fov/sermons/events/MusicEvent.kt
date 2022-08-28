package com.fov.sermons.events

import com.fov.sermons.models.Album
import com.fov.sermons.models.Genre
import com.fov.sermons.models.Song
import com.fov.sermons.utils.MediaPlayback
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.SimpleExoPlayer

sealed class MusicEvent {
    object LoadTopSongs : MusicEvent()
    object GoToGenres: MusicEvent()
    object GoToDownloadedSermons: MusicEvent()
    object GoToGenre : MusicEvent()
    data class SongStarted(val started: Boolean) : MusicEvent()
    object LoadTopAlbums : MusicEvent()
    object GoToLikedMusic: MusicEvent()
    object LoadLikedAlbums :  MusicEvent()
    object LoadLikedMusic:  MusicEvent()
    object LoadHome : MusicEvent()
    object LoadVideos : MusicEvent()
    object LoadForYou : MusicEvent()
    object LoadGenres : MusicEvent()
    object LoadRecentSearch : MusicEvent()
    object ClearRecentSongSearch : MusicEvent()
    object LoadRecentActivities : MusicEvent()
    object NavigateToHome : MusicEvent()
    data class LikeSong(val songId: String, val isLiked : Boolean) : MusicEvent()
    data class GetSong(val songId: String) : MusicEvent()
    data class LikeAlbum(val albumId: String,val isLiked : Boolean) : MusicEvent()
    data class SearchSongTextChanged(val search : String) : MusicEvent()
    data class SearchSong(val search : String) : MusicEvent()
    data class SaveRecentSearch(val song:Song) : MusicEvent()
    data class SongSelected(val song : Song) : MusicEvent()
    data class ChangeSongSelected(val song : Song) : MusicEvent()
    data class AlbumSelected(val album : Album) : MusicEvent()
    data class GenreSelected(val genre : Genre) : MusicEvent()
    data class ChangeShowingSong(val show : Boolean) : MusicEvent()
    data class ChangeShowingAlbum(val show : Boolean) : MusicEvent()
    data class AddToNowPlaying(val song : Song) : MusicEvent()
    data class PlaySong(val song : Song) : MusicEvent()
    data class SetMediaPlayback(val player : MediaPlayback) : MusicEvent()
    data class MinimizeMusicPlayer(val minimized: Boolean) : MusicEvent()
    data class LoadPlayer(val player : ExoPlayer) : MusicEvent()
}