package com.codingstudio.jetpackcomposemusicplayer.domain.usecase

import com.codingstudio.jetpackcomposemusicplayer.domain.model.Song
import com.codingstudio.jetpackcomposemusicplayer.domain.service.MusicController
import javax.inject.Inject

class SkipToPreviousSongUseCase @Inject constructor(private val musicController: MusicController) {
    operator fun invoke(updateHomeUi: (Song?) -> Unit) {
        musicController.skipToPreviousSong()
        updateHomeUi(musicController.getCurrentSong())
    }
}