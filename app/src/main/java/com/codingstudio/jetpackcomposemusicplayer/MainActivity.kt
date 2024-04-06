package com.codingstudio.jetpackcomposemusicplayer

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.codingstudio.jetpackcomposemusicplayer.data.service.MusicService
import com.codingstudio.jetpackcomposemusicplayer.ui.MusicPlayerApp
import com.codingstudio.jetpackcomposemusicplayer.ui.theme.JetpackComposeMusicPlayerTheme
import com.codingstudio.jetpackcomposemusicplayer.ui.theme.MusicPlayerTheme
import com.codingstudio.jetpackcomposemusicplayer.ui.viewmodels.SharedViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val sharedViewModel: SharedViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            /*val systemUiController = rememberSystemUiController()
            val useDarkIcon = !isSystemInDarkTheme()

            SideEffect {
                systemUiController.setSystemBarsColor(
                    color = Color.Transparent,
                    darkIcons = useDarkIcon
                )
            }

            MusicPlayerTheme {
                MusicPlayerApp(
                    sharedViewModel = sharedViewModel
                )
            }*/


            JetpackComposeMusicPlayerTheme {

                MusicPlayerTheme {
                    MusicPlayerApp(
                        sharedViewModel = sharedViewModel
                    )
                }

            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()

        sharedViewModel.destroyMediaController()
        stopService(Intent(this, MusicService::class.java))
    }
}
