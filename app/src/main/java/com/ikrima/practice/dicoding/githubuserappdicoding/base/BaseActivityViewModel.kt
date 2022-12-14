package com.ikrima.practice.dicoding.githubuserappdicoding.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.ikrima.practice.dicoding.githubuserappdicoding.data.retrofit.ApiConfig
import com.ikrima.practice.dicoding.githubuserappdicoding.data.retrofit.GitHubUserApiServices

abstract class BaseActivityViewModel<ActivityViewModel : ViewModel> : AppCompatActivity() {

    protected lateinit var viewModel: ActivityViewModel
    protected var setViewModel: ActivityViewModel ? = null
    protected lateinit var service : GitHubUserApiServices

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = setViewModel!!
        service = ApiConfig.getApiClientGitHubUser()!!.create(GitHubUserApiServices::class.java)
    }

    protected fun settingActionBar(title : String) {
        supportActionBar?.title = title
    }

}