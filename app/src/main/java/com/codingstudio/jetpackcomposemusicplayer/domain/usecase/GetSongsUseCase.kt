package com.codingstudio.jetpackcomposemusicplayer.domain.usecase

import com.codingstudio.jetpackcomposemusicplayer.domain.repository.MusicRepository
import javax.inject.Inject

class GetSongsUseCase @Inject constructor(private val musicRepository: MusicRepository) {
    suspend operator fun invoke() = musicRepository.getSongs()
}