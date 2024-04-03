package com.codingstudio.jetpackcomposemusicplayer.ui.home

import com.codingstudio.jetpackcomposemusicplayer.domain.model.Song


data class HomeUiState(
    val loading: Boolean? = false,
    val songs: List<Song>? = emptyList(),
    val selectedSong: Song? = null,
    val errorMessage: String? = null
)
