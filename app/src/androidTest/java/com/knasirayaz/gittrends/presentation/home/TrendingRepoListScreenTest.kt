package com.knasirayaz.gittrends.presentation.home

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertAll
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertTextContains
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
        composeTestRule.activity.setContent {
            TrendingRepoListScreen(
                getTrendingListItems()
            )
        }
        context = composeTestRule.activity
    }

    //Sample data to test functionality.
    private fun getTrendingListItems(): ArrayList<TrendingListItem> {
        val mTrendingListItems: ArrayList<TrendingListItem> = ArrayList()

        mTrendingListItems.add(
           TrendingListItem(
               owner = TrendingListItem.Owner(userProfilePicture = "profilePicture",
    userName = "TestName-1"),
                repoName = "Kotlin-DSL",
                repoDesc = "The Kotlin DSL Plugin provides a convenient way to develop Kotlin-based projects that contribute build logic",
                repoLanguage = "Kotlin",
                starsCount = "5000"
            )
        )

        mTrendingListItems.add(
           TrendingListItem(
               owner = TrendingListItem.Owner(userProfilePicture = "profilePicture",
    userName = "TestName-2"),
                repoName = "Kotlin-DSL",
                repoDesc = null,
                repoLanguage = "Kotlin",
                starsCount = "5000"
            )
        )

        mTrendingListItems.add(
           TrendingListItem(
               owner = TrendingListItem.Owner(userProfilePicture = "profilePicture",
    userName = "TestName-3"),
                repoName = "Kotlin-DSL",
                repoDesc = "The Kotlin DSL Plugin provides a convenient way to develop Kotlin-based projects that contribute build logic",
                repoLanguage = null,
                starsCount = "5000"
            )
        )

        mTrendingListItems.add(
           TrendingListItem(
               owner = TrendingListItem.Owner(userProfilePicture = "profilePicture",
    userName = "TestName-4"),
                repoName = "Kotlin-DSL",
                repoDesc = "The Kotlin DSL Plugin provides a convenient way to develop Kotlin-based projects that contribute build logic",
                repoLanguage = "Kotlin",
                starsCount = null
            )
        )
        return mTrendingListItems
    }

    @Test
    fun app_bar_is_present() {

        composeTestRule.onNodeWithTag(context.getString(string.tt_app_bar)).assertExists()
        composeTestRule.onNodeWithContentDescription(context.getString(string.tt_menu_button))
            .assertExists()
    }

    @Test
    fun trending_list_is_present() {
        composeTestRule.activity.setContent {
            TrendingRepoListScreen(
                getTrendingListItems()
            )
        }
        composeTestRule
            .onNodeWithTag(context.getString(string.tt_trending_list))
            .assertExists()
    }

    @Test
    fun trending_list_items_are_present() {
        val mCurrentTrendingListItem = getTrendingListItems().first()

        composeTestRule.activity.setContent {
            TrendingRepoListScreen(
                arrayListOf(mCurrentTrendingListItem)
            )
        }


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
                getTrendingListItems()
            )
        }

        composeTestRule
            .onNodeWithTag(context.getString(string.tt_trending_list))
            .onChildren()
            .assertCountEquals(getTrendingListItems().size)

    }

    @Test
    fun verify_first_and_last_items_are_available() {
        composeTestRule.apply {
            onNodeWithTag(context.getString(string.tt_trending_list))
                .onChildren()
                .onFirst()
                .onChildAt(1)
                .assert(hasText(getTrendingListItems().first().owner.userName))
        }

        composeTestRule.apply {
            onNodeWithTag(context.getString(string.tt_trending_list))
                .onChildren()
                .onLast()
                .onChildAt(1)
                .assert(hasText(getTrendingListItems().last().owner.userName))
        }

    }

    @Test
    fun should_hide_desc_when_desc_is_null(){
        composeTestRule.activity.setContent {
            TrendingRepoListScreen(
                //On Trending List at Index 1, Description is null on sample data.
                listOf(getTrendingListItems()[1])
            )
        }

        composeTestRule.apply {
            onNodeWithTag(context.getString(string.tt_repo_desc))
                .assertDoesNotExist()
        }
    }

    @Test
    fun should_hide_language_icon_and_text_when_it_is_null_or_empty(){
        composeTestRule.activity.setContent {

            //On Trending List at Index 2, Language is null on sample data.
            TrendingRepoListScreen(
                listOf(getTrendingListItems()[2])
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

            //On Trending List at Index 3, 'Star Count' is null on sample data provided above for test cases.
            TrendingRepoListScreen(
                listOf(getTrendingListItems()[3])
            )
        }

        composeTestRule.apply {
            onNodeWithTag(context.getString(string.tt_star_text_and_icon))
                .assertDoesNotExist()
        }
    }
}