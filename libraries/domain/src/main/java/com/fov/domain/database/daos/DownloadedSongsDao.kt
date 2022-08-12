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
    suspend fun deleteAll()

    @Delete
    suspend fun delete(song : DownloadedSong)

    @Query("DELETE FROM DownloadedSong WHERE songId=:id")
    suspend fun deleteSong(id : String)

    @Insert
    fun insertAll(vararg songs: DownloadedSong)

    @Update
    fun update(vararg song: DownloadedSong)

    @Query("SELECT EXISTS(SELECT * FROM DownloadedSong WHERE songId= :id)")
    fun doesSongExist(id : String) : Flow<Boolean>

}