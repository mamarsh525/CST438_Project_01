package com.example.project01group03

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class LogoutTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testLogoutButtonTriggersCallback() {
        var logoutCalled = false
        val latch = CountDownLatch(1)

        composeTestRule.setContent {
            HomeScreen(onLogout = {
                logoutCalled = true
                latch.countDown()
            })
        }

        // Verify "Welcome" text is displayed
        composeTestRule.onNodeWithText("Welcome to the Home Screen!").assertExists()

        // Click the Logout button
        composeTestRule.onNodeWithText("Logout").performClick()

        // Wait for callback or timeout
        latch.await(2, TimeUnit.SECONDS)

        // Assert that the callback was triggered
        assert(logoutCalled)
    }
}
