package com.example.apicontactos_v1.models

import android.os.Parcel
import android.os.Parcelable

data class Contacto(
    var id: Long? = null,
    var name: String,
    var last_name: String,
    var company: String? = null,
    var address: String? = null,
    var city: String? = null,
    var state: String? = null,
    var profile_picture: String? = null,
    var phones: List<Telefono> = emptyList(),
    var emails: List<Correo> = emptyList()
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.createTypedArrayList(Telefono.CREATOR) ?: emptyList(),
        parcel.createTypedArrayList(Correo.CREATOR) ?: emptyList()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(name)
        parcel.writeString(last_name)
        parcel.writeString(company)
        parcel.writeString(address)
        parcel.writeString(city)
        parcel.writeString(state)
        parcel.writeString(profile_picture)
        parcel.writeTypedList(phones)
        parcel.writeTypedList(emails)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Contacto> {
        override fun createFromParcel(parcel: Parcel): Contacto {
            return Contacto(parcel)
        }

        override fun newArray(size: Int): Array<Contacto?> {
            return arrayOfNulls(size)
        }
    }
}
