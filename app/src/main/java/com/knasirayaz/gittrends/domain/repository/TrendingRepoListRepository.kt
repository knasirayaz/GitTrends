package com.knasirayaz.gittrends.domain.repository

import com.knasirayaz.gittrends.domain.common.ResultStates
import com.knasirayaz.gittrends.domain.models.TrendingListItem

interface TrendingRepoListRepository {
    suspend fun getRepoList(isRefresh: Boolean): ResultStates<List<TrendingListItem>>
}
