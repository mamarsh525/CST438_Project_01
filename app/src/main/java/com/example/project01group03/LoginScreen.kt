package com.example.project01group03

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions // Added
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction // Added
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

import com.example.project01group03.data.UserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun LoginScreen(
    userDao: UserDao,
    onLoginSuccess: () -> Unit = {}
) { // Added a callback for navigation
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) } // State for validation

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Welcome Back",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        OutlinedTextField(
            value = username,
            onValueChange = {
                username = it
                errorMessage = null // Clear error when typing
            },
            label = { Text("Username") },
            isError = errorMessage != null, // Highlights red if error exists
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                errorMessage = null
            },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            isError = errorMessage != null,
            modifier = Modifier.fillMaxWidth(),
            // KEYBOARD OPTIONS: Makes the keyboard show "Done" instead of a new line
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
        )

        // VALIDATION: Show error message if it exists
        if (errorMessage != null) {
            Text(
                text = errorMessage!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                // VALIDATION LOGIC
                if (username.isEmpty() || password.isEmpty()) {
                    errorMessage = "Please fill in all fields"
                } else if (password.length < 6) {
                    errorMessage = "Password must be at least 6 characters"
                } else {
                    // database login check
                    scope.launch {
                        val user = withContext(Dispatchers.IO) {
                            userDao.login(username, password)
                        }

                        if (user != null) {
                            // SUCCESS
                            onLoginSuccess()
                        } else {
                            errorMessage = "Invalid username or password"
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }
    }
}
