package com.mamacare.app.ui.healthlog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mamacare.app.data.local.entity.HealthLog
import com.mamacare.app.databinding.FragmentAddEditHealthLogBinding
import com.mamacare.app.ui.FormValidator
import com.mamacare.app.ui.Formatters
import com.mamacare.app.ui.MamaCareViewModelFactory
import com.mamacare.app.ui.clearErrorOnTyping
import com.mamacare.app.ui.showError
import com.mamacare.app.ui.text
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Add/Edit Health Log form.
 * logId == -1L -> new record; otherwise editing the record with that id.
 */
class AddEditHealthLogFragment : Fragment() {

    private var _binding: FragmentAddEditHealthLogBinding? = null
    private val binding get() = _binding!!

    private val args: AddEditHealthLogFragmentArgs by navArgs()
    private lateinit var viewModel: HealthLogViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddEditHealthLogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = MamaCareViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(this, factory)[HealthLogViewModel::class.java]

        val mode = if (args.logId == -1L) "New record" else "Editing id ${args.logId}"
        binding.tvArgs.text = mode

        // Prefill default date if creating a new entry
        if (args.logId == -1L && binding.tilDate.text().isBlank()) {
            binding.etDate.setText(Formatters.date(System.currentTimeMillis()))
        }

        listOf(binding.tilBloodPressure, binding.tilWeight, binding.tilDate).forEach { it.clearErrorOnTyping() }

        if (args.logId != -1L) {
            viewModel.load(args.logId)
            viewModel.selected.observe(viewLifecycleOwner) { log ->
                log?.let {
                    binding.etBloodPressure.setText(Formatters.bloodPressure(it.systolic, it.diastolic))
                    binding.etWeight.setText(Formatters.weight(it.weightKg))
                    binding.etDate.setText(Formatters.date(it.dateMillis))
                }
            }
        }

        binding.btnSave.setOnClickListener {
            val bpError = FormValidator.validateBloodPressure(binding.tilBloodPressure.text())
            val weightError = FormValidator.validateWeight(binding.tilWeight.text())
            val dateError = FormValidator.validateRequired(binding.tilDate.text())

            binding.tilBloodPressure.showError(bpError)
            binding.tilWeight.showError(weightError)
            binding.tilDate.showError(dateError)

            if (listOf(bpError, weightError, dateError).all { it == null }) {
                val bpParts = binding.tilBloodPressure.text().split("/")
                val sys = bpParts[0].trim().toInt()
                val dia = bpParts[1].trim().toInt()
                val weight = binding.tilWeight.text().trim().toDouble()
                val dateMillis = parseDate(binding.tilDate.text())
                val existingId = if (args.logId == -1L) 0 else args.logId.toInt()

                val log = HealthLog(
                    id = existingId,
                    weightKg = weight,
                    systolic = sys,
                    diastolic = dia,
                    dateMillis = dateMillis
                )
                viewModel.save(log)
            }
        }

        viewModel.saved.observe(viewLifecycleOwner) { isSaved ->
            if (isSaved) {
                findNavController().popBackStack()
            }
        }
    }

    private fun parseDate(dateStr: String): Long {
        return try {
            val format = SimpleDateFormat("MMM d, yyyy", Locale.getDefault())
            format.parse(dateStr)?.time ?: System.currentTimeMillis()
        } catch (_: Exception) {
            System.currentTimeMillis()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
