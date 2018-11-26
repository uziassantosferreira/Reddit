package com.uziassantosferreira.presentation.model

import android.os.Parcel
import android.os.Parcelable

data class Image(val url: String = "", val width: Int = 0, val height: Int = 0): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(url)
        parcel.writeInt(width)
        parcel.writeInt(height)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Image> {
        override fun createFromParcel(parcel: Parcel): Image {
            return Image(parcel)
        }

        override fun newArray(size: Int): Array<Image?> {
            return arrayOfNulls(size)
        }
    }
}