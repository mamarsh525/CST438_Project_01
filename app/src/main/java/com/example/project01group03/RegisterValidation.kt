package com.example.project01group03

fun validateRegisterFields(
    username: String,
    password: String,
    confirmPassword: String
): String? {
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
