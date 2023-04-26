package com.knasirayaz.gittrends.data.repository

import com.knasirayaz.gittrends.domain.common.ResultStates
import com.knasirayaz.gittrends.domain.models.TrendingListItem
import com.knasirayaz.gittrends.domain.repository.TrendingRepoListRepository

class FakeTrendingListRepository :
    TrendingRepoListRepository {
    override suspend fun getRepoList(): ResultStates<TrendingListItem> {
        TODO("Not yet implemented")
    }

}
