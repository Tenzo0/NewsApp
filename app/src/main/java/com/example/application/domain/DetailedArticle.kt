/*
 * Copyright (c) Barykin Alexey, 2020
 */

package com.example.application.domain

import android.os.Parcel
import android.os.Parcelable


data class DetailedArticle(
    val title: String,
    val description: String,
    val content: String,
    val url: String,
    val imgUrl: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(content)
        parcel.writeString(url)
        parcel.writeString(imgUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DetailedArticle> {
        override fun createFromParcel(parcel: Parcel): DetailedArticle {
            return DetailedArticle(parcel)
        }

        override fun newArray(size: Int): Array<DetailedArticle?> {
            return arrayOfNulls(size)
        }
    }
}
