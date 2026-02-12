package com.example.project01group03

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.project01group03.data.AppDatabase
import com.example.project01group03.data.User
import com.example.project01group03.data.UserCollectionItem
import com.example.project01group03.data.UserCollectionItemDao
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
@Config(sdk = [34]) // Specify the SDK for Robolectric
class DatabaseTest {
    private lateinit var userDao: UserDao
    private lateinit var userCollectionItemDao: UserCollectionItemDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        userDao = db.userDao()
        userCollectionItemDao = db.userCollectionItemDao() // Initialize the new DAO
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    // --- User DAO Tests ---

    @Test
    @Throws(Exception::class)
    fun insertAndGetUser() = runBlocking {
        val user = User(username = "testuser", password = "password")
        userDao.insert(user)
        val allUsers = userDao.getAllUsers().first()
        assertEquals("testuser", allUsers[0].username)
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
    }

    // --- User Collection DAO Tests ---

    @Test
    fun addAndRetrieveCollectionItem() = runBlocking {
        // 1. Create and insert a user to be the owner
        val owner = User(id = 1, username = "collector", password = "password")
        userDao.insert(owner)

        // 2. Create a collection item linked to the user
        val item = UserCollectionItem(
            ownerId = 1,
            releaseId = 12345L,
            title = "Test Album",
            year = "1991",
            thumbUrl = "http://example.com/image.jpg"
        )
        userCollectionItemDao.insert(item)

        // 3. Retrieve the collection for that user
        val collection = userCollectionItemDao.getCollectionForUser(1).first()

        // 4. Verify the retrieved item is correct
        assertEquals(1, collection.size)
        assertEquals("Test Album", collection[0].title)
        assertEquals(1, collection[0].ownerId)
    }
}
