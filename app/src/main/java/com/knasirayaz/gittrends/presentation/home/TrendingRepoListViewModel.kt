package com.knasirayaz.gittrends.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.knasirayaz.gittrends.domain.common.ResultStates
import com.knasirayaz.gittrends.domain.models.TrendingListItem
import com.knasirayaz.gittrends.domain.repository.TrendingRepoListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrendingRepoListViewModel @Inject constructor(private var repository : TrendingRepoListRepository) : ViewModel(){


    private val trendingListLiveData = MutableLiveData<ResultStates<List<TrendingListItem>>>()

    fun getTrendingListObserver() : LiveData<ResultStates<List<TrendingListItem>>>{
        return trendingListLiveData.also {
            getTrendingRepoList()
        }
    }

    fun getTrendingRepoList() {
        viewModelScope.launch {
            trendingListLiveData.value = ResultStates.Loading(isLoading = true)
            trendingListLiveData.postValue(repository.getRepoList())
            trendingListLiveData.value = ResultStates.Loading(isLoading = false)
        }

    }

}
