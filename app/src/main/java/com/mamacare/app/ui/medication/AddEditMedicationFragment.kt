package com.mamacare.app.ui.medication

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.mamacare.app.R

/**
 * STUB. TODO: add/edit form.
 * medicationId == -1L  -> creating a new medication
 * medicationId != -1L  -> editing the medication with that id (load it from the ViewModel)
 */
class AddEditMedicationFragment : Fragment(R.layout.fragment_add_edit_medication) {

    private val args: AddEditMedicationFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mode = if (args.medicationId == -1L) "New medication" else "Editing id ${args.medicationId}"
        view.findViewById<TextView>(R.id.tvArgs).text = mode
    }
}
