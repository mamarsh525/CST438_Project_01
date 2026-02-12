package com.example.project01group03


import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class RegisterScreenTest {

    @Test
    fun validateRegisterFields_emptyFields_returnsFillAllFieldsError() {
        val error = validateRegisterFields("", "password123", "password123")
        assertEquals("Please fill in all fields", error)
    }

    @Test
    fun validateRegisterFields_shortPassword_returnsLengthError() {
        val error = validateRegisterFields("edmond", "123", "123")
        assertEquals("Password must be at least 6 characters", error)
    }

    @Test
    fun validateRegisterFields_passwordMismatch_returnsMismatchError() {
        val error = validateRegisterFields("edmond", "password123", "password124")
        assertEquals("Passwords do not match", error)
    }

    @Test
    fun validateRegisterFields_validInput_returnsNull() {
        val error = validateRegisterFields("edmond", "password123", "password123")
        assertNull(error)
    }
}

