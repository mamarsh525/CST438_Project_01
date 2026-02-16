package com.example.project01group03

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.project01group03.API.RetrofitClient
import com.example.project01group03.API.VinylRelease
import com.example.project01group03.API.isVinyl
import com.example.project01group03.data.UserCollectionItem
import com.example.project01group03.data.UserCollectionItemDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtistScreen(
    artistId: Long,
    artistName: String,
    userId: Int,
    collectionDao: UserCollectionItemDao,
    onBackToHome: () -> Unit
) {
    var allReleases by remember { mutableStateOf<List<VinylRelease>>(emptyList()) }
    var vinylReleases by remember { mutableStateOf<List<VinylRelease>>(emptyList()) }
    var artistImageUrl by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var debugInfo by remember { mutableStateOf<String?>(null) }
    var collectionStatus by remember { mutableStateOf<Map<Long, Boolean>>(emptyMap()) }

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    // Check which releases are in collection
    LaunchedEffect(vinylReleases) {
        if (vinylReleases.isNotEmpty()) {
            withContext(Dispatchers.IO) {
                val statusMap = mutableMapOf<Long, Boolean>()
                vinylReleases.forEach { release ->
                    statusMap[release.id] = collectionDao.isReleaseInCollection(userId, release.id)
                }
                collectionStatus = statusMap
            }
        }
    }

    LaunchedEffect(artistId) {
        try {
            Log.d("ArtistScreen", "=== Starting fetch for artist ID: $artistId ===")

            // Fetch artist details for the image
            val artistDetails = RetrofitClient.discogsApiService.getArtistDetails(artistId)
            artistImageUrl = artistDetails.images?.firstOrNull()?.uri
            Log.d("ArtistScreen", "Artist image URL: $artistImageUrl")

            // Fetch artist releases
            val releasesResponse = RetrofitClient.discogsApiService.getArtistReleases(artistId)
            allReleases = releasesResponse.releases

            Log.d("ArtistScreen", "=== RAW API RESPONSE ===")
            Log.d("ArtistScreen", "Total releases received: ${allReleases.size}")

            // Log first few releases to see the format field
            allReleases.take(5).forEachIndexed { index, release ->
                Log.d("ArtistScreen", "Release $index:")
                Log.d("ArtistScreen", "  Title: ${release.title}")
                Log.d("ArtistScreen", "  Format: ${release.format}")
                Log.d("ArtistScreen", "  Year: ${release.year}")
                Log.d("ArtistScreen", "  Type: ${release.type}")
            }

            // Filter for vinyl releases
            vinylReleases = allReleases.filter { it.isVinyl() }

            Log.d("ArtistScreen", "=== FILTERING RESULTS ===")
            Log.d("ArtistScreen", "Vinyl releases found: ${vinylReleases.size}")

            // Create debug info for display
            debugInfo = buildString {
                appendLine("Total releases: ${allReleases.size}")
                appendLine("Vinyl releases: ${vinylReleases.size}")
                appendLine()
                appendLine("Sample formats from first 3 releases:")
                allReleases.take(3).forEach { release ->
                    appendLine("- ${release.title}: '${release.format}'")
                }
            }

        } catch (e: Exception) {
            Log.e("ArtistScreen", "=== ERROR ===")
            Log.e("ArtistScreen", "Error type: ${e.javaClass.simpleName}")
            Log.e("ArtistScreen", "Error message: ${e.message}")
            Log.e("ArtistScreen", "Stack trace:", e)
            errorMessage = "Error: ${e.message}"
        } finally {
            isLoading = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Artist: $artistName") },
                navigationIcon = {
                    IconButton(onClick = onBackToHome) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back to Home"
                        )
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
            if (isLoading) {
                CircularProgressIndicator()
                Text("Loading releases...")
            } else if (errorMessage != null) {
                Text(
                    text = errorMessage!!,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyLarge
                )
            } else {
                // Show artist image
                artistImageUrl?.let { url ->
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(url)
                            .crossfade(true)
                            .build(),
                        contentDescription = "Artist image",
                        modifier = Modifier.size(220.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Show vinyl releases
                Text(
                    text = "Vinyl Releases (${vinylReleases.size}):",
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(8.dp))

                if (vinylReleases.isNotEmpty()) {
                    LazyColumn {
                        items(vinylReleases) { release ->
                            VinylReleaseItem(
                                release = release,
                                isInCollection = collectionStatus[release.id] == true,
                                onAddToCollection = {
                                    scope.launch(Dispatchers.IO) {
                                        try {
                                            val collectionItem = UserCollectionItem(
                                                ownerId = userId,
                                                releaseId = release.id,
                                                artistName = artistName,
                                                title = release.title,
                                                year = release.year,
                                                format = release.format,
                                                thumbUrl = release.thumb
                                            )
                                            collectionDao.insert(collectionItem)

                                            // Update status
                                            collectionStatus = collectionStatus + (release.id to true)

                                            withContext(Dispatchers.Main) {
                                                snackbarHostState.showSnackbar("Added to collection!")
                                            }
                                        } catch (e: Exception) {
                                            Log.e("ArtistScreen", "Error adding to collection", e)
                                            withContext(Dispatchers.Main) {
                                                snackbarHostState.showSnackbar("Error adding to collection")
                                            }
                                        }
                                    }
                                }
                            )
                        }
                    }
                } else {
                    Text("No vinyl releases found.")
                }
            }
        }
    }
}