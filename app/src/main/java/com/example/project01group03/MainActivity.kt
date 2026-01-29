package com.example.project01group03

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.project01group03.ui.theme.Project01Group03Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Project01Group03Theme {
                // 1. Initialize the NavController inside the Theme
                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Surface(
                        modifier = Modifier.padding(innerPadding),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        // 2. The NavHost is the ONLY thing that should be here.
                        // It decides whether to show LoginScreen or HomeScreen.
                        NavHost(navController = navController, startDestination = "login") {

                            composable("login") {
                                LoginScreen(onLoginSuccess = {
                                    // Navigate to home and clear login from history
                                    navController.navigate("home") {
                                        popUpTo("login") { inclusive = true }
                                    }
                                })
                            }

                            composable("home") {
                                HomeScreen(onLogout = {
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
// 3. ADD THIS FUNCTION AT THE BOTTOM (Outside the MainActivity class)
// This fixes the "HomeScreen" error.
@Composable
fun HomeScreen(onLogout: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
    ) {
        Text(text = "Welcome to the Home Screen!")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onLogout) {
            Text(text = "Logout")
        }
    }
}
