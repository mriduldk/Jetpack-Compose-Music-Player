package com.codingstudio.jetpackcomposemusicplayer.data.remote

import com.codingstudio.jetpackcomposemusicplayer.domain.model.Song
import retrofit2.Response
import retrofit2.http.GET

interface SongAPI {

    @GET("items/songs")
    suspend fun fetchSongs() : Response<List<Song>>


}