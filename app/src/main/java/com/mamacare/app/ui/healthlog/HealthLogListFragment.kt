package com.mamacare.app.ui.healthlog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mamacare.app.R

/**
 * STUB. TODO (UI + ViewModel members): RecyclerView of weight / blood pressure records.
 * Row tap: HealthLogListFragmentDirections.actionHealthLogListToAddEdit(logId = log.id)
 */
class HealthLogListFragment : Fragment(R.layout.fragment_health_log_list) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<View>(R.id.btnAdd).setOnClickListener {
            findNavController().navigate(
                HealthLogListFragmentDirections.actionHealthLogListToAddEdit()
            )
        }
    }
}
