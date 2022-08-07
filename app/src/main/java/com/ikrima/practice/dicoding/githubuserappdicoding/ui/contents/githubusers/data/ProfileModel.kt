package com.ikrima.practice.dicoding.githubuserappdicoding.ui.contents.githubusers.data

import android.os.Parcel
import android.os.Parcelable

data class ProfileModel(val username : String? = "",
                        val name : String? = "",
                        val avatar : String? = "",
                        val company : String? = "",
                        val location : String? = "",
                        val repository : Int? = 0,
                        val follower : Int? = 0,
                        val following : Int? = 0
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(username)
        parcel.writeString(name)
        parcel.writeString(avatar)
        parcel.writeString(company)
        parcel.writeString(location)
        parcel.writeValue(repository)
        parcel.writeValue(follower)
        parcel.writeValue(following)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProfileModel> {
        override fun createFromParcel(parcel: Parcel): ProfileModel {
            return ProfileModel(parcel)
        }

        override fun newArray(size: Int): Array<ProfileModel?> {
            return arrayOfNulls(size)
        }
    }
}