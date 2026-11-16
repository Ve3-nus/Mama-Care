package com.mamacare.app.ui.healthlog

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.mamacare.app.R

/**
 * STUB. TODO: add/edit form for weight and blood pressure.
 * logId == -1L -> new record; otherwise edit the record with that id.
 */
class AddEditHealthLogFragment : Fragment(R.layout.fragment_add_edit_health_log) {

    private val args: AddEditHealthLogFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mode = if (args.logId == -1L) "New record" else "Editing id ${args.logId}"
        view.findViewById<TextView>(R.id.tvArgs).text = mode
    }
}
