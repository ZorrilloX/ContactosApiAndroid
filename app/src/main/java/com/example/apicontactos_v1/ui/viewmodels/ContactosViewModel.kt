package com.example.apicontactos_v1.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apicontactos_v1.models.Contacto
import com.example.apicontactos_v1.models.Correo
import com.example.apicontactos_v1.models.Telefono
import com.example.apicontactos_v1.repositories.ContactosRepository
import okhttp3.MultipartBody

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


    // Método para subir la imagen de perfil
    fun subirFoto(contactoId: Long, imageFile: MultipartBody.Part) {
        ContactosRepository.uploadProfilePicture(contactoId, imageFile,
            onSuccess = {
                loadContacts()
            },
            onError = { error ->
                Log.e("ContactoViewModel", "Error al subir la imagen: $error")
            }
        )
    }

    //metodo para post telefono:
    fun addTelefono(telefono: Telefono) {
        ContactosRepository.createTelefono(telefono,
            onSuccess = {
                loadContacts()
            },
            onError = { it.printStackTrace() }
        )
    }

    fun deleteTelefono(phoneId: Long) {
        ContactosRepository.deleteTelefono(phoneId,
            onSuccess = {
                loadContacts()
            },
            onError = { it.printStackTrace() }
        )
    }

    fun updateTelefono(telefono: Telefono) {
        ContactosRepository.updateTelefono(telefono,
            onSuccess = {
                loadContacts()
            },
            onError = { it.printStackTrace() }
        )
    }


    // Método para agregar correo:
    fun addEmail(email: Correo) {
        ContactosRepository.createEmail(email,
            onSuccess = {
                loadContacts()
            },
            onError = { it.printStackTrace() }
        )
    }

    // Método para eliminar correo:
    fun deleteEmail(emailId: Long) {
        ContactosRepository.deleteEmail(emailId,
            onSuccess = {
                loadContacts()
            },
            onError = { it.printStackTrace() }
        )
    }

    // Método para actualizar correo:
    fun updateEmail(email: Correo) {
        ContactosRepository.updateEmail(email,
            onSuccess = {
                loadContacts()
            },
            onError = { it.printStackTrace() }
        )
    }

    fun searchContact(query: String) {
        ContactosRepository.searchContactos(query,
            onSuccess = { resultados ->
                _contactList.value = resultados
            },
            onError = { error ->
                Log.e("ContactoViewModel", "Error al buscar el contacto: $error")
            }
        )
    }
}
