package com.epikason.ozzoapp.views.login

import android.content.Intent
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.epikason.ozzoapp.R
import com.epikason.ozzoapp.base.BaseFragment
import com.epikason.ozzoapp.core.DataState
import com.epikason.ozzoapp.core.extract
import com.epikason.ozzoapp.data.models.UserLogIn
import com.epikason.ozzoapp.databinding.FragmentLoginBinding
import com.epikason.ozzoapp.core.isEmpty
import com.epikason.ozzoapp.views.dashboard.seller.SellerDashboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    private val viewModel: LogInViewModel by viewModels()

    override fun setListener() {
        with(binding) {
            btnLogin.setOnClickListener {
                etEmail.isEmpty()
                etPassword.isEmpty()
                if (!etEmail.isEmpty() && !etPassword.isEmpty()) {
                    var user = UserLogIn(etEmail.extract(), etPassword.extract())

                    viewModel.userLogin(user)
                    loadingDialog?.show()
                }
            }
            btnRegister.setOnClickListener {
                findNavController().navigate(
                    R.id.action_loginFragment_to_registerFragment2,
                    null,
                    NavOptions.Builder()
                        .setPopUpTo(R.id.loginFragment, true)
                        .build()
                )
            }
        }
    }

    override fun allObserver() {
        logInResponse()
    }


    private fun logInResponse() {
        viewModel.logInResponse.observe(viewLifecycleOwner) {
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
                    val intent = Intent(requireContext(), SellerDashboard::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }
            }
        }
    }
}