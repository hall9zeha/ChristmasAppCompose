package com.barryzea.christmasapp.data.repository

import com.barryzea.christmasapp.common.getDays
import com.barryzea.christmasapp.data.database.ReminderDao
import com.barryzea.christmasapp.data.database.ReminderDatabase
import com.barryzea.christmasapp.data.model.CountdownEntity
import com.barryzea.christmasapp.data.model.Reminder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


/**
 * Project ChristmasApp
 * Created by Barry Zea H. on 3/10/23.
 * Copyright (c)  All rights reserved.
 **/

class MainRepositoryImpl @Inject constructor(reminderDb: ReminderDatabase) :MainRepository{
    val db = reminderDb.reminderDao()
    override suspend fun getChristmasCountdown(): Flow<CountdownEntity> {
        return flow{
            while(true){
                emit(getDays())
                delay(1000)
            }
        }.flowOn(Dispatchers.IO)
    }
    override suspend fun saveReminder(reminder: Reminder)=db.saveReminder(reminder)
    override suspend fun deleteReminder(idReminder: Long) = db.deleteReminder(idReminder)
    override suspend fun updateReminder(reminder: Reminder) = db.updateReminder(reminder)

    override suspend fun getReminderById(idReminder: Long)=db.getReminderById(idReminder)
    override suspend fun getAllReminders()=db.getAllReminders()
}