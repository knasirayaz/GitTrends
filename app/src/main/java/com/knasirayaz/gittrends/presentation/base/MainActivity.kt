@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.knasirayaz.gittrends.presentation.base

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.knasirayaz.gittrends.domain.models.TrendingListItem
import com.knasirayaz.gittrends.presentation.home.TrendingRepoListScreen
import com.knasirayaz.gittrends.presentation.home.TrendingRepoListViewModel
import com.knasirayaz.gittrends.presentation.ui.theme.GitTrendsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GitTrendsTheme(dynamicColor = false) {
                val viewModel = viewModel<TrendingRepoListViewModel>()
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TrendingRepoListScreen(viewModel)
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewScreen() {
    GitTrendsTheme {

    }
}

object SampleData{
    fun getTrendingListItems(): ArrayList<TrendingListItem> {
        val mTrendingListItems: ArrayList<TrendingListItem> = ArrayList()

        mTrendingListItems.add(
            TrendingListItem(
                owner = TrendingListItem.Owner(userProfilePicture = "profilePicture",
                    userName = "TestName"),
                repoName = "Kotlin-DSL",
                repoDesc = "The Kotlin DSL Plugin provides a convenient way to develop Kotlin-based projects that contribute build logic",
                repoLanguage = "Kotlin",
                starsCount = "5000"
            )
        )

        mTrendingListItems.add(
            TrendingListItem(
                owner = TrendingListItem.Owner(userProfilePicture = "profilePicture",
                    userName = "TestName"),
                repoName = "Kotlin-DSL",
                repoDesc = null,
                repoLanguage = "Kotlin",
                starsCount = "5000"
            )
        )

        mTrendingListItems.add(
            TrendingListItem(
                owner = TrendingListItem.Owner(userProfilePicture = "profilePicture",
                    userName = "TestName"),
                repoName = "Kotlin-DSL",
                repoDesc = "The Kotlin DSL Plugin provides a convenient way to develop Kotlin-based projects that contribute build logic",
                repoLanguage = null,
                starsCount = "5000"
            )
        )

        mTrendingListItems.add(
            TrendingListItem(
                owner = TrendingListItem.Owner(userProfilePicture = "profilePicture",
                    userName = "TestName"),
                repoName = "Kotlin-DSL",
                repoDesc = "The Kotlin DSL Plugin provides a convenient way to develop Kotlin-based projects that contribute build logic",
                repoLanguage = "Kotlin",
                starsCount = null
            )
        )
        return mTrendingListItems
    }

}