package com.fov.sermons.pagination

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fov.common_ui.utils.constants.SongRequestType
import com.fov.domain.interactors.music.MusicInteractor
import com.fov.domain.models.music.song.SongsResult
import com.fov.sermons.models.Song

class SongsSource constructor(
    private val musicInteractor: MusicInteractor,
    private val songRequestType: SongRequestType,
    private  val search : String? = null,
    private val genreId: String? = null,
    private val userId  :  String? = null,
    private val year: Int? = null
) : PagingSource<Int, Song>()  {
    override fun getRefreshKey(state: PagingState<Int, Song>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Song> {
        return try {
            Log.d("LoadByYear", "year ${year}")
            val nextPage = params.key ?: 1
            var songResult: SongsResult? = null
            if (genreId != null) {
                if (songRequestType == SongRequestType.GENRE_SONGS) {
                    val result = musicInteractor.getGenreSongsGraph(genreId, nextPage)
                    val songs = result?.songsPaginated?.map { s ->
                        Song.ModelMapper.fromGenreGraph(s!!)

                    }
                    if (songs != null) {
                        LoadResult.Page(
                            data = songs!!,
                            prevKey = if (nextPage == 1) null else nextPage - 1,
                            nextKey = nextPage.plus(1)
                        )
                    } else {
                        LoadResult.Error(Exception(""))
                    }
                }

                else {
                    LoadResult.Error(Exception(""))
                }
            }

            else if(userId != null){
                if (songRequestType == SongRequestType.LIKED_SONGS) {
                    val result = musicInteractor.getUserLikedSongsPaginated(userId,nextPage)
                    val songs = result?.likedSongsPaginated?.map { s ->
                        Song.ModelMapper.fromLikedSongsGraph(s!!)

                    }
                    if (songs != null) {
                        LoadResult.Page(
                            data = songs!!,
                            prevKey = if (nextPage == 1) null else nextPage - 1,
                            nextKey = nextPage.plus(1)
                        )
                    } else {
                        LoadResult.Error(Exception(""))
                    }
                } else {
                    LoadResult.Error(Exception(""))
                }

            }
            else{
                    if (search != null) {
                        songResult = musicInteractor.searchSongs(search, nextPage)
                        when {
                            songResult != null -> {

                                LoadResult.Page(
                                    data = songResult!!.songs.map { song ->
                                        Song.ModelMapper.from(song)
                                    },
                                    prevKey = if (nextPage == 1) null else nextPage - 1,
                                    nextKey = nextPage.plus(1)
                                )


                            }
                            else -> {
                                LoadResult.Error(Exception(""))
                            }
                        }
                    } else {
                        if (songRequestType == SongRequestType.RECENT_SEARCH) {
                            val res = musicInteractor
                                .recentSongSearches()
                                .map { recent ->
                                    Song.ModelMapper.fromRecentSearch(recent)
                                }
                            LoadResult.Page(
                                data = res,
                                prevKey = if (nextPage == 1) null else nextPage - 1,
                                nextKey = nextPage.plus(1)
                            )
                        }
                        else if (songRequestType == SongRequestType.YEAR_SONGS && year != null) {
                            Log.d("LoadByYear", "${SongRequestType.YEAR_SONGS}")
                            val result = musicInteractor.getSongsByYearGraph(year,nextPage)
                            val songs = result?.songsByYearPaginated?.map { s ->
                                Song.ModelMapper.fromYearGraph(s!!)

                            }
                            if (songs != null) {
                                Log.d("LoadByYear", "Within")
                                LoadResult.Page(
                                    data = songs!!,
                                    prevKey = if (nextPage == 1) null else nextPage - 1,
                                    nextKey = nextPage.plus(1)
                                )
                            } else {
                                Log.d("LoadByYearError", "Error")
                                LoadResult.Error(Exception(""))
                            }
                        }
                        else {
                            when (songRequestType) {
                                SongRequestType.TOP_SONGS -> {
                                    songResult = musicInteractor.getTopSongs(nextPage)
                                }
                                SongRequestType.FOR_YOU -> {
                                    songResult = musicInteractor.getForYouSongs(nextPage)
                                }

                                else -> {

                                }
                            }
                            if (songResult != null) {

                                LoadResult.Page(
                                    data = songResult!!.songs.map { song ->
                                        Song.ModelMapper.from(song)
                                    },
                                    prevKey = if (nextPage == 1) null else nextPage - 1,
                                    nextKey = nextPage.plus(1)
                                )


                            } else {
                                LoadResult.Error(Exception(""))
                            }
                        }
                    }
        }


        }
        catch (e: Exception) {
            Log.d("LoadByYearError", "${e.message}")
            LoadResult.Error(e)
        }
    }

}