package com.knasirayaz.gittrends.domain.models

data class TrendingListItem(
    val userProfilePicture: String,
    val userName : String,
    val repoName : String,
    val repoDesc : String?,
    val repoLanguage : String?,
    val starsCount : String?) {
    var isLanguageVisible = !(repoLanguage.isNullOrEmpty())
    var isStarVisible = !(starsCount.isNullOrEmpty())
    var isDescVisible = !(repoDesc.isNullOrEmpty())

}