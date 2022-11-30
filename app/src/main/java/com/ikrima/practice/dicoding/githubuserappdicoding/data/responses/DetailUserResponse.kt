package com.ikrima.practice.dicoding.githubuserappdicoding.data.responses

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class DetailUserResponse(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_fav_user")
    var idFavUser : Int = 0,

    @ColumnInfo(name = "username")
    @SerializedName("login") val username : String? = "",

    @ColumnInfo(name = "is_user")
    val id : Int? = 0,

    @ColumnInfo(name = "avatar_url")
    @SerializedName("avatar_url") val avatarURL : String? = "",

    @ColumnInfo(name = "followers_url")
    @SerializedName("followers_url") val followersUrl : String? = "",

    @ColumnInfo(name = "following_url")
    @SerializedName("following_url") val followingUrl : String? = "",

    @ColumnInfo(name = "name")
    val name : String? = "",

    @ColumnInfo(name = "company")
    val company : String? = "",

    @ColumnInfo(name = "blog")
    val blog : String? = "",

    @ColumnInfo(name = "location")
    val location : String?,

    @ColumnInfo(name = "email")
    val email : String? = "",

    @ColumnInfo(name = "bio")
    val bio : String? = "",

    @ColumnInfo(name = "twitter_username")
    @SerializedName("twitter_username") val twitter : String? = "",

    @ColumnInfo(name = "public_repos")
    @SerializedName("public_repos") val publicRepo : Int? = 0,

    @ColumnInfo(name = "followers")
    val followers : Int? = 0,

    @ColumnInfo(name = "following")
    val following : Int? = 0
) : Parcelable
