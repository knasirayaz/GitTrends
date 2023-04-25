@file:OptIn(ExperimentalMaterial3Api::class)

package com.knasirayaz.gittrends.presentation.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.knasirayaz.gittrends.R
import com.knasirayaz.gittrends.domain.models.TrendingListItem
import com.knasirayaz.gittrends.presentation.ui.theme.GitTrendsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GitTrendsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TrendingRepoListScreen(getTrendingListItems())
                }
            }
        }
    }
}

private fun getTrendingListItems(): ArrayList<TrendingListItem> {
    val mTrendingListItems: ArrayList<TrendingListItem> = ArrayList()

    mTrendingListItems.add(
        TrendingListItem(
            userProfilePicture = "profilePicture",
            userName = "TestName",
            repoName = "Kotlin-DSL",
            repoDesc = "The Kotlin DSL Plugin provides a convenient way to develop Kotlin-based projects that contribute build logic",
            repoLanguage = "Kotlin",
            starsCount = "5000"
        )
    )

    mTrendingListItems.add(
        TrendingListItem(
            userProfilePicture = "profilePicture",
            userName = "TestName",
            repoName = "Kotlin-DSL",
            repoDesc = null,
            repoLanguage = "Kotlin",
            starsCount = "5000"
        )
    )

    mTrendingListItems.add(
        TrendingListItem(
            userProfilePicture = "profilePicture",
            userName = "TestName",
            repoName = "Kotlin-DSL",
            repoDesc = "The Kotlin DSL Plugin provides a convenient way to develop Kotlin-based projects that contribute build logic",
            repoLanguage = null,
            starsCount = "5000"
        )
    )

    mTrendingListItems.add(
        TrendingListItem(
            userProfilePicture = "profilePicture",
            userName = "TestName",
            repoName = "Kotlin-DSL",
            repoDesc = "The Kotlin DSL Plugin provides a convenient way to develop Kotlin-based projects that contribute build logic",
            repoLanguage = "Kotlin",
            starsCount = null
        )
    )
    return mTrendingListItems
}

@Composable
fun TrendingRepoListScreen(trendingListItem: List<TrendingListItem>) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { AppBar() },
        content = { topBarPaddingValues ->
            Column(Modifier.padding(topBarPaddingValues)) {
                LazyColumn(
                    modifier = Modifier.testTag(stringResource(id = R.string.tt_trending_list)),
                    content = {
                        items(items = trendingListItem) {
                            TrendingListItem(it)
                        }
                    })
            }
        })

}

@Composable
fun TrendingListItem(mTrendingListItem: TrendingListItem) {
    Row(Modifier.testTag(stringResource(id = R.string.tt_list_item))) {
        ProfilePictureView(mTrendingListItem.userProfilePicture)
        ProfileDetailsView(mTrendingListItem)
    }
}

@Composable
fun ProfileDetailsView(mTrendingListItem: TrendingListItem) {
    Column {
        NameView(mTrendingListItem.userName)
        RepoNameView(mTrendingListItem.repoName)
        RepoDescView(mTrendingListItem)
        Row {
            LanguageView(mTrendingListItem)
            StarsView(mTrendingListItem)
        }

    }
}

@Composable
private fun RepoDescView(mTrendingListItem: TrendingListItem) {
    if (mTrendingListItem.isDescVisible)
        Text(
            text = mTrendingListItem.repoDesc.toString(),
            modifier = Modifier.testTag(stringResource(id = R.string.tt_repo_desc))
        )
}

@Composable
private fun RepoNameView(mRepoName: String) {
    Text(
        text = mRepoName,
        modifier = Modifier.testTag(stringResource(id = R.string.tt_repo_name))
    )
}

@Composable
private fun NameView(mUserName: String) {
    Text(
        text = mUserName,
        modifier = Modifier.testTag(stringResource(id = R.string.tt_user_name))
    )
}

@Composable
fun StarsView(mTrendingListItem: TrendingListItem) {
    if (mTrendingListItem.isStarVisible)
        Row(Modifier.testTag(stringResource(id = R.string.tt_star_text_and_icon))) {
            Image(
                imageVector = Icons.Filled.Star,
                contentDescription = stringResource(id = R.string.tt_stars_icon)
            )
            Text(
                text = mTrendingListItem.starsCount.toString(),
                modifier = Modifier.testTag(stringResource(id = R.string.tt_repo_stars))
            )
        }
}

@Composable
fun LanguageView(mTrendingListItem: TrendingListItem) {
    if (mTrendingListItem.isLanguageVisible)
        Row(Modifier.testTag(stringResource(id = R.string.tt_language_with_shape))) {
            Box(
                modifier = Modifier
                    .size(15.dp)
                    .clip(CircleShape)
                    .background(Color.Blue)
                    .testTag(stringResource(id = R.string.tt_language_icon))
            )
            Text(
                text = mTrendingListItem.repoLanguage.toString(),
                modifier = Modifier.testTag(stringResource(id = R.string.tt_repo_language))
            )
        }

}

@Composable
fun ProfilePictureView(url: String) {
    Image(
        painter = painterResource(id = R.drawable.ic_launcher_background),
        contentDescription = stringResource(
            id = R.string.tt_profile_picture
        )
    )
}

@Composable
fun AppBar() {
    CenterAlignedTopAppBar(
        title = { Text(text = stringResource(R.string.title_home_screen)) },
        modifier = Modifier
            .testTag(stringResource(R.string.tt_app_bar))
            .shadow(5.dp),
        actions = {
            Icon(
                imageVector = Icons.Filled.MoreVert,
                contentDescription = stringResource(id = R.string.tt_menu_button)
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GitTrendsTheme {
        TrendingRepoListScreen(
            listOf(
                TrendingListItem(
                    userProfilePicture = "profilePicture",
                    userName = "TestName",
                    repoName = "Kotlin-DSL",
                    repoDesc = "The Kotlin DSL Plugin provides a convenient way to develop Kotlin-based projects that contribute build logic",
                    repoLanguage = "Kotlin",
                    starsCount = "5000"
                )
            )
        )
    }
}