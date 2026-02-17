package com.example.project01group03

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.project01group03.data.UserCollectionItemDao

@Composable
fun StatsScreen(
    userId: Int,
    collectionDao: UserCollectionItemDao
) {
    val collectionItems by collectionDao
        .getCollectionForUser(userId)
        .collectAsState(initial = emptyList())

    if (collectionItems.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("No data available yet.")
        }
        return
    }

    val totalReleases = collectionItems.size
    val uniqueArtists = collectionItems.map { it.artistName }.distinct().size

    val years = collectionItems.mapNotNull { it.year }
    val oldestYear = years.minOrNull()
    val newestYear = years.maxOrNull()

    val formatCounts = collectionItems
        .mapNotNull { it.format }
        .groupingBy { it }
        .eachCount()
        .toList()
        .sortedByDescending { it.second }

    val topArtists = collectionItems
        .groupingBy { it.artistName }
        .eachCount()
        .toList()
        .sortedByDescending { it.second }
        .take(3)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Collection Stats",
                style = MaterialTheme.typography.headlineMedium
            )
        }

        item {
            StatsOverviewCard(
                totalReleases = totalReleases,
                uniqueArtists = uniqueArtists,
                oldestYear = oldestYear,
                newestYear = newestYear
            )
        }

        item {
            Text(
                text = "Top Artists",
                style = MaterialTheme.typography.titleLarge
            )
        }

        items(topArtists) { (artist, count) ->
            StatRow(label = artist, value = "$count releases")
        }

        item {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Format Breakdown",
                style = MaterialTheme.typography.titleLarge
            )
        }

        items(formatCounts) { (format, count) ->
            StatRow(label = format, value = "$count")
        }
    }
}

@Composable
fun StatsOverviewCard(
    totalReleases: Int,
    uniqueArtists: Int,
    oldestYear: Int?,
    newestYear: Int?
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Total Releases: $totalReleases")
            Text("Unique Artists: $uniqueArtists")
            Text("Oldest Release: ${oldestYear ?: "N/A"}")
            Text("Newest Release: ${newestYear ?: "N/A"}")
        }
    }
}

@Composable
fun StatRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label)
        Text(value, style = MaterialTheme.typography.bodyMedium)
    }
}
