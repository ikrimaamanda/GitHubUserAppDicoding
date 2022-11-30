package com.ikrima.practice.dicoding.githubuserappdicoding.ui.contents.githubusers.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ikrima.practice.dicoding.githubuserappdicoding.data.responses.DetailUserResponse
import com.ikrima.practice.dicoding.githubuserappdicoding.data.responses.SearchUserResponse
import com.ikrima.practice.dicoding.githubuserappdicoding.data.retrofit.GitHubUserApiServices
import com.ikrima.practice.dicoding.githubuserappdicoding.utils.helper.ResultWrapper
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection
import kotlin.coroutines.CoroutineContext

class GitHubUserViewModel : ViewModel(), CoroutineScope {

    private lateinit var service : GitHubUserApiServices

    private val _userData = MutableLiveData<ResultWrapper<Any>>()
    val userData : LiveData<ResultWrapper<Any>> = _userData

    private val _listSearchUser = MutableLiveData<ResultWrapper<List<Any>>>()
    val listSearchUser : LiveData<ResultWrapper<List<Any>>> = _listSearchUser

    private val _listAllUser = MutableLiveData<ResultWrapper<List<Any>>>()
    val listAllUser : LiveData<ResultWrapper<List<Any>>> = _listAllUser

    private val _listFollowers = MutableLiveData<ResultWrapper<List<Any>>>()
    val listFollowers : LiveData<ResultWrapper<List<Any>>> = _listFollowers

    private val _listFollowing = MutableLiveData<ResultWrapper<List<Any>>>()
    val listFollowing : LiveData<ResultWrapper<List<Any>>> = _listFollowing

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main


    init {
        _userData.value = ResultWrapper.default()
        _listSearchUser.value = ResultWrapper.default()
        _listAllUser.value = ResultWrapper.default()
        _listFollowers.value = ResultWrapper.default()
        _listFollowing.value = ResultWrapper.default()
    }


    /*
    * This method to set eureka api service
    * */
    fun setGitHubApiService(service : GitHubUserApiServices) {
        this.service = service
    }


    fun getDetailUser(username : String) {
        _userData.value = ResultWrapper.loading()
        launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    service.getDetailUser(username)
                }

