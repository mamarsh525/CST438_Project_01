package com.example.project01group03

import com.example.project01group03.ui.theme.MainViewModel
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class MainViewModelTest {

    @Test
    fun logout_updatesIsLoggedInToFalse() {
        // logged in by default
        val viewModel = MainViewModel()
        assertTrue(viewModel.isLoggedIn)

        // When logout is called
        viewModel.logout()

        // isLoggedIn should be false
        assertFalse(viewModel.isLoggedIn)
    }

    @Test
    fun login_updatesIsLoggedInToTrue() {
        // Start logged out
        val viewModel = MainViewModel()
        viewModel.logout()
        assertFalse(viewModel.isLoggedIn)

        // When login is called
        viewModel.login()

        // Then isLoggedIn should be true
        assertTrue(viewModel.isLoggedIn)
    }
}
