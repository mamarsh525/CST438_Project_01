package com.example.project01group03
import com.example.project01group03.API.RetrofitClient
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.project01group03.data.AppDatabase
import com.example.project01group03.data.UserDao
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
                var loggedInUserId by remember { mutableIntStateOf(-1) }
                // Use LaunchedEffect to initialize the database off the main thread
                LaunchedEffect(Unit) {
                    database = withContext(Dispatchers.IO) {
                        AppDatabase.getDatabase(context)
                    }
                }

                // Show a loading indicator while the database is being created
                if (database == null) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    // Once the database is ready, get the dao and show the NavHost
                    val userDao = remember { database!!.userDao() }

                    val navController = rememberNavController()
                    val snackbarHostState = remember { SnackbarHostState() }
                    val scope = rememberCoroutineScope()

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
                                        onLoginSuccess = { user ->
                                            loggedInUserId = user.id
                                            // Navigate to home and clear login from history
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
                                    HomeScreen(userId = loggedInUserId,
                                        favoriteDao = database!!.favoriteDao(),
                                        onLogout = {
                                        // Navigate back to login
                                        navController.navigate("login") {
                                            popUpTo("home") { inclusive = true }
                                        }
                                    })
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

