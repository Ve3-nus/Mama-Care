package com.mamacare.app.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mamacare.app.databinding.FragmentDashboardBinding
import com.mamacare.app.data.local.SessionManager
import com.mamacare.app.ui.Formatters
import com.mamacare.app.ui.MamaCareViewModelFactory
import com.mamacare.app.ui.medication.MedicationViewModel
import androidx.navigation.NavOptions

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private lateinit var medicationViewModel: MedicationViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val nav = findNavController()

        val factory = MamaCareViewModelFactory(requireActivity().application)
        medicationViewModel = ViewModelProvider(this, factory)[MedicationViewModel::class.java]

        medicationViewModel.medications.observe(viewLifecycleOwner) { meds ->
            if (meds.isNullOrEmpty()) {
                binding.dailyMedication = "No medications scheduled today"
            } else {
                val nextMed = meds.first()
                binding.dailyMedication = "${nextMed.name} (${nextMed.dosage}) • ${Formatters.time(nextMed.hour, nextMed.minute)}"
            }
        }

        binding.btnMedications.setOnClickListener {
            nav.navigate(DashboardFragmentDirections.actionDashboardToMedicationList())
        }
        binding.btnHealthLog.setOnClickListener {
            nav.navigate(DashboardFragmentDirections.actionDashboardToHealthLogList())
        }
        binding.btnHealthInfo.setOnClickListener {
            nav.navigate(DashboardFragmentDirections.actionDashboardToHealthInfo())
        }
        binding.btnSos.setOnClickListener {
            nav.navigate(DashboardFragmentDirections.actionDashboardToSos())
        }
        binding.btnLogout.setOnClickListener {
            SessionManager(requireContext()).logout()
            nav.navigate(
                com.mamacare.app.R.id.loginFragment,
                null,
                NavOptions.Builder()
                    .setPopUpTo(com.mamacare.app.R.id.dashboardFragment, true)
                    .build()
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
