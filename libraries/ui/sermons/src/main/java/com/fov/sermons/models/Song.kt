package com.fov.sermons.models


import com.fov.domain.database.models.DownloadedSong
import com.fov.domain.database.models.RecentSongSearch
import com.fov.domain.genres.GetGenreSongsPaginatedQuery
import com.fov.domain.music.GetUserLikedSongsPaginatedQuery
import com.fov.domain.songs.GetTrendingSongsPaginatedQuery

data class Song(
    val songId : String,
    val genres: List<String>? = listOf(),
    val songName : String,
    val songLength : String = "",
    val artwork : String,
    val artistName: String = "Apostle Ziba",
    val description : String = "",
    val previewPath : String = "",
    var path : String = "",
    val likes : Int = 0,
    val userLikes : List<String> = emptyList(),
    val streams : Int = 0
){
    object ModelMapper {
        fun fromDownloadedSong(song : DownloadedSong) = Song(
            songId = song.songId,
            genres = listOf(),
            songName = song.songName,
            songLength = "",
            artwork = song.imagePath,
            description = "",
            previewPath = song.songPath,
            path = song.songPath,
            likes = 0,
            streams = 0

        )
        fun fromRecentSearch(recent : RecentSongSearch) =  Song(
            songId = recent.songId,           
            genres = listOf(),
            songName = recent.songName,
            songLength = recent.songLength,
            artwork = recent.artwork,
            description = recent.description,
            previewPath = recent.previewPath,
            path = recent.path,
            likes = recent.likes,
            streams = recent.streams

        )
        fun toRecentSearch(song : Song) = RecentSongSearch(
            songId = song.songId,
            songName = song.songName,
            songLength = song.songLength,
            artwork = song.artwork,
            description = song.description,
            previewPath = song.previewPath,
            path = song.path,
            likes = song.likes,
            streams = song.streams,
            artistName = song.artistName
        )
        fun fromGenreGraph(song :  GetGenreSongsPaginatedQuery.SongsPaginated) =
            Song(
                songName = song.name,
                songId = song.id,
                genres = listOf(),
                songLength = "",
                artistName = "Apostle Ziba",
                artwork = song.artworkPath,
                previewPath = song.path,
                path = song.path,
                likes = 0,
                userLikes = song.likes?.map { like ->
                    like?.userId ?: ""
                } ?: emptyList(),
                streams = 0
            )
        fun fromTopSongsGraph(song :  GetTrendingSongsPaginatedQuery.TrendingSongsPaginated) =
            Song(
                songName = song.name,
                songId = song.id,
                genres = listOf(),
                songLength = "",
                artistName = "Apostle Ziba",
                artwork = song.artworkPath,
                previewPath = song.path,
                path = song.path,
                likes = 0,
                userLikes = song.likes?.map { like ->
                    like?.userId ?: ""
                } ?: emptyList(),
                streams = 0
            )
      
        fun fromLikedSongsGraph(song :  GetUserLikedSongsPaginatedQuery.LikedSongsPaginated) =
            Song(
                songName = song.name,
                songId = song.id,
                genres = listOf(),
                songLength = "",
                artwork = song.artworkPath,
                previewPath = song.path,
                path = song.path,
                likes = 0,
                userLikes = song.likes?.map { like ->
                    like?.userId ?: ""
                } ?: emptyList(),
                streams = 0
            )
        fun toSongDTO(song : Song) =
            com.fov.domain.models.music.song.Song(
                name = song.songName,
                id = song.songId,
                path = song.path,
                artworkPath = song.artwork,
                previewPath = song.previewPath,
                description = song.description,               
                songGenres = listOf(),
                songLikes = listOf(),
                songStreams = listOf()

            )
        fun from(song: com.fov.domain.models.music.song.Song) =
            Song(
                songId = song.id,              
                genres = song.songGenres.takeIf{
                    it.isNotEmpty()
                }?.map{ genre ->
                    genre.name

                },
                songName = song.name,
                artwork  = song.artworkPath,
                description = song.description,
                previewPath = song.previewPath,
                path = song.path,
                likes = song.songLikes.size,
                streams = song.songStreams.size
            )
    }
}






