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

class SignUpFragment : Fragment() {
    private lateinit var viewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_signup, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = MamaCareViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]

        val name = view.findViewById<TextInputEditText>(R.id.etSignUpName)
        val email = view.findViewById<TextInputEditText>(R.id.etSignUpEmail)
        val password = view.findViewById<TextInputEditText>(R.id.etSignUpPassword)
        val confirmPassword = view.findViewById<TextInputEditText>(R.id.etSignUpConfirmPassword)
        val signUpButton = view.findViewById<MaterialButton>(R.id.btnSignUp)

        signUpButton.setOnClickListener {
            viewModel.signUp(
                name.text.toString(),
                email.text.toString(),
                password.text.toString(),
                confirmPassword.text.toString()
            )
        }

        view.findViewById<View>(R.id.tvGoToLogin).setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.busy.observe(viewLifecycleOwner) { busy ->
            signUpButton.isEnabled = !busy
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
