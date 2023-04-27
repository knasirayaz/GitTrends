package com.knasirayaz.gittrends.data.repository

import com.knasirayaz.gittrends.data.source.remote.Webservice
import com.knasirayaz.gittrends.domain.common.ResultStates
import com.knasirayaz.gittrends.domain.models.TrendingListItem
import com.knasirayaz.gittrends.domain.repository.TrendingRepoListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

class TrendingRepoListRepositoryImpl @Inject constructor(private val webService: Webservice) :
    TrendingRepoListRepository {
    override suspend fun getRepoList(): ResultStates<List<TrendingListItem>> =
        withContext(Dispatchers.IO) {
            try {
                val results  = webService.fetchTrendingRepositories()
                if(results.items.isNotEmpty())
                    return@withContext ResultStates.Success(results.items)
                else
                    return@withContext ResultStates.Failed("Api Unreachable")
            } catch (e: HttpException) {
                return@withContext ResultStates.Failed("Api Unreachable")
            }
        }


}
