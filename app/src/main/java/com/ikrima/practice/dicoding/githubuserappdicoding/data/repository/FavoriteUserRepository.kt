package com.ikrima.practice.dicoding.githubuserappdicoding.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.ikrima.practice.dicoding.githubuserappdicoding.data.local.FavoriteUserDao
import com.ikrima.practice.dicoding.githubuserappdicoding.data.local.FavoriteUserRoomDatabase
import com.ikrima.practice.dicoding.githubuserappdicoding.data.responses.DetailUserResponse
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteUserRepository(application: Application) {

    private val mFavUserDao : FavoriteUserDao
    private val executorService : ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteUserRoomDatabase.getDatabase(application)
        mFavUserDao = db.favUserDao()
    }

    fun getListFavUser() : LiveData<List<DetailUserResponse>> = mFavUserDao.getListFavUser()

    fun insertFavUser(user : DetailUserResponse) {
        executorService.execute { mFavUserDao.insertFavUser(user) }
    }

    fun unFavUser(user : DetailUserResponse) {
        executorService.execute { mFavUserDao.unFavUser(user) }
    }

    fun getFavUserByUsername(username : String) : LiveData<List<DetailUserResponse>> = mFavUserDao.getFavUserByUsername(username)

}