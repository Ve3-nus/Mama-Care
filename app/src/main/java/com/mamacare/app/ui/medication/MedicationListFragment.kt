package com.mamacare.app.ui.medication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mamacare.app.databinding.FragmentMedicationListBinding
import com.mamacare.app.ui.MamaCareViewModelFactory

class MedicationListFragment : Fragment() {

    private var _binding: FragmentMedicationListBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MedicationViewModel
    private lateinit var adapter: MedicationAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMedicationListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // No argument given => default -1 => "add new"
        view.findViewById<View>(R.id.btnAdd).setOnClickListener {

        val factory = MamaCareViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(this, factory)[MedicationViewModel::class.java]

        setupRecyclerView()
        observeMedications()

        binding.btnAdd.setOnClickListener {
            findNavController().navigate(
                MedicationListFragmentDirections.actionMedicationListToAddEdit()
            )
        }
    }

    private fun setupRecyclerView() {
        adapter = MedicationAdapter(
            onItemClick = { med ->
                findNavController().navigate(
                    MedicationListFragmentDirections.actionMedicationListToAddEdit(medicationId = med.id.toLong())
                )
            },
            onDeleteClick = { med ->
                viewModel.delete(med)
            }
        )
        binding.rvMedications.layoutManager = LinearLayoutManager(requireContext())
        binding.rvMedications.adapter = adapter
    }

    private fun observeMedications() {
        viewModel.medications.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
            if (list.isNullOrEmpty()) {
                binding.tvEmptyState.visibility = View.VISIBLE
                binding.rvMedications.visibility = View.GONE
            } else {
                binding.tvEmptyState.visibility = View.GONE
                binding.rvMedications.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
