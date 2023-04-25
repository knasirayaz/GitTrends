@file:OptIn(ExperimentalMaterial3Api::class)

package com.knasirayaz.gittrends.presentation.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.knasirayaz.gittrends.R
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
                    TrendingRepoListScreen()
                }
            }
        }
    }
}

@Composable
fun TrendingRepoListScreen() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { AppBar() },
        content = { topBarPaddingValues ->
            Column(Modifier.padding(topBarPaddingValues)) {

            }
        })

}

@Composable
fun AppBar() {
    CenterAlignedTopAppBar(
        title = { Text(text = stringResource(R.string.title_home_screen)) },
        modifier = Modifier.testTag(stringResource(R.string.testtag_app_bar)),
        actions = {
            Icon(imageVector = Icons.Filled.MoreVert, contentDescription = stringResource(id = R.string.tt_menu_button))
        }
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GitTrendsTheme {
        TrendingRepoListScreen()
    }
}