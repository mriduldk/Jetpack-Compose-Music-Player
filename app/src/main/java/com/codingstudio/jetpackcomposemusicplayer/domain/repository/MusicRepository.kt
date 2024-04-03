package com.codingstudio.jetpackcomposemusicplayer.domain.repository

import com.codingstudio.jetpackcomposemusicplayer.domain.model.Song
import com.codingstudio.jetpackcomposemusicplayer.other.Resource
import kotlinx.coroutines.flow.Flow

interface MusicRepository {
    suspend fun getSongs(): Flow<Resource<List<Song>>>
}