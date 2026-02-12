package com.example.project01group03.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * Represents a single music release in a user's collection.
 */
@Entity(
    tableName = "user_collection", // Changed to a more descriptive name
    // This foreign key establishes the relationship between a collection item and a user.
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"], // The User's primary key
            childColumns = ["ownerId"], // The key in this table that points to the User
            onDelete = ForeignKey.CASCADE // If a user is deleted, their collection items are also deleted.
        )
    ]
)
data class UserCollectionItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    // This ID links the item to its owner in the 'users' table.
    val ownerId: Int,

    // --- Data from the Discogs API ---
    val releaseId: Long, // The unique ID for the release from Discogs
    val title: String,
    val year: String?,
    val thumbUrl: String // URL for the thumbnail image
)
