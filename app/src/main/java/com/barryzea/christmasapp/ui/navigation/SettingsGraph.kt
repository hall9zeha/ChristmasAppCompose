package com.barryzea.christmasapp.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.barryzea.christmasapp.data.model.BottomBarTab
import com.barryzea.christmasapp.ui.screens.SettingsScreen


/**
 * Project ChristmasApp
 * Created by Barry Zea H. on 26/10/23.
 * Copyright (c)  All rights reserved.
 **/

const val SETTINGS_GRAPH = "Settings"
object SettingsDestinations {
 const val SETTINGS_ROUTE = "root"
}
fun NavGraphBuilder.addSettingsGraph() {
 composable(BottomBarTab.SETTINGS.route) {
  SettingsScreen()
 }
}