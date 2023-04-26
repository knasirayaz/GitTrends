package com.knasirayaz.gittrends.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.knasirayaz.gittrends.domain.common.ResultStates
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TrendingRepoListViewModel : ViewModel(){

    private val trendingListLiveData = MutableLiveData<ResultStates>()

    fun getTrendingListObserver() : LiveData<ResultStates>{
        return trendingListLiveData
    }

    fun getTrendingRepoList() {
        viewModelScope.launch {
            trendingListLiveData.value = ResultStates.Loading(isLoading = true)
            trendingListLiveData.value = ResultStates.Success(listOf = emptyList())
            trendingListLiveData.value = ResultStates.Loading(isLoading = false)
        }

    }

}
