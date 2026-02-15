package com.example.project01group03.data.FavoriteDao
import androidx.room.Dao
import com.example.project01group03.data.FavoriteArtist
import androidx.room.Insert
import androidx.room.Query


@Dao
interface FavoriteDao{
    @Insert
    suspend fun insert(favorite: FavoriteArtist)

    @Query("SELECT * FROM favorites WHERE userId = :userId")
    fun getFavorites(userId: Int): List<FavoriteArtist>



}