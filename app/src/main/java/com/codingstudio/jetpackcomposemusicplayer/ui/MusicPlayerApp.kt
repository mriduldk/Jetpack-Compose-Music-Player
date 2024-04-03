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
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.codingstudio.jetpackcomposemusicplayer.ui.home.HomeEvent
import com.codingstudio.jetpackcomposemusicplayer.ui.home.HomeScreen
import com.codingstudio.jetpackcomposemusicplayer.ui.home.HomeViewModel
import com.codingstudio.jetpackcomposemusicplayer.ui.navigation.Destination


@Composable
fun MusicPlayerApp() {
    val navController = rememberNavController()
    MusicPlayerNavHost(
        navController = navController
    )
}


@Composable
fun MusicPlayerNavHost(navController: NavHostController) {

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
                    uiState = mainViewModel.homeUiState
                )
            }

        }

    }


}