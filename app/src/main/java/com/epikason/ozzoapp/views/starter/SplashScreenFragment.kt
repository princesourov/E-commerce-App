package com.epikason.ozzoapp.views.starter


import android.content.Intent
import android.os.CountDownTimer
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.epikason.ozzoapp.R
import com.epikason.ozzoapp.base.BaseFragment
import com.epikason.ozzoapp.databinding.FragmentSplashScreenBinding
import com.epikason.ozzoapp.views.dashboard.seller.SellerDashboard
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashScreenFragment :
    BaseFragment<FragmentSplashScreenBinding>(FragmentSplashScreenBinding::inflate) {
        @Inject
        lateinit var qAuth: FirebaseAuth


    override fun setListener() {


        object : CountDownTimer(1500, 1000) {
            override fun onFinish() {
                val currentUser = qAuth.currentUser
                if (currentUser != null) {
                    val intent = Intent(requireContext(), SellerDashboard::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                } else {
                    findNavController().navigate(R.id.action_splashScreenFragment_to_startFragment2,null,
                        NavOptions.Builder()
                            .setPopUpTo(R.id.splashScreenFragment, true)
                            .build()
                    )
                }
            }

            override fun onTick(millisUntilFinished: Long) {}
        }.start()

    }

    override fun allObserver() {

    }

}