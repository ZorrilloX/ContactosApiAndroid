package com.example.apicontactos_v1.repositories

import android.util.Log
import com.example.apicontactos_v1.api.ContactosApiService
import com.example.apicontactos_v1.models.Contacto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ContactosRepository {
    private val api = RetrofitRepository.getRetrofitInstance().create(ContactosApiService::class.java)

    fun getContactList(onSuccess: (List<Contacto>) -> Unit, onError: (Throwable) -> Unit) {
        api.getContactList().enqueue(object : Callback<List<Contacto>> {
            override fun onResponse(call: Call<List<Contacto>>, response: Response<List<Contacto>>) {
                if (response.isSuccessful) {
                    onSuccess(response.body() ?: emptyList())
                }
            }
            override fun onFailure(call: Call<List<Contacto>>, t: Throwable) {
                onError(t)
            }
        })
    }

    fun createContacto(contacto: Contacto, onSuccess: () -> Unit, onError: (Throwable) -> Unit) {
        api.createContacto(contacto).enqueue(object : Callback<Contacto> {
            override fun onResponse(call: Call<Contacto>, response: Response<Contacto>) {
                if (response.isSuccessful) {
                    onSuccess()
                    Log.d("ContactosRepository", "Contacto creado: ${response.body()}")
                } else {
                    Log.e("ContactosRepository", "Error al crear contacto: ${response.errorBody()}")
                    onError(Throwable("Error al crear contacto"))
                }
            }

            override fun onFailure(call: Call<Contacto>, t: Throwable) {
                Log.e("ContactosRepository", "Fallo en la conexión: ${t.message}")
                onError(t)
            }
        })
    }


    fun updateContacto(contacto: Contacto, onSuccess: () -> Unit, onError: (Throwable) -> Unit) {
        // Supongamos que estás usando Retrofit para hacer la llamada
        val call = contacto.id?.let { api.updateContacto(it, contacto) }

        if (call != null) {
            call.enqueue(object : Callback<Contacto> {
                override fun onResponse(call: Call<Contacto>, response: Response<Contacto>) {
                    if (response.isSuccessful) {
                        onSuccess() // Llama a la función de éxito si la respuesta es correcta
                    } else {
                        // Maneja el error aquí, por ejemplo:
                        val errorBody = response.errorBody()?.string()
                        Log.e("ContactosRepository", "Error en la respuesta de actualización: $errorBody")
                        onError(Throwable("Error al actualizar el contacto: ${response.code()}"))
                    }
                }

                override fun onFailure(call: Call<Contacto>, t: Throwable) {
                    Log.e("ContactosRepository", "Error al actualizar el contacto", t)
                    onError(t) // Llama a la función de error
                }
            })
        }
    }



    fun deleteContacto(contactId: Long, onSuccess: () -> Unit, onError: (Throwable) -> Unit) {
        api.deleteContacto(contactId).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) onSuccess()
                else onError(Throwable("Error al eliminar el contacto"))
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                onError(t)
            }
        })
    }


    // Agrega métodos para actualizar y eliminar contactos.
}
