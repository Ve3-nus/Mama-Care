package com.mamacare.app.ui.medication

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mamacare.app.R

/**
 * STUB. TODO (UI + ViewModel members): RecyclerView of medications.
 * When a row is tapped, navigate with the medication's id:
 *   MedicationListFragmentDirections.actionMedicationListToAddEdit(medicationId = med.id)
 */
class MedicationListFragment : Fragment(R.layout.fragment_medication_list) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // No argument given => default -1 => "add new"
        view.findViewById<Button>(R.id.btnAdd).setOnClickListener {
            findNavController().navigate(
                MedicationListFragmentDirections.actionMedicationListToAddEdit()
            )
        }
    }
}
