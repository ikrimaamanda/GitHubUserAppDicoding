package com.ikrima.practice.dicoding.githubuserappdicoding.ui.contents.githubusers.features.detailuser

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import com.google.android.material.tabs.TabLayoutMediator
import com.ikrima.practice.dicoding.githubuserappdicoding.R
import com.ikrima.practice.dicoding.githubuserappdicoding.base.BaseActivityViewModel
import com.ikrima.practice.dicoding.githubuserappdicoding.data.responses.DetailUserResponse
import com.ikrima.practice.dicoding.githubuserappdicoding.databinding.ActivityDetailGithubUserBinding
import com.ikrima.practice.dicoding.githubuserappdicoding.ui.contents.githubusers.viewmodel.GitHubUserViewModel
import com.ikrima.practice.dicoding.githubuserappdicoding.utils.helper.ResultWrapper
import com.ikrima.practice.dicoding.githubuserappdicoding.utils.uiutils.UIUtils.loadImage

class DetailGithubUserActivity : BaseActivityViewModel<GitHubUserViewModel>() {

    private lateinit var binding : ActivityDetailGithubUserBinding

    private lateinit var list : DetailUserResponse
    private var username = ""

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setViewModel = ViewModelProvider(this)[GitHubUserViewModel::class.java]
        super.onCreate(savedInstanceState)

        binding = ActivityDetailGithubUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setParcelableData()

        settingViewModel()

        subscribeDetailUser()

        settingViewPager()

        sendDataToFragment()

    }

    private fun setParcelableData() {
        list = intent.getParcelableExtra<DetailUserResponse>("githubUserData") as DetailUserResponse
        username = list.username?:""
    }

    private fun settingViewModel() {
        viewModel.apply {
            setGitHubApiService(service)
            getDetailUser(username)
        }
    }

    private fun subscribeDetailUser() {
        binding.apply {
            viewModel.userData.observe(this@DetailGithubUserActivity) {
                when(it) {
                    is ResultWrapper.Default -> {
                        // empty
                    }
                    is ResultWrapper.Empty -> {
                        progressBar.visibility = View.GONE
                        Toast.makeText(this@DetailGithubUserActivity, it.message, Toast.LENGTH_SHORT).show()
                    }
                    is ResultWrapper.Failure -> {
                        progressBar.visibility = View.GONE
                        Toast.makeText(this@DetailGithubUserActivity, it.title, Toast.LENGTH_SHORT).show()
                    }
                    is ResultWrapper.Loading -> {
                        progressBar.visibility = View.VISIBLE
                    }
                    is ResultWrapper.Success -> {
                        progressBar.visibility = View.GONE

                        val data = it.data as DetailUserResponse

                        civImageGithubUser.loadImage(data.avatarURL, this@DetailGithubUserActivity, progressBar)

                        tvName.text = data.name
                        tvUsername.text = data.username

                        if (list.company == null || list.location == null) {
                            tvCompany.text = String.format("-")
                            tvLocation.text = String.format("-")
                        } else {
                            tvCompany.text = list.company
                            tvLocation.text = list.location
                        }

                        tvAmountFollowers.text = String.format("${data.followers} followers")
                        tvAmountFollowing.text = String.format("${data.following} following")
                        tvAmountRepository.text = String.format("Repositories ${data.publicRepo}")
                    }
                    else -> {
                        progressBar.visibility = View.GONE
                        Toast.makeText(this@DetailGithubUserActivity, "Server dalam perbaikan", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }


    private fun settingViewPager() {
        binding.apply {
            val detailUserPagerAdapter = DetailUserPagerAdapter(this@DetailGithubUserActivity)

            viewPager.adapter = detailUserPagerAdapter
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()

            supportActionBar?.elevation = 0f
        }
    }

    private fun sendDataToFragment() {
        FollowersFragment.USERNAME = list.username?:"Kosong"
        FollowingFragment.USERNAME = list.username?:"Kosong"
    }

}