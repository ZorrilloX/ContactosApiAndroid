package com.example.apicontactos_v1.api

import com.example.apicontactos_v1.models.Contacto
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

    // También puedes agregar endpoints para manejar teléfonos y correos
}
