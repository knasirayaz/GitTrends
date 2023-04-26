package com.knasirayaz.gittrends.data.repository

import com.knasirayaz.gittrends.data.source.remote.Webservice
import com.knasirayaz.gittrends.domain.common.ResultStates
import com.knasirayaz.gittrends.domain.models.TrendingListItem
import com.knasirayaz.gittrends.domain.repository.TrendingRepoListRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.given
import java.net.HttpRetryException

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class TrendingRepoListRepositoryImplTest{

    private lateinit var mTrendingListItem: TrendingListItem

    private lateinit var mTrendingRepoListRepository: TrendingRepoListRepository

    @Mock
    private lateinit var webService : Webservice

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

        mTrendingRepoListRepository = TrendingRepoListRepositoryImpl(webService)
    }

    @Test
    fun `it should return success when list is fetched successfully`() = runTest{
        given(webService.fetchTrendingRepositories()).willReturn(mTrendingListItem)
        val results = mTrendingRepoListRepository.getRepoList()
        assertEquals(ResultStates.Success(mTrendingListItem), results)
    }

    @Test
    fun `it should return failure when list is not fetched successfully`() = runTest{
        given(webService.fetchTrendingRepositories()).willThrow(HttpRetryException("",404))
        val results = mTrendingRepoListRepository.getRepoList()
        assertEquals(ResultStates.Failed("Api Unreachable"), results)
    }
}