package com.example.project01group03.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class UserDatabaseTest {

    private lateinit var database: AppDatabase
    private lateinit var userDao: UserDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        database = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()

        userDao = database.userDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertUser_userIsSavedInDatabase() = runBlocking {
        // create user
        val user = User(
            username = "testuser",
            password = "password123"
        )

        // insert user
        userDao.insertUser(user)

        // read user back
        val savedUser = userDao.getAllUsers().firstOrNull()

        // verify
        assertNotNull(savedUser)
        assertEquals("testuser", savedUser?.username)
    }
}
