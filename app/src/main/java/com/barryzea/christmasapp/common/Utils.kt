package com.barryzea.christmasapp.common

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import com.barryzea.christmasapp.MainActivity
import com.barryzea.christmasapp.MyApp
import com.barryzea.christmasapp.MyApp.Companion.context
import com.barryzea.christmasapp.R
import com.barryzea.christmasapp.data.model.Reminder
import com.barryzea.christmasapp.ui.br.AlarmReceiver
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


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
const val REMINDER_ENTITY_KEY = "REMINDER_ENTITY_KEY"

fun getFontProviders():GoogleFont.Provider{
    return GoogleFont.Provider(
        providerAuthority = "com.google.android.gms.fonts",
        providerPackage = "com.google.android.gms",
        certificates = R.array.com_google_android_gms_fonts_certs
    )
}
// Configuramos el manejador de notificaciónes
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
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
    }
    val notificationManager = ContextCompat.getSystemService(context, NotificationManager::class.java) as NotificationManager
    createNotificationChannel(notificationManager)
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
fun createNotificationChannel(notificationManager: NotificationManager){

    val christmasBellsSoundUri= Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"+MyApp.context.packageName+"/raw/"+"bells_sound")
    val audioAttributes = AudioAttributes.Builder()
        .setUsage(AudioAttributes.USAGE_NOTIFICATION)
        .build()

    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
        val notificationChannel = NotificationChannel(
            CHANNEL_ID, CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            enableLights(true)
            enableVibration(true)
            lightColor = Color.BLUE
            setSound(Settings.System.DEFAULT_RINGTONE_URI, audioAttributes)
            setSound(christmasBellsSoundUri,audioAttributes)
        }
        notificationManager.createNotificationChannel(notificationChannel)
    }
}

// Solo devolverá la fecha en día/mes/año (tipo Long) de cualquier valor de tiempo en milisegundos
fun getDatetimeWithoutHours(timeInMillis: Long): Long {
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ROOT)
    val dateStringFormatted = sdf.format(timeInMillis)
    return sdf.parse(dateStringFormatted)?.time ?: 0
}
@SuppressLint("ScheduleExactAlarm")
fun setAlarm(reminderEntity:Reminder){
    val context = MyApp.context
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, AlarmReceiver::class.java)
    intent.putExtra(REMINDER_ENTITY_KEY,reminderEntity)
    val pendingIntent = PendingIntent.getBroadcast(context,reminderEntity.id.toInt(), intent,PendingIntent.FLAG_IMMUTABLE)
    val mainActivityIntent = Intent(context,MainActivity::class.java)
    val basicPendingIntent = PendingIntent.getActivity(context,reminderEntity.id.toInt(),mainActivityIntent,PendingIntent.FLAG_IMMUTABLE)

    val clock = AlarmManager.AlarmClockInfo(reminderEntity.timeInMillis,basicPendingIntent)
    alarmManager.setAlarmClock(clock,pendingIntent)
}
fun removeAlarm(reminderEntity: Reminder){
    val context = MyApp.context
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, AlarmReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(context, reminderEntity.id.toInt(),intent, PendingIntent.FLAG_IMMUTABLE)
    alarmManager.cancel(pendingIntent)
}