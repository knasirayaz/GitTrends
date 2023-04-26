package com.knasirayaz.gittrends.domain.models

data class GetTrendingRepoListResponse(
    val incomplete_results: Boolean,
    val items: List<TrendingListItem>?,
    val total_count: Int = 0
)