package com.knasirayaz.gittrends.presentation.home

import androidx.activity.compose.setContent
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import com.knasirayaz.gittrends.R.*
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.manipulation.Ordering.Context

class TrendingRepoListScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    lateinit var context : android.content.Context

    @Before
    fun setUp() {
        context = composeTestRule.activity
        composeTestRule.activity.setContent {
            TrendingRepoListScreen()
        }
    }

    @Test
    fun app_bar_is_present(){
        composeTestRule.onNodeWithTag(context.getString(string.testtag_app_bar)).assertExists()
        composeTestRule.onNodeWithContentDescription(context.getString(string.tt_menu_button)).assertExists()
    }




}