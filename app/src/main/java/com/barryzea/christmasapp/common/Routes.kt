package com.barryzea.christmasapp.common


/**
 * Project ChristmasApp
 * Created by Barry Zea H. on 11/10/23.
 * Copyright (c)  All rights reserved.
 **/
 
sealed class Routes (val route:String){
    object CountDownScreen:Routes("Home")
    object SettingsScreen:Routes("Settings")
    object RemindersScreen:Routes("Reminders")
    //le enviaremos como parámetro el id del recordatorio, será opcional,
    //ya que usaremos la misma vista para nuevo registro como para editar
    object ReminderDetail:Routes("ReminderDetail?idReminderArg={idReminderArg}"){
        fun createRoute(idReminder:Long)= "ReminderDetail?idReminderArg=$idReminder"
    }
}