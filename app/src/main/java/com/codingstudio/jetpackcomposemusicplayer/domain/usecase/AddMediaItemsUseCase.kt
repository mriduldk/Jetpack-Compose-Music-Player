package com.codingstudio.jetpackcomposemusicplayer.domain.usecase

import com.codingstudio.jetpackcomposemusicplayer.domain.model.Song
import com.codingstudio.jetpackcomposemusicplayer.domain.service.MusicController
import javax.inject.Inject


class AddMediaItemsUseCase @Inject constructor(private val musicController: MusicController) {

    operator fun invoke(songs: List<Song>) {
        musicController.addMediaItems(songs)
    }
}