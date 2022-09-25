package com.fov.sermons.events

import com.fov.domain.database.models.DownloadedAlbum
import com.fov.domain.database.models.DownloadedSong
import com.fov.sermons.models.Album
import com.fov.sermons.models.Song

sealed class StoredMusicEvent {
    object LoadDownloadedSongs : StoredMusicEvent()
    object LoadDownloadedAlbums : StoredMusicEvent()
    data class UpdateSongDownloadProgress(val progress: Float?, val songId: String) : StoredMusicEvent()
    data class SaveDownloadedSong(val song : DownloadedSong) : StoredMusicEvent()
    data class DeleteDownloadedSong(val songId : String) : StoredMusicEvent()
    data class DownloadAlbum(val album : Album, val privateKey: String) : StoredMusicEvent()
    data class DeleteDownloadedAlbum(val albumId : String) : StoredMusicEvent()
    data class DownloadSong(val song: Song, val privateKey: String) : StoredMusicEvent()
    data class DecryptSong(val song: DownloadedSong, val secretKey : String) : StoredMusicEvent()

}