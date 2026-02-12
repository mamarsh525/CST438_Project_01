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
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

import com.example.project01group03.data.User
import com.example.project01group03.data.UserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun LoginScreen(
    userDao: UserDao,
    onLoginSuccess: () -> Unit = {},
    onCreateAccountSuccess: () -> Unit = {}
) { // Added a callback for navigation
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) } // State for validation

    fun stripWhitespace(input: String): String = input.filterNot { it.isWhitespace() }

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Vinyl Vault",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        OutlinedTextField(
            value = username,
            onValueChange = { input ->
                username = stripWhitespace(input)
                errorMessage = null // Clear error when typing
            },
            label = { Text("Username") },
            isError = errorMessage != null, // Highlights red if error exists
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            maxLines = 1,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Ascii,
                capitalization = KeyboardCapitalization.None,
                autoCorrect = false
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { input ->
                password = stripWhitespace(input)
                errorMessage = null
            },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            isError = errorMessage != null,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            maxLines = 1,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password,
                autoCorrect = false
            )
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

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(
                onClick = {
                    val u = username.trim()
                    val p = password.trim()

                    if (u.isBlank() || p.isBlank()) {
                        errorMessage = "Please fill in all fields"
                    } else if (u.any { it.isWhitespace() } || p.any { it.isWhitespace() }) {
                        errorMessage = "Username and password cannot contain spaces, tabs, or new lines"
                    } else {
                        scope.launch {
                            val user = withContext(Dispatchers.IO) {
                                userDao.login(u, p)
                            }

                            if (user != null) {
                                onLoginSuccess()
                            } else {
                                errorMessage = "Invalid username or password"
                            }
                        }
                    }
                },
            ) {
                Text("Sign In")
            }

            Button(
                onClick = {
                    val u = username.trim()
                    val p = password.trim()

                    if (u.isBlank() || p.isBlank()) {
                        errorMessage = "Please fill in all fields"
                    } else if (u.any { it.isWhitespace() } || p.any { it.isWhitespace() }) {
                        errorMessage = "Username and password cannot contain spaces, tabs, or new lines"
                    } else {
                        scope.launch {
                            val existingUser = withContext(Dispatchers.IO) {
                                userDao.getUserByUsername(u)
                            }
                            if (existingUser != null) {
                                errorMessage = "Username already exists"
                            } else {
                                val newUser = User(username = u, password = p)
                                withContext(Dispatchers.IO) {
                                    userDao.insert(newUser)
                                }
                                onCreateAccountSuccess()
                            }
                        }
                    }
                }
            ) {
                Text("Create Account")
            }
        }
    }
}
