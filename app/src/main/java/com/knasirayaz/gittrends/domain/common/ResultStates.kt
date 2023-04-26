package com.knasirayaz.gittrends.domain.common

import com.knasirayaz.gittrends.domain.models.TrendingListItem

sealed class ResultStates<out T> {
    data class Success<out R>(val data: R) : ResultStates<R>()
    data class Loading(val isLoading: Boolean) : ResultStates<Nothing>()


}
