package com.ikrima.practice.dicoding.githubuserappdicoding.ui.contents.githubusers.features.detailuser.favoriteuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ikrima.practice.dicoding.githubuserappdicoding.databinding.ActivityFavoriteUserBinding
import com.ikrima.practice.dicoding.githubuserappdicoding.ui.contents.githubusers.activities.GitHubUsersActivity
import com.ikrima.practice.dicoding.githubuserappdicoding.ui.contents.githubusers.viewmodel.ViewModelFactory

class FavoriteUserActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFavoriteUserBinding

    private lateinit var favUserAdapter : FavoriteUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        favUserAdapter = FavoriteUserAdapter()

        val mainViewModel = obtainViewModel(this)
        mainViewModel.getAllFavUser().observe(this) { favList ->
            if (favList != null) {
                if (favList.isEmpty()) {
                    binding.apply {
                        emptyLayout.visibility = View.VISIBLE
                        rvGithubUsers.visibility = View.GONE
                    }
                } else {
                    binding.apply {
                        emptyLayout.visibility = View.GONE
                        rvGithubUsers.visibility = View.VISIBLE
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
            rvGithubUsers.apply {
                layoutManager = LinearLayoutManager(this@FavoriteUserActivity)
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