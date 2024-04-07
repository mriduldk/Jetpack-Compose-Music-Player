@file:OptIn(ExperimentalWearMaterialApi::class)

package com.codingstudio.jetpackcomposemusicplayer.ui.songscreeen

import android.graphics.Path.Direction
import android.view.HapticFeedbackConstants
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.FractionalThreshold
import androidx.wear.compose.material.rememberSwipeableState
import androidx.wear.compose.material.swipeable
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.codingstudio.jetpackcomposemusicplayer.R
import com.codingstudio.jetpackcomposemusicplayer.domain.model.Song
import com.codingstudio.jetpackcomposemusicplayer.other.MusicControllerUiState
import com.codingstudio.jetpackcomposemusicplayer.other.PlayerState
import com.codingstudio.jetpackcomposemusicplayer.other.toTime
import com.codingstudio.jetpackcomposemusicplayer.ui.home.HomeEvent
import com.codingstudio.jetpackcomposemusicplayer.ui.songscreeen.component.AnimatedVinyl

@Composable
fun SongScreen(
    onEvent: (SongEvent) -> Unit,
    musicControllerUiState: MusicControllerUiState,
    onNavigateUp: () -> Unit,
) {
    if (musicControllerUiState.currentSong != null) {

        SongScreenBody(
            song = musicControllerUiState.currentSong,
            onNavigateUp = onNavigateUp,
            musicControllerUiState = musicControllerUiState,
            onEvent = onEvent
        )

    }
}

@Composable
fun SongScreenBody(
    song: Song,
    onEvent: (SongEvent) -> Unit,
    musicControllerUiState: MusicControllerUiState,
    onNavigateUp: () -> Unit,
) {
    val swipeableState = rememberSwipeableState(initialValue = 0)
    val endAnchor = LocalConfiguration.current.screenHeightDp * LocalDensity.current.density
    val anchors = mapOf(
        0f to 0,
        endAnchor to 1,
    )

    val backgroundColor = MaterialTheme.colorScheme.background

    val dominantColor by remember { mutableStateOf(Color.Transparent) }

    val context = LocalContext.current

    val imagePainter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context).data("https://cms.samespace.com/assets/${song.cover}").crossfade(true).build()
    )

    val iconResId =
        if (musicControllerUiState.playerState == PlayerState.PLAYING) R.drawable.ic_round_pause else R.drawable.ic_round_play_arrow

    var offsetX by remember { mutableFloatStateOf(0f) }
    var offsetY by remember { mutableFloatStateOf(0f) }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .swipeable(
                state = swipeableState,
                anchors = anchors,
                thresholds = { _, _ -> FractionalThreshold(0.34f) },
                orientation = Orientation.Vertical
            )
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragEnd = {
                        when {
                            offsetY > 10 -> {
                                onNavigateUp()
                            }

                            offsetX > 0 -> {
                                onEvent(SongEvent.SkipToPreviousSong)
                            }

                            offsetX < 0 -> {
                                onEvent(SongEvent.SkipToNextSong)
                            }
                        }

                    },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        val (x, _) = dragAmount
                        offsetX = dragAmount.x
                        offsetY = dragAmount.y



                    }
                )
               /* detectHorizontalDragGestures { _, dragAmount ->
                    if (dragAmount > 100) {
                        onEvent(SongEvent.SkipToPreviousSong)
                    } else if (dragAmount < 100) {
                        onEvent(SongEvent.SkipToNextSong)
                    }
                }*/
            },
    ) {
        if (swipeableState.currentValue >= 1) {
            LaunchedEffect(key1 = Unit) {
                onNavigateUp()
            }
        }




        SongScreenContent(
            song = song,
            isSongPlaying = musicControllerUiState.playerState == PlayerState.PLAYING,
            imagePainter = imagePainter,
            dominantColor = dominantColor,
            currentTime = musicControllerUiState.currentPosition,
            totalTime = musicControllerUiState.totalDuration,
            playPauseIcon = iconResId,
            playOrToggleSong = {
                onEvent(if (musicControllerUiState.playerState == PlayerState.PLAYING) SongEvent.PauseSong else SongEvent.ResumeSong)
            },
            playNextSong = { onEvent(SongEvent.SkipToNextSong) },
            playPreviousSong = { onEvent(SongEvent.SkipToPreviousSong) },
            onSliderChange = { newPosition ->
                onEvent(SongEvent.SeekSongToPosition(newPosition.toLong()))
            },
            onForward = {
                onEvent(SongEvent.SeekSongToPosition(musicControllerUiState.currentPosition + 10 * 1000))
            },
            onRewind = {
                musicControllerUiState.currentPosition.let { currentPosition ->
                    onEvent(SongEvent.SeekSongToPosition(if (currentPosition - 10 * 1000 < 0) 0 else currentPosition - 10 * 1000))
                }
            },
            onClose = { onNavigateUp() }
        )
    }
}

