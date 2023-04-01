package com.fov.sermons.models

import com.fov.domain.BuildConfig
import com.fov.domain.database.models.DownloadedAlbum
import com.fov.domain.genres.GetGenreAlbumsPaginatedQuery
import com.fov.domain.music.GetUserLikedAlbumsPaginatedQuery


data class Album(
    val albumId : String,
    val artistName : String = "Apostle Ziba",
    val genres: List<String>? = listOf(),
    var artwork : String,
    val albumName : String,
    val description:String = "",
    var path : String = "",
    val likes : Int = 0,
    val userLikes : List<String> = emptyList(),
    val streams : Int = 0,
    val songs : List<Song> = emptyList()

){
    object ModelMapper {


        fun fromDownloadedAlbum(album : DownloadedAlbum) = Album(
            albumId = album.albumId,
            genres = listOf(),
            artwork = album.imagePath,
            albumName = album.albumName,
            description = "This is a good series",
            path = "",
            likes = 0,
            streams = 0,
            songs = listOf()

        )
        fun from(album: com.fov.domain.models.music.album.Album) = Album(
            albumId = album.id,
            genres = album.seriesGenres.takeIf{
                it.isNotEmpty()
            }?.map{ genre ->
                genre.name

            },
            songs = album.sermons.map { song ->
                 Song(
                     songId = song.id,
                     genres = song.songGenres.map{
                                                 it.name
                     },
                     songName = song.name,
                     songLength = "",
                     artwork = "${BuildConfig.FOV_URL}/${song.artworkPath}",
                     description = song.description,
                     previewPath = "${BuildConfig.FOV_URL}/${song.previewPath}",
                     path = "${BuildConfig.FOV_URL}/${song.path}",
                     likes = song.likes.size,
                     streams = song.streams.size

                 )
            },
            artwork = "${BuildConfig.FOV_URL}/${album.artworkPath}",
            albumName = album.name,
            description = album.description,
            path = "${BuildConfig.FOV_URL}${album.path}",
            likes = album.likes.size,
            streams = album.streams.size
        )
        fun  fromLikedAlbumsGraph(album : GetUserLikedAlbumsPaginatedQuery.LikedAlbumsPaginated) =
            Album(
                albumId = album.id,
                genres = listOf(),
                artwork = "${BuildConfig.FOV_URL}/${album.artworkPath}",
                albumName = album.name,
                description = album.name,
                path = "${BuildConfig.FOV_URL}/${album.path}",
                userLikes = album.likes?.map { like ->
                    like?.userId ?: ""
                } ?: emptyList(),
                likes = 0,
                streams = 0

            )
        fun fromGenreGraph(album : GetGenreAlbumsPaginatedQuery.AlbumsPaginated) =
            Album(
                albumId = album.id,
                genres = listOf(),
                artwork = "${BuildConfig.FOV_URL}/${album.artworkPath}",
                albumName = album.name,
                description = album.name,
                path = "${BuildConfig.FOV_URL}/${album.path}",
                userLikes = album.likes?.map { like ->
                    like?.userId ?: ""

                } ?: emptyList(),
                likes = 0,
                streams = 0

            )

    }
}
