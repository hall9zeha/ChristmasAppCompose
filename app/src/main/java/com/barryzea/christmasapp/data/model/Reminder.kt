package com.barryzea.christmasapp.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Reminder(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")val id:Long=0,
    @ColumnInfo(name="description")val description:String="",
    @ColumnInfo(name="timeInMillis")val timeInMillis:Long=0,
    @ColumnInfo(name="enable")val enable:Boolean = false) : Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Reminder

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
