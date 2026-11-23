package com.mamacare.app.ui

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Display helpers called from Data Binding layouts (item_health_log, item_medication).
 * They turn the raw Room values (millis, hour/minute, systolic/diastolic, kg)
 * into the text shown on screen, so the entities stay plain data.
 */
object Formatters {

    @JvmStatic
    fun date(millis: Long): String =
        SimpleDateFormat("MMM d, yyyy", Locale.getDefault()).format(Date(millis))

    @JvmStatic
    fun bloodPressure(systolic: Int, diastolic: Int): String = "$systolic/$diastolic"

    @JvmStatic
    fun weight(kg: Double): String = String.format(Locale.getDefault(), "%.1f", kg)

    /** 8, 0 -> "08:00 AM" */
    @JvmStatic
    fun time(hour: Int, minute: Int): String {
        val period = if (hour < 12) "AM" else "PM"
        val hour12 = when (val h = hour % 12) { 0 -> 12 else -> h }
        return String.format(Locale.getDefault(), "%02d:%02d %s", hour12, minute, period)
    }
}
