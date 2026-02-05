package com.example.project01group03

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.project01group03.data.AppDatabase
import com.example.project01group03.data.User
import com.example.project01group03.data.UserDao
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.IOException

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34]) // Add this line to specify the SDK for Robolectric
class DatabaseTest {
    private lateinit var userDao: UserDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        userDao = db.userDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetUser() = runBlocking {
        val user = User(username = "testuser", password = "password")
        userDao.insert(user)
        val allUsers = userDao.getAllUsers().first()
        assertEquals(allUsers[0].username, user.username)
        assertEquals(allUsers[0].password, user.password)
    }

    @Test
    fun loginUser_success() = runBlocking {
        val user = User(username = "testuser", password = "password")
        userDao.insert(user)
        val loggedInUser = userDao.login("testuser", "password")
        assertNotNull(loggedInUser)
        assertEquals("testuser", loggedInUser?.username)
    }

    @Test
    fun loginUser_wrongPassword_fails() = runBlocking {
        val user = User(username = "testuser", password = "password")
        userDao.insert(user)
        val loggedInUser = userDao.login("testuser", "wrongpassword")
        assertNull(loggedInUser)
    }

    @Test
    fun getUserByUsername_exists() = runBlocking {
        val user = User(username = "testuser", password = "password")
        userDao.insert(user)
        val foundUser = userDao.getUserByUsername("testuser")
        assertNotNull(foundUser)
        assertEquals("testuser", foundUser?.username)
    }

    @Test
    fun getUserByUsername_doesNotExist() = runBlocking {
        val foundUser = userDao.getUserByUsername("nonexistent")
        assertNull(foundUser)
    }
}
