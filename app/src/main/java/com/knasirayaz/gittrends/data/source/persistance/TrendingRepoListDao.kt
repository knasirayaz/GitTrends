package com.knasirayaz.gittrends.data.source.persistance

import com.knasirayaz.gittrends.domain.models.GetTrendingRepoListResponse
import com.knasirayaz.gittrends.domain.models.TrendingListItem

interface TrendingRepoListDao {
    suspend fun fetchTrendingRepositories(): List<TrendingListItem>
    suspend fun saveTrendingRepositories(listOfTrendingRepositories : List<TrendingListItem>)
}
