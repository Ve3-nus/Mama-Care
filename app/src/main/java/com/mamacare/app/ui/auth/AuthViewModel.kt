package com.mamacare.app.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mamacare.app.data.repository.AuthRepository
import com.mamacare.app.data.repository.AuthResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {
    private val _result = MutableLiveData<AuthResult>()
    val result: LiveData<AuthResult> = _result

    private val _busy = MutableLiveData(false)
    val busy: LiveData<Boolean> = _busy

    fun signUp(name: String, email: String, password: String, confirmPassword: String) {
        val validationError = when {
            name.isBlank() -> "Name is required"
            email.isBlank() -> "Email is required"
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches() -> "Enter a valid email"
            password.length < 8 -> "Password must be at least 8 characters"
            password != confirmPassword -> "Passwords do not match"
            else -> null
        }

        if (validationError != null) {
            _result.value = AuthResult.Error(validationError)
            return
        }

        _busy.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val authResult = authRepository.signUp(name, email, password)
            _busy.postValue(false)
            _result.postValue(authResult)
        }
    }

    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _result.value = AuthResult.Error("Email and password are required")
            return
        }

        _busy.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val authResult = authRepository.login(email, password)
            _busy.postValue(false)
            _result.postValue(authResult)
        }
    }
}
