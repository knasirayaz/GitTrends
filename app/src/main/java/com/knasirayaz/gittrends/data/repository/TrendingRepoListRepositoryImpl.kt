package com.knasirayaz.gittrends.data.repository

import com.knasirayaz.gittrends.data.source.persistance.TrendingRepoListDao
import com.knasirayaz.gittrends.data.source.remote.Webservice
import com.knasirayaz.gittrends.domain.common.ResultStates
import com.knasirayaz.gittrends.domain.models.TrendingListItem
import com.knasirayaz.gittrends.domain.repository.TrendingRepoListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException
import javax.inject.Inject

class TrendingRepoListRepositoryImpl @Inject constructor(
    private val webService: Webservice,
    private val dao: TrendingRepoListDao
) :
    TrendingRepoListRepository {
    override suspend fun getRepoList(isRefresh: Boolean): ResultStates<List<TrendingListItem>> =
        withContext(Dispatchers.IO) {
            if(isRefresh){
                return@withContext getDataFromApi()
            }else{
                val resultsFromDb = dao.fetchTrendingRepositories()
                if(resultsFromDb.isNullOrEmpty()){
                    return@withContext getDataFromApi()
                }else{
                    return@withContext ResultStates.Success(resultsFromDb)
                }
            }

        }

    private suspend fun getDataFromApi(): ResultStates<List<TrendingListItem>> {
        return withContext(Dispatchers.IO) {
            try {
                val results = webService.fetchTrendingRepositories()
                if (results.items.isNotEmpty()) {
                    saveTrendingListToDatabase(results.items)
                    return@withContext ResultStates.Success(results.items)
                }else
                    return@withContext ResultStates.Failed("Something went wrong")
            } catch (e: HttpException) {
                return@withContext ResultStates.Failed("Something went wrong")
            } catch (e: UnknownHostException){
                return@withContext ResultStates.Failed(e.message.toString())
            }
        }
    }

    private suspend fun saveTrendingListToDatabase(items: List<TrendingListItem>) {
        dao.saveTrendingRepositories(items)
    }

}
