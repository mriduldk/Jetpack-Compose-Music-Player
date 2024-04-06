package com.codingstudio.jetpackcomposemusicplayer.ui

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import com.codingstudio.jetpackcomposemusicplayer.R
import com.codingstudio.jetpackcomposemusicplayer.ui.home.HomeEvent
import com.codingstudio.jetpackcomposemusicplayer.ui.home.HomeScreen
import com.codingstudio.jetpackcomposemusicplayer.ui.home.HomeViewModel
import com.codingstudio.jetpackcomposemusicplayer.ui.home.component.HomeBottomBar
import com.codingstudio.jetpackcomposemusicplayer.ui.navigation.Destination
import com.codingstudio.jetpackcomposemusicplayer.ui.songscreeen.SongScreen
import com.codingstudio.jetpackcomposemusicplayer.ui.songscreeen.SongViewModel
import com.codingstudio.jetpackcomposemusicplayer.ui.viewmodels.SharedViewModel


@Composable
fun MusicPlayerApp(sharedViewModel: SharedViewModel) {
    val navController = rememberNavController()
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
                HomeBottomBar(
                    modifier = Modifier
                        .align(Alignment.BottomCenter),
                    onEvent = mainViewModel::onEvent,
                    song = musicControllerUiState.currentSong,
                    playerState = musicControllerUiState.playerState,
                    onBarClick = { navController.navigate(Destination.songScreen) }

                )
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