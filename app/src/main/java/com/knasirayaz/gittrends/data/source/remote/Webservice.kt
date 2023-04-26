package com.knasirayaz.gittrends.data.source.remote

import com.knasirayaz.gittrends.domain.models.GetTrendingRepoListResponse
import com.knasirayaz.gittrends.domain.models.TrendingListItem

interface Webservice {
    suspend fun fetchTrendingRepositories() : GetTrendingRepoListResponse
}
