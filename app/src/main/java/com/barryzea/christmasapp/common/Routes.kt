package com.barryzea.christmasapp.common


/**
 * Project ChristmasApp
 * Created by Barry Zea H. on 11/10/23.
 * Copyright (c)  All rights reserved.
 **/
 
sealed class Routes (val route:String){
    object CountDownScreen:Routes("Home")
    object SettingsScreen:Routes("Settings")
}