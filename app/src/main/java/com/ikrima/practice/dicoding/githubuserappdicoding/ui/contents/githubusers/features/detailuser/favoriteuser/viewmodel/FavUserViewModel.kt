package com.ikrima.practice.dicoding.githubuserappdicoding.ui.contents.githubusers.features.detailuser.favoriteuser.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ikrima.practice.dicoding.githubuserappdicoding.data.repository.FavoriteUserRepository
import com.ikrima.practice.dicoding.githubuserappdicoding.data.responses.DetailUserResponse

class FavUserViewModel(application: Application) : ViewModel() {

    private val mFavUserRepository : FavoriteUserRepository = FavoriteUserRepository(application)


    fun insertFavUser(user : DetailUserResponse) {
        mFavUserRepository.insertFavUser(user)
    }

    fun unFavUser(user : DetailUserResponse) {
        mFavUserRepository.unFavUser(user)
    }

    fun getFavUserByUsername(username : String) : LiveData<List<DetailUserResponse>> = mFavUserRepository.getFavUserByUsername(username)

}