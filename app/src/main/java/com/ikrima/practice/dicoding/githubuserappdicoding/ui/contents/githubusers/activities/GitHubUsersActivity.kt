package com.ikrima.practice.dicoding.githubuserappdicoding.ui.contents.githubusers.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.ikrima.practice.dicoding.githubuserappdicoding.databinding.ActivityGitHubUsersBinding
import com.ikrima.practice.dicoding.githubuserappdicoding.ui.contents.githubusers.adapters.RvGithubUsersAdapter
import com.ikrima.practice.dicoding.githubuserappdicoding.ui.contents.githubusers.data.DataGithubUserModel
import com.ikrima.practice.dicoding.githubuserappdicoding.ui.contents.githubusers.data.ProfileModel


class GitHubUsersActivity : AppCompatActivity() {


    private lateinit var binding : ActivityGitHubUsersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGitHubUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        readJsonFile()

    }

    private fun readJsonFile() {
        val jsonString = this.assets.open("Githubuser.json").bufferedReader().use { it.readText() }
        val gson = Gson()
        val test = gson.fromJson(jsonString, DataGithubUserModel::class.java)

        test.users.forEachIndexed{ idx, user ->
            Log.d("data", "item $idx : ${user.username}")
        }

        settingRecyclerview(test.users)

    }

    private fun settingRecyclerview(users: List<ProfileModel>) {
        val rvGithubUserAdapter = RvGithubUsersAdapter()
        binding.rvGithubUsers.apply {
            layoutManager = LinearLayoutManager(this@GitHubUsersActivity)
            adapter = rvGithubUserAdapter
        }

        rvGithubUserAdapter.addGithubUsers(users)
    }

}