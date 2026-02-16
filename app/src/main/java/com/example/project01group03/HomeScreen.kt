package com.example.project01group03

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.project01group03.API.RetrofitClient
import kotlinx.coroutines.launch


@Composable
fun HomeScreen(
    onLogout: () -> Unit,
    onArtistSearch: (artistId: Long, artistName: String) -> Unit,
    onNavigateToCollection: () -> Unit
) {
    val scope = rememberCoroutineScope()
    var artistName by remember { mutableStateOf("No artist loaded") }
    var artistImageUrl by remember { mutableStateOf<String?>(null) }
    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Welcome to Vinyl Collector!", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        // My Collection button
        Button(
            onClick = onNavigateToCollection,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("My Collection")
        }
        Spacer(modifier = Modifier.height(24.dp))

        // Search UI
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search for an artist") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            if (searchQuery.isNotBlank()) {
                scope.launch {
                    try {
                        val response = RetrofitClient.discogsApiService.searchArtists(
                            query = searchQuery,
                            page = 1,
                            perPage = 1
                        )
                        response.results.firstOrNull()?.let { artist ->
                            onArtistSearch(artist.id, artist.title)
                        }
                    } catch (e: Exception) {
                        Log.e("HomeScreen", "Error searching for artist: ", e)
                    }
                }
            }
        }) {
            Text("Search")
        }
        Spacer(modifier = Modifier.height(24.dp))

        // Random Artist UI
        Text(text = "Discover Random Artist")
        Spacer(modifier = Modifier.height(12.dp))

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(artistImageUrl)
                .crossfade(true)
                .build(),
            contentDescription = "Artist image",
            modifier = Modifier.size(220.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))
        Text(text = artistName, style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = {
            scope.launch {
                try {
                    val response = RetrofitClient.discogsApiService.searchArtists(
                        query = "",
                        page = (1..1000).random() // Increased range for more variety
                    )
                    response.results.firstOrNull()?.let {
                        artistName = it.title
                        // Artist search results use 'thumb' for the image. 'coverImage' is for releases.
                        artistImageUrl = it.thumb
                    }
                } catch (e: Exception) {
                    Log.e("HomeScreen", "Error getting random artist: ", e)
                }
            }
        }) {
            Text("Get Random Artist")
        }

        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = onLogout) {
            Text("Logout")
        }
    }
}