package com.ikrima.practice.dicoding.githubuserappdicoding.ui.contents.githubusers.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.content.res.AppCompatResources
import com.ikrima.practice.dicoding.githubuserappdicoding.databinding.ActivityDetailGithubUserBinding
import com.ikrima.practice.dicoding.githubuserappdicoding.ui.contents.githubusers.data.ProfileModel

class DetailGithubUserActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailGithubUserBinding

    private lateinit var list : ProfileModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailGithubUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setParcelableData()

        setProfile()

    }

    private fun setParcelableData() {
        list = intent.getParcelableExtra<ProfileModel>("githubUserData") as ProfileModel
    }

    private fun setProfile() {
        binding.apply {
            val imgResource =resources.getIdentifier(list.avatar, null, packageName)
            val res = AppCompatResources.getDrawable(this@DetailGithubUserActivity, imgResource)
            civImageGithubUser.setImageDrawable(res)

            tvName.text = list.name
            tvUsername.text = list.username
            tvCompany.text = list.company
            tvLocation.text = list.location
            tvAmountFollowers.text = String.format("${list.follower} followers")
            tvAmountFollowing.text = String.format("${list.following} following")
            tvAmountRepository.text = String.format("Repositories ${list.repository}")
        }
    }

}