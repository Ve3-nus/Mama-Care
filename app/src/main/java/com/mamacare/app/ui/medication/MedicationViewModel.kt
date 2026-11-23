package com.mamacare.app.ui.medication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mamacare.app.data.local.entity.Medication
import com.mamacare.app.data.repository.MamaCareRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MedicationViewModel(private val repo: MamaCareRepository) : ViewModel() {

    val medications: LiveData<List<Medication>> = repo.medications

    private val _selected = MutableLiveData<Medication?>()
    val selected: LiveData<Medication?> = _selected

    private val _saved = MutableLiveData(false)
    val saved: LiveData<Boolean> = _saved

    /** Call only when args.medicationId != -1L */
    fun load(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _selected.postValue(repo.getMedication(id))
        }
    }

    /** Returns null if valid, otherwise the error message to show. */
    fun validate(name: String, dosage: String): String? = when {
        name.isBlank() -> "Medication name is required"
        dosage.isBlank() -> "Dosage is required"
        (dosage.trim().toDoubleOrNull() ?: 0.0) <= 0.0 -> "Dosage must be a number greater than 0"
        else -> null
    }

    fun save(med: Medication) {
        viewModelScope.launch(Dispatchers.IO) {
            if (med.id == 0) repo.addMedication(med) else repo.updateMedication(med)
            _saved.postValue(true)
        }
    }

    fun delete(med: Medication) {
        viewModelScope.launch(Dispatchers.IO) { repo.deleteMedication(med) }
    }
}