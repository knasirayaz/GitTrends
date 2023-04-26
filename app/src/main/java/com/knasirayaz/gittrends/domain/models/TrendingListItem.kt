package com.knasirayaz.gittrends.domain.models

import com.google.gson.annotations.SerializedName

data class TrendingListItem(
    @SerializedName("name")
    val repoName : String,
    @SerializedName("description")
    val repoDesc : String?,
    @SerializedName("language")
    val repoLanguage : String?,
    @SerializedName("stargazers_count")
    val starsCount : String?,
    val owner: Owner) {

    var isLanguageVisible = !(repoLanguage.isNullOrEmpty())
    var isStarVisible = !(starsCount.isNullOrEmpty())
    var isDescVisible = !(repoDesc.isNullOrEmpty())

    data class Owner(
        @SerializedName("avatar_url")
        val userProfilePicture: String,
        @SerializedName("login")
        val userName: String
    )
}
