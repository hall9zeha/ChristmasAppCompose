package com.barryzea.christmasapp.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.barryzea.christmasapp.data.model.BottomBarTab
import com.barryzea.christmasapp.ui.screens.CountdownScreen


/**
 * Project ChristmasApp
 * Created by Barry Zea H. on 26/10/23.
 * Copyright (c)  All rights reserved.
 **/

const val COUNTDOWN_GRAPH = "Countdown"
object CountdownDestinations{
    const val COUNTDOWN_ROUTE = "root"
}
fun NavGraphBuilder.addCountdownGraph() {
    composable(BottomBarTab.COUNTDOWN.route) {
        CountdownScreen()
    }
}