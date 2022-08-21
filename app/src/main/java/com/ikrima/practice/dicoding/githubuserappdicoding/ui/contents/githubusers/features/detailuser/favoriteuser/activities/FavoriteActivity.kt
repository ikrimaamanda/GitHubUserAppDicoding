package com.ikrima.practice.dicoding.githubuserappdicoding.ui.contents.githubusers.features.detailuser.favoriteuser.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ikrima.practice.dicoding.githubuserappdicoding.databinding.ActivityFavoriteBinding
import com.ikrima.practice.dicoding.githubuserappdicoding.ui.contents.githubusers.activities.GitHubUsersActivity
import com.ikrima.practice.dicoding.githubuserappdicoding.ui.contents.githubusers.features.detailuser.favoriteuser.adapters.FavoriteUserAdapter
import com.ikrima.practice.dicoding.githubuserappdicoding.ui.contents.githubusers.features.detailuser.favoriteuser.viewmodel.MainViewModel
import com.ikrima.practice.dicoding.githubuserappdicoding.ui.contents.githubusers.viewmodel.ViewModelFactory

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFavoriteBinding

    private lateinit var favUserAdapter : FavoriteUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        favUserAdapter = FavoriteUserAdapter()

        val mainViewModel = obtainViewModel(this)
        mainViewModel.getAllFavUser().observe(this) { favList ->
            if (favList != null) {
                if (favList.isEmpty()) {
                    binding.apply {
                        emptyLayoutFavUser.visibility = View.VISIBLE
                        rvFavGithubUsers.visibility = View.GONE
                    }
                } else {
                    binding.apply {
                        emptyLayoutFavUser.visibility = View.GONE
                        rvFavGithubUsers.visibility = View.VISIBLE
                    }
                    favUserAdapter.setListFavUser(favList)
                }
                Log.e("isiFavList", favList.toString())
            }
        }


        settingRvFavUser()

    }

    override fun onBackPressed() {
        val i = Intent(this, GitHubUsersActivity::class.java)
        startActivity(i)
        finish()
    }

    private fun settingRvFavUser() {
        binding.apply {
            rvFavGithubUsers.apply {
                layoutManager = LinearLayoutManager(this@FavoriteActivity)
                setHasFixedSize(true)
                adapter = favUserAdapter
            }
        }
    }

    private fun obtainViewModel(activity : AppCompatActivity) : MainViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[MainViewModel::class.java]
    }


}