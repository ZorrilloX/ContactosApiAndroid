package com.example.apicontactos_v1.repositories

import android.util.Log
import com.example.apicontactos_v1.api.ContactosApiService
import com.example.apicontactos_v1.models.Contacto
import com.example.apicontactos_v1.models.Correo
import com.example.apicontactos_v1.models.Telefono
import okhttp3.MultipartBody
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

    //IMAGEN----------------------------------------------------------------
    fun uploadProfilePicture(contactId: Long, imageFile: MultipartBody.Part, onSuccess: () -> Unit, onError: (Throwable) -> Unit) {
        api.uploadProfilePicture(contactId, imageFile).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    onSuccess()
                    Log.d("ContactosRepository", "Imagen de perfil subida con éxito")
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("ContactosRepository", "Error al subir imagen de perfil: $errorBody")
                    onError(Throwable("Error al subir imagen de perfil: ${response.code()}"))
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("ContactosRepository", "Error de conexión al subir imagen de perfil", t)
                onError(t)
            }
        })
    }

    //TELEFONOS----------------------------------------------------------------
    //metodo para postear un nuevo telefono:
    fun createTelefono(telefono: Telefono, onSuccess: () -> Unit, onError: (Throwable) -> Unit) {
        api.createTelefono(telefono).enqueue(object : Callback<Telefono> {
            override fun onResponse(call: Call<Telefono>, response: Response<Telefono>) {
                if (response.isSuccessful) {
                    onSuccess()
                    Log.d("ContactosRepository", "Telefono creado: ${response.body()}")
                } else {
                    Log.e("ContactosRepository", "Error al crear telefono: ${response.errorBody()}")
                    onError(Throwable("Error al crear telefono"))
                }
            }

            override fun onFailure(call: Call<Telefono>, t: Throwable) {
                Log.e("ContactosRepository", "Fallo en la conexión: ${t.message}")
                onError(t)
            }
        })
    }

    //metodo para eliminar telefono:
    fun deleteTelefono(telefonoId: Long, onSuccess: () -> Unit, onError: (Throwable) -> Unit) {
        api.deleteTelefono(telefonoId).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) onSuccess()
                else onError(Throwable("Error al eliminar el telefono"))
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                onError(t)
            }
        })
    }

    //metodo para actualizar telefono:
    fun updateTelefono(telefono: Telefono, onSuccess: () -> Unit, onError: (Throwable) -> Unit) {
        val call = telefono.id?.let { api.updateTelefono(it, telefono) }

        if (call != null) {
            call.enqueue(object : Callback<Telefono> {
                override fun onResponse(call: Call<Telefono>, response: Response<Telefono>) {
                    if (response.isSuccessful) {
                        onSuccess() // Llama a la función de éxito si la respuesta es correcta
                    } else {
                        val errorBody = response.errorBody()?.string()
                        Log.e("ContactosRepository", "Error en la respuesta de actualización de teléfono: $errorBody")
                        onError(Throwable("Error al actualizar el teléfono: ${response.code()}"))
                    }
                }

                override fun onFailure(call: Call<Telefono>, t: Throwable) {
                    Log.e("ContactosRepository", "Error al actualizar el teléfono", t)
                    onError(t) // Llama a la función de error
                }
            })
        }
    }

    // CORREOS ----------------------------------------------------------------

    // Método para postear un nuevo correo:
    fun createEmail(email: Correo, onSuccess: () -> Unit, onError: (Throwable) -> Unit) {
        api.createEmail(email).enqueue(object : Callback<Correo> {
            override fun onResponse(call: Call<Correo>, response: Response<Correo>) {
                if (response.isSuccessful) {
                    onSuccess()
                    Log.d("ContactosRepository", "Correo creado: ${response.body()}")
                } else {
                    Log.e("ContactosRepository", "Error al crear correo: ${response.errorBody()}")
                    onError(Throwable("Error al crear correo"))
                }
            }

            override fun onFailure(call: Call<Correo>, t: Throwable) {
                Log.e("ContactosRepository", "Fallo en la conexión: ${t.message}")
                onError(t)
            }
        })
    }

    // Método para eliminar correo:
    fun deleteEmail(correoId: Long, onSuccess: () -> Unit, onError: (Throwable) -> Unit) {
        api.deleteEmail(correoId).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) onSuccess()
                else onError(Throwable("Error al eliminar el correo"))
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                onError(t)
            }
        })
    }

    // Método para actualizar correo:
    fun updateEmail(email: Correo, onSuccess: () -> Unit, onError: (Throwable) -> Unit) {
        val call = email.id?.let { api.updateEmail(it, email) }

        if (call != null) {
            call.enqueue(object : Callback<Correo> {
                override fun onResponse(call: Call<Correo>, response: Response<Correo>) {
                    if (response.isSuccessful) {
                        onSuccess() // Llama a la función de éxito si la respuesta es correcta
                    } else {
                        val errorBody = response.errorBody()?.string()
                        Log.e("ContactosRepository", "Error en la respuesta de actualización de correo: $errorBody")
                        onError(Throwable("Error al actualizar el correo: ${response.code()}"))
                    }
                }

                override fun onFailure(call: Call<Correo>, t: Throwable) {
                    Log.e("ContactosRepository", "Error al actualizar el correo", t)
                    onError(t) // Llama a la función de error
                }
            })
        }
    }


    fun searchContactos(query: String, onSuccess: (List<Contacto>) -> Unit, onError: (Throwable) -> Unit) {
        api.searchContactos(query).enqueue(object : Callback<List<Contacto>> {
            override fun onResponse(call: Call<List<Contacto>>, response: Response<List<Contacto>>) {
                if (response.isSuccessful) {
                    onSuccess(response.body() ?: emptyList())
                } else {
                    Log.e("ContactosRepository", "Error en la búsqueda: ${response.errorBody()}")
                    onError(Throwable("Error en la búsqueda"))
                }
            }

            override fun onFailure(call: Call<List<Contacto>>, t: Throwable) {
                Log.e("ContactosRepository", "Fallo en la conexión para búsqueda: ${t.message}")
                onError(t)
            }
        })
    }








}
