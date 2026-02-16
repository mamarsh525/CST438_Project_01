package com.example.project01group03.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Represents a single music release in a user's collection.
 */
@Entity(
    tableName = "user_collection",
    indices = [Index("ownerId"), Index("releaseId")],
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["ownerId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class UserCollectionItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val ownerId: Int,

    // Discogs data
    val releaseId: Long,
    val artistName: String,
    val title: String,
    val year: Int?,
    val format: String?,
    val thumbUrl: String?
)