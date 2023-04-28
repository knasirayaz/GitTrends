package com.knasirayaz.gittrends.data.source.persistance

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.knasirayaz.gittrends.di.AppModule
import com.knasirayaz.gittrends.presentation.base.SampleData
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
@UninstallModules(AppModule::class)
class TrendingRepoListDaoTest{

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var mDatabase : GitTrendsDatabase


    @Inject
    lateinit var mDao : TrendingRepoListDao


    @Before
    fun setup(){
        hiltRule.inject()
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