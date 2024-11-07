package com.example.apicontactos_v1.models

import android.os.Parcel
import android.os.Parcelable

data class Correo(
    val id: Long?,
    var email: String,
    var persona_id: Long? = null,
    val label: String? = null, // La etiqueta, como 'Personal', 'Trabajo', etc.
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        email = parcel.readString() ?: "",  // Default to an empty string if null
        persona_id = parcel.readValue(Long::class.java.classLoader) as? Long,  // Handles null safely
        label = parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id ?: -1L)  // Use -1 as default if id is null
        parcel.writeString(email)
        parcel.writeValue(persona_id)  // Write persona_id as nullable Long
        parcel.writeString(label)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Correo> {
        override fun createFromParcel(parcel: Parcel): Correo {
            return Correo(parcel)
        }

        override fun newArray(size: Int): Array<Correo?> {
            return arrayOfNulls(size)
        }
    }
}
