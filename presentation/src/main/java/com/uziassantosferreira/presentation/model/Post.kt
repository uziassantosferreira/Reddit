package com.uziassantosferreira.presentation.model

import android.os.Parcel
import android.os.Parcelable
import java.util.Date

data class Post(val title: String = "", val author: Author = Author(),
                val imagePreview: List<Image> = listOf(), val date: Date = Date(),
                val text: String = "") : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readParcelable(Author::class.java.classLoader)!!,
        parcel.createTypedArrayList(Image)!!,
        Date(parcel.readLong()),
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeParcelable(author, flags)
        parcel.writeTypedList(imagePreview)
        parcel.writeLong(date.time)
        parcel.writeString(text)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Post> {
        override fun createFromParcel(parcel: Parcel): Post {
            return Post(parcel)
        }

        override fun newArray(size: Int): Array<Post?> {
            return arrayOfNulls(size)
        }
    }
}