package com.mamacare.app.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.mamacare.app.R
import com.mamacare.app.data.local.SessionManager
import com.mamacare.app.data.repository.AuthResult
import com.mamacare.app.ui.MamaCareViewModelFactory

class LoginFragment : Fragment() {
    private lateinit var viewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_login, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = MamaCareViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]

        val email = view.findViewById<TextInputEditText>(R.id.etLoginEmail)
        val password = view.findViewById<TextInputEditText>(R.id.etLoginPassword)
        val loginButton = view.findViewById<MaterialButton>(R.id.btnLogin)

        loginButton.setOnClickListener {
            viewModel.login(email.text.toString(), password.text.toString())
        }

        view.findViewById<View>(R.id.tvGoToSignUp).setOnClickListener {
            findNavController().navigate(R.id.action_login_to_signup)
        }

        viewModel.busy.observe(viewLifecycleOwner) { busy ->
            loginButton.isEnabled = !busy
        }

        viewModel.result.observe(viewLifecycleOwner) { result ->
            when (result) {
                is AuthResult.Success -> {
                    SessionManager(requireContext()).login(
                        result.user.id,
                        result.user.name,
                        result.user.email
                    )
                    findNavController().navigate(
                        R.id.dashboardFragment,
                        null,
                        NavOptions.Builder()
                            .setPopUpTo(R.id.loginFragment, true)
                            .build()
                    )
                }
                is AuthResult.Error -> Toast.makeText(requireContext(), result.message, Toast.LENGTH_LONG).show()
            }
        }
    }
}
