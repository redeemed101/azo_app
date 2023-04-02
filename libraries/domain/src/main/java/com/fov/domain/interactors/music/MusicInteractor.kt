package com.fov.domain.interactors.music

import com.fov.domain.database.models.RecentActivity
import com.fov.domain.database.models.RecentSongSearch
import com.fov.domain.models.music.album.AlbumsResult
import com.fov.domain.models.music.song.SermonsResult
import com.fov.domain.remote.apollo.music.ApolloMusicService
import com.fov.domain.repositories.music.SermonRepository
import com.fov.domain.utils.constants.QueryConstants
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers


class MusicInteractor constructor(
    private val sermonRepository: SermonRepository,
    private val musicGraphQLService: ApolloMusicService
) {


    suspend fun getTopSongs(
        token: String,
        page : Int
    ) = withContext(Dispatchers.IO) { sermonRepository.getTopSongs(token,page) }

    suspend fun getTopAlbums(token: String,page: Int): AlbumsResult? = withContext(Dispatchers.IO) {
        sermonRepository.getTopAlbums(token,page)
    }

    suspend fun getForYouSongs(token: String,page: Int) : SermonsResult? = withContext(Dispatchers.IO) {
        sermonRepository.getForYouSongs(token,page)
    }


    suspend fun getAlbumsGraph(token: String,page:Int)  = withContext(Dispatchers.IO){
        musicGraphQLService.getAlbumsPaginated(token,page,QueryConstants.NUM_ROWS)
    }
    suspend fun getAlbumGraph(token: String,id:String) =  withContext(Dispatchers.IO){
        musicGraphQLService.getAlbum(token,id)
    }



    suspend fun  getGenresGraph(token: String,) = withContext(Dispatchers.IO) {
        musicGraphQLService.getGenres(token)
    }

    suspend fun getGenreAlbumsGraph(token: String,id:String,page:Int)= withContext(Dispatchers.IO) {
        musicGraphQLService.getGenreAlbumsPaginated(token,id,page, QueryConstants.NUM_ROWS)
    }



    suspend fun getSong(token: String,id: String)= withContext(Dispatchers.IO) {
        sermonRepository.getSong(token,id)
    }


    suspend fun getGenreSongsGraph(token: String,id:String,page:Int) = withContext(Dispatchers.IO) {
        musicGraphQLService.getGenreSongsPaginated(token,id,page, QueryConstants.NUM_ROWS)
    }

    suspend fun getSongsGraph(token: String,page:Int) = withContext(Dispatchers.IO) {
        musicGraphQLService.getSongsPaginated(token,page, QueryConstants.NUM_ROWS)
    }
    suspend fun getTrendingSongsGraph(token: String,page:Int) = withContext(Dispatchers.IO) {
        musicGraphQLService.getTrendingSongsPaginated(token,page, QueryConstants.NUM_ROWS)
    }

    suspend fun getSongsByYearGraph(token: String,year:Int,page:Int) = withContext(Dispatchers.IO) {
        musicGraphQLService.getSongsByYearPaginated(token,year,page, QueryConstants.NUM_ROWS)
    }

    suspend fun  getGenreGraph(token: String,id : String) =  withContext(Dispatchers.IO) {
        musicGraphQLService.getGenre(token,id)

    }


    suspend fun getUserLikedAlbumsPaginated(token: String,id:String,page: Int)= withContext(Dispatchers.IO) {
        musicGraphQLService.getUserLikedAlbumsPaginated(token,id,page,QueryConstants.NUM_ROWS)
    }
    suspend fun getUserLikedSongsPaginated(token: String,id:String,page: Int)= withContext(Dispatchers.IO) {
        musicGraphQLService.getUserLikedSongsPaginated(token,id,page,QueryConstants.NUM_ROWS)
    }

    suspend fun likeAlbum(albumId : String) = withContext(Dispatchers.IO) {

    }
    suspend fun likeSong(songId : String) = withContext(Dispatchers.IO) {

    }



    suspend fun searchSongs(token: String,search: String, page: Int)= withContext(Dispatchers.IO) {
        sermonRepository.searchSongs(token,search,page)
    }

    suspend fun recentSongSearches()  = withContext(Dispatchers.IO) {
        sermonRepository.recentSongSearches()
    }
    suspend fun insertRecentSongSearch(song: RecentSongSearch){
        sermonRepository.insertRecentSongSearch(song)
    }

    suspend fun deleteRecentSongSearch() = sermonRepository.deleteRecentSongSearch()


    suspend fun recentActivities()  = withContext(Dispatchers.IO) {
        sermonRepository.recentActivities()
    }
    suspend fun insertRecentActivity(activity: RecentActivity){
        sermonRepository.insertRecentActivity(activity)
    }

    suspend fun deleteRecentActivity() = sermonRepository.deleteRecentActivity()


}
