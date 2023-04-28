package com.knasirayaz.gittrends.presentation.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.knasirayaz.gittrends.domain.repository.TrendingRepoListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify


@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class TrendingRepoListViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var mTrendingListRepository: TrendingRepoListRepository

    private lateinit var mTrendingListViewModel: TrendingRepoListViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        mTrendingListViewModel = TrendingRepoListViewModel(mTrendingListRepository)
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `it should fetch trending list from repository`() = runTest {
        mTrendingListViewModel.getTrendingRepoList(false)
        advanceUntilIdle()
        verify(mTrendingListRepository).getRepoList(false)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


}