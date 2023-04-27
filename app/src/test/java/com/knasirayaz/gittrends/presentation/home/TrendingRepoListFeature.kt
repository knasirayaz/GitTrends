package com.knasirayaz.gittrends.presentation.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.knasirayaz.gittrends.data.repository.TrendingRepoListRepositoryImpl
import com.knasirayaz.gittrends.data.source.remote.Webservice
import com.knasirayaz.gittrends.domain.common.ResultStates
import com.knasirayaz.gittrends.domain.models.GetTrendingRepoListResponse
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
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.given
import org.mockito.kotlin.inOrder

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class TrendingRepoListFeature {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var mObserver : Observer<ResultStates<Any?>>

    @Mock
    lateinit var webService : Webservice

    private lateinit var mRepository : TrendingRepoListRepository
    private lateinit var mViewModel : TrendingRepoListViewModel
    private lateinit var mTrendingListItem: GetTrendingRepoListResponse

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup(){
        mTrendingListItem =  GetTrendingRepoListResponse(
            false,
            listOf(
                TrendingListItem(
                    repoName = "Kotlin-DSL",
                    repoDesc = "The Kotlin DSL Plugin provides a convenient way to develop Kotlin-based projects that contribute build logic",
                    repoLanguage = "Kotlin",
                    starsCount = "5000",
                    owner = TrendingListItem.Owner(
                        userProfilePicture = "profilePicture",
                        userName = "TestName-1",
                    )
                )
            )
        )
        Dispatchers.setMain(testDispatcher)
    }

    //Acceptance Test
    @Test
    fun `fetch trending repo list`() = runTest{
        given(webService.fetchTrendingRepositories()).willReturn(mTrendingListItem)
        mRepository = TrendingRepoListRepositoryImpl(webService)
        mViewModel = TrendingRepoListViewModel(mRepository)
        mViewModel.getTrendingListObserver().observeForever(mObserver)

        mViewModel.getTrendingRepoList()
        advanceUntilIdle()


        launch {
            inOrder(mObserver){
                verify(mObserver).onChanged(ResultStates.Loading(true))
                advanceUntilIdle()
                verify(mObserver).onChanged(ResultStates.Success(mTrendingListItem.items))
                verify(mObserver).onChanged(ResultStates.Loading(false))
            }

        }

    }

    @Test
    fun `failed to fetch trending repo list`() = runTest{
        given(webService.fetchTrendingRepositories()).willReturn(GetTrendingRepoListResponse(false, null, 0))
        mRepository = TrendingRepoListRepositoryImpl(webService)
        mViewModel = TrendingRepoListViewModel(mRepository)
        mViewModel.getTrendingListObserver().observeForever(mObserver)
        mViewModel.getTrendingRepoList()
        advanceUntilIdle()

        launch {
            inOrder(mObserver){
                verify(mObserver).onChanged(ResultStates.Loading(true))
                advanceUntilIdle()
                verify(mObserver).onChanged(ResultStates.Failed("Api Unreachable"))
                verify(mObserver).onChanged(ResultStates.Loading(false))
            }

        }

    }


    @Test
    fun `observer is working fine`() = runTest{
        mRepository  = mock(TrendingRepoListRepository::class.java)
        mViewModel = TrendingRepoListViewModel(mRepository)
        mViewModel.getTrendingListObserver().observeForever(mObserver)
        mViewModel.getTrendingRepoList()
        given(mRepository.getRepoList()).willReturn(ResultStates.Success(mTrendingListItem.items))

        launch {
            inOrder(mObserver){
                verify(mObserver).onChanged(ResultStates.Loading(true))
                verify(mObserver).onChanged(ResultStates.Success(mTrendingListItem.items))
                verify(mObserver).onChanged(ResultStates.Loading(false))
            }

        }
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }
}