package com.example.apicontactos_v1.models

import android.os.Parcel
import android.os.Parcelable

data class Telefono(
    val id: Long?,
    val number: String,
    val persona_id: Long?,
    val label: String
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString() ?: "",
        parcel.readLong(),
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id ?: -1L)  // Escribe el id, o un valor por defecto
        parcel.writeString(number)
        parcel.writeLong(persona_id ?: -1L)
        parcel.writeString(label)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Telefono> {
        override fun createFromParcel(parcel: Parcel): Telefono {
            return Telefono(parcel)
        }

        override fun newArray(size: Int): Array<Telefono?> {
            return arrayOfNulls(size)
        }
    }
}
