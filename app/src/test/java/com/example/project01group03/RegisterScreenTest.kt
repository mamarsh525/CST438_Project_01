package com.example.project01group03

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class RegisterScreenTest {

    @Before
    fun setup() {
        // No setup needed, included for consistency with other tests
    }

    @Test
    fun validateRegisterFields_emptyFields_returnsFillAllFieldsError() {
        val error = validateRegisterFields(
            username = "",
            password = "password123",
            confirmPassword = "password123"
        )

        assertEquals("Please fill in all fields", error)
    }

    @Test
    fun validateRegisterFields_shortPassword_returnsLengthError() {
        val error = validateRegisterFields(
            username = "testuser1",
            password = "123",
            confirmPassword = "123"
        )

        assertEquals("Password must be at least 6 characters", error)
    }

    @Test
    fun validateRegisterFields_passwordMismatch_returnsMismatchError() {
        val error = validateRegisterFields(
            username = "testuser1",
            password = "password123",
            confirmPassword = "password124"
        )

        assertEquals("Passwords do not match", error)
    }

    @Test
    fun validateRegisterFields_validInput_returnsNull() {
        val error = validateRegisterFields(
            username = "testuser1",
            password = "password123",
            confirmPassword = "password123"
        )

        assertNull(error)
    }
}
