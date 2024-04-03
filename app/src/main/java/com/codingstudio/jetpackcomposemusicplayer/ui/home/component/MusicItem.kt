package com.codingstudio.jetpackcomposemusicplayer.ui.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.codingstudio.jetpackcomposemusicplayer.domain.model.Song

@Composable
fun MusicItem (
    onClick: () -> Unit,
    song: Song
){

    ConstraintLayout (
        modifier = Modifier
            .clickable { }
            .fillMaxWidth()
    ) {
        val (divider, songTitle, songSubtitle, image ) = createRefs()

        Divider(
            modifier = Modifier.constrainAs(divider) {
                top.linkTo(parent.top)
                centerHorizontallyTo(parent)
                width = Dimension.fillToConstraints
            }
        )

        Image(
            painter = rememberAsyncImagePainter(
                        ImageRequest.Builder(LocalContext.current)
                            .data("https://cms.samespace.com/assets/${song.cover}")
                            .crossfade(true).build()
                        ),
            contentDescription = "Song Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(56.dp)
                .clip(MaterialTheme.shapes.medium)
                .constrainAs(image) {
                    end.linkTo(parent.end, 16.dp)
                    top.linkTo(parent.top, 16.dp)
                    bottom.linkTo(parent.bottom, 16.dp)
                }
            )

        Text(
            text = song.name,
            maxLines = 2,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.constrainAs(songTitle){
                linkTo(
                    start = parent.start,
                    end = image.start,
                    startMargin = 24.dp,
                    endMargin = 16.dp,
                    bias = 0f
                )
                top.linkTo(parent.top, 16.dp)
                start.linkTo(parent.start, 16.dp)
                width = Dimension.preferredWrapContent
            }
        )

        CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onSurfaceVariant) {
            Text(
                text = song.name,
                maxLines = 2,
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.constrainAs(songSubtitle) {
                    linkTo(
                        start = parent.start,
                        end = image.start,
                        startMargin = 24.dp,
                        endMargin = 16.dp,
                        bias = 0f
                    )
                    top.linkTo(songTitle.bottom, 6.dp)
                    start.linkTo(parent.start, 16.dp)
                    width = Dimension.preferredWrapContent
                }
            )
        }


    }

}