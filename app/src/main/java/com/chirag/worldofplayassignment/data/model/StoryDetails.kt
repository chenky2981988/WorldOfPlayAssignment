package com.chirag.worldofplayassignment.data.model

import android.os.Parcel
import android.os.Parcelable


/**
 * Created by Chirag Sidhiwala on 24/4/20.
 */
data class StoryDetails(
    val by: String = "",
    val descendants: Int = 0,
    val id: Long = 0,
    val kids: List<Long> = emptyList(),
    val score: Int = 0,
    val time: String = "",
    val title: String = "",
    val type: String = "",
    val url: String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readLong(),
        TODO("kids"),
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(by)
        parcel.writeInt(descendants)
        parcel.writeLong(id)
        parcel.writeInt(score)
        parcel.writeString(time)
        parcel.writeString(title)
        parcel.writeString(type)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<StoryDetails> {
        override fun createFromParcel(parcel: Parcel): StoryDetails {
            return StoryDetails(parcel)
        }

        override fun newArray(size: Int): Array<StoryDetails?> {
            return arrayOfNulls(size)
        }
    }
}