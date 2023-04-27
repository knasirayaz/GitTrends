package com.knasirayaz.gittrends.presentation.home

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onLast
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.knasirayaz.gittrends.R.*
import com.knasirayaz.gittrends.domain.models.TrendingListItem

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TrendingRepoListScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    lateinit var context: android.content.Context


    @Before
    fun setUp() {
        context = composeTestRule.activity
    }

    @Test
    fun verify_all_screen_items_are_present() {
        val mCurrentTrendingListItem =
            TrendingListItem(
                owner = TrendingListItem.Owner(
                    userProfilePicture = "profilePicture",
                    userName = "TestName-1"
                ),
                repoName = "Kotlin-DSL",
                repoDesc = "The Kotlin DSL Plugin provides a convenient way to develop Kotlin-based projects that contribute build logic",
                repoLanguage = "Kotlin",
                starsCount = "5000"
            )

        composeTestRule.activity.setContent {
            TrendingRepoListScreen(
                arrayListOf(mCurrentTrendingListItem)
            )
        }


        composeTestRule.onNodeWithTag(context.getString(string.tt_app_bar)).assertExists()
        composeTestRule.onNodeWithContentDescription(context.getString(string.tt_menu_button))
            .assertExists()

        composeTestRule
            .onNodeWithTag(context.getString(string.tt_trending_list))
            .assertExists()

        composeTestRule
            .onNodeWithTag(context.getString(string.tt_list_item))
            .assertExists()
        composeTestRule
            .onNodeWithContentDescription(context.getString(string.tt_profile_picture))
            .assertExists()

        composeTestRule
            .onNodeWithTag(context.getString(string.tt_user_name))
            .assertExists()

        composeTestRule
            .onNodeWithTag(context.getString(string.tt_user_name))
            .assert(hasText(mCurrentTrendingListItem.owner.userName))

        composeTestRule
            .onNodeWithTag(context.getString(string.tt_repo_name))
            .assertExists()

        composeTestRule
            .onNodeWithTag(context.getString(string.tt_repo_desc))
            .assertExists()

        composeTestRule
            .onNodeWithTag(context.getString(string.tt_language_icon))
            .assertExists()
        composeTestRule
            .onNodeWithTag(context.getString(string.tt_repo_language))
            .assertExists()

        composeTestRule
            .onNodeWithContentDescription(context.getString(string.tt_stars_icon))
            .assertExists()

        composeTestRule
            .onNodeWithTag(context.getString(string.tt_repo_stars))
            .assertExists()
    }

    @Test
    fun should_not_list_items_when_list_is_empty() {
        composeTestRule.activity.setContent {
            TrendingRepoListScreen(
                emptyList()
            )
        }

        composeTestRule
            .onNodeWithTag(context.getString(string.tt_trending_list))
            .onChildren()
            .assertCountEquals(0)
    }

    @Test
    fun should_show_list_when_list_is_not_empty() {
        composeTestRule.activity.setContent {
            TrendingRepoListScreen(
                listOf(
                    TrendingListItem(
                        owner = TrendingListItem.Owner(userProfilePicture = "profilePicture",
                            userName = "TestName-1"),
                        repoName = "Kotlin-DSL",
                        repoDesc = "The Kotlin DSL Plugin provides a convenient way to develop Kotlin-based projects that contribute build logic",
                        repoLanguage = "Kotlin",
                        starsCount = "5000"
                    )
                )
            )
        }

        composeTestRule
            .onNodeWithTag(context.getString(string.tt_trending_list))
            .onChildren()
            .assertCountEquals(1)

    }

    @Test
    fun should_hide_desc_when_desc_is_null() {
        composeTestRule.activity.setContent {
            TrendingRepoListScreen(
                //On Trending List at Index 1, Description is null on sample data.
                listOf(
                    TrendingListItem(
                        owner = TrendingListItem.Owner(
                            userProfilePicture = "profilePicture",
                            userName = "TestName-2"
                        ),
                        repoName = "Kotlin-DSL",
                        repoDesc = null,
                        repoLanguage = "Kotlin",
                        starsCount = "5000"
                    )
                )
            )
        }

        composeTestRule.apply {
            onNodeWithTag(context.getString(string.tt_repo_desc))
                .assertDoesNotExist()
        }
    }

    @Test
    fun should_hide_language_icon_and_text_when_it_is_null_or_empty() {
        composeTestRule.activity.setContent {
            TrendingRepoListScreen(
                listOf(
                    TrendingListItem(
                        owner = TrendingListItem.Owner(
                            userProfilePicture = "profilePicture",
                            userName = "TestName-3"
                        ),
                        repoName = "Kotlin-DSL",
                        repoDesc = "The Kotlin DSL Plugin provides a convenient way to develop Kotlin-based projects that contribute build logic",
                        repoLanguage = null,
                        starsCount = "5000"
                    )
                )
            )
        }

        composeTestRule.apply {
            onNodeWithTag(context.getString(string.tt_language_with_shape))
                .assertDoesNotExist()
        }
    }

    @Test
    fun should_hide_stars_icon_and_text_when_it_is_null_or_empty() {
        composeTestRule.activity.setContent {
            TrendingRepoListScreen(
                listOf(
                    TrendingListItem(
                        owner = TrendingListItem.Owner(
                            userProfilePicture = "profilePicture",
                            userName = "TestName-4"
                        ),
                        repoName = "Kotlin-DSL",
                        repoDesc = "The Kotlin DSL Plugin provides a convenient way to develop Kotlin-based projects that contribute build logic",
                        repoLanguage = "Kotlin",
                        starsCount = null
                    )
                )
            )
        }

        composeTestRule.apply {
            onNodeWithTag(context.getString(string.tt_star_text_and_icon))
                .assertDoesNotExist()
        }
    }
}