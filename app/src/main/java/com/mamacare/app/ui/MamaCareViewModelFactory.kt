package com.mamacare.app.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mamacare.app.data.local.AppDatabase
import com.mamacare.app.data.remote.RetrofitClient
import com.mamacare.app.data.repository.MamaCareRepository
import com.mamacare.app.ui.healthinfo.HealthInfoViewModel
import com.mamacare.app.ui.healthlog.HealthLogViewModel
import com.mamacare.app.ui.medication.MedicationViewModel

class MamaCareViewModelFactory(app: Application) : ViewModelProvider.Factory {

    private val repo: MamaCareRepository by lazy {
        val db = AppDatabase.getInstance(app)
        MamaCareRepository(db.medicationDao(), db.healthLogDao(), RetrofitClient.service)
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = when {
        modelClass.isAssignableFrom(MedicationViewModel::class.java) -> MedicationViewModel(repo) as T
        modelClass.isAssignableFrom(HealthLogViewModel::class.java) -> HealthLogViewModel(repo) as T
        modelClass.isAssignableFrom(HealthInfoViewModel::class.java) -> HealthInfoViewModel(repo) as T
        else -> throw IllegalArgumentException("Unknown ViewModel: ${modelClass.name}")
    }
}