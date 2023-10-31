package com.barryzea.christmasapp.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.barryzea.christmasapp.data.model.BottomBarTab
import com.barryzea.christmasapp.ui.navigation.RemindersDestinations.REMINDER_ITEM_ROUTE
import kotlinx.coroutines.CoroutineScope


/**
 * Project ChristmasApp
 * Created by Barry Zea H. on 26/10/23.
 * Copyright (c)  All rights reserved.
 **/

const val ID_INSERTED_KEY="idInsertedKey"
@Composable
fun rememberAppState(

  navController: NavHostController = rememberNavController(),
  coroutineScope: CoroutineScope = rememberCoroutineScope()
) =
  remember(navController) {
    AppState(navController)
  }
@Stable
class AppState(val navController:NavHostController) {
  val bottomBarTabs = BottomBarTab.values()
  private val bottomBarRoutes = bottomBarTabs.map { it.route }

  val shouldShowBottomBar:Boolean
    @Composable get() = navController
      .currentBackStackEntryAsState().value?.destination?.route in bottomBarRoutes
  val currentRoute:String?
    get() = navController.currentDestination?.route

  fun upPress(idInserted:Long?){
    navController.previousBackStackEntry?.savedStateHandle?.set(ID_INSERTED_KEY,idInserted)
    navController.navigateUp()
  }

  fun navigateToBottomBarRoute(route:String){
    if(route != currentRoute){
      navController.navigate(route){
        launchSingleTop = true
        restoreState = true
        popUpTo(findStartDestination(navController.graph).id){
          saveState = true
        }
      }
    }
  }
  fun navigateToReminderItemDetail(itemId:Long?, from:NavBackStackEntry){
    if(from.lifecycleIsResumed()){
      navController.navigate("${REMINDER_ITEM_ROUTE}/${itemId}")
    }
  }
}

private fun NavBackStackEntry.lifecycleIsResumed()= this.lifecycle.currentState == Lifecycle.State.RESUMED

private val NavGraph.startDestination: NavDestination?
  get() = findNode(startDestinationId)

private tailrec fun findStartDestination(graph: NavDestination): NavDestination {
  return if (graph is NavGraph) findStartDestination(graph.startDestination!!) else graph
}
