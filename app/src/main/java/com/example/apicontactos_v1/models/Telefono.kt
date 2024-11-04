package com.example.apicontactos_v1.models

import android.os.Parcel
import android.os.Parcelable

data class Telefono(
    var number: String
) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString() ?: "")

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(number)
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