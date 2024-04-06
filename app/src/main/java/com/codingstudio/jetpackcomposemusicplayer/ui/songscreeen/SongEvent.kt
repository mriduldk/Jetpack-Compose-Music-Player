package com.codingstudio.jetpackcomposemusicplayer.ui.songscreeen

sealed class SongEvent {
    object PauseSong : SongEvent()
    object ResumeSong : SongEvent()
    object SkipToNextSong : SongEvent()
    object SkipToPreviousSong : SongEvent()
    data class SeekSongToPosition(val position: Long) : SongEvent()
}