package com.ikrima.practice.dicoding.githubuserappdicoding.ui.contents.githubusers.activities

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ikrima.practice.dicoding.githubuserappdicoding.R
import com.ikrima.practice.dicoding.githubuserappdicoding.base.BaseActivityViewModel
import com.ikrima.practice.dicoding.githubuserappdicoding.data.responses.DetailUserResponse
import com.ikrima.practice.dicoding.githubuserappdicoding.databinding.ActivityGitHubUsersBinding
import com.ikrima.practice.dicoding.githubuserappdicoding.ui.contents.githubusers.adapters.RvAllUserAdapter
import com.ikrima.practice.dicoding.githubuserappdicoding.ui.contents.githubusers.viewmodel.GitHubUserViewModel
import com.ikrima.practice.dicoding.githubuserappdicoding.utils.helper.ResultWrapper


class GitHubUsersActivity : BaseActivityViewModel<GitHubUserViewModel>() {


    private lateinit var binding : ActivityGitHubUsersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setViewModel = ViewModelProvider(this)[GitHubUserViewModel::class.java]
        super.onCreate(savedInstanceState)

        binding = ActivityGitHubUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        settingViewModel()

//        readJsonFile()
        subscibeGetAllUsers()

        subscribeSearchUser()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    viewModel.searchUser(query)
                } else {
                    Toast.makeText(this@GitHubUsersActivity, "Pencarian null", Toast.LENGTH_SHORT).show()
                }
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

        return true
    }

    private fun settingViewModel() {
        viewModel.apply {
            setGitHubApiServic(service)
            setSharedPref(sharedPref)
            getAllUsers()
        }
    }

    private fun subscibeGetAllUsers() {
        binding.apply {
            viewModel.listAllUser.observe(this@GitHubUsersActivity) {
                when(it) {
                    is ResultWrapper.Default -> {

                    }
                    is ResultWrapper.Empty -> {
                        progressBar.visibility = View.GONE

                        Toast.makeText(this@GitHubUsersActivity, it.message, Toast.LENGTH_SHORT).show()
                    }
                    is ResultWrapper.Failure -> {
                        progressBar.visibility = View.GONE

                        Toast.makeText(this@GitHubUsersActivity, it.title, Toast.LENGTH_SHORT).show()
                    }
                    is ResultWrapper.Loading -> {
                        progressBar.visibility = View.VISIBLE
                    }
                    is ResultWrapper.Success -> {
                        progressBar.visibility = View.GONE

                        val data = it.data as List<DetailUserResponse>
                        Log.e("isi?", data.toString())
                        settingRvSearch(data)
                    }
                    else -> {
                        progressBar.visibility = View.GONE
                        Toast.makeText(this@GitHubUsersActivity, "Server dalam perbaikan", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }


    private fun subscribeSearchUser() {
        binding.apply {
            viewModel.listSearchUser.observe(this@GitHubUsersActivity) {
                when(it) {
                    is ResultWrapper.Default -> {

                    }
                    is ResultWrapper.Empty -> {
                        progressBar.visibility = View.GONE

                        Toast.makeText(this@GitHubUsersActivity, it.message, Toast.LENGTH_SHORT).show()
                    }
                    is ResultWrapper.Failure -> {
                        progressBar.visibility = View.GONE

                        Toast.makeText(this@GitHubUsersActivity, it.title, Toast.LENGTH_SHORT).show()
                    }
                    is ResultWrapper.Loading -> {
                        progressBar.visibility = View.VISIBLE
                    }
                    is ResultWrapper.Success -> {
                        progressBar.visibility = View.GONE

                        val data = it.data as List<DetailUserResponse>

                        if (data.isEmpty()) {
                            emptyLayout.visibility = View.VISIBLE
                        } else {
                            emptyLayout.visibility = View.GONE
                        }

                        settingRvSearch(data)

                    }
                    else -> {
                        progressBar.visibility = View.GONE
                        Toast.makeText(this@GitHubUsersActivity, "Server dalam perbaikan", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun settingRvSearch(users: List<DetailUserResponse>) {
        val rvGithubUserAdapter = RvAllUserAdapter()
        binding.rvGithubUsers.apply {
            layoutManager = LinearLayoutManager(this@GitHubUsersActivity)
            adapter = rvGithubUserAdapter
        }

        rvGithubUserAdapter.addListSearch(users)
    }

}