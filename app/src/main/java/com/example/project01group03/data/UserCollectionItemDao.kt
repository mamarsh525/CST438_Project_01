package com.example.project01group03.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserCollectionItemDao {
    @Insert
    suspend fun insert(item: UserCollectionItem)

    @Query("SELECT * FROM user_collection WHERE ownerId = :userId")
    fun getCollectionForUser(userId: Int): Flow<List<UserCollectionItem>>
}
