@file:OptIn(ExperimentalMaterial3Api::class)

package com.knasirayaz.gittrends.presentation.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.knasirayaz.gittrends.R
import com.knasirayaz.gittrends.domain.common.ResultStates
import com.knasirayaz.gittrends.domain.models.TrendingListItem
import com.knasirayaz.gittrends.presentation.ui.theme.Golden


@Composable
fun TrendingRepoListScreen(viewModel: TrendingRepoListViewModel = hiltViewModel()) {

    val state by viewModel.getTrendingListObserver().observeAsState()

    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = { AppBar() },
        content = { topBarPaddingValues ->
            SwipeRefresh(
                modifier = Modifier.testTag(stringResource(id = R.string.tt_pull_to_refresh)),
                state = SwipeRefreshState(false),
                onRefresh = {
                    viewModel.getTrendingRepoList(true)
                }) {
                Column(Modifier.padding(topBarPaddingValues)) {
                    when(state){
                        is ResultStates.Success -> {
                            val data = (state as ResultStates.Success).data
                            ListView(data)
                        }
                        is ResultStates.Failed ->{
                            RetryView {
                                viewModel.getTrendingRepoList(true)
                            }
                        }
                        is ResultStates.Loading ->{
                            val isLoading = (state as ResultStates.Loading).isLoading
                            if(isLoading)
                                LoadingView()
                        }
                        else -> {}
                    }
                }
            }

        })

}

@Composable
fun RetryView(retry : () -> Unit) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.anim_retry))
    val progress by animateLottieCompositionAsState(composition, iterations = LottieConstants.IterateForever)
    Box(modifier = Modifier
        .fillMaxSize()
        .testTag(stringResource(id = R.string.tt_error_view))){
        Column (horizontalAlignment = Alignment.CenterHorizontally,  modifier = Modifier.fillMaxWidth()){
            Spacer(modifier = Modifier.height(50.dp))

            LottieAnimation(
                modifier = Modifier.align(CenterHorizontally),
                composition = composition,
                progress = { progress },
            )

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                modifier = Modifier.align(CenterHorizontally),
                text = stringResource(R.string.something_went_wrong),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.labelMedium,
            )

            Text(
                modifier = Modifier.align(CenterHorizontally),
                text = stringResource(R.string.an_alien_is_probably_blocking_your_signal),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.labelSmall)
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(start = 30.dp, end = 30.dp, bottom = 50.dp),
            border = BorderStroke(0.5.dp, Color.Green),
            shape = RoundedCornerShape(5.dp),
            onClick = {
                    retry.invoke()
            }, ) {
            Text(text = "RETRY", color = Color.Green,  style = MaterialTheme.typography.labelMedium)

        }
    }


}

@Composable
fun ListView(data: List<TrendingListItem>) {
    LazyColumn(modifier = Modifier.testTag(stringResource(id = R.string.tt_trending_list)),
        content = {
            items(items = data) {
                TrendingListItem(it)
            }
        })
}

@Composable
fun LoadingView() {
    LazyColumn(modifier = Modifier.testTag(stringResource(id = R.string.tt_loading_view)),
        content = {
            items(20) {
                ShimmerListItem()
            }
        })
}

@Composable
fun TrendingListItem(mTrendingListItem: TrendingListItem) {
    Row(
        Modifier
            .testTag(stringResource(id = R.string.tt_list_item))
            .padding(start = 20.dp, top = 20.dp, end = 10.dp)
    ) {
        ProfilePictureView(mTrendingListItem.owner.userProfilePicture)
        ProfileDetailsView(mTrendingListItem)
    }
    Divider(Modifier.padding(start = 20.dp, top = 20.dp))
}

@Composable
fun ProfileDetailsView(mTrendingListItem: TrendingListItem) {
    Column(Modifier.padding(start = 15.dp)) {
        NameView(mTrendingListItem.owner.userName)
        RepoNameView(mTrendingListItem.repoName)
        RepoDescView(mTrendingListItem)
        Spacer(Modifier.height(2.dp))
        Row {
            LanguageView(mTrendingListItem)
            StarsView(mTrendingListItem)
        }

    }
}

@Composable
private fun RepoDescView(mTrendingListItem: TrendingListItem) {
    if (mTrendingListItem.isDescVisible) Text(
        text = mTrendingListItem.repoDesc.toString(),
        modifier = Modifier.testTag(stringResource(id = R.string.tt_repo_desc)),
        color = MaterialTheme.colorScheme.onSurface,
        maxLines = 2,
        style = MaterialTheme.typography.labelSmall,
    )
}

@Composable
private fun RepoNameView(mRepoName: String) {
    Text(
        text = mRepoName,
        color = MaterialTheme.colorScheme.onBackground,
        maxLines = 1,
        style = MaterialTheme.typography.labelMedium,
        modifier = Modifier.testTag(stringResource(id = R.string.tt_repo_name))
    )
}

@Composable
private fun NameView(mUserName: String) {
    Text(
        text = mUserName,
        color = MaterialTheme.colorScheme.onSurface,
        maxLines = 1,
        style = MaterialTheme.typography.labelSmall,
        modifier = Modifier.testTag(stringResource(id = R.string.tt_user_name))
    )
}

@Composable
fun StarsView(mTrendingListItem: TrendingListItem) {
    if (mTrendingListItem.isStarVisible) Row(Modifier.testTag(stringResource(id = R.string.tt_star_text_and_icon))) {
        Icon(
            modifier = Modifier
                .size(15.dp)
                .align(Alignment.CenterVertically),
            imageVector = Icons.Filled.Star,
            tint = Golden,
            contentDescription = stringResource(id = R.string.tt_stars_icon)
        )
        Spacer(Modifier.width(3.dp))
        Text(
            text = mTrendingListItem.starsCount.toString(),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.testTag(stringResource(id = R.string.tt_repo_stars))

        )
    }
}

@Composable
fun LanguageView(mTrendingListItem: TrendingListItem) {
    if (mTrendingListItem.isLanguageVisible) Row(Modifier.testTag(stringResource(id = R.string.tt_language_with_shape))) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .clip(CircleShape)
                .background(Color.Blue)
                .testTag(stringResource(id = R.string.tt_language_icon))
                .align(Alignment.CenterVertically)
        )
        Spacer(Modifier.width(5.dp))
        Text(
            text = mTrendingListItem.repoLanguage.toString(),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.testTag(stringResource(id = R.string.tt_repo_language))
        )
        Spacer(Modifier.width(8.dp))
    }

}

@Composable
fun ProfilePictureView(url: String) {
    Box(modifier = Modifier.padding(top = 5.dp)) {
        AsyncImage(
            placeholder = painterResource(id = R.drawable.ic_launcher_background),
            model = url,
            contentDescription = stringResource(id = R.string.tt_profile_picture),
            modifier = Modifier
                .size(39.dp)
                .clip(CircleShape)
        )
    }

}

@Composable
fun AppBar() {
    CenterAlignedTopAppBar(title = {
        Text(
            text = stringResource(R.string.title_home_screen),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.labelLarge,
        )
    },
        modifier = Modifier
            .testTag(stringResource(R.string.tt_app_bar))
            .shadow(5.dp, spotColor = MaterialTheme.colorScheme.onPrimary),
        actions = {
            Icon(
                imageVector = Icons.Filled.MoreVert,
                contentDescription = stringResource(id = R.string.tt_menu_button)
            )
        })
}