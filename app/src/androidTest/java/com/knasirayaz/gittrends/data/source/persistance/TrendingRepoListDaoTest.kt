package com.knasirayaz.gittrends.data.source.persistance

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.knasirayaz.gittrends.presentation.base.SampleData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TrendingRepoListDaoTest{

    lateinit var mDatabase : GitTrendsDatabase
    lateinit var mDao : TrendingRepoListDao


    @Before
    fun createDatabase(){
        mDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            GitTrendsDatabase::class.java).build()

        mDao = mDatabase.trendingRepoListDao()
    }

    @After
    fun closeDatabase(){
        mDatabase.close()
    }


    @Test
    fun insertTrendingList_expectedTrendingList() = runTest{
        val trendingList = SampleData.getTrendingListItems()
        mDao.saveTrendingRepositories(trendingList)
        val results = mDao.fetchTrendingRepositories()
        assertEquals(trendingList, results)
    }

}