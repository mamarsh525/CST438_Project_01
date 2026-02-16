package com.example.project01group03

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.project01group03.data.AppDatabase
import com.example.project01group03.ui.theme.Project01Group03Theme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Project01Group03Theme {
                val context = LocalContext.current
                var database by remember { mutableStateOf<AppDatabase?>(null) }

                LaunchedEffect(Unit) {
                    database = withContext(Dispatchers.IO) {
                        AppDatabase.getDatabase(context)
                    }
                }

                if (database == null) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    val userDao = remember { database!!.userDao() }
                    val collectionDao = remember { database!!.userCollectionItemDao() }
                    val navController = rememberNavController()
                    val snackbarHostState = remember { SnackbarHostState() }
                    val scope = rememberCoroutineScope()

                    // Store logged-in user ID
                    var currentUserId by remember { mutableStateOf<Int?>(null) }

                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
                    ) { innerPadding ->
                        Surface(
                            modifier = Modifier.padding(innerPadding),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            NavHost(navController = navController, startDestination = "login") {
                                composable("login") {
                                    LoginScreen(
                                        userDao = userDao,
                                        onLoginSuccess = { userId ->
                                            currentUserId = userId
                                            navController.navigate("home") {
                                                popUpTo("login") { inclusive = true }
                                            }
                                        },
                                        onCreateAccountSuccess = {
                                            scope.launch {
                                                snackbarHostState.showSnackbar("Account created successfully!")
                                            }
                                        }
                                    )
                                }

                                composable("home") {
                                    HomeScreen(
                                        onLogout = {
                                            currentUserId = null
                                            navController.navigate("login") {
                                                popUpTo("home") { inclusive = true }
                                            }
                                        },
                                        onArtistSearch = { artistId, artistName ->
                                            navController.navigate("artist/$artistId/$artistName")
                                        },
                                        onNavigateToCollection = {
                                            navController.navigate("collection")
                                        }
                                    )
                                }

                                composable("collection") {
                                    CollectionScreen(
                                        userId = currentUserId ?: 0,
                                        collectionDao = collectionDao,
                                        onBackToHome = {
                                            navController.popBackStack()
                                        }
                                    )
                                }

                                composable(
                                    "artist/{artistId}/{artistName}",
                                    arguments = listOf(
                                        navArgument("artistId") { type = NavType.LongType },
                                        navArgument("artistName") { type = NavType.StringType }
                                    )
                                ) { backStackEntry ->
                                    val artistId = backStackEntry.arguments?.getLong("artistId") ?: 0L
                                    val artistName = backStackEntry.arguments?.getString("artistName") ?: ""
                                    ArtistScreen(
                                        artistId = artistId,
                                        artistName = artistName,
                                        userId = currentUserId ?: 0,
                                        collectionDao = collectionDao,
                                        onBackToHome = {
                                            navController.popBackStack()
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}