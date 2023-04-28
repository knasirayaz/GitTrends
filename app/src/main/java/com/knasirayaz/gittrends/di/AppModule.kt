package com.knasirayaz.gittrends.di

import android.app.Application
import androidx.room.Room
import com.google.gson.Gson
import com.knasirayaz.gittrends.data.repository.TrendingRepoListRepositoryImpl
import com.knasirayaz.gittrends.data.source.persistance.GitTrendsDatabase
import com.knasirayaz.gittrends.data.source.persistance.TrendingRepoListDao
import com.knasirayaz.gittrends.data.source.remote.Webservice
import com.knasirayaz.gittrends.domain.repository.TrendingRepoListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun providesGson(): Gson {
        return Gson()
    }

    @Singleton
    @Provides
    fun provideWebService(
        gson: Gson, okHttpClient: OkHttpClient
    ): Webservice {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(Webservice::class.java)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()


    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        return httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }
    }



    @Singleton
    @Provides
    fun provideTrendingRepository(webService: Webservice, dao : TrendingRepoListDao): TrendingRepoListRepository {
        return TrendingRepoListRepositoryImpl(webService, dao)
    }


    @Provides
    @Singleton
    fun provideDatabase(app: Application): GitTrendsDatabase {
        return Room.databaseBuilder(
            app,
            GitTrendsDatabase::class.java,
            name = GitTrendsDatabase::class.simpleName
        ).build()
    }

    @Provides
    @Singleton
    fun provideDao(database: GitTrendsDatabase): TrendingRepoListDao {
        return database.trendingRepoListDao()
    }


}