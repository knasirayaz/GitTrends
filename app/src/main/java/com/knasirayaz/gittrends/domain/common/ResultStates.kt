package com.knasirayaz.gittrends.domain.common

import com.knasirayaz.gittrends.domain.models.TrendingListItem

sealed class ResultStates {
    data class Success(val listOf: List<TrendingListItem>) : ResultStates()
    data class Loading(val isLoading: Boolean) : ResultStates()


}
