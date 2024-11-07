package com.example.apicontactos_v1.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.apicontactos_v1.databinding.ActivityContactoFormBinding
import com.example.apicontactos_v1.models.Contacto
import com.example.apicontactos_v1.ui.viewmodels.ContactosViewModel

class   ContactoFormActivity : AppCompatActivity() {
    private lateinit var binding: ActivityContactoFormBinding
    private val viewModel: ContactosViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactoFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Cargar datos si estamos en modo edición
        val contacto = intent.getParcelableExtra<Contacto>("contacto")
        contacto?.let {
            binding.etName.setText(it.name)
            binding.etLastName.setText(it.last_name)
            binding.etCompany.setText(it.company)
            binding.etAddress.setText(it.address)
            binding.etCity.setText(it.city)
            binding.etState.setText(it.state)
        }

        // Configura el botón de guardar
        binding.btnSave.setOnClickListener {
            val newContacto = Contacto(
                id = contacto?.id, // Mantener el ID si estamos actualizando
                name = binding.etName.text.toString(),
                last_name = binding.etLastName.text.toString(),
                company = binding.etCompany.text.toString(),
                address = binding.etAddress.text.toString(),
                city = binding.etCity.text.toString(),
                state = binding.etState.text.toString()
            )

            if (contacto == null) {
                // Crear nuevo contacto
                viewModel.addContact(newContacto)
                Log.d("ContactoFormActivity", "Creando nuevo contacto: $newContacto")
            } else {
                // Actualizer contacto existente
                viewModel.updateContact(newContacto)
                Log.d("ContactoFormActivity", "Actualizando contacto: $newContacto")
            }
            finish() // Termina la actividad
        }

    }
}
