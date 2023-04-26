package com.knasirayaz.gittrends.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.knasirayaz.gittrends.domain.common.ResultStates
import com.knasirayaz.gittrends.domain.models.TrendingListItem
import com.knasirayaz.gittrends.domain.repository.TrendingRepoListRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TrendingRepoListViewModel(private val repository : TrendingRepoListRepository) : ViewModel(){

    private val trendingListLiveData = MutableLiveData<ResultStates<TrendingListItem?>>()

    fun getTrendingListObserver() : LiveData<ResultStates<TrendingListItem?>>{
        return trendingListLiveData
    }

    fun getTrendingRepoList() {
        viewModelScope.launch {
            trendingListLiveData.value = ResultStates.Loading(isLoading = true)
            trendingListLiveData.value = repository.getRepoList()
            trendingListLiveData.value = ResultStates.Loading(isLoading = false)
        }

    }

}
