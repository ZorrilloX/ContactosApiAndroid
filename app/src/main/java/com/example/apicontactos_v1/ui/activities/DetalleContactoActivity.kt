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




    }


}
