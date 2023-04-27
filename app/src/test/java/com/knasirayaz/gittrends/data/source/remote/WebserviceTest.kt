package com.knasirayaz.gittrends.data.source.remote

import com.google.gson.Gson
import com.knasirayaz.gittrends.domain.common.Utils
import com.knasirayaz.gittrends.domain.models.GetTrendingRepoListResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@OptIn(ExperimentalCoroutinesApi::class)
class WebserviceTest{
    private lateinit var mockWebServer : MockWebServer
    private lateinit var webService : Webservice
    @Before
    fun setup(){
        mockWebServer = MockWebServer()
        webService = Retrofit
            .Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(Webservice::class.java)

    }


    @Test
    fun `fetch trending repositories list from server`() = runTest{
        val sampleResponseBody: String = Utils.readFileResource("TrendingRepositorySampleResponse.json")
        val mockResponse = MockResponse()
        mockResponse.setBody(sampleResponseBody)
        mockWebServer.enqueue(mockResponse)

        val response = webService.fetchTrendingRepositories()
        mockWebServer.takeRequest()

        Assert.assertEquals(Gson().fromJson(sampleResponseBody, GetTrendingRepoListResponse::class.java), response)
    }


    @After
    fun tearDown(){
        mockWebServer.shutdown()
    }
}