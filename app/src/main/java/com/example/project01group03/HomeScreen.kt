package com.example.project01group03

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.project01group03.API.RetrofitClient
import kotlinx.coroutines.launch


@Composable
fun HomeScreen(onLogout: () -> Unit) {
    //since searchArtist is a suspend function, it needs coroutine to run,
    //scope.launch allows button to start the coroutine
    //coeoutine scope runs network call in background to keep ui from freezing up
    val scope = rememberCoroutineScope()
    //artist nameis what holds api call info and displays in on screen
    //remember keeps the value from being reset
    //without it, it breakes :(
    var artistName by remember { mutableStateOf("No artist loaded") }
    Column(modifier = Modifier.padding(16.dp)) {
        //displays the random artist
        Text(text = "Artist: $artistName")
        Button(onClick = {
            //starts the task for network call to api
            scope.launch {
                //calls discog api and gets random artist from pages 1-100 for now just to test
                val response = RetrofitClient.discogsApiService.searchArtists(query = "",
                    page = (1..100).random()//random number for artist page
                )
                artistName = response.results.firstOrNull()?.title ?: "No artist found"//displays no artist found if nothing returns
            }
        }) {
            Text("Get Random Artist")
        }
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Welcome to the Home Screen!")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onLogout) {
            Text("Logout")
        }
    }
}}
