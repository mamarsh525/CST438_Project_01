package com.example.project01group03

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.project01group03.API.RetrofitClient
import com.example.project01group03.data.FavoriteArtist
import com.example.project01group03.data.FavoriteDao.FavoriteDao
import kotlinx.coroutines.launch


@Composable
fun HomeScreen(userId: Int, favoriteDao: FavoriteDao, onLogout: () -> Unit) {
    //since searchArtist is a suspend function, it needs coroutine to run,
    //scope.launch allows button to start the coroutine
    //coeoutine scope runs network call in background to keep ui from freezing up
    val scope = rememberCoroutineScope()
    //artist name is what holds api call info and displays in on screen
    //remember keeps the value from being reset
    //without it, it breakes :(
    var artistName by remember { mutableStateOf("No artist loaded") }
    //Column(modifier = Modifier.padding(16.dp)) {
    var artistImageUrl by remember { mutableStateOf<String?>(null) }
    Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        //displays the random artist
        Text(text = "Artist: $artistName")
        Spacer(modifier = Modifier.height(12.dp))

        // Show the artist image (if available)
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(artistImageUrl)
                .crossfade(true)
                .build(),
            contentDescription = "Artist image",
            modifier = Modifier.size(220.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = {
            //starts the task for network call to api
            scope.launch {
                //calls discog api and gets random artist from pages 1-100 for now just to test
                val response = RetrofitClient.discogsApiService.searchArtists(
                    query = "",
                    page = (1..100).random()
                )

                val first = response.results.firstOrNull()
                artistName = first?.title ?: "No artist found"

                // Discogs search results typically expose an image URL like `cover_image`.
                // If your model uses a different property name, update this line to match.
                artistImageUrl = first?.coverImage
            }
        }) {
            Text("Get Random Artist")
        }
        Button(onClick = {
            scope.launch {
                // Only save if we actually have an artist loaded
                if (artistName != "No artist loaded" && artistName != "No artist found") {
                    val favorite = FavoriteArtist(
                        userId = userId,
                        artistName = artistName,
                        imageUrl = artistImageUrl
                    )
                    // Save to the database
                    favoriteDao.insert(favorite)
                }
            }
        }) {
            Text("Favorite ️")
        }

        Spacer(modifier = Modifier.height(24.dp))
        Text(text = "Welcome to the Home Screen!")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onLogout) {
            Text("Logout")
        }
    }
}
