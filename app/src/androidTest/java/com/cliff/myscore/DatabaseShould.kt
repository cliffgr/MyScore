package com.cliff.myscore

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider


import com.cliff.myscore.data.local.AppDatabase
import com.cliff.myscore.data.local.dao.FavouriteLeaguesDao
import com.cliff.myscore.data.local.entity.FavLeague
import com.cliff.myscore.utils.BaseUnitTest
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.*

import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.IOException


class DatabaseShould : BaseUnitTest() {

    private lateinit var favouriteLeaguesDao: FavouriteLeaguesDao
    private lateinit var appDatabase: AppDatabase


    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .fallbackToDestructiveMigration()
            .setQueryExecutor(coroutineTestRule.testDispatcher.asExecutor())
            .allowMainThreadQueries()
            .build()
        favouriteLeaguesDao = appDatabase.favouriteLeaguesDao()

    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        appDatabase.clearAllTables()
        appDatabase.close()
    }

    @ExperimentalCoroutinesApi
    @Test
    @Throws(Exception::class)
    fun shouldFindLeagueById() = coroutineTestRule.testDispatcher.runBlockingTest {
        val favLeague = FavLeague(10, true)
        favouriteLeaguesDao.insertFavouriteLeague(favLeague)
        val favLeaguesFromRoom = favouriteLeaguesDao.findLeagueById(10).take(1).toList()
        assertEquals(favLeaguesFromRoom[0].leagueId, 10)
    }
}