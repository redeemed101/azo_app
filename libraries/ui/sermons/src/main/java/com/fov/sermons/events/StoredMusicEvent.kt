package com.fov.sermons.events

import com.fov.domain.database.models.DownloadedAlbum
import com.fov.domain.database.models.DownloadedSong

sealed class StoredMusicEvent {
    object LoadDownloadedSongs : StoredMusicEvent()
    object LoadDownloadedAlbums : StoredMusicEvent()
    data class UpdateSongDownloadProgress(val progress: Int) : StoredMusicEvent()
    data class SaveDownloadedSong(val song : DownloadedSong) : StoredMusicEvent()
    data class DeleteDownloadedSong(val songId : String) : StoredMusicEvent()
    data class SaveDownloadedAlbum(val album : DownloadedAlbum) : StoredMusicEvent()
    data class DeleteDownloadedAlbum(val albumId : String) : StoredMusicEvent()
    data class DeleteDownloadedPlaylist(val playlistId : String) : StoredMusicEvent()
}