package com.epikason.ozzoapp.views.register

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.epikason.ozzoapp.R
import com.epikason.ozzoapp.base.BaseFragment
import com.epikason.ozzoapp.core.DataState
import com.epikason.ozzoapp.core.extract
import com.epikason.ozzoapp.data.models.UserRegistration
import com.epikason.ozzoapp.databinding.FragmentRegisterBinding
import com.epikason.ozzoapp.core.isEmpty
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val viewModel: RegistrationViewModel by viewModels()

    override fun setListener() {
        with(binding) {
            btnRegister.setOnClickListener {
                etName.isEmpty()
                etEmail.isEmpty()
                etPassword.isEmpty()
                if (!etName.isEmpty() && !etEmail.isEmpty() && !etPassword.isEmpty()) {
                    val user = UserRegistration(
                        etName.extract(),
                        etEmail.extract(),
                        etPassword.extract(),
                        "Seller",
                        ""
                    )

                    viewModel.userRegistration(user)
                }
            }
            btLogin.setOnClickListener {
                findNavController().navigate(
                    R.id.action_registerFragment_to_loginFragment2,
                    null,
                    NavOptions
                        .Builder()
                        .setPopUpTo(R.id.registerFragment, true)
                        .build()
                )
            }
        }
    }

    override fun allObserver() {
        registrationResponse()
    }

    private fun registrationResponse() {

        viewModel.registrationResponse.observe(viewLifecycleOwner) {

            when (it) {
                is DataState.Error -> {
                    loadingDialog?.dismiss()
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }

                is DataState.Loading -> {
                    loadingDialog?.show()

                }

                is DataState.Success -> {
                    loadingDialog?.dismiss()
                    Toast.makeText(context, "Registration Successful", Toast.LENGTH_SHORT).show()

                    findNavController().navigate(
                        R.id.action_registerFragment_to_loginFragment2,
                        null,
                        NavOptions
                            .Builder()
                            .setPopUpTo(R.id.registerFragment, true)
                            .build()
                    )

                }
            }
        }
    }
}
