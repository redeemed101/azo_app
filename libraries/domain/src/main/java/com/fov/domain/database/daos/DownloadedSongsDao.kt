package com.fov.domain.database.daos

import androidx.paging.DataSource
import androidx.room.*
import com.fov.domain.database.models.DownloadedSong
import kotlinx.coroutines.flow.Flow


@Dao
interface DownloadedSongsDao {

    @Query("SELECT * FROM DownloadedSong ORDER BY dbId DESC")
    fun getDownloadedSongs():  List<DownloadedSong>

    @Query("SELECT * FROM DownloadedSong ORDER BY dbId DESC")
    fun getDownloadedSongsPaginated(): DataSource.Factory<Int, DownloadedSong>

    @Query("DELETE FROM DownloadedSong")
    suspend fun deleteAll() : Int

    @Delete
    suspend fun delete(song : DownloadedSong) : Int

    @Query("DELETE FROM DownloadedSong WHERE songId=:id")
    suspend fun deleteSong(id : String) : Int

    @Insert
    fun insertAll(vararg songs: DownloadedSong) : List<Long>

    @Update
    fun update(vararg song: DownloadedSong) : Int

    @Query("SELECT EXISTS(SELECT * FROM DownloadedSong WHERE songId= :id)")
    fun doesSongExist(id : String) : Flow<Boolean>

    @Query("SELECT songPath FROM DownloadedSong WHERE songId= :id")
    fun getSongPath(id : String) : Flow<String>

}