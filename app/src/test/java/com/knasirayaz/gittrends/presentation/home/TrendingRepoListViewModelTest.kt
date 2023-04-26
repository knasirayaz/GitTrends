package com.knasirayaz.gittrends.presentation.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.knasirayaz.gittrends.domain.common.ResultStates
import com.knasirayaz.gittrends.domain.models.TrendingListItem
import com.knasirayaz.gittrends.domain.repository.TrendingRepoListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
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
import org.mockito.kotlin.given
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
        mTrendingListViewModel.getTrendingRepoList()
        advanceUntilIdle()
        verify(mTrendingListRepository).getRepoList()
    }


    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


}