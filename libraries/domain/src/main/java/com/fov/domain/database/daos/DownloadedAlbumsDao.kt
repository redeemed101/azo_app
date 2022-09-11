package com.fov.domain.database.daos

import androidx.paging.DataSource
import androidx.room.*
import com.fov.domain.database.models.DownloadedAlbum
import kotlinx.coroutines.flow.Flow

@Dao
interface DownloadedAlbumsDao {

    @Query("SELECT * FROM DownloadedAlbum ORDER BY dbId DESC")
    fun getDownloadedAlbums():  List<DownloadedAlbum>

    @Query("SELECT * FROM DownloadedAlbum ORDER BY dbId DESC")
    fun getDownloadedAlbumsPaginated(): DataSource.Factory<Int, DownloadedAlbum>

    @Query("DELETE FROM DownloadedAlbum")
    suspend fun deleteAll() : Int

    @Query("DELETE FROM DownloadedAlbum WHERE albumId= :id")
    suspend fun deleteAlbum(id : String) : Int

    @Delete
    suspend fun delete(album : DownloadedAlbum) : Int

    @Insert
    fun insertAll(vararg albums: DownloadedAlbum) : List<Long>

    @Update
    fun update(vararg album: DownloadedAlbum) : Int

    @Query("SELECT EXISTS(SELECT * FROM DownloadedAlbum WHERE albumId= :id)")
    fun doesAlbumExist(id : String) : Flow<Boolean>

    @Query("SELECT albumPath FROM DownloadedAlbum WHERE albumId= :id")
    fun getAlbumPath(id : String) : Flow<String>

}