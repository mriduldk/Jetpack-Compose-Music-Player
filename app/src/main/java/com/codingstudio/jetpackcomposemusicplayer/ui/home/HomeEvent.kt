package com.codingstudio.jetpackcomposemusicplayer.ui.home

import com.codingstudio.jetpackcomposemusicplayer.domain.model.Song


sealed class HomeEvent {
    object PlaySong : HomeEvent()
    object PauseSong : HomeEvent()
    object ResumeSong : HomeEvent()
    object FetchSong : HomeEvent()
    object SkipToNextSong : HomeEvent()
    object SkipToPreviousSong : HomeEvent()
    data class OnSongSelected(val selectedSong: Song) : HomeEvent()
}