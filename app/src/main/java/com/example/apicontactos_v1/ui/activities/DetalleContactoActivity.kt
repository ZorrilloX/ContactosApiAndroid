package com.example.apicontactos_v1.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.apicontactos_v1.R
import com.example.apicontactos_v1.databinding.ActivityContactoDetailBinding
import com.example.apicontactos_v1.databinding.ActivityContactoFormBinding
import com.example.apicontactos_v1.models.Contacto

class DetalleContactoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityContactoDetailBinding
    private lateinit var contacto: Contacto

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactoDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)



        setupEventListeners()
    }

    private fun setupEventListeners() {
        binding.buttonUploadPhoto.setOnClickListener {
            openGallery()
        }
        binding.btnSubmit.setOnClickListener {
            selectedImageUri?.let { uri ->
                uploadProfilePicture(uri)
            }
        }
    }
    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK).apply {
            type = "image/*"
        }
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == RESULT_OK) {
            selectedImageUri = data?.data
            binding.imageViewProfile.setImageURI(selectedImageUri)
        }
    }
}
