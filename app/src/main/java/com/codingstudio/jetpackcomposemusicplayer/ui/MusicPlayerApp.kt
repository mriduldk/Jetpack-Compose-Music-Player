package com.codingstudio.jetpackcomposemusicplayer.ui

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.Scaffold
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.codingstudio.jetpackcomposemusicplayer.R
import com.codingstudio.jetpackcomposemusicplayer.ui.home.HomeEvent
import com.codingstudio.jetpackcomposemusicplayer.ui.home.HomeScreen
import com.codingstudio.jetpackcomposemusicplayer.ui.home.HomeScreenTopTracks
import com.codingstudio.jetpackcomposemusicplayer.ui.home.HomeViewModel
import com.codingstudio.jetpackcomposemusicplayer.ui.home.component.HomeBottomBar
import com.codingstudio.jetpackcomposemusicplayer.ui.navigation.Destination
import com.codingstudio.jetpackcomposemusicplayer.ui.navigation.SongNavigationBar
import com.codingstudio.jetpackcomposemusicplayer.ui.songscreeen.SongScreen
import com.codingstudio.jetpackcomposemusicplayer.ui.songscreeen.SongViewModel
import com.codingstudio.jetpackcomposemusicplayer.ui.viewmodels.SharedViewModel


@Composable
fun MusicPlayerApp(sharedViewModel: SharedViewModel) {
    val navController = rememberNavController()

    /*Scaffold (
        bottomBar = { SongNavigationBar(navController) },
        content = { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)){
                MusicPlayerNavHost(
                    navController = navController,
                    sharedViewModel = sharedViewModel
                )
            }
        }
    )*/

    MusicPlayerNavHost(
        navController = navController,
        sharedViewModel = sharedViewModel
    )


}


@Composable
fun MusicPlayerNavHost(navController: NavHostController, sharedViewModel: SharedViewModel) {

    val musicControllerUiState = sharedViewModel.musicControllerUiState
    val activity = (LocalContext.current as ComponentActivity)

    NavHost(navController = navController, startDestination = Destination.home) {

        composable(route = Destination.home) {

            val mainViewModel: HomeViewModel = hiltViewModel()
            val isInitialized = rememberSaveable { mutableStateOf(false) }

            if (!isInitialized.value) {
                LaunchedEffect(key1 = Unit) {
                    mainViewModel.onEvent(HomeEvent.FetchSong)
                    isInitialized.value = true
                }
            }

            Box(modifier = Modifier.fillMaxSize()) {
                HomeScreen(
                    onEvent = mainViewModel::onEvent,
                    uiState = mainViewModel.homeUiState,
                    navController = navController
                )

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    HomeBottomBar(
                        onEvent = mainViewModel::onEvent,
                        song = musicControllerUiState.currentSong,
                        playerState = musicControllerUiState.playerState,
                        onBarClick = { navController.navigate(Destination.songScreen) }

                    )

                    SongNavigationBar(
                        navController,
                    )
                }


            }


            /*ConstraintLayout (
                modifier = Modifier.fillMaxSize()
            ) {
                val ( homeScreen, bottomNav ) = createRefs()

                HomeScreen(
                    modifier = Modifier.constrainAs(homeScreen) {
                        start.linkTo(parent.start, 16.dp)
                        end.linkTo(parent.end, 16.dp)
                        top.linkTo(parent.top, 16.dp)
                        bottom.linkTo(bottomNav.top)
                    },
                    onEvent = mainViewModel::onEvent,
                    uiState = mainViewModel.homeUiState,
                    navController = navController,

                )


                Column(
                    modifier = Modifier.constrainAs(bottomNav) {
                        linkTo(
                            start = parent.start,
                            end = parent.end,
                            startMargin = 0.dp,
                            endMargin = 0.dp,
                            bias = 0f
                        )
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.preferredWrapContent
                    },
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    HomeBottomBar(
                        onEvent = mainViewModel::onEvent,
                        song = musicControllerUiState.currentSong,
                        playerState = musicControllerUiState.playerState,
                        onBarClick = { navController.navigate(Destination.songScreen) }

                    )

                    SongNavigationBar(
                        navController,
                    )
                }


            }*/


        }

        composable(route = Destination.topTracks) {

            val mainViewModel: HomeViewModel = hiltViewModel()
            val isInitialized = rememberSaveable { mutableStateOf(false) }

            if (!isInitialized.value) {
                LaunchedEffect(key1 = Unit) {
                    mainViewModel.onEvent(HomeEvent.FetchSongTopTracks)
                    isInitialized.value = true
                }
            }

            Box(modifier = Modifier.fillMaxSize()) {
                HomeScreenTopTracks(
                    onEvent = mainViewModel::onEvent,
                    uiState = mainViewModel.homeUiState,
                    navController = navController
                )

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    HomeBottomBar(
                        onEvent = mainViewModel::onEvent,
                        song = musicControllerUiState.currentSong,
                        playerState = musicControllerUiState.playerState,
                        onBarClick = { navController.navigate(Destination.songScreen) }

                    )

                    SongNavigationBar(
                        navController,
                    )
                }

                /*HomeBottomBar(
                    modifier = Modifier
                        .align(Alignment.BottomCenter),
                    onEvent = mainViewModel::onEvent,
                    song = musicControllerUiState.currentSong,
                    playerState = musicControllerUiState.playerState,
                    onBarClick = { navController.navigate(Destination.songScreen) }

                )

                SongNavigationBar(
                    navController,
                    modifier = Modifier
                        .align(Alignment.BottomCenter),
                )*/

            }

        }

        composable(route = Destination.songScreen) {
            val songViewModel: SongViewModel = hiltViewModel()
            SongScreen(
                onEvent = songViewModel::onEvent,
                musicControllerUiState = musicControllerUiState,
                onNavigateUp = { navController.navigateUp() }
            )
        }

    }


}