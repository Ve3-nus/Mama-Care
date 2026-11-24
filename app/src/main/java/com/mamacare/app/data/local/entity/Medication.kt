package com.mamacare.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Locale

@Entity(tableName = "medications")
data class Medication(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val dosage: String,
    val hour: Int,
    val minute: Int,
    val notes: String = ""
) {
    val time: String
        get() = String.format(java.util.Locale.US, "%02d:%02d", hour, minute)
}

        get() {
            val h = if (hour == 0) 12 else if (hour > 12) hour - 12 else hour
            val amPm = if (hour >= 12) "PM" else "AM"
            return String.format(Locale.getDefault(), "%d:%02d %s", h, minute, amPm)
        }
}
