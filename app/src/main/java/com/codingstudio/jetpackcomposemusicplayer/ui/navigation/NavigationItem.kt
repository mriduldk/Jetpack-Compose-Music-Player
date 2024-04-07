package com.codingstudio.jetpackcomposemusicplayer.ui.navigation

import com.codingstudio.jetpackcomposemusicplayer.R

sealed class NavigationItem(var route: String, var icon: Int, var title: String) {
    object ForYou : NavigationItem(Destination.home, R.drawable.round_circle_24, "For You")
    object TopTrack : NavigationItem(Destination.topTracks, R.drawable.round_circle_24, "Top Tracks")
}