package com.ikrima.practice.dicoding.githubuserappdicoding.data.retrofit

import com.ikrima.practice.dicoding.githubuserappdicoding.data.remote.UrlEndPoint
import com.ikrima.practice.dicoding.githubuserappdicoding.data.responses.DetailUserResponse
import com.ikrima.practice.dicoding.githubuserappdicoding.data.responses.SearchUserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubUserApiServices {

    @GET("/users/{username}")
    fun getDetailUser(@Path("username") username : String) : Call<DetailUserResponse>

    @GET(UrlEndPoint.SEARCH_USER)
    fun searchUser(@Query("q") query : String) : Call<SearchUserResponse>

    @GET("/users")
    fun getAllUsers() : Call<List<DetailUserResponse>>

    @GET("/users/{username}/{type}")
    fun getListUserByType(@Path("username") username : String,
                          @Path("type") type : String
    ) : Call<List<DetailUserResponse>>

}