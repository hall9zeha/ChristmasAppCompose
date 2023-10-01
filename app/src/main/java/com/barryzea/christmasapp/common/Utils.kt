package com.barryzea.christmasapp.common

import android.util.Log

import java.util.Calendar
import java.util.concurrent.TimeUnit


/**
 * Project ChristmasApp
 * Created by Barry Zea H. on 30/9/23
 * Copyright (c)  All rights reserved.
 **/
 
fun getDays():String{
    var calendar = Calendar.getInstance()
    var christmasYear = calendar.get(Calendar.YEAR)

    var setupChristmas= Calendar.getInstance()
    setupChristmas.set(christmasYear,11,25)

    var christmasDays =TimeUnit.MILLISECONDS.toDays(setupChristmas.timeInMillis)
    if(Calendar.getInstance().timeInMillis > setupChristmas.timeInMillis){
        var cal = Calendar.getInstance()
        cal.add(Calendar.YEAR,1)
        christmasYear = cal.get(Calendar.YEAR)
        Log.e("christmasYear + 1", christmasYear.toString())
        setupChristmas.set(christmasYear,12,25)
    }

    var days = (christmasDays - TimeUnit.MILLISECONDS.toDays(calendar.timeInMillis)).toInt()
    var hours= (23-calendar.get(Calendar.HOUR_OF_DAY))
    var minutes = (60-calendar.get(Calendar.MINUTE))
    var seconds = (60-calendar.get(Calendar.SECOND))
    return if( days==0){
        "Ya es navidad"
    }else{
        "Faltan $days  días $hours Horas $minutes minutos $seconds segundos para navidad"
    }
}