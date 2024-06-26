package com.codingstudio.jetpackcomposemusicplayer.ui.home.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeAppBar(
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Row {
                Text(
                    modifier = Modifier
                        .padding(start = 8.dp),
                    text = "Music Player"
                )
            }
        },
        actions = {
            CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onSurfaceVariant) {
            }
        },
        modifier = modifier
    )
}