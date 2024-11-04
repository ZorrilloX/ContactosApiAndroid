package com.example.apicontactos_v1.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apicontactos_v1.models.Contacto
import com.example.apicontactos_v1.repositories.ContactosRepository

class ContactosViewModel : ViewModel() {
    private val _contactList = MutableLiveData<List<Contacto>>()
    val contactList: LiveData<List<Contacto>> = _contactList

    fun loadContacts() {
        ContactosRepository.getContactList(
            onSuccess = { _contactList.value = it },
            onError = { it.printStackTrace() }
        )
    }

    fun addContact(contacto: Contacto) {
        ContactosRepository.createContacto(contacto,
            onSuccess = {
                loadContacts() // Asegúrate de que esto se llame aquí
            },
            onError = { it.printStackTrace() }
        )
    }

    fun updateContact(contacto: Contacto) {
        ContactosRepository.updateContacto(contacto,
            onSuccess = {
                loadContacts() // Asegúrate de que esto se llame aquí
            },
            onError = { it.printStackTrace() }
        )
    }




    fun deleteContact(contactId: Long) {
        ContactosRepository.deleteContacto(contactId,
            onSuccess = { loadContacts() },
            onError = { it.printStackTrace() }
        )
    }


    // Agrega funciones para actualizar y eliminar contactos.
}
