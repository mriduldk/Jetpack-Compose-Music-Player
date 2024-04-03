package com.codingstudio.jetpackcomposemusicplayer.data.mapper

import androidx.media3.common.MediaItem
import com.codingstudio.jetpackcomposemusicplayer.data.dto.SongDto
import com.codingstudio.jetpackcomposemusicplayer.domain.model.Song



fun MediaItem.toSong() =
    Song(
        id = 1,
        status = mediaId,
        sort = "",
        user_created = "",
        date_created = "",
        user_updated = "",
        date_updated = "",
        name = mediaMetadata.title.toString(),
        artist = mediaMetadata.subtitle.toString(),
        accent = "",
        cover = mediaMetadata.artworkUri.toString(),
        top_track = false,
        url = mediaId,

    )