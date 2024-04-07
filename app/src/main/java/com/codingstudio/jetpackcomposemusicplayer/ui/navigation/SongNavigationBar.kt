package com.codingstudio.jetpackcomposemusicplayer.ui.navigation

import android.view.HapticFeedbackConstants
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.codingstudio.jetpackcomposemusicplayer.R


@Composable
fun SongNavigationBar(
    navController: NavController,
    modifier: Modifier = Modifier
) {

    val navigationBarItems = listOf(
        NavigationItem.ForYou,
        NavigationItem.TopTrack,
    )


    val colorScheme = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    Column(
        modifier = modifier
    ) {

        NavigationBar(
            windowInsets = NavigationBarDefaults.windowInsets,
            containerColor = colorScheme.background,
            contentColor = colorScheme.onBackground,
            tonalElevation = 0.dp,
        ) {

            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            val view = LocalView.current

            navigationBarItems.forEach { item ->

                Box(
                    modifier = Modifier
                        .height(60.dp)
                        .weight(1f)
                        .wrapContentWidth(Alignment.CenterHorizontally)
                        .background(colorScheme.background)
                        .clickable(onClick = {
                            navController.navigate(item.route) {
                                navController.graph.startDestinationRoute?.let { route ->
                                    popUpTo(route) {
                                        saveState = true
                                    }

                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }

                            view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)

                        })
                ) {

                    Column (
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp, top = 8.dp, bottom = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {

                        Text(
                            text = item.title,
                            color = colorScheme.onBackground,
                            modifier = Modifier.alpha(
                                if (currentRoute == item.route) {
                                    1f
                                }
                                else{
                                    .5f
                                }
                            )
                        )

                        if (currentRoute == item.route) {
                            Icon(
                                painter = painterResource(R.drawable.round_circle_24),
                                contentDescription = item.title,
                                modifier = Modifier.height(8.dp)
                            )
                        }

                    }
                }


                /*NavigationBarItem(
                    icon = {
                        Icon(
                            painter = painterResource(item.icon),
                            contentDescription = item.title,
                            modifier = Modifier
                        )
                    },
                    label = {
                        Text(
                            text = item.title,
                            style = typography.labelMedium
                        )
                    },
                    alwaysShowLabel = true,
                    selected = true,
                    onClick = {
                        navController.navigate(item.route) {
                            navController.graph.startDestinationRoute?.let { route ->
                                popUpTo(route){
                                    saveState = true
                                }

                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                )*/


            }
        }
    }
}
