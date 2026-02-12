package com.example.project01group03

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

private fun Modifier.noTabOrEnter(): Modifier {
    return this.onPreviewKeyEvent { event: KeyEvent ->
        val blockedKey = (
                event.key == Key.Tab ||
                        event.k


                        ey() == Key.Enter ||
                        event.
                        key == Key.NumPadEnter
                )

        event.ty
        pe == KeyEventType.KeyDown && blockedKey
    }
}

fun validateRegisterFields(username: String, password: String, confirmPassword: String): String? {
    return when {
        username.isBlank() || password.isBlank() || confirmPassword.isBlank() ->
            "Please fill in all fields"
        password.length < 6 ->
            "Password must be at least 6 characters"
        password != confirmPassword ->
            "Passwords do not match"
        else -> null
    }
}

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit = {},
    onGoToLogin: () -> Unit = {}
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Create Account",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        OutlinedTextField(
            value = username,
            onValueChange = {
                username = it
                errorMessage = null
            },
            label = { Text("Username") },
            isError = errorMessage != null,
            modifier = Modifier.fillMaxWidth().noTabOrEnter(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.None)
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
            modifier = Modifier.fillMaxWidth().noTabOrEnter(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.None)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = {
                confirmPassword = it
                errorMessage = null
            },
            label = { Text("Confirm Password") },
            visualTransformation = PasswordVisualTransformation(),
            isError = errorMessage != null,
            modifier = Modifier.fillMaxWidth().noTabOrEnter(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.None)
        )

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
                val result = validateRegisterFields(username, password, confirmPassword)
                if (result != null) errorMessage = result else onRegisterSuccess()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Create Account")
        }

        Spacer(modifier = Modifier.height(12.dp))

        TextButton(onClick = onGoToLogin) {
            Text("Already have an account? Log in")
        }
    }
}
