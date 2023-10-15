package com.barryzea.christmasapp.ui.br

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import com.barryzea.christmasapp.common.REMINDER_ENTITY_KEY
import com.barryzea.christmasapp.common.getDatetimeWithoutHours
import com.barryzea.christmasapp.common.sendNotification
import com.barryzea.christmasapp.data.model.Reminder
import java.util.Calendar

class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(ctx: Context?, intent: Intent?) {
        if (intent?.action == "android.intent.action.BOOT_COMPLETED") {
            //TODO podemos manejar las alarmas guardadas en una base de datos o podemos usar una sola fecha de modo manual
            val intentService = Intent(ctx, BroadcastReceiver::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                ctx?.startForegroundService(intentService)
            }
            ctx?.startService(intentService)

        }else{// Si no se ha reiniciado o apagado el movil
            var reminderEntity:Reminder? = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent?.getParcelableExtra(REMINDER_ENTITY_KEY,Reminder::class.java)
            }else{
                intent?.getParcelableExtra(REMINDER_ENTITY_KEY) as? Reminder
            }
            ctx?.let{
                    reminderEntity?.let {
                        sendNotification("Feliz navidad", reminderEntity?.description ?: "")
                    }
                }
            }
        }
    }
