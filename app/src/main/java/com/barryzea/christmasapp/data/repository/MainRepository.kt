package com.barryzea.christmasapp.data.repository

import com.barryzea.christmasapp.data.model.CountdownEntity
import com.barryzea.christmasapp.data.model.Reminder
import kotlinx.coroutines.flow.Flow


/**
 * Project ChristmasApp
 * Created by Barry Zea H. on 3/10/23.
 * Copyright (c)  All rights reserved.
 **/

interface MainRepository {
    suspend fun  getChristmasCountdown():Flow<CountdownEntity>
    suspend fun saveReminder(reminder:Reminder):Long
    suspend fun updateReminder(reminder: Reminder):Int
    suspend fun deleteReminder(idReminder:Long)
    suspend fun getReminderById(idReminder:Long):Reminder
    suspend fun getAllReminders():MutableList<Reminder>
}