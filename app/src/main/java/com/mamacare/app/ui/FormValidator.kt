package com.mamacare.app.ui

import androidx.annotation.StringRes
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.textfield.TextInputLayout
import com.mamacare.app.R

/**
 * Validation rules for the Medication and Health Log forms.
 * Each function returns null when the value is valid, or the string
 * resource of the error message to show on the TextInputLayout.
 * Kept separate from the fragments so the rules are easy to test and explain.
 */
object FormValidator {

    @StringRes
    fun validateRequired(value: String): Int? =
        if (value.isBlank()) R.string.error_required else null

    // Accepts 24-hour "14:30" or 12-hour "8:00 AM"
    @StringRes
    fun validateTime(value: String): Int? {
        if (value.isBlank()) return R.string.error_required
        val pattern = Regex("^(([01]?\\d|2[0-3]):[0-5]\\d|(0?[1-9]|1[0-2]):[0-5]\\d\\s?(AM|PM|am|pm))$")
        return if (pattern.matches(value.trim())) null else R.string.error_time_invalid
    }

    // Weight in kg, sensible human range
    @StringRes
    fun validateWeight(value: String): Int? {
        if (value.isBlank()) return R.string.error_required
        val weight = value.trim().toDoubleOrNull() ?: return R.string.error_number_invalid
        return if (weight in 20.0..300.0) null else R.string.error_weight_range
    }

    // Blood pressure typed as "systolic/diastolic", e.g. 120/80
    @StringRes
    fun validateBloodPressure(value: String): Int? {
        if (value.isBlank()) return R.string.error_required
        val parts = value.trim().split("/")
        if (parts.size != 2) return R.string.error_bp_format
        val systolic = parts[0].trim().toIntOrNull()
        val diastolic = parts[1].trim().toIntOrNull()
        if (systolic == null || diastolic == null) return R.string.error_bp_format
        if (systolic !in 50..250 || diastolic !in 30..150 || systolic <= diastolic) {
            return R.string.error_bp_range
        }
        return null
    }
}

// Small helpers so the fragments stay short

fun TextInputLayout.text(): String = editText?.text?.toString().orEmpty()

fun TextInputLayout.showError(@StringRes messageRes: Int?) {
    error = messageRes?.let { context.getString(it) }
}

fun TextInputLayout.clearErrorOnTyping() {
    editText?.doAfterTextChanged { error = null }
}