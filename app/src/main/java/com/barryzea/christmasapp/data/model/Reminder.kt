package com.barryzea.christmasapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Reminder(val id:Long=0,val description:String="",val timeInMillis:Long=0, val enable:Boolean = false) : Parcelable
