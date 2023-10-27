package com.barryzea.christmasapp.ui.navigation

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.barryzea.christmasapp.common.BottomBarTab


/**
 * Project ChristmasApp
 * Created by Barry Zea H. on 26/10/23.
 * Copyright (c)  All rights reserved.
 **/

fun NavGraphBuilder.navGraph(onReminderItemSelected:(Long,NavBackStackEntry)->Unit,
                             upPress:()->Unit) {
    navigation(
        route = COUNTDOWN_GRAPH,
        startDestination = BottomBarTab.COUNTDOWN.route
    ) {
        addCountdownGraph()
        addRemindersGraph(onReminderItemSelected, upPress)
        addSettingsGraph()
    }
}