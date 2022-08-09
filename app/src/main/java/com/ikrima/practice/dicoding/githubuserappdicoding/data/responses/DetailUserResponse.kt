package com.ikrima.practice.dicoding.githubuserappdicoding.data.responses

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class DetailUserResponse(@SerializedName("login") val username : String? = "",
                              val id : Int? = 0,
                              @SerializedName("avatar_url") val avatarURL : String? = "",
                              @SerializedName("followers_url") val followersUrl : String? = "",
                              @SerializedName("following_url") val followingUrl : String? = "",
                              val name : String? = "",
                              val company : String? = "test",
                              val blog : String? = "",
                              val location : String?,
                              val email : String? = "",
                              val bio : String? = "",
                              @SerializedName("twitter_username") val twitter : String? = "",
                              @SerializedName("public_repos") val publicRepo : Int? = 0,
                              val followers : Int? = 0,
                              val following : Int? = 0
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(username)
        parcel.writeValue(id)
        parcel.writeString(avatarURL)
        parcel.writeString(followersUrl)
        parcel.writeString(followingUrl)
        parcel.writeString(name)
        parcel.writeString(company)
        parcel.writeString(blog)
        parcel.writeString(location)
        parcel.writeString(email)
        parcel.writeString(bio)
        parcel.writeString(twitter)
        parcel.writeValue(publicRepo)
        parcel.writeValue(followers)
        parcel.writeValue(following)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DetailUserResponse> {
        override fun createFromParcel(parcel: Parcel): DetailUserResponse {
            return DetailUserResponse(parcel)
        }

        override fun newArray(size: Int): Array<DetailUserResponse?> {
            return arrayOfNulls(size)
        }
    }
}
