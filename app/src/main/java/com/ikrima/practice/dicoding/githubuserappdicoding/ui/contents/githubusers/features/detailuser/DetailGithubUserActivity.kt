package com.ikrima.practice.dicoding.githubuserappdicoding.ui.contents.githubusers.features.detailuser

import android.graphics.drawable.Drawable
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.ikrima.practice.dicoding.githubuserappdicoding.R
import com.ikrima.practice.dicoding.githubuserappdicoding.base.BaseActivityViewModel
import com.ikrima.practice.dicoding.githubuserappdicoding.data.responses.DetailUserResponse
import com.ikrima.practice.dicoding.githubuserappdicoding.databinding.ActivityDetailGithubUserBinding
import com.ikrima.practice.dicoding.githubuserappdicoding.ui.contents.githubusers.viewmodel.GitHubUserViewModel
import com.ikrima.practice.dicoding.githubuserappdicoding.utils.helper.ResultWrapper

class DetailGithubUserActivity : BaseActivityViewModel<GitHubUserViewModel>() {

    private lateinit var binding : ActivityDetailGithubUserBinding

    private lateinit var list : DetailUserResponse
    private var username = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        setViewModel = ViewModelProvider(this)[GitHubUserViewModel::class.java]
        super.onCreate(savedInstanceState)

        binding = ActivityDetailGithubUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setParcelableData()

        settingViewModel()

//        setProfile()

        subscribeDetailUser()

    }

    private fun setParcelableData() {
        list = intent.getParcelableExtra<DetailUserResponse>("githubUserData") as DetailUserResponse
        username = list.username?:""
    }

    private fun settingViewModel() {
        viewModel.apply {
            setGitHubApiServic(service)
            setSharedPref(sharedPref)
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
                        // setting progressbar
                        progressBar.visibility = View.VISIBLE
                    }
                    is ResultWrapper.Success -> {
                        progressBar.visibility = View.GONE

                        val data = it.data as DetailUserResponse

                        Glide.with(this@DetailGithubUserActivity)
                            .load(data.avatarURL)
                            .listener(object : RequestListener<Drawable> {
                                override fun onLoadFailed(
                                    e: GlideException?,
                                    model: Any?,
                                    target: com.bumptech.glide.request.target.Target<Drawable>?,
                                    isFirstResource: Boolean
                                ): Boolean {
                                    progressBar.visibility = View.GONE
                                    return false                        }

                                override fun onResourceReady(
                                    resource: Drawable?,
                                    model: Any?,
                                    target: com.bumptech.glide.request.target.Target<Drawable>?,
                                    dataSource: DataSource?,
                                    isFirstResource: Boolean
                                ): Boolean {
                                    progressBar.visibility = View.GONE
                                    return false                        }

                            })
                            .placeholder(R.drawable.icon_github)
                            .error(R.drawable.icon_github)
                            .dontAnimate()
                            .into(civImageGithubUser)

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

}