package com.mamacare.app.ui.healthlog

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.material.textfield.TextInputLayout
import com.mamacare.app.R
import com.mamacare.app.ui.FormValidator
import com.mamacare.app.ui.clearErrorOnTyping
import com.mamacare.app.ui.showError
import com.mamacare.app.ui.text

/**
 * Add/Edit Health Log form.
 * logId == -1L -> new record; otherwise editing the record with that id.
 * Validates blood pressure, weight and date before saving.
 */
class AddEditHealthLogFragment : Fragment(R.layout.fragment_add_edit_health_log) {

    private val args: AddEditHealthLogFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mode = if (args.logId == -1L) "New record" else "Editing id ${args.logId}"
        view.findViewById<TextView>(R.id.tvArgs).text = mode

        val tilBp = view.findViewById<TextInputLayout>(R.id.tilBloodPressure)
        val tilWeight = view.findViewById<TextInputLayout>(R.id.tilWeight)
        val tilDate = view.findViewById<TextInputLayout>(R.id.tilDate)

        // Error disappears as soon as the user starts correcting the field
        listOf(tilBp, tilWeight, tilDate).forEach { it.clearErrorOnTyping() }

        view.findViewById<View>(R.id.btnSave).setOnClickListener {
            val bpError = FormValidator.validateBloodPressure(tilBp.text())
            val weightError = FormValidator.validateWeight(tilWeight.text())
            // TODO: check the date format once agreed with Boniface and Dollan
            val dateError = FormValidator.validateRequired(tilDate.text())

            tilBp.showError(bpError)
            tilWeight.showError(weightError)
            tilDate.showError(dateError)

            if (listOf(bpError, weightError, dateError).all { it == null }) {
                // TODO (Sync with Dollan): call HealthLogViewModel to save, then navigate back
            }
        }
    }
}