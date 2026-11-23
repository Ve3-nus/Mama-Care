package com.mamacare.app.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mamacare.app.data.remote.RetrofitClient
import com.mamacare.app.ui.healthinfo.HealthInfoViewModel

class MamaCareViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HealthInfoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HealthInfoViewModel(RetrofitClient.service) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
