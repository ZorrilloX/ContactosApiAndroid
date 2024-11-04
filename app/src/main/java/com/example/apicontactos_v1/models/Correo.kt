package com.example.apicontactos_v1.models

import android.os.Parcel
import android.os.Parcelable

data class Correo(
    var email: String
) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString() ?: "")

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(email)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Correo> {
        override fun createFromParcel(parcel: Parcel): Correo {
            return Correo(parcel)
        }

        override fun newArray(size: Int): Array<Correo?> {
            return arrayOfNulls(size)
        }
    }
}