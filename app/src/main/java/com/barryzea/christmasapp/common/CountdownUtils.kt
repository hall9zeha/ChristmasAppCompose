package com.barryzea.christmasapp.common

import android.app.NotificationManager
import android.util.Log
import com.barryzea.christmasapp.MyApp
import com.barryzea.christmasapp.R
import com.barryzea.christmasapp.data.model.CountdownEntity
import com.barryzea.christmasapp.data.model.Reminder

import java.util.Calendar
import java.util.Random
import java.util.concurrent.TimeUnit


/**
 * Project ChristmasApp
 * Created by Barry Zea H. on 30/9/23
 * Copyright (c)  All rights reserved.
 **/
 
fun getDays():CountdownEntity{

    var calendar = Calendar.getInstance()
    var christmasYear = calendar.get(Calendar.YEAR)

    var setupChristmas= Calendar.getInstance()
    //El conteo de los meses en la mayoría de las librerías  en programación comienzan en 0, así
    //que es la razón de poner  11 como mes de diciembre
    setupChristmas.set(christmasYear,11,25)

    //Si ya se pasó de la fecha esperada, reajustamos el calendario
    if(Calendar.getInstance().timeInMillis > setupChristmas.timeInMillis){
        val cal = Calendar.getInstance()
        cal.add(Calendar.YEAR,1)
        christmasYear = cal.get(Calendar.YEAR)
        setupChristmas.set(christmasYear,11,25)
    }
    var christmasDays =TimeUnit.MILLISECONDS.toDays(setupChristmas.timeInMillis) - 1
    val days = (christmasDays - TimeUnit.MILLISECONDS.toDays(calendar.timeInMillis)).toInt()

    val hours= (23-calendar.get(Calendar.HOUR_OF_DAY))
    val minutes = (60-calendar.get(Calendar.MINUTE))
    val seconds = (60-calendar.get(Calendar.SECOND))
    return if( days<0){
       if(checkIfIsAlreadyCountdownDate()){
            setAlarm(
                Reminder(
                    CHRISTMAS_ID_NOTIFY.toLong(),
                MyApp.context?.getString(R.string.countdownMsg)!!,
                getDatetimeWithoutHours(Calendar.getInstance().timeInMillis),
                true
            )
            )

        }
        CountdownEntity(itsChristmas = true)
    }else{
        CountdownEntity(days,hours,minutes,seconds)
    }
}