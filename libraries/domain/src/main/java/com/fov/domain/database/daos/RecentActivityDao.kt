package com.fov.domain.database.daos

import androidx.room.*
import com.fov.domain.database.models.RecentActivity


@Dao
interface RecentActivityDao {
    @Query("SELECT * FROM RecentActivity ORDER BY dbId DESC")
    fun getRecentActivities():  List<RecentActivity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg user: RecentActivity)

    @Query("DELETE FROM RecentActivity")
    suspend fun deleteAll()

    @Query("DELETE FROM RecentActivity where dbId NOT IN (SELECT dbId from RecentActivity ORDER BY dbId DESC LIMIT 10)")
    suspend fun deleteMoreThanLimit()

    @Delete
    suspend fun delete(user: RecentActivity)
}