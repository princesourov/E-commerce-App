package com.epikason.ozzoapp.views.dashboard.seller.upload

import android.Manifest
import android.os.Build
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.epikason.ozzoapp.base.BaseFragment
import com.epikason.ozzoapp.core.areAllPermissionGranted
import com.epikason.ozzoapp.core.extract
import com.epikason.ozzoapp.core.isEmpty
import com.epikason.ozzoapp.core.requestPermission
import com.epikason.ozzoapp.data.models.Product
import com.epikason.ozzoapp.databinding.FragmentUploadProductBinding
import com.github.dhaval2404.imagepicker.ImagePicker
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UploadProductFragment :
    BaseFragment<FragmentUploadProductBinding>(FragmentUploadProductBinding::inflate) {


    override fun setListener() {

        permissionsRequest = getPermissionRequest()

        binding.apply {
            ivProduct.setOnClickListener {
                requestPermission(permissionsRequest,permissionList)
            }

            btnUploadProduct.setOnClickListener {
                etProductName.isEmpty()
                etProductDescription.isEmpty()
                etProductPrice.isEmpty()
                etProductAmount.isEmpty()
                if (!etProductName.isEmpty() && !etProductDescription.isEmpty() && !etProductPrice.isEmpty() && !etProductAmount.isEmpty()) {

                    val product = Product(
                        etProductName.extract(),
                        etProductDescription.extract(),
                        etProductPrice.extract().toDouble(),
                        etProductAmount.extract().toInt(),
                    )
                    uploadProduct(product)

                }
            }
        }

    }
    private fun getPermissionRequest(): ActivityResultLauncher<Array<String>> {

        return registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){
            if (areAllPermissionGranted(permissionList)) {
                ImagePicker.with(this)
                    .compress(1024)
                    .maxResultSize(512, 512)
                    .start()
            } else {
                Toast.makeText(requireContext(), "Permissions not granted", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun uploadProduct(product: Product) {


    }

    override fun allObserver() {


    }

    companion object {
        private val permissionList = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arrayOf(
                Manifest.permission.READ_MEDIA_IMAGES,
                Manifest.permission.CAMERA
            )
        } else {
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            )
        }}

    private lateinit var permissionsRequest: ActivityResultLauncher<Array<String>>

}