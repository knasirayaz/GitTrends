package com.knasirayaz.gittrends.presentation.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.knasirayaz.gittrends.domain.common.ResultStates
import com.knasirayaz.gittrends.domain.models.TrendingListItem
import com.knasirayaz.gittrends.domain.repository.TrendingRepoListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.inOrder

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class TrendingRepoListFeature {

    @get:Rule
    val rule = InstantTaskExecutorRule()


    @Mock
    lateinit var mObserver : Observer<ResultStates<Any?>>

    @Mock
    lateinit var mRepository : TrendingRepoListRepository

    lateinit var mViewModel : TrendingRepoListViewModel


    //Need to add Coroutine test to add StandardTestDispatcher.
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var mTrendingListItem: TrendingListItem

    @Before
    fun setup(){
        mTrendingListItem =  TrendingListItem(
            userProfilePicture = "profilePicture",
            userName = "TestName-1",
            repoName = "Kotlin-DSL",
            repoDesc = "The Kotlin DSL Plugin provides a convenient way to develop Kotlin-based projects that contribute build logic",
            repoLanguage = "Kotlin",
            starsCount = "5000"
        )
        Dispatchers.setMain(testDispatcher)

        mViewModel = TrendingRepoListViewModel(mRepository)
        mViewModel.getTrendingListObserver().observeForever(mObserver)
        mViewModel.getTrendingRepoList()

    }

    //Acceptance Test
    @Test
    fun `fetch trending repo list`() = runTest{
        //Moved To-Do list to scratches
    }


    @Test
    fun `observer is working fine`() = runTest{
        Mockito.`when`(mRepository.getRepoList()).thenReturn(ResultStates.Success(mTrendingListItem))

        launch {
            inOrder(mObserver){
                verify(mObserver).onChanged(ResultStates.Loading(true))
                verify(mObserver).onChanged(ResultStates.Success(mTrendingListItem))
                verify(mObserver).onChanged(ResultStates.Loading(false))
            }

        }
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }
}