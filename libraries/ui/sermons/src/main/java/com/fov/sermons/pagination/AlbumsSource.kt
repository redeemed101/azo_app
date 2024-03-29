package com.fov.sermons.pagination

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fov.common_ui.utils.constants.AlbumRequestType
import com.fov.domain.interactors.music.MusicInteractor
import com.fov.domain.models.music.album.AlbumsResult
import com.fov.sermons.models.Album

class AlbumsSource constructor(
    private val musicInteractor: MusicInteractor,
    private val albumRequestType: AlbumRequestType,
    private val accessToken: String,
    private val genreId: String? = null,
    private val userId:  String? = null,
    private val callback : () -> Unit = {}
) : PagingSource<Int, Album>() {
    override fun getRefreshKey(state: PagingState<Int, Album>): Int? {

        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Album> {
        return try {
            val nextPage = params.key ?: 1
            if(genreId != null){

               if(albumRequestType == AlbumRequestType.GENRE_ALBUMS) {

                        val result = musicInteractor.getGenreAlbumsGraph(accessToken,genreId, nextPage)
                        callback()
                        val albums = result?.albumsPaginated?.map { a ->
                            Album.ModelMapper.fromGenreGraph(a!!)
                        }
                        if(albums != null) {
                            LoadResult.Page(
                                data = albums!!,
                                prevKey = if (nextPage == 1) null else nextPage - 1,
                                nextKey = if (result.albumsPaginated.isNullOrEmpty()) null else nextPage.plus(1)
                            )
                        }
                        else{
                            LoadResult.Error(Exception(""))
                        }

                }
                else{
                   LoadResult.Error(Exception(""))
               }
            }
            else if(userId  != null){
                if(albumRequestType == AlbumRequestType.LIKED_ALBUMS) {
                    val result = musicInteractor.getUserLikedAlbumsPaginated(accessToken,userId, nextPage)
                    callback()
                    val albums = result?.likedAlbumsPaginated?.map { a ->
                        Album.ModelMapper.fromLikedAlbumsGraph(a!!)

                    }
                    if(albums != null) {
                        LoadResult.Page(
                            data = albums!!,
                            prevKey = if (nextPage == 1) null else nextPage - 1,
                            nextKey = if (result.likedAlbumsPaginated.isNullOrEmpty()) null else nextPage.plus(1)
                        )
                    }
                    else{
                        LoadResult.Error(Exception(""))
                    }

                }
                else{
                    LoadResult.Error(Exception(""))
                }
            }
            else{
                var albumResult: AlbumsResult? = null
                when (albumRequestType) {
                    AlbumRequestType.TOP_ALBUMS -> {
                        albumResult = musicInteractor.getTopAlbums(accessToken,nextPage)
                        callback()
                        Log.e("Series",albumResult.toString())
                    }
                    AlbumRequestType.ALL_ALBUMS ->{
                        musicInteractor.getAlbumsGraph(accessToken,nextPage)
                        callback()
                    }
                    else -> {

                    }
                }
                if (albumResult != null) {

                    LoadResult.Page(
                        data = albumResult!!.albums.map { album ->
                            Album.ModelMapper.from(album)
                        },
                        prevKey = if (nextPage == 1) null else nextPage - 1,
                        nextKey = if (albumResult.albums.isNullOrEmpty()) null else nextPage.plus(1)
                    )


                } else {
                    LoadResult.Error(Exception(""))
                }
            }


        }
        catch (e: Exception) {
            callback()
            LoadResult.Error(e)
        }
    }

}