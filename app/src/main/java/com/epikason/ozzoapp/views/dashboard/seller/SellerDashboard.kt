package com.epikason.ozzoapp.views.dashboard.seller

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.epikason.ozzoapp.R
import com.epikason.ozzoapp.databinding.ActivitySellerDashboardBinding
import com.epikason.ozzoapp.views.starter.MainActivity
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SellerDashboard : AppCompatActivity() {
    lateinit var binding: ActivitySellerDashboardBinding
    lateinit var navController: NavController

    @Inject
    lateinit var qAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySellerDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)


        navController = findNavController(R.id.fragmentContainerView2)

        val appBarConfig = AppBarConfiguration(
            setOf(
                R.id.myProductFragment,
                R.id.uploadProductFragment,
                R.id.sellerProfileFragment
            )
        )

        binding.bottomNavigationView.setupWithNavController(navController)
        setupActionBarWithNavController(navController, appBarConfig)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.seller_to_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_report -> {

            }

            R.id.menu_settings -> {

            }

            R.id.menu_logout -> {
                val alert = AlertDialog.Builder(this)
                    .setTitle("Log out?")
                    .setMessage("Are you sure you want to log out?")
                    .setPositiveButton("LOG OUT", null)
                    .setNegativeButton("CANCEL", null)
                    .show()
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                    qAuth.signOut()
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    alert.dismiss()
                    finish()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}