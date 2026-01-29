package com.example.project01group03

import org.junit.Assert.assertEquals
import org.junit.Test

class LoginScreenUnitTest {

    // Test 1: Verifies that empty fields trigger the correct message
    @Test
    fun `validateLogin returns error when fields are empty`() {
        val username = ""
        val password = ""

        val result = validateLoginLogic(username, password)

        assertEquals("Please fill in all fields", result)
    }

    // Test 2: Verifies that short passwords trigger the correct message
    @Test
    fun `validateLogin returns error when password is too short`() {
        val username = "user123"
        val password = "123"

        val result = validateLoginLogic(username, password)

        assertEquals("Password must be at least 6 characters", result)
    }

    // Test 3: Verifies that correct input returns no error (null)
    @Test
    fun `validateLogin returns null when input is valid`() {
        val username = "user123"
        val password = "password123"

        val result = validateLoginLogic(username, password)

        assertEquals(null, result)
    }
}

/**
 * This function stays here in the test file.
 * It mirrors the logic you wrote in your LoginScreen.kt
 * so you can test the logic independently of the UI.
 */
fun validateLoginLogic(username: String, password: String): String? {
    return when {
        username.isEmpty() || password.isEmpty() -> "Please fill in all fields"
        password.length < 6 -> "Password must be at least 6 characters"
        else -> null
    }
}