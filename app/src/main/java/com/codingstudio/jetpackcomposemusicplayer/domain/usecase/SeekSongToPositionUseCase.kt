package com.codingstudio.jetpackcomposemusicplayer.domain.usecase

import com.codingstudio.jetpackcomposemusicplayer.domain.service.MusicController
import javax.inject.Inject

class SeekSongToPositionUseCase @Inject constructor(private val musicController: MusicController) {
    operator fun invoke(position: Long) {
        musicController.seekTo(position)
    }
}