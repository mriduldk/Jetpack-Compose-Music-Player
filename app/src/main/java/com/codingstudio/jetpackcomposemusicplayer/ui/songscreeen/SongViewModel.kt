package com.codingstudio.jetpackcomposemusicplayer.ui.songscreeen

import android.graphics.Bitmap
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.palette.graphics.Palette
import com.codingstudio.jetpackcomposemusicplayer.domain.usecase.PauseSongUseCase
import com.codingstudio.jetpackcomposemusicplayer.domain.usecase.ResumeSongUseCase
import com.codingstudio.jetpackcomposemusicplayer.domain.usecase.SeekSongToPositionUseCase
import com.codingstudio.jetpackcomposemusicplayer.domain.usecase.SkipToNextSongUseCase
import com.codingstudio.jetpackcomposemusicplayer.domain.usecase.SkipToPreviousSongUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SongViewModel @Inject constructor(
    private val pauseSongUseCase: PauseSongUseCase,
    private val resumeSongUseCase: ResumeSongUseCase,
    private val skipToNextSongUseCase: SkipToNextSongUseCase,
    private val skipToPreviousSongUseCase: SkipToPreviousSongUseCase,
    private val seekSongToPositionUseCase: SeekSongToPositionUseCase,
) : ViewModel() {
    fun onEvent(event: SongEvent) {
        when (event) {
            SongEvent.PauseSong -> pauseMusic()
            SongEvent.ResumeSong -> resumeMusic()
            is SongEvent.SeekSongToPosition -> seekToPosition(event.position)
            SongEvent.SkipToNextSong -> skipToNextSong()
            SongEvent.SkipToPreviousSong -> skipToPreviousSong()
        }
    }

    private fun pauseMusic() {
        pauseSongUseCase()
    }

    private fun resumeMusic() {
        resumeSongUseCase()
    }

    private fun skipToNextSong() = skipToNextSongUseCase {

    }

    private fun skipToPreviousSong() = skipToPreviousSongUseCase {

    }

    private fun seekToPosition(position: Long) {
        seekSongToPositionUseCase(position)
    }

    fun calculateColorPalette(drawable: Bitmap, onFinish: (Color) -> Unit) {
        Palette.from(drawable).generate { palette ->
            palette?.dominantSwatch?.rgb?.let { colorValue ->
                onFinish(Color(colorValue))
            }
        }
    }

}
