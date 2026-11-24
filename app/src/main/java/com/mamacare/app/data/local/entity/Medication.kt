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
    val notes: String = "",
) {
    val time: String
        get() = String.format(Locale.US, "%02d:%02d", hour, minute)
}
