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
        page : Int
    ) = withContext(Dispatchers.IO) { sermonRepository.getTopSongs(page) }

    suspend fun getTopAlbums(page: Int): AlbumsResult? = withContext(Dispatchers.IO) {
        sermonRepository.getTopAlbums(page)
    }

    suspend fun getForYouSongs(page: Int) : SermonsResult? = withContext(Dispatchers.IO) {
        sermonRepository.getForYouSongs(page)
    }


    suspend fun getAlbumsGraph(page:Int)  = withContext(Dispatchers.IO){
        musicGraphQLService.getAlbumsPaginated(page,QueryConstants.NUM_ROWS)
    }
    suspend fun getAlbumGraph(id:String) =  withContext(Dispatchers.IO){
        musicGraphQLService.getAlbum(id)
    }



    suspend fun  getGenresGraph() = withContext(Dispatchers.IO) {
        musicGraphQLService.getGenres()
    }

    suspend fun getGenreAlbumsGraph(id:String,page:Int)= withContext(Dispatchers.IO) {
        musicGraphQLService.getGenreAlbumsPaginated(id,page, QueryConstants.NUM_ROWS)
    }



    suspend fun getSong(id: String)= withContext(Dispatchers.IO) {
        sermonRepository.getSong(id)
    }


    suspend fun getGenreSongsGraph(id:String,page:Int) = withContext(Dispatchers.IO) {
        musicGraphQLService.getGenreSongsPaginated(id,page, QueryConstants.NUM_ROWS)
    }

    suspend fun getSongsGraph(page:Int) = withContext(Dispatchers.IO) {
        musicGraphQLService.getSongsPaginated(page, QueryConstants.NUM_ROWS)
    }
    suspend fun getTrendingSongsGraph(page:Int) = withContext(Dispatchers.IO) {
        musicGraphQLService.getTrendingSongsPaginated(page, QueryConstants.NUM_ROWS)
    }

    suspend fun getSongsByYearGraph(year:Int,page:Int) = withContext(Dispatchers.IO) {
        musicGraphQLService.getSongsByYearPaginated(year,page, QueryConstants.NUM_ROWS)
    }

    suspend fun  getGenreGraph(id : String) =  withContext(Dispatchers.IO) {
        musicGraphQLService.getGenre(id)

    }


    suspend fun getUserLikedAlbumsPaginated(id:String,page: Int)= withContext(Dispatchers.IO) {
        musicGraphQLService.getUserLikedAlbumsPaginated(id,page,QueryConstants.NUM_ROWS)
    }
    suspend fun getUserLikedSongsPaginated(id:String,page: Int)= withContext(Dispatchers.IO) {
        musicGraphQLService.getUserLikedSongsPaginated(id,page,QueryConstants.NUM_ROWS)
    }

    suspend fun likeAlbum(albumId : String) = withContext(Dispatchers.IO) {

    }
    suspend fun likeSong(songId : String) = withContext(Dispatchers.IO) {

    }



    suspend fun searchSongs(search: String, page: Int)= withContext(Dispatchers.IO) {
        sermonRepository.searchSongs(search,page)
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
