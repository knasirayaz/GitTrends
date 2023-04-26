package com.knasirayaz.gittrends.data.repository

import com.knasirayaz.gittrends.domain.common.ResultStates
import com.knasirayaz.gittrends.domain.models.TrendingListItem
import com.knasirayaz.gittrends.domain.repository.TrendingRepoListRepository

class FakeTrendingListRepository : TrendingRepoListRepository {
    override suspend fun getRepoList(): ResultStates<TrendingListItem> {
       return ResultStates.Success(TrendingListItem(
           userProfilePicture = "profilePicture",
           userName = "TestName-1",
           repoName = "Kotlin-DSL",
           repoDesc = "The Kotlin DSL Plugin provides a convenient way to develop Kotlin-based projects that contribute build logic",
           repoLanguage = "Kotlin",
           starsCount = "5000"
       ))
    }

}
