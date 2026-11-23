package com.mamacare.app.ui.medication

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
 * STUB. TODO: add/edit form.
 * medicationId == -1L  -> creating a new medication
 * medicationId != -1L  -> editing the medication with that id (load it from the ViewModel)
 */
class AddEditMedicationFragment : Fragment(R.layout.fragment_add_edit_medication) {

    private val args: AddEditMedicationFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mode =
            if (args.medicationId == -1L) "New medication" else "Editing id ${args.medicationId}"
        view.findViewById<TextView>(R.id.tvArgs).text = mode

        val tilName = view.findViewById<TextInputLayout>(R.id.tilMedName)
        val tilDosage = view.findViewById<TextInputLayout>(R.id.tilDosage)
        val tilTime = view.findViewById<TextInputLayout>(R.id.tilTime)

        // Error disappears as soon as the user starts correcting the field
        listOf(tilName, tilDosage, tilTime).forEach { it.clearErrorOnTyping() }

        view.findViewById<View>(R.id.btnSave).setOnClickListener {
            val nameError = FormValidator.validateRequired(tilName.text())
            val dosageError = FormValidator.validateRequired(tilDosage.text())
            val timeError = FormValidator.validateTime(tilTime.text())

            tilName.showError(nameError)
            tilDosage.showError(dosageError)
            tilTime.showError(timeError)

            if (listOf(nameError, dosageError, timeError).all { it == null }) {
                // TODO (Sync with Dollan): call MedicationViewModel to save, then navigate back
            }
        }
    }
}