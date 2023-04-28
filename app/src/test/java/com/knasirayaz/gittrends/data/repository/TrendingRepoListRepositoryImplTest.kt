package com.knasirayaz.gittrends.data.repository

import com.knasirayaz.gittrends.data.source.persistance.TrendingRepoListDao
import com.knasirayaz.gittrends.data.source.remote.Webservice
import com.knasirayaz.gittrends.domain.common.ResultStates
import com.knasirayaz.gittrends.domain.models.GetTrendingRepoListResponse
import com.knasirayaz.gittrends.domain.models.TrendingListItem
import com.knasirayaz.gittrends.domain.repository.TrendingRepoListRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.atLeastOnce
import org.mockito.kotlin.given
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import retrofit2.HttpException
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class TrendingRepoListRepositoryImplTest{

    private lateinit var mTrendingListItem: GetTrendingRepoListResponse

    private lateinit var mTrendingRepoListRepository: TrendingRepoListRepository

    @Mock
    private lateinit var webService : Webservice

    @Mock
    private lateinit var mTrendingRepoListDao : TrendingRepoListDao


    @Before
    fun setup(){
        mTrendingListItem =  GetTrendingRepoListResponse(
            false,
            listOf(
                TrendingListItem(
                    id = 0,
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

        mTrendingRepoListRepository = TrendingRepoListRepositoryImpl(webService, mTrendingRepoListDao)
    }

    @Test
    fun `it should return success when list is fetched successfully`() = runTest{
        given(webService.fetchTrendingRepositories()).willReturn(mTrendingListItem)
        val results = mTrendingRepoListRepository.getRepoList()
        assertEquals(ResultStates.Success(mTrendingListItem.items), results)
    }

    @Test
    fun `it should return failure when list is not fetched successfully`() = runTest{
        val error =  Response.error<TrendingListItem>(404, ResponseBody.create(null, ""))
        given(webService.fetchTrendingRepositories()).willThrow(HttpException(error))
        val results = mTrendingRepoListRepository.getRepoList()
        assertEquals(ResultStates.Failed(error.message()), results)
    }

    @Test
    fun `fetch data from database using dao`() = runTest{
        given(mTrendingRepoListDao.fetchTrendingRepositories()).willReturn(mTrendingListItem.items)
        val results = mTrendingRepoListRepository.getRepoList()
        assertEquals(ResultStates.Success(mTrendingListItem.items), results)
    }

    @Test
    fun `it should get data from api if database is empty`() = runTest{
        given(webService.fetchTrendingRepositories()).willReturn(mTrendingListItem)
        val results = mTrendingRepoListRepository.getRepoList()
        assertEquals(ResultStates.Success(mTrendingListItem.items), results)
    }

    @Test
    fun `it should save data from api to database`() = runTest{
        given(webService.fetchTrendingRepositories()).willReturn(mTrendingListItem)
        mTrendingRepoListRepository.getRepoList()
        verify(mTrendingRepoListDao, times(1)).saveTrendingRepositories(mTrendingListItem.items)
    }
}