package com.barryzea.christmasapp.common

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.barryzea.christmasapp.R
import com.barryzea.christmasapp.ui.navigation.COUNTDOWN_GRAPH
import com.barryzea.christmasapp.ui.navigation.CountdownDestinations.COUNTDOWN_ROUTE
import com.barryzea.christmasapp.ui.navigation.REMINDER_GRAPH
import com.barryzea.christmasapp.ui.navigation.RemindersDestinations.REMINDERS_ROUTE
import com.barryzea.christmasapp.ui.navigation.SETTINGS_GRAPH
import com.barryzea.christmasapp.ui.navigation.SettingsDestinations.SETTINGS_ROUTE


/**
 * Project ChristmasApp
 * Created by Barry Zea H. on 26/10/23.
 * Copyright (c)  All rights reserved.
 **/

enum class BottomBarTab(
 @DrawableRes val icon: Int,
 val graphName:String,
 val route: String
) {
 COUNTDOWN(
  R.drawable.ic_santa,
  COUNTDOWN_GRAPH,
  "$COUNTDOWN_GRAPH/$COUNTDOWN_ROUTE"
 ),
 REMINDERS(
  R.drawable.ic_box_gift,
  REMINDER_GRAPH,
  "$REMINDER_GRAPH/$REMINDERS_ROUTE"
 ),
 SETTINGS(
  R.drawable.ic_snowflake,
  SETTINGS_GRAPH,
  "$SETTINGS_GRAPH/$SETTINGS_ROUTE"
 )
}