package com.barryzea.christmasapp.common

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.barryzea.christmasapp.MainActivity
import com.barryzea.christmasapp.MyApp
import com.barryzea.christmasapp.MyApp.Companion.context
import com.barryzea.christmasapp.R


/**
 * Project ChristmasApp
 * Created by Barry Zea H. on 6/10/23.
 * Copyright (c)  All rights reserved.
 **/

/*
* Ya que tenemos la tipografía de forma global en temas esta función ya no se utiliza
* */
private val NOTIFICATION_ID = 125
private const val CHANNEL_ID ="christmas_notify_id"
private const val CHANNEL_NAME = "ChristmasApp"
fun getFontProviders():GoogleFont.Provider{
    return GoogleFont.Provider(
        providerAuthority = "com.google.android.gms.fonts",
        providerPackage = "com.google.android.gms",
        certificates = R.array.com_google_android_gms_fonts_certs
    )
}
//Configuramos el manejador de notificaciónes
fun sendNotification(
    title:String,
    message:String
){
    val mainIntent = Intent(context, MainActivity::class.java)
    mainIntent.flags=Intent.FLAG_ACTIVITY_SINGLE_TOP

    val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
        PendingIntent.getActivity(context, NOTIFICATION_ID, mainIntent,
            PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE)

    }else{
        PendingIntent.getActivity(context, NOTIFICATION_ID, mainIntent,
            PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE)
    }
    val notificationManager = ContextCompat.getSystemService(context, NotificationManager::class.java) as NotificationManager
    createNotificationChannel(context, notificationManager)
    val builder = NotificationCompat.Builder(
        context, CHANNEL_ID)
        .setSmallIcon(R.mipmap.ic_launcher)
        .setContentIntent(pendingIntent)
        .setContentTitle(title)
        .setAutoCancel(true)
        .setOnlyAlertOnce(true)
        .setContentText(message)
    notificationManager.notify(NOTIFICATION_ID, builder.build())


}
fun NotificationManager.cancelNotification()=cancelAll()
fun createNotificationChannel(context: Context, notificationManager: NotificationManager){

    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
        val notificationChannel = NotificationChannel(
            CHANNEL_ID, CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            enableLights(true)
            enableVibration(true)
            lightColor = Color.WHITE
        }
        notificationManager.createNotificationChannel(notificationChannel)
    }

}
