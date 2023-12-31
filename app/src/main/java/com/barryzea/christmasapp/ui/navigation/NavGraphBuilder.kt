package com.barryzea.christmasapp.ui.navigation

import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.barryzea.christmasapp.data.model.BottomBarTab


/**
 * Project ChristmasApp
 * Created by Barry Zea H. on 26/10/23.
 * Copyright (c)  All rights reserved.
 **/

fun NavGraphBuilder.navGraph(onReminderItemSelected:(Long?,NavBackStackEntry)->Unit,
                             upPress:(idInserted:Long?)->Unit) {
    navigation(
        route = COUNTDOWN_GRAPH,
        startDestination = BottomBarTab.COUNTDOWN.route
    ) {
        addCountdownGraph()
        addRemindersGraph(onReminderItemSelected, upPress)
        addSettingsGraph()
    }
}