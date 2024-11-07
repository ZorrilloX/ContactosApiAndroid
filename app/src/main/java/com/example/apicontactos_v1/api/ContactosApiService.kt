package com.example.apicontactos_v1.api

import com.example.apicontactos_v1.models.Contacto
import com.example.apicontactos_v1.models.Telefono
import com.example.apicontactos_v1.models.Correo
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ContactosApiService {
    @GET("/api/personas")
    fun getContactList(): Call<List<Contacto>>

    @POST("/api/personas")
    fun createContacto(@Body contacto: Contacto): Call<Contacto>

    @PUT("/api/personas/{id}")
    fun updateContacto(@Path("id") id: Long, @Body contacto: Contacto): Call<Contacto>

    @DELETE("/api/personas/{id}")
    fun deleteContacto(@Path("id") id: Long): Call<Void>


    @Multipart
    @POST("/api/personas/{id}/profile-picture")
    fun uploadProfilePicture(@Path("id") id: Long, @Part file: MultipartBody.Part): Call<Void>

    // Método para buscar contactos por nombre (por ejemplo, "pepito")
    @GET("/api/search")
    fun searchContactos(@Query("q") query: String): Call<List<Contacto>>



    @POST("/api/phones")
    fun createTelefono(@Body telefono: Telefono): Call<Telefono>  // Crear un teléfono para una persona

    @PUT("/api/phones/{id}")
    fun updateTelefono(@Path("id") id: Long, @Body telefono: Telefono): Call<Telefono>  // Actualizar un teléfono

    @DELETE("/api/phones/{id}")
    fun deleteTelefono(@Path("id") id: Long): Call<Void>  // Eliminar un teléfono de una persona

    //misma version que los 3 metodos de arriba pero para emails:
    @POST("/api/emails")
    fun createEmail(@Body email: Correo): Call<Correo>  // Crear un correo electrónico para una persona

    @PUT("/api/emails/{id}")
    fun updateEmail(@Path("id") id: Long, @Body email: Correo): Call<Correo>  // Actualizar un correo electrónico

    @DELETE("/api/emails/{id}")
    fun deleteEmail(@Path("id") id: Long): Call<Void>  // Eliminar un correo electrónico de una persona

}
