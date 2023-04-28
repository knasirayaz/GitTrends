package com.knasirayaz.gittrends.di

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.knasirayaz.gittrends.data.repository.TrendingRepoListRepositoryImpl
import com.knasirayaz.gittrends.data.source.persistance.GitTrendsDatabase
import com.knasirayaz.gittrends.data.source.persistance.TrendingRepoListDao
import com.knasirayaz.gittrends.data.source.remote.Webservice
import com.knasirayaz.gittrends.domain.models.GetTrendingRepoListResponse
import com.knasirayaz.gittrends.domain.repository.TrendingRepoListRepository
import com.knasirayaz.gittrends.presentation.base.SampleData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.junit.Assert.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModuleTest{
    @Provides
    @Singleton
    fun provideDatabase() : GitTrendsDatabase{
        return Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            GitTrendsDatabase::class.java).build()
    }

    @Provides
    @Singleton
    fun provideDao(database : GitTrendsDatabase) : TrendingRepoListDao {
        return database.trendingRepoListDao()
    }


    @Provides
    @Singleton
    fun provideRepository(dao: TrendingRepoListDao):TrendingRepoListRepository{
        return TrendingRepoListRepositoryImpl(object : Webservice{
            override suspend fun fetchTrendingRepositories(): GetTrendingRepoListResponse {
               return GetTrendingRepoListResponse(false, SampleData.getTrendingListItems(),  SampleData.getTrendingListItems().size)
            }

        }, dao)
    }



}