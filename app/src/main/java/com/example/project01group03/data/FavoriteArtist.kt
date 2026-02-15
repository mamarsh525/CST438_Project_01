package com.example.project01group03.data
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteArtist(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: Int,
    val artistName: String,
    val imageUrl: String?

)