package com.knasirayaz.gittrends.data.source.persistance

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.knasirayaz.gittrends.domain.models.GetTrendingRepoListResponse
import com.knasirayaz.gittrends.domain.models.TrendingListItem

@Dao
interface TrendingRepoListDao {
    @Query("SELECT * FROM trendinglistitem")
    suspend fun fetchTrendingRepositories(): List<TrendingListItem>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTrendingRepositories(listOfTrendingRepositories : List<TrendingListItem>)
}
