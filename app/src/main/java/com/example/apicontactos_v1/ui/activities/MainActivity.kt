package com.example.apicontactos_v1.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apicontactos_v1.ui.viewmodels.ContactosViewModel
import com.example.apicontactos_v1.databinding.ActivityMainBinding
import com.example.apicontactos_v1.ui.adapters.ContactoAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: ContactosViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupEventListeners()
        setupObservers()
        viewModel.loadContacts()
    }

    private fun setupEventListeners() {
        binding.fabAddContact.setOnClickListener {
            val intent = Intent(this, ContactoFormActivity::class.java)
            startActivity(intent)
        }
        val adapter = binding.rvContactos.adapter as ContactoAdapter
        adapter.onContactClick = { contacto ->
            val intent = Intent(this, DetalleContactoActivity::class.java).apply {
                putExtra("contacto", contacto) // Pasar el contacto seleccionado
            }
            startActivity(intent)
        }


    }


    private fun setupRecyclerView() {
        binding.rvContactos.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = ContactoAdapter(
                arrayListOf(),
                onContactClick = { contacto ->
                    val intent = Intent(this@MainActivity, DetalleContactoActivity::class.java)
                    intent.putExtra("contacto", contacto)
                    startActivity(intent)
                },
                onContactDelete = { contactId ->
                    viewModel.deleteContact(contactId)
                },
                onContactUpdate = { contacto ->
                    val intent = Intent(this@MainActivity, ContactoFormActivity::class.java)
                    intent.putExtra("contacto", contacto)
                    startActivity(intent)
                }
            )
        }
    }



    private fun setupObservers() {
        viewModel.contactList.observe(this) { contactos ->
            val adapter = binding.rvContactos.adapter as ContactoAdapter
            adapter.updateData(contactos)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadContacts()
    }


}
