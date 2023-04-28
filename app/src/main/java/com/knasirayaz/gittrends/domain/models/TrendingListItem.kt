package com.knasirayaz.gittrends.domain.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class TrendingListItem(
    @PrimaryKey
    val id: Int,
    @SerializedName("name")
    val repoName : String,
    @SerializedName("description")
    val repoDesc : String?,
    @SerializedName("language")
    val repoLanguage : String?,
    @SerializedName("stargazers_count")
    val starsCount : String?,
    @Embedded val owner: Owner) {

    val isLanguageVisible
        get() = !(repoLanguage.isNullOrEmpty())
    val isStarVisible
        get() = !(starsCount.isNullOrEmpty())
    val isDescVisible
        get() = !(repoDesc.isNullOrEmpty())

    data class Owner(
        @SerializedName("avatar_url")
        val userProfilePicture: String,
        @SerializedName("login")
        val userName: String
    )
}
