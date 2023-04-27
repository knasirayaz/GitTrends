package com.knasirayaz.gittrends.data.source.remote

import com.knasirayaz.gittrends.domain.models.GetTrendingRepoListResponse
import com.knasirayaz.gittrends.domain.models.TrendingListItem
import retrofit2.http.GET

interface Webservice {
    @GET("/search/repositories?q=language=+sort:stars")
    suspend fun fetchTrendingRepositories() : GetTrendingRepoListResponse
}
