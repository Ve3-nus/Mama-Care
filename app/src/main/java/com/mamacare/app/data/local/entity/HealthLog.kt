package com.mamacare.app.data.local.entity
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "health_logs")
data class HealthLog(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val weightKg: Double,
    val systolic: Int,
    val diastolic: Int,
    val dateMillis: Long = System.currentTimeMillis(),
    val notes: String = ""
)