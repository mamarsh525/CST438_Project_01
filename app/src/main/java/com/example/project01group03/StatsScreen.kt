package com.example.project01group03

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.project01group03.data.UserCollectionItemDao

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatsScreen(
    userId: Int,
    collectionDao: UserCollectionItemDao
) {
    val collectionItems by collectionDao
        .getCollectionForUser(userId)
        .collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Collection Stats") }
            )
        }
    ) { paddingValues ->

        if (collectionItems.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No collection data available yet.",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } else {

            // Calculate favorite artist (most collected)
            val artistCounts = collectionItems
                .groupingBy { it.artistName }
                .eachCount()

            val favoriteArtistEntry = artistCounts
                .maxByOrNull { it.value }

            val favoriteArtist = favoriteArtistEntry?.key ?: "N/A"
            val favoriteCount = favoriteArtistEntry?.value ?: 0

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {

                Text(
                    text = "Your Favorite Artist",
                    style = MaterialTheme.typography.headlineMedium
                )

                Card(
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Favorite Artist",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(40.dp)
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        Column {
                            Text(
                                text = favoriteArtist,
                                style = MaterialTheme.typography.titleLarge
                            )

                            Text(
                                text = "$favoriteCount releases collected",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                Divider()

                Text(
                    text = "Total Releases: ${collectionItems.size}",
                    style = MaterialTheme.typography.bodyLarge
                )

                Text(
                    text = "Unique Artists: ${artistCounts.size}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}