                result.enqueue(object : Callback<DetailUserResponse> {
                    override fun onResponse(
                        call: Call<DetailUserResponse>,
                        response: Response<DetailUserResponse>
                    ) {
                        if (response.isSuccessful) {
                            if (response.code() == HttpURLConnection.HTTP_OK) {
                                response.body().let {
                                    if (it != null) {
                                        _userData.value = ResultWrapper.success(it)
                                    } else {
                                        _userData.value = ResultWrapper.empty("Data Tidak Ditemukan")
                                    }
                                }
                            }

                            Log.d("successGetDetailUser", response.body().toString())
                        } else {
                            Log.e("failedGetDetailUser", response.toString())
                            when(response.code()) {
                                404 -> {
                                    _userData.value = ResultWrapper.empty("Data Tidak Ditemukan")
                                }
                                else -> {
                                    _userData.value = ResultWrapper.fail(title = "Server dalam perbaikan", "")
                                }
                            }
                        }
                    }

                    override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                        Log.e("failureGetDetailUser", "onResponse : ${t.localizedMessage}")
                        _userData.value = ResultWrapper.fail(title = "Gagal mendapatkan data", "")
                    }

                })

            } catch (e : Throwable) {
                Log.e("errorGetDetailUser", "Msg : ${e.localizedMessage}")
                e.printStackTrace()
            }
        }
    }


    fun searchUser(username : String) {
        _listSearchUser.value = ResultWrapper.loading()
        launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    service.searchUser(username)
                }

                result.enqueue(object : Callback<SearchUserResponse> {
                    override fun onResponse(
                        call: Call<SearchUserResponse>,
                        response: Response<SearchUserResponse>
                    ) {
                        if (response.isSuccessful) {
                            if (response.code() == HttpURLConnection.HTTP_OK) {
                                response.body().let {
                                    if (it != null) {
                                        _listSearchUser.value = ResultWrapper.success(it.items)
                                    } else {
                                        _listSearchUser.value = ResultWrapper.empty("Data Tidak Ditemukan")
                                    }
                                }
                            }

                            Log.d("successSearchUser", response.body().toString())
                        } else {
                            Log.e("failedSearchUser", response.toString())
                            when(response.code()) {
                                404 -> {
                                    _listSearchUser.value = ResultWrapper.empty("Data Tidak Ditemukan")
                                }
                                else -> {
                                    _listSearchUser.value = ResultWrapper.fail(title = "Server dalam perbaikan", "")
                                }
                            }
                        }
                    }

                    override fun onFailure(call: Call<SearchUserResponse>, t: Throwable) {
                        Log.e("failureSearchUser", "onResponse : ${t.localizedMessage}")
                        _listSearchUser.value = ResultWrapper.fail(title = "Gagal mendapatkan data", "")
                    }

                })

            } catch (e : Throwable) {
                Log.e("errorSearchUser", "Msg : ${e.localizedMessage}")
                e.printStackTrace()
            }
        }

    }


    fun getAllUsers() {
        _listAllUser.value = ResultWrapper.loading()
        launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    service.getAllUsers()
                }

                result.enqueue(object : Callback<List<DetailUserResponse>> {
                    override fun onResponse(
                        call: Call<List<DetailUserResponse>>,
                        response: Response<List<DetailUserResponse>>
                    ) {
                        if (response.isSuccessful) {
                            if (response.code() == HttpURLConnection.HTTP_OK) {
                                response.body().let {
                                    if (it != null) {
                                        _listAllUser.value = ResultWrapper.success(it)
                                    } else {
                                        _listAllUser.value = ResultWrapper.empty("Data Tidak Ditemukan")
                                    }
                                }
                            }

                            Log.d("successGetAllUsers", response.body().toString())
                        } else {
                            Log.e("failedGetAllUsers", response.toString())
                            when(response.code()) {
                                404 -> {
                                    _listAllUser.value = ResultWrapper.empty("Data Tidak Ditemukan")
                                }
                                else -> {
                                    _listAllUser.value = ResultWrapper.fail(title = "Server dalam perbaikan", "")
                                }
                            }
                        }
                    }

                    override fun onFailure(call: Call<List<DetailUserResponse>>, t: Throwable) {
                        Log.e("failureGetAllusers", "onResponse : ${t.localizedMessage}")
                        _listAllUser.value = ResultWrapper.fail(title = "Gagal mendapatkan data", "")
                    }

                })

            } catch (e : Throwable) {
                Log.e("errorGetAllUsers", "Msg : ${e.localizedMessage}")
                e.printStackTrace()
            }
        }
    }

    fun getFollowers(username: String, type : String) {
        _listFollowers.value = ResultWrapper.loading()
        launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    service.getListUserByType(username, type)
                }

                result.enqueue(object : Callback<List<DetailUserResponse>> {
                    override fun onResponse(
                        call: Call<List<DetailUserResponse>>,
                        response: Response<List<DetailUserResponse>>
                    ) {
                        if (response.isSuccessful) {
                            if (response.code() == HttpURLConnection.HTTP_OK) {
                                response.body().let {
                                    if (it != null) {
                                        _listFollowers.value = ResultWrapper.success(it)
                                    } else {
                                        _listFollowers.value = ResultWrapper.empty("Data Tidak Ditemukan")
                                    }
                                }
                            }

                            Log.d("successGetFollowers", response.body().toString())
                        } else {
                            Log.e("failedGetFollowers", response.toString())
                            when(response.code()) {
                                404 -> {
                                    _listFollowers.value = ResultWrapper.empty("Data Tidak Ditemukan")
                                }
                                else -> {
                                    _listFollowers.value = ResultWrapper.fail(title = "Server dalam perbaikan", "")
                                }
                            }
                        }
                    }

                    override fun onFailure(call: Call<List<DetailUserResponse>>, t: Throwable) {
                        Log.e("failureGetFollowers", "onResponse : ${t.localizedMessage}")
                        _listFollowers.value = ResultWrapper.fail(title = "Gagal mendapatkan data", "")
                    }

                })

            } catch (e : Throwable) {
                Log.e("errorGetFollowers", "Msg : ${e.localizedMessage}")
                e.printStackTrace()
            }
        }

    }

    fun getFollowing(username: String, type : String) {
        _listFollowing.value = ResultWrapper.loading()
        launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    service.getListUserByType(username, type)
                }

                result.enqueue(object : Callback<List<DetailUserResponse>> {
                    override fun onResponse(
                        call: Call<List<DetailUserResponse>>,
                        response: Response<List<DetailUserResponse>>
                    ) {
                        if (response.isSuccessful) {
                            if (response.code() == HttpURLConnection.HTTP_OK) {
                                response.body().let {
                                    if (it != null) {
                                        _listFollowing.value = ResultWrapper.success(it)
                                    } else {
                                        _listFollowing.value = ResultWrapper.empty("Data Tidak Ditemukan")
                                    }
                                }
                            }

                            Log.d("successGetFollowing", response.body().toString())
                        } else {
                            Log.e("failedGetFollowing", response.toString())
                            when(response.code()) {
                                404 -> {
                                    _listFollowing.value = ResultWrapper.empty("Data Tidak Ditemukan")
                                }
                                else -> {
                                    _listFollowing.value = ResultWrapper.fail(title = "Server dalam perbaikan", "")
                                }
                            }
                        }
                    }

                    override fun onFailure(call: Call<List<DetailUserResponse>>, t: Throwable) {
                        Log.e("failureGetFollowing", "onResponse : ${t.localizedMessage}")
                        _listFollowing.value = ResultWrapper.fail(title = "Gagal mendapatkan data", "")
                    }

                })

            } catch (e : Throwable) {
                Log.e("errorGetFollowing", "Msg : ${e.localizedMessage}")
                e.printStackTrace()
            }
        }
    }


    /*
    * This method to cancel coroutine when activity/fragment is destroyed
    * */
    override fun onCleared() {
        Job().cancel()
        super.onCleared()
    }



}