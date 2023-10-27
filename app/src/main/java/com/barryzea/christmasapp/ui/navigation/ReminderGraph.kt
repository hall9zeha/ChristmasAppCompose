package com.barryzea.christmasapp.ui.navigation

import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.barryzea.christmasapp.common.BottomBarTab
import com.barryzea.christmasapp.ui.navigation.RemindersDestinations.REMINDER_ITEM_ID_KEY
import com.barryzea.christmasapp.ui.navigation.RemindersDestinations.REMINDER_ITEM_ROUTE
import com.barryzea.christmasapp.ui.screens.ReminderDetail
import com.barryzea.christmasapp.ui.screens.RemindersScreen
import com.barryzea.christmasapp.ui.viewModel.MainViewModel


/**
 * Project ChristmasApp
 * Created by Barry Zea H. on 26/10/23.
 * Copyright (c)  All rights reserved.
 **/

const val REMINDER_GRAPH = "Reminders"
object RemindersDestinations{
    const val REMINDERS_ROUTE = "root"
    const val REMINDER_ITEM_ROUTE = "item"
    const val REMINDER_ITEM_ID_KEY = "itemId"
}
fun NavGraphBuilder.addRemindersGraph(onReminderItemSelected:(Long, NavBackStackEntry)->Unit,
                                      upPress:()->Unit) {
    composable(BottomBarTab.REMINDERS.route) {from->
        RemindersScreen(onItemClick = {id->onReminderItemSelected(id,from)})
    }
    composable(
        route="$REMINDER_ITEM_ROUTE/{${REMINDER_ITEM_ID_KEY}}",
        arguments= listOf(navArgument(REMINDER_ITEM_ID_KEY){type = NavType.LongType} )
    ){backStackEntry->
        val arguments = requireNotNull(backStackEntry.arguments)
        val itemId = arguments.getLong(REMINDER_ITEM_ID_KEY)
        ReminderDetail(idReminder = itemId, upPress = upPress)
    }
}