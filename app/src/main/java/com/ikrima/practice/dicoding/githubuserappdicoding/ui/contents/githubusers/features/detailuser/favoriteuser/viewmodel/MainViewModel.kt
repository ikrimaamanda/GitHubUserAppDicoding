package com.ikrima.practice.dicoding.githubuserappdicoding.ui.contents.githubusers.features.detailuser.favoriteuser.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ikrima.practice.dicoding.githubuserappdicoding.data.repository.FavoriteUserRepository
import com.ikrima.practice.dicoding.githubuserappdicoding.data.responses.DetailUserResponse

class MainViewModel(application: Application) : ViewModel() {

    private val mFavoriteUser : FavoriteUserRepository = FavoriteUserRepository(application)

    fun getAllFavUser() : LiveData<List<DetailUserResponse>> = mFavoriteUser.getListFavUser()

}