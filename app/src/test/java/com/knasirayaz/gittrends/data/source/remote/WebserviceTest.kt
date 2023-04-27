package com.knasirayaz.gittrends.data.source.remote

import com.knasirayaz.gittrends.domain.models.GetTrendingRepoListResponse
import com.knasirayaz.gittrends.domain.models.TrendingListItem
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

    private val sampleBody: String = "{" +
            "  \"total_count\": 393555," +
            "  \"incomplete_results\": false," +
            "  \"items\": [" +
            "    {" +
            "      \"name\": \"go\"," +
            "      \"full_name\": \"golang/go\"," +
            "      \"owner\": {" +
            "        \"login\": \"golang\"," +
            "        \"avatar_url\": \"https://avatars.githubusercontent.com/u/4314092?v=4\"" +
            "      }," +
            "      \"description\": \"The Go programming language\"," +
            "      \"stargazers_count\": 110740," +
            "      \"language\": \"Go\"" +
            "    }" +
            "  ]" +
            "}"

    private val sampleBodyModel = GetTrendingRepoListResponse(
            incomplete_results = false,
            total_count = 393555,
            items = listOf(
                TrendingListItem(
                    repoName = "go",
                    repoDesc = "The Go programming language",
                    repoLanguage = "Go",
                    starsCount = "110740",
                    owner = TrendingListItem.Owner(
                        userName = "golang",
                        userProfilePicture = "https://avatars.githubusercontent.com/u/4314092?v=4"
                    )

                )
            )
    )
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
        val mockResponse = MockResponse()
        mockResponse.setBody(sampleBody)
        mockWebServer.enqueue(mockResponse)

        val response = webService.fetchTrendingRepositories()
        mockWebServer.takeRequest()

        Assert.assertEquals(sampleBodyModel, response)
    }


    @After
    fun tearDown(){
        mockWebServer.shutdown()
    }
}