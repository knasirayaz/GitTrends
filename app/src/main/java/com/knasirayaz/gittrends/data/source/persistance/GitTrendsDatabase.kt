package com.knasirayaz.gittrends.data.source.persistance

import androidx.room.Database
import androidx.room.RoomDatabase
import com.knasirayaz.gittrends.domain.models.TrendingListItem

@Database(entities = [TrendingListItem::class], version = 1, exportSchema = false)
abstract class GitTrendsDatabase : RoomDatabase() {
    abstract fun trendingRepoListDao() : TrendingRepoListDao
}
