package com.fov.domain.remote.music

import com.fidarr.domain.models.music.album.AlbumsResult
import com.fidarr.domain.models.music.artist.ArtistsResult
import com.fidarr.domain.models.music.playlist.MoodResult
import com.fidarr.domain.models.music.song.Song
import com.fidarr.domain.models.music.song.SongsResult
import com.fidarr.domain.models.users.UserStoryResult
import com.fidarr.domain.models.users.UsersWithStoriesResult
import com.fidarr.domain.utils.constants.QueryConstants
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

class MusicKtorService constructor(private val client: HttpClient)  {

    suspend fun getArtistsWithNewMusic(page : Int) : UsersWithStoriesResult =  client.request("music/Artist/newSongs?page=${page}") {
        method = HttpMethod.Get
        headers {
            append("Content-Type", "application/json")
        }
    }
    suspend fun getArtistsNewMusic(artistId: String) : UserStoryResult =  client.request("music/Artist/newSongs/${artistId}") {
        method = HttpMethod.Get
        headers {
            append("Content-Type", "application/json")
        }
    }

    suspend fun getToSongs(page: Int): SongsResult? =  client.request("music/Song/topSongs?page=${page}") {
        method = HttpMethod.Get
        headers {
            append("Content-Type", "application/json")
        }
    }

    suspend fun getTopAlbums(page: Int): AlbumsResult?  =  client.request("music/Album/topAlbums?page=${page}&size=${QueryConstants.NUM_ROWS}") {
        method = HttpMethod.Get
        headers {
            append("Content-Type", "application/json")
        }
    }

    suspend fun getForYouSongs(page: Int): SongsResult? =  client.request("music/Song/forYou?page=${page}&size=${QueryConstants.NUM_ROWS}") {
        method = HttpMethod.Get
        headers {
            append("Content-Type", "application/json")
        }
    }

    suspend fun getArtists(page: Int): ArtistsResult?  =  client.request("music/Artist/page?page=${page}&size=${QueryConstants.NUM_ROWS}") {
        method = HttpMethod.Get
        headers {
            append("Content-Type", "application/json")
        }
    }

    suspend fun searchArtists(search: String, page: Int): ArtistsResult?  =  client.request("music/Artist/search/${search}?page=${page}&size=${QueryConstants.NUM_ROWS}") {
        method = HttpMethod.Get
        headers {
            append("Content-Type", "application/json")
        }
    }

    suspend fun searchSongs(search: String, page: Int): SongsResult? =  client.request("music/Song/search?search=${search}?page=${page}&size=${QueryConstants.NUM_ROWS}") {
        method = HttpMethod.Get
        headers {
            append("Content-Type", "application/json")
        }
    }

    suspend fun getMoods(page: Int): MoodResult? = client.request("music/Playlist/moods"){
        method = HttpMethod.Get
        headers {
            append("Content-Type", "application/json")
        }
    }

    suspend fun getSong(id: String): Song?  = client.request("music/Song/byId/${id}"){
        method = HttpMethod.Get
        headers {
            append("Content-Type", "application/json")
        }
    }
}