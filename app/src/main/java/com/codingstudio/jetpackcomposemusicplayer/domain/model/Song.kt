package com.codingstudio.jetpackcomposemusicplayer.domain.model

data class Song (
    val id: Int,
    val status: String ?= null,
    val sort: String ?= null,
    val user_created: String ?= null,
    val date_created: String ?= null,
    val user_updated: String ?= null,
    val date_updated: String ?= null,
    val name: String,
    val artist: String ?= null,
    val accent: String ?= null,
    val cover: String,
    val top_track: Boolean,
    val url: String,
)