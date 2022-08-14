package com.ikrima.practice.dicoding.githubuserappdicoding.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ikrima.practice.dicoding.githubuserappdicoding.data.responses.DetailUserResponse


@Dao
interface FavoriteUserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavUser(user : DetailUserResponse)

    @Delete
    fun unFavUser(user : DetailUserResponse)

    @Query("SELECT * from detailuserresponse ORDER BY id_fav_user ASC")
    fun getListFavUser() : LiveData<List<DetailUserResponse>>

    @Query("SELECT * from detailuserresponse WHERE username= :username")
    fun getFavUserByUsername(username : String) : LiveData<List<DetailUserResponse>>

}