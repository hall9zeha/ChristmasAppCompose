package com.barryzea.christmasapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.barryzea.christmasapp.data.model.Reminder


/**
 * Project ChristmasApp
 * Created by Barry Zea H. on 28/10/23.
 * Copyright (c)  All rights reserved.
 **/

@Database(entities = [(Reminder::class)], version = 1, exportSchema = false)
abstract class ReminderDatabase:RoomDatabase() {
 abstract fun reminderDao():ReminderDao
}