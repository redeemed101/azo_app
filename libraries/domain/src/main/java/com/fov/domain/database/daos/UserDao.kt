package com.fov.domain.database.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.fov.domain.database.models.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM User")
    fun getUser(): Flow<User>

    //@Query("SELECT * FROM User WHERE id = :id")
    //fun getUserById(id: String): Flow<User>

    @Query("SELECT * FROM User")
    fun getUsers(): Flow<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg user: User)

    @Query("DELETE FROM User")
    suspend fun deleteAll() : Int

    @Delete
    suspend fun delete(user: User) : Int
}