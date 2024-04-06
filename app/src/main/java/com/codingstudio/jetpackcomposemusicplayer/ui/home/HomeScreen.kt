package com.codingstudio.jetpackcomposemusicplayer.ui.home

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.codingstudio.jetpackcomposemusicplayer.ui.home.component.MusicItem
import com.codingstudio.jetpackcomposemusicplayer.ui.home.component.HomeAppBar
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.navigation.NavHostController
import com.codingstudio.jetpackcomposemusicplayer.ui.navigation.Destination

@Composable
fun HomeScreen(
    onEvent: (HomeEvent) -> Unit,
    uiState: HomeUiState,
    navController: NavHostController
) {

    Surface (modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            val appBarColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.87f)

            Spacer(modifier = Modifier
                .background(appBarColor)
                .fillMaxWidth()
                .windowInsetsTopHeight(WindowInsets.statusBars)
            )

            HomeAppBar(
                backgroundColor = appBarColor,
                modifier = Modifier.fillMaxWidth()
            )

            with(uiState) {
                when {
                    loading == true -> {
                        Box(modifier = Modifier.fillMaxSize()) {
                            CircularProgressIndicator(
                                color = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier
                                    .size(100.dp)
                                    .fillMaxHeight()
                                    .align(Alignment.Center)
                                    .padding(
                                        top = 16.dp,
                                        start = 16.dp,
                                        end = 16.dp,
                                        bottom = 16.dp
                                    )
                            )
                        }
                    }

                    loading == false && errorMessage == null -> {
                        if (songs != null) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize(),
                                contentAlignment = Alignment.BottomCenter
                            )
                            {
                                LazyColumn(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(MaterialTheme.colorScheme.background)
                                        .align(Alignment.TopCenter),
                                    contentPadding = PaddingValues(bottom = 60.dp)
                                ) {

                                    items(songs) { _songs ->
                                        MusicItem(
                                            onClick = {
                                                onEvent(HomeEvent.OnSongSelected(_songs))
                                                onEvent(HomeEvent.PlaySong)
                                                //navController.navigate(Destination.songScreen)
                                            },
                                            song = _songs
                                        )
                                    }
                                }
                            }
                        }
                    }

                    errorMessage != null -> {

                    }

                }
            }

        }
    }



}