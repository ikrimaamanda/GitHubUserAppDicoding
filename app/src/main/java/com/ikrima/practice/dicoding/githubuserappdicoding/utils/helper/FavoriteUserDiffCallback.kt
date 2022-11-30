package com.ikrima.practice.dicoding.githubuserappdicoding.utils.helper

import androidx.recyclerview.widget.DiffUtil
import com.ikrima.practice.dicoding.githubuserappdicoding.data.responses.DetailUserResponse

class FavoriteUserDiffCallback(private val mOldFavUser : List<DetailUserResponse>, private val mNewFavUser : List<DetailUserResponse>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return mOldFavUser.size
    }

    override fun getNewListSize(): Int {
        return mNewFavUser.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldFavUser[oldItemPosition].id == mNewFavUser[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldFavUser = mOldFavUser[oldItemPosition]
        val newFavUser = mNewFavUser[newItemPosition]
        return oldFavUser.username == newFavUser.username && oldFavUser.avatarURL == newFavUser.avatarURL
    }

}