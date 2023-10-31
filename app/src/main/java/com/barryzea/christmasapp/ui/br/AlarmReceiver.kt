package com.barryzea.christmasapp.ui.br

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import com.barryzea.christmasapp.R
import com.barryzea.christmasapp.common.REMINDER_ENTITY_KEY
import com.barryzea.christmasapp.common.getDatetimeWithoutHours
import com.barryzea.christmasapp.common.preferences.SettingsStore
import com.barryzea.christmasapp.common.sendNotification
import com.barryzea.christmasapp.common.setAlarm
import com.barryzea.christmasapp.data.database.ReminderDatabase
import com.barryzea.christmasapp.data.model.Reminder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

import java.util.Calendar
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver: BroadcastReceiver() {
    @Inject
   lateinit var preferences:SettingsStore

    @Inject
    lateinit var roomDb:ReminderDatabase

    @OptIn(DelicateCoroutinesApi::class)
    override fun onReceive(ctx: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            val db=roomDb.reminderDao()
            val currentDate = getDatetimeWithoutHours(Calendar.getInstance().timeInMillis)
            GlobalScope.launch(Dispatchers.IO) {
                preferences.getFromDataStore().collect{prefs->
                    if(prefs.dateNotify) {
                        val remindersList = db.getAllReminders()
                        remindersList.forEach { reminder ->
                            if (reminder.timeInMillis >= currentDate) {
                                setAlarm(reminder)
                            }
                        }
                    }
                }
            }

        }else{// Si no se ha reiniciado o apagado el movil
            var reminderEntity:Reminder? = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent?.getParcelableExtra(REMINDER_ENTITY_KEY,Reminder::class.java)
            }else{
                intent?.getParcelableExtra(REMINDER_ENTITY_KEY) as? Reminder
            }
            ctx?.let{
                    reminderEntity?.let {
                        sendNotification(ctx.getString(R.string.christmas_notify),reminderEntity.id, reminderEntity?.description ?: "Merry christmas")
                    }
                }
            }
        }
    }
