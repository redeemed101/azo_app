package com.fov.sermons.events

sealed class StoredMusicEvent {
    object LoadDownloadedSongs : StoredMusicEvent()
    object LoadDownloadedAlbums : StoredMusicEvent()
    data class SaveDownloadedSong(val song : DownloadedSong) : StoredMusicEvent()
    data class DeleteDownloadedSong(val songId : String) : StoredMusicEvent()
    data class SaveDownloadedAlbum(val album : DownloadedAlbum) : StoredMusicEvent()
    data class DeleteDownloadedAlbum(val albumId : String) : StoredMusicEvent()
    data class DeleteDownloadedPlaylist(val playlistId : String) : StoredMusicEvent()
    data class SaveDownloadedPlaylist(val playlist : DownloadedPlaylist) : StoredMusicEvent()
}