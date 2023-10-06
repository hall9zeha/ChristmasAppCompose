package com.barryzea.christmasapp.common

import android.util.Log
import com.barryzea.christmasapp.data.model.CountdownEntity

import java.util.Calendar
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
    setupChristmas.set(christmasYear,11,25)

    var christmasDays =TimeUnit.MILLISECONDS.toDays(setupChristmas.timeInMillis)
    if(Calendar.getInstance().timeInMillis > setupChristmas.timeInMillis){
        val cal = Calendar.getInstance()
        cal.add(Calendar.YEAR,1)
        christmasYear = cal.get(Calendar.YEAR)
        Log.e("christmasYear + 1", christmasYear.toString())
        setupChristmas.set(christmasYear,12,25)
    }

    val days = (christmasDays - TimeUnit.MILLISECONDS.toDays(calendar.timeInMillis)).toInt()
    val hours= (23-calendar.get(Calendar.HOUR_OF_DAY))
    val minutes = (60-calendar.get(Calendar.MINUTE))
    val seconds = (60-calendar.get(Calendar.SECOND))
    return if( days==0){
       CountdownEntity(itsChristmas = true)
    }else{
        CountdownEntity(days,hours,minutes,seconds)
    }
}