package com.example.project01group03.ui.theme


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    var isLoggedIn by mutableStateOf(true)
        private set

    fun logout() {
        isLoggedIn = false
    }

    fun login() {
        isLoggedIn = true
    }
}
