package com.knasirayaz.gittrends.data.repository

import com.knasirayaz.gittrends.data.source.remote.Webservice
import com.knasirayaz.gittrends.domain.common.ResultStates
import com.knasirayaz.gittrends.domain.models.TrendingListItem
import com.knasirayaz.gittrends.domain.repository.TrendingRepoListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpRetryException

class TrendingRepoListRepositoryImpl(private val webService: Webservice) :
    TrendingRepoListRepository {
    override suspend fun getRepoList(): ResultStates<TrendingListItem> =
        withContext(Dispatchers.IO) {
            try {
                return@withContext ResultStates.Success(webService.fetchTrendingRepositories())
            } catch (e: HttpRetryException) {
                return@withContext ResultStates.Failed("Something is wrong")
            }
        }


}
