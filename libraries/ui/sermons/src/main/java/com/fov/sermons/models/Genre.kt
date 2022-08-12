package com.fov.sermons.models

import com.fov.domain.genres.GetGenreQuery
import com.fov.domain.genres.GetGenresQuery

data class Genre(
    val genreId : String,
    val name : String,
    val songs : List<Song> = emptyList(),
    val albums : List<Album> = emptyList()
){
    object ModelMapper {
        fun fromGenreDTO(genre : com.fov.domain.models.music.genre.Genre) =
            Genre(
                genreId = genre.id,
                name = genre.name
            )
        fun fromGenresGraph(genre : GetGenresQuery.Genre) =
            Genre(
                genreId = genre.id(),
                name = genre.name()
            )
        fun fromGenreGraph(genre : GetGenreQuery.Data) =
            Genre(
                name = genre.genre()!!.name(),
                genreId = genre.genre()!!.id()
            )
    }
}
