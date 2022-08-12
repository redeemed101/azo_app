package com.fov.domain.database.daos

import androidx.room.*
import com.fov.domain.database.models.RecentSongSearch

@Dao
interface RecentSongSearchDao {

    @Query("SELECT * FROM RecentSongSearch ORDER BY dbId DESC")
    fun getSongs():  List<RecentSongSearch>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg user: RecentSongSearch)

    @Query("DELETE FROM RecentSongSearch")
    suspend fun deleteAll()

    @Query("DELETE FROM RecentSongSearch where dbId NOT IN (SELECT dbId from RecentSongSearch ORDER BY dbId DESC LIMIT 10)")
    suspend fun deleteMoreThanLimit()

    @Delete
    suspend fun delete(user: RecentSongSearch)
}