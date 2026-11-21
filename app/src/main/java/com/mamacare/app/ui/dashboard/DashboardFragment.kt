package com.mamacare.app.ui.dashboard

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mamacare.app.R

/**
 * STUB. TODO (UI members): replace with the real dashboard
 * (next appointment / today's medication). The navigation actions below stay.
 */
class DashboardFragment : Fragment(R.layout.fragment_dashboard) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val nav = findNavController()

        view.findViewById<Button>(R.id.btnMedications).setOnClickListener {
            nav.navigate(DashboardFragmentDirections.actionDashboardToMedicationList())
        }
        view.findViewById<Button>(R.id.btnHealthLog).setOnClickListener {
            nav.navigate(DashboardFragmentDirections.actionDashboardToHealthLogList())
        }
        view.findViewById<Button>(R.id.btnHealthInfo).setOnClickListener {
            nav.navigate(DashboardFragmentDirections.actionDashboardToHealthInfo())
        }
        view.findViewById<Button>(R.id.btnSos).setOnClickListener {
            nav.navigate(DashboardFragmentDirections.actionDashboardToSos())
        }
    }
}
