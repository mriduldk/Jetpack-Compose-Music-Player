package com.codingstudio.jetpackcomposemusicplayer.ui.home.component

import android.view.HapticFeedbackConstants
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import coil.compose.rememberAsyncImagePainter
import com.codingstudio.jetpackcomposemusicplayer.R
import com.codingstudio.jetpackcomposemusicplayer.domain.model.Song
import com.codingstudio.jetpackcomposemusicplayer.other.PlayerState
import com.codingstudio.jetpackcomposemusicplayer.ui.home.HomeEvent

@Composable
fun HomeBottomBar(
    modifier: Modifier = Modifier,
    onEvent: (HomeEvent) -> Unit,
    playerState: PlayerState?,
    song: Song?,
    onBarClick: () -> Unit
) {

    var offsetX by remember { mutableFloatStateOf(0f) }

    AnimatedVisibility(
        visible = playerState != PlayerState.STOPPED,
        enter = slideInHorizontally(
            initialOffsetX = { -1000 },
            animationSpec = androidx.compose.animation.core.spring()
        ),
        exit = slideOutHorizontally(
            targetOffsetX = { -1000 },
            animationSpec = androidx.compose.animation.core.spring()
        ),
        modifier = modifier
    ) {
        if (song != null) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragEnd = {
                                when {
                                    offsetX > 0 -> {
                                        onEvent(HomeEvent.SkipToPreviousSong)
                                    }

                                    offsetX < 0 -> {
                                        onEvent(HomeEvent.SkipToNextSong)
                                    }
                                }
                            },
                            onDrag = { change, dragAmount ->
                                change.consume()
                                val (x, _) = dragAmount
                                offsetX = x
                            }
                        )

                    }
                    .background(
                        if (!isSystemInDarkTheme()) {
                            Color("#BB8493".toColorInt())
                        } else Color("#49243E".toColorInt())
                    ),
            ) {
                HomeBottomBarItem(
                    song = song,
                    onEvent = onEvent,
                    playerState = playerState,
                    onBarClick = onBarClick
                )
            }
        }
    }
}


@Composable
fun HomeBottomBarItem(
    song: Song,
    onEvent: (HomeEvent) -> Unit,
    playerState: PlayerState?,
    onBarClick: () -> Unit
) {

    val view  = LocalView.current

    Box(
        modifier = Modifier
            .height(76.dp)
            .clickable(onClick = { onBarClick() })

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = rememberAsyncImagePainter("https://cms.samespace.com/assets/${song.cover}"),
                contentDescription = song.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(48.dp)
                    .offset(16.dp)
                    .clip(CircleShape)
            )
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(vertical = 8.dp, horizontal = 32.dp),
            ) {
                Text(
                    song.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

            }
            val painter = rememberAsyncImagePainter(
                if (playerState == PlayerState.PLAYING) {
                    R.drawable.round_pause_circle_24
                } else {
                    R.drawable.round_play_circle_24
                }
            )

            Image(
                painter = painter,
                contentDescription = "Music",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .size(48.dp)
                    .clickable(
                        interactionSource = remember {
                            MutableInteractionSource()
                        },
                        indication = rememberRipple(
                            bounded = false,
                            radius = 24.dp
                        )
                    ) {
                        if (playerState == PlayerState.PLAYING) {
                            onEvent(HomeEvent.PauseSong)
                        } else {
                            onEvent(HomeEvent.ResumeSong)
                        }
                        view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
                    },
            )

        }
    }
}
