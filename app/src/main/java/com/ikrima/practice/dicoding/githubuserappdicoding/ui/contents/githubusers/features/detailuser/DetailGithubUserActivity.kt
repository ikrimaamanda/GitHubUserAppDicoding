package com.ikrima.practice.dicoding.githubuserappdicoding.ui.contents.githubusers.features.detailuser

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.google.android.material.tabs.TabLayoutMediator
import com.ikrima.practice.dicoding.githubuserappdicoding.R
import com.ikrima.practice.dicoding.githubuserappdicoding.base.BaseActivityViewModel
import com.ikrima.practice.dicoding.githubuserappdicoding.data.responses.DetailUserResponse
import com.ikrima.practice.dicoding.githubuserappdicoding.databinding.ActivityDetailGithubUserBinding
import com.ikrima.practice.dicoding.githubuserappdicoding.ui.contents.githubusers.features.detailuser.favoriteuser.FavUserViewModel
import com.ikrima.practice.dicoding.githubuserappdicoding.ui.contents.githubusers.viewmodel.GitHubUserViewModel
import com.ikrima.practice.dicoding.githubuserappdicoding.ui.contents.githubusers.viewmodel.ViewModelFactory
import com.ikrima.practice.dicoding.githubuserappdicoding.utils.helper.ResultWrapper
import com.ikrima.practice.dicoding.githubuserappdicoding.utils.uiutils.UIUtils.loadImage

class DetailGithubUserActivity : BaseActivityViewModel<GitHubUserViewModel>() {

    private lateinit var binding : ActivityDetailGithubUserBinding

    private lateinit var list : DetailUserResponse
    private var username = ""

    // to favorite user
    private lateinit var favUserViewModel : FavUserViewModel
    private var isFavUser = false
    private var dataFavUser = ArrayList<DetailUserResponse>()

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

        settingActionBar("Detail User")

        setParcelableData()

        settingViewModel()

        subscribeDetailUser()

        settingViewPager()

        sendDataToFragment()

        onClickListener()

    }

    private fun setParcelableData() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        list = intent.getParcelableExtra<DetailUserResponse>("githubUserData") as DetailUserResponse
        username = list.username?:""
    }

    private fun settingViewModel() {
        viewModel.apply {
            setGitHubApiService(service)
            getDetailUser(username)
        }

        favUserViewModel = obtainViewModel(this)

        favUserViewModel.getFavUserByUsername(username).observe(this) { user ->
            if (user != null) {
                if (user.isEmpty()) {
                    isFavUser = false
                    binding.fabFavUser.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_baseline_favorite_border_24))
                } else {
                    list = user[0]
                    isFavUser = true
                    binding.fabFavUser.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_baseline_favorite_24))
                }
            } else {
                Toast.makeText(this, "User not found in room database", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun obtainViewModel(detailGithubUserActivity: DetailGithubUserActivity): FavUserViewModel {
        val factory = ViewModelFactory.getInstance(detailGithubUserActivity.application)
        return ViewModelProvider(detailGithubUserActivity, factory)[FavUserViewModel::class.java]
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

                        val mutableListFavUser = mutableListOf<DetailUserResponse>()
                        mutableListFavUser.add(data)

                        dataFavUser = mutableListFavUser as ArrayList<DetailUserResponse>

                        civImageGithubUser.loadImage(data.avatarURL, this@DetailGithubUserActivity, progressBar)

                        tvName.text = data.name
                        tvUsername.text = data.username

                        if (data.company == null || data.location == null) {
                            tvCompany.text = String.format("-")
                            tvLocation.text = String.format("-")
                        } else {
                            tvCompany.text = data.company
                            tvLocation.text = data.location
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

    private fun onClickListener() {
        binding.apply {

            fabFavUser.setOnClickListener {
                if (isFavUser) {
                    favUserViewModel.unFavUser(list)
                    Toast.makeText(this@DetailGithubUserActivity, "UnFav it!", Toast.LENGTH_SHORT).show()
                    isFavUser = false
                    fabFavUser.setImageDrawable(ContextCompat.getDrawable(this@DetailGithubUserActivity, R.drawable.ic_baseline_favorite_border_24))
                } else {
                    favUserViewModel.insertFavUser(dataFavUser[0])
                    Toast.makeText(this@DetailGithubUserActivity, "Fav it!", Toast.LENGTH_SHORT).show()
                    isFavUser = true
                    fabFavUser.setImageDrawable(ContextCompat.getDrawable(this@DetailGithubUserActivity, R.drawable.ic_baseline_favorite_24))

                }
            }
        }
    }

}