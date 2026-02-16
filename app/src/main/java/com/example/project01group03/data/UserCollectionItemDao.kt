package com.example.project01group03.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserCollectionItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: UserCollectionItem)

    @Delete
    suspend fun delete(item: UserCollectionItem)

    @Query("SELECT * FROM user_collection WHERE ownerId = :userId ORDER BY id DESC")
    fun getCollectionForUser(userId: Int): Flow<List<UserCollectionItem>>

    @Query("SELECT EXISTS(SELECT 1 FROM user_collection WHERE ownerId = :userId AND releaseId = :releaseId LIMIT 1)")
    suspend fun isReleaseInCollection(userId: Int, releaseId: Long): Boolean

    @Query("DELETE FROM user_collection WHERE ownerId = :userId AND releaseId = :releaseId")
    suspend fun deleteByReleaseId(userId: Int, releaseId: Long)
}