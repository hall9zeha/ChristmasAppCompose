package com.barryzea.christmasapp.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.barryzea.christmasapp.data.model.Reminder


/**
 * Project ChristmasApp
 * Created by Barry Zea H. on 28/10/23.
 * Copyright (c)  All rights reserved.
 **/

@Dao
interface ReminderDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveReminder(reminder:Reminder):Long

    @Query("delete from Reminder where id=:idReminder")
    suspend fun deleteReminder(idReminder:Long)

    @Query("select * from Reminder where id=:idReminder")
    suspend fun getReminderById(idReminder:Long):Reminder

    @Query("select * from Reminder order by timeInMillis desc")
    suspend fun getAllReminders():MutableList<Reminder>
}