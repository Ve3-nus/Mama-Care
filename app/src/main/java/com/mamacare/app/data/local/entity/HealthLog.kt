package com.mamacare.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Entity(tableName = "health_logs")
data class HealthLog(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val weightKg: Double,
    val systolic: Int,
    val diastolic: Int,
    val dateMillis: Long = System.currentTimeMillis(),
    val notes: String = ""
) {
    val bloodPressure: String
        get() = "$systolic/$diastolic"

    val weight: String
        get() = weightKg.toString()

    val date: String
        get() {
            val formatter = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
            return formatter.format(Date(dateMillis))
        }
}
