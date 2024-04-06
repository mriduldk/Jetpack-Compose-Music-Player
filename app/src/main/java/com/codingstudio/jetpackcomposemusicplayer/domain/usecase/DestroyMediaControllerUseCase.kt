package com.codingstudio.jetpackcomposemusicplayer.domain.usecase

import com.codingstudio.jetpackcomposemusicplayer.domain.service.MusicController
import javax.inject.Inject

class DestroyMediaControllerUseCase @Inject constructor(
    private val musicController: MusicController
) {
    operator fun invoke() {
        musicController.destroy()
    }
}