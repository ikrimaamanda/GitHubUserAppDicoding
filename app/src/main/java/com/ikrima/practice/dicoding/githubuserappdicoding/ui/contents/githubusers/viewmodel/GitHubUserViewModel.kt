package com.ikrima.practice.dicoding.githubuserappdicoding.ui.contents.githubusers.viewmodel

import android.util.Log
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

    val userData = MutableLiveData<ResultWrapper<Any>>()
    val listSearchUser = MutableLiveData<ResultWrapper<List<Any>>>()
    val listAllUser = MutableLiveData<ResultWrapper<List<Any>>>()

    val listFollowers = MutableLiveData<ResultWrapper<List<Any>>>()
    val listFollowing = MutableLiveData<ResultWrapper<List<Any>>>()

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main


    init {
        userData.value = ResultWrapper.default()
        listSearchUser.value = ResultWrapper.default()
        listAllUser.value = ResultWrapper.default()
        listFollowers.value = ResultWrapper.default()
        listFollowing.value = ResultWrapper.default()
    }


    /*
    * This method to set eureka api service
    * */
    fun setGitHubApiService(service : GitHubUserApiServices) {
        this.service = service
    }


    fun getDetailUser(username : String) {
        userData.value = ResultWrapper.loading()
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
                                        userData.value = ResultWrapper.success(it)
                                    } else {
                                        userData.value = ResultWrapper.empty("Data Tidak Ditemukan")
                                    }
                                }
                            }

                            Log.d("successGetDetailUser", response.body().toString())
                        } else {
                            Log.e("failedGetDetailUser", response.toString())
                            when(response.code()) {
                                404 -> {
                                    userData.value = ResultWrapper.empty("Data Tidak Ditemukan")
                                }
                                else -> {
                                    userData.value = ResultWrapper.fail(title = "Server dalam perbaikan", "")
                                }
                            }
                        }
                    }

                    override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                        Log.e("failureGetDetailUser", "onResponse : ${t.localizedMessage}")
                        userData.value = ResultWrapper.fail(title = "Gagal mendapatkan data", "")
                    }

                })

            } catch (e : Throwable) {
                Log.e("errorGetDetailUser", "Msg : ${e.localizedMessage}")
                e.printStackTrace()
            }
        }
    }


    fun searchUser(username : String) {
        listSearchUser.value = ResultWrapper.loading()
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
                                        listSearchUser.value = ResultWrapper.success(it.items)
                                    } else {
                                        listSearchUser.value = ResultWrapper.empty("Data Tidak Ditemukan")
                                    }
                                }
                            }

                            Log.d("successSearchUser", response.body().toString())
                        } else {
                            Log.e("failedSearchUser", response.toString())
                            when(response.code()) {
                                404 -> {
                                    listSearchUser.value = ResultWrapper.empty("Data Tidak Ditemukan")
                                }
                                else -> {
                                    listSearchUser.value = ResultWrapper.fail(title = "Server dalam perbaikan", "")
                                }
                            }
                        }
                    }

                    override fun onFailure(call: Call<SearchUserResponse>, t: Throwable) {
                        Log.e("failureSearchUser", "onResponse : ${t.localizedMessage}")
                        listSearchUser.value = ResultWrapper.fail(title = "Gagal mendapatkan data", "")
                    }

                })

            } catch (e : Throwable) {
                Log.e("errorSearchUser", "Msg : ${e.localizedMessage}")
                e.printStackTrace()
            }
        }

    }


    fun getAllUsers() {
        listAllUser.value = ResultWrapper.loading()
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
                                        listAllUser.value = ResultWrapper.success(it)
                                    } else {
                                        listAllUser.value = ResultWrapper.empty("Data Tidak Ditemukan")
                                    }
                                }
                            }

                            Log.d("successGetAllUsers", response.body().toString())
                        } else {
                            Log.e("failedGetAllUsers", response.toString())
                            when(response.code()) {
                                404 -> {
                                    listAllUser.value = ResultWrapper.empty("Data Tidak Ditemukan")
                                }
                                else -> {
                                    listAllUser.value = ResultWrapper.fail(title = "Server dalam perbaikan", "")
                                }
                            }
                        }
                    }

                    override fun onFailure(call: Call<List<DetailUserResponse>>, t: Throwable) {
                        Log.e("failureGetAllusers", "onResponse : ${t.localizedMessage}")
                        listAllUser.value = ResultWrapper.fail(title = "Gagal mendapatkan data", "")
                    }

                })

            } catch (e : Throwable) {
                Log.e("errorGetAllUsers", "Msg : ${e.localizedMessage}")
                e.printStackTrace()
            }
        }
    }

    fun getFollowers(username: String) {
        listFollowers.value = ResultWrapper.loading()
        launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    service.getListFollowers(username)
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
                                        listFollowers.value = ResultWrapper.success(it)
                                    } else {
                                        listFollowers.value = ResultWrapper.empty("Data Tidak Ditemukan")
                                    }
                                }
                            }

                            Log.d("successGetFollowers", response.body().toString())
                        } else {
                            Log.e("failedGetFollowers", response.toString())
                            when(response.code()) {
                                404 -> {
                                    listFollowers.value = ResultWrapper.empty("Data Tidak Ditemukan")
                                }
                                else -> {
                                    listFollowers.value = ResultWrapper.fail(title = "Server dalam perbaikan", "")
                                }
                            }
                        }
                    }

                    override fun onFailure(call: Call<List<DetailUserResponse>>, t: Throwable) {
                        Log.e("failureGetFollowers", "onResponse : ${t.localizedMessage}")
                        listFollowers.value = ResultWrapper.fail(title = "Gagal mendapatkan data", "")
                    }

                })

            } catch (e : Throwable) {
                Log.e("errorGetFollowers", "Msg : ${e.localizedMessage}")
                e.printStackTrace()
            }
        }

    }

    fun getFollowing(username: String) {
        listFollowing.value = ResultWrapper.loading()
        launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    service.getListFollowing(username)
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
                                        listFollowing.value = ResultWrapper.success(it)
                                    } else {
                                        listFollowing.value = ResultWrapper.empty("Data Tidak Ditemukan")
                                    }
                                }
                            }

                            Log.d("successGetFollowing", response.body().toString())
                        } else {
                            Log.e("failedGetFollowing", response.toString())
                            when(response.code()) {
                                404 -> {
                                    listFollowing.value = ResultWrapper.empty("Data Tidak Ditemukan")
                                }
                                else -> {
                                    listFollowing.value = ResultWrapper.fail(title = "Server dalam perbaikan", "")
                                }
                            }
                        }
                    }

                    override fun onFailure(call: Call<List<DetailUserResponse>>, t: Throwable) {
                        Log.e("failureGetFollowing", "onResponse : ${t.localizedMessage}")
                        listFollowing.value = ResultWrapper.fail(title = "Gagal mendapatkan data", "")
                    }

                })

            } catch (e : Throwable) {
                Log.e("errorGetFollowing", "Msg : ${e.localizedMessage}")
                e.printStackTrace()
            }
        }
    }

}