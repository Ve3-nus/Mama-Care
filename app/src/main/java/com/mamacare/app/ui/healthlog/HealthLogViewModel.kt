package com.mamacare.app.ui.healthlog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mamacare.app.data.local.HealthLog
import com.mamacare.app.data.repository.MamaCareRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HealthLogViewModel(private val repo: MamaCareRepository) : ViewModel() {

    val logs: LiveData<List<HealthLog>> = repo.healthLogs

    private val _selected = MutableLiveData<HealthLog?>()
    val selected: LiveData<HealthLog?> = _selected

    private val _saved = MutableLiveData(false)
    val saved: LiveData<Boolean> = _saved

    /** Call only when args.logId != -1L */
    fun load(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _selected.postValue(repo.getHealthLog(id))
        }
    }

    fun validate(weight: String, systolic: String, diastolic: String): String? {
        val w = weight.trim().toDoubleOrNull()
        val s = systolic.trim().toIntOrNull()
        val d = diastolic.trim().toIntOrNull()
        return when {
            w == null || w <= 0.0 -> "Enter a valid weight"
            s == null || s <= 0 -> "Enter a valid systolic value"
            d == null || d <= 0 -> "Enter a valid diastolic value"
            s <= d -> "Systolic must be higher than diastolic"
            else -> null
        }
    }

    fun save(log: HealthLog) {
        viewModelScope.launch(Dispatchers.IO) {
            if (log.id == 0L) repo.addHealthLog(log) else repo.updateHealthLog(log)
            _saved.postValue(true)
        }
    }

    fun delete(log: HealthLog) {
        viewModelScope.launch(Dispatchers.IO) { repo.deleteHealthLog(log) }
    }
}