package com.ikrima.practice.dicoding.githubuserappdicoding.ui.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.ikrima.practice.dicoding.githubuserappdicoding.databinding.ActivityMainBinding
import com.ikrima.practice.dicoding.githubuserappdicoding.ui.contents.githubusers.activities.GitHubUsersActivity

class MainActivity : AppCompatActivity() {


    companion object {
        private const val splashDuration = 2000
    }

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showLogo()
    }

    private fun showLogo() {
        Handler(mainLooper).postDelayed(
            {
                moveToContent()
            }, splashDuration.toLong()
        )
    }

    private fun moveToContent() {
        val i = Intent(this, GitHubUsersActivity::class.java)
        startActivity(i)
        finish()
    }

}