@Composable
fun SongScreenContent(
    song: Song,
    isSongPlaying: Boolean,
    imagePainter: Painter,
    dominantColor: Color,
    currentTime: Long,
    totalTime: Long,
    @DrawableRes playPauseIcon: Int,
    playOrToggleSong: () -> Unit,
    playNextSong: () -> Unit,
    playPreviousSong: () -> Unit,
    onSliderChange: (Float) -> Unit,
    onRewind: () -> Unit,
    onForward: () -> Unit,
    onClose: () -> Unit
) {

    val sliderColors = if (isSystemInDarkTheme()) {
        SliderDefaults.colors(
            thumbColor = MaterialTheme.colorScheme.onBackground,
            activeTrackColor = MaterialTheme.colorScheme.onBackground,
            inactiveTrackColor = MaterialTheme.colorScheme.onBackground.copy(
                alpha = 0.24f
            ),
        )
    } else SliderDefaults.colors(
        thumbColor = MaterialTheme.colorScheme.onBackground,
        activeTrackColor = dominantColor,
        inactiveTrackColor = dominantColor.copy(
            alpha = 0.24f
        ),
    )

    val colorGradientEnd = if (isSystemInDarkTheme()) { Color.Black } else { Color.White}

    val gradient = Brush.verticalGradient(
        0.0f to Color(song.accent.toColorInt()),
        1.0f to colorGradientEnd,
        startY = 0.0f,
        endY = 1500.0f
    )

    val view = LocalView.current

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Surface {
            Box(
                modifier = Modifier
                    .background(gradient)
                    .fillMaxSize()
                    .systemBarsPadding()
            ) {
                Column {
                    IconButton(
                        onClick = onClose
                    ) {
                        Image(
                            imageVector = Icons.Rounded.KeyboardArrowDown,
                            contentDescription = "Close",
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                    }

                    Column(
                        modifier = Modifier.padding(horizontal = 24.dp)
                    ) {


                        Box(
                            modifier = Modifier
                                .padding(vertical = 32.dp)
                                .clip(MaterialTheme.shapes.medium)
                                .weight(1f, fill = false)
                                .aspectRatio(1f)

                        ) {

                            //AnimatedVinyl(painter = imagePainter, isSongPlaying = isSongPlaying)

                            Image(
                                painter = rememberAsyncImagePainter(
                                    ImageRequest.Builder(LocalContext.current)
                                        .data("https://cms.samespace.com/assets/${song.cover}")
                                        .crossfade(true).build()
                                ),
                                contentDescription = "Song Image",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(MaterialTheme.shapes.medium)
                            )

                        }

                        Row(
                            modifier = Modifier
                                .padding(vertical = 16.dp)
                                .align(alignment = Alignment.CenterHorizontally)
                        ) {

                            Column(
                                modifier = Modifier.align(alignment = Alignment.CenterVertically)
                            ) {

                                Text(
                                    text = song.name,
                                    style = MaterialTheme.typography.displaySmall,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                                )

                                Text(song.artist,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier
                                        .graphicsLayer {
                                            alpha = 0.60f
                                        }
                                        .align(alignment = Alignment.CenterHorizontally)
                                )
                            }
                        }



                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 24.dp)
                        ) {

                            Slider(
                                value = currentTime.toFloat(),
                                modifier = Modifier.fillMaxWidth(),
                                valueRange = 0f..totalTime.toFloat(),
                                colors = sliderColors,
                                onValueChange = onSliderChange,
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onSurfaceVariant) {
                                    Text(
                                        currentTime.toTime(),
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                                CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onSurfaceVariant) {
                                    Text(
                                        totalTime.toTime(), style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                        }

                        Row(
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.round_fast_rewind_24),
                                contentDescription = "Skip Previous",
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .clickable(onClick = {
                                        playPreviousSong()
                                        view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
                                    })
                                    .padding(12.dp)
                                    .size(32.dp)
                            )
                            Icon(
                                painter = painterResource(R.drawable.rounded_replay_10_24),
                                contentDescription = "Replay 10 seconds",
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .clickable(onClick = {
                                        onRewind()
                                        view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
                                    })
                                    .padding(12.dp)
                                    .size(32.dp)
                            )
                            Icon(
                                painter = painterResource(playPauseIcon),
                                contentDescription = "Play",
                                tint = MaterialTheme.colorScheme.background,
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.onBackground)
                                    .clickable(onClick = {

                                        playOrToggleSong()
                                        view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)

                                    })
                                    .size(64.dp)
                                    .padding(8.dp)
                            )
                            Icon(
                                painter = painterResource(R.drawable.rounded_forward_10_24),
                                contentDescription = "Forward 10 seconds",
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .clickable(onClick = {
                                        onForward()
                                        view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
                                    })
                                    .padding(12.dp)
                                    .size(32.dp)
                            )
                            Icon(
                                painter = painterResource(R.drawable.round_fast_forward_24),
                                contentDescription = "Skip Next",
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .clickable(onClick = {
                                        playNextSong()
                                        view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
                                    })
                                    .padding(12.dp)
                                    .size(32.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}




