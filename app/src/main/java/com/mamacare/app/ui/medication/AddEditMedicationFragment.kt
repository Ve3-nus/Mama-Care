package com.mamacare.app.ui.medication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mamacare.app.data.local.entity.Medication
import com.mamacare.app.databinding.FragmentAddEditMedicationBinding
import com.mamacare.app.ui.FormValidator
import com.mamacare.app.ui.Formatters
import com.mamacare.app.ui.MamaCareViewModelFactory
import com.mamacare.app.ui.clearErrorOnTyping
import com.mamacare.app.ui.showError
import com.mamacare.app.ui.text

/**
 * Add/Edit Medication form.
 * medicationId == -1L -> creating a new medication
 * medicationId != -1L -> editing the medication with that id
 */
class AddEditMedicationFragment : Fragment() {

    private var _binding: FragmentAddEditMedicationBinding? = null
    private val binding get() = _binding!!

    private val args: AddEditMedicationFragmentArgs by navArgs()
    private lateinit var viewModel: MedicationViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddEditMedicationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = MamaCareViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(this, factory)[MedicationViewModel::class.java]

        val mode = if (args.medicationId == -1L) "New medication" else "Editing id ${args.medicationId}"
        binding.tvArgs.text = mode

        listOf(binding.tilMedName, binding.tilDosage, binding.tilTime).forEach { it.clearErrorOnTyping() }

        if (args.medicationId != -1L) {
            viewModel.load(args.medicationId)
            viewModel.selected.observe(viewLifecycleOwner) { med ->
                med?.let {
                    binding.etMedName.setText(it.name)
                    binding.etDosage.setText(it.dosage)
                    binding.etTime.setText(Formatters.time(it.hour, it.minute))
                    binding.etNotes.setText(it.notes)
                }
            }
        }

        binding.btnSave.setOnClickListener {
            val nameError = FormValidator.validateRequired(binding.tilMedName.text())
            val dosageError = FormValidator.validateRequired(binding.tilDosage.text())
            val timeError = FormValidator.validateTime(binding.tilTime.text())

            binding.tilMedName.showError(nameError)
            binding.tilDosage.showError(dosageError)
            binding.tilTime.showError(timeError)

            if (listOf(nameError, dosageError, timeError).all { it == null }) {
                val (hour, minute) = parseTime(binding.tilTime.text())
                val existingId = if (args.medicationId == -1L) 0 else args.medicationId.toInt()
                val med = Medication(
                    id = existingId,
                    name = binding.tilMedName.text().trim(),
                    dosage = binding.tilDosage.text().trim(),
                    hour = hour,
                    minute = minute,
                    notes = binding.tilNotes.text().trim()
                )
                viewModel.save(med)
            }
        }

        viewModel.saved.observe(viewLifecycleOwner) { isSaved ->
            if (isSaved) {
                findNavController().popBackStack()
            }
        }
    }

    private fun parseTime(timeStr: String): Pair<Int, Int> {
        val trimmed = timeStr.trim().uppercase()
        return try {
            if (trimmed.contains("AM") || trimmed.contains("PM")) {
                val isPm = trimmed.contains("PM")
                val clean = trimmed.replace("AM", "").replace("PM", "").trim()
                val parts = clean.split(":")
                var h = parts[0].trim().toInt()
                val m = parts[1].trim().toInt()
                if (isPm && h < 12) h += 12
                if (!isPm && h == 12) h = 0
                Pair(h, m)
            } else {
                val parts = trimmed.split(":")
                Pair(parts[0].trim().toInt(), parts[1].trim().toInt())
            }
        } catch (e: Exception) {
            Pair(8, 0)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
