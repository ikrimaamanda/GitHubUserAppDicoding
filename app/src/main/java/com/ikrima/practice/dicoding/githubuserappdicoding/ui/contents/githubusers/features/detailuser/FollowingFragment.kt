package com.ikrima.practice.dicoding.githubuserappdicoding.ui.contents.githubusers.features.detailuser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ikrima.practice.dicoding.githubuserappdicoding.data.responses.DetailUserResponse
import com.ikrima.practice.dicoding.githubuserappdicoding.data.retrofit.ApiConfig
import com.ikrima.practice.dicoding.githubuserappdicoding.data.retrofit.GitHubUserApiServices
import com.ikrima.practice.dicoding.githubuserappdicoding.databinding.FragmentFollowingBinding
import com.ikrima.practice.dicoding.githubuserappdicoding.ui.contents.githubusers.adapters.RvAllUserAdapter
import com.ikrima.practice.dicoding.githubuserappdicoding.ui.contents.githubusers.viewmodel.GitHubUserViewModel
import com.ikrima.practice.dicoding.githubuserappdicoding.utils.helper.ResultWrapper


class FollowingFragment : Fragment() {

    private lateinit var binding : FragmentFollowingBinding
    private lateinit var viewModel : GitHubUserViewModel
    private lateinit var service : GitHubUserApiServices


    companion object {
        var USERNAME = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        settingViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFollowingBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeFollowing()
    }

    private fun settingViewModel() {
        viewModel = ViewModelProvider(this)[GitHubUserViewModel::class.java]
        service = ApiConfig.getApiClientGitHubUser(requireContext())!!.create(GitHubUserApiServices::class.java)

        viewModel.apply {
            setGitHubApiService(service)
            getFollowing(USERNAME, "following")
        }

    }

    private fun subscribeFollowing() {
        binding.apply {
            viewModel.listFollowing.observe(viewLifecycleOwner) {
                when(it) {
                    is ResultWrapper.Default -> {

                    }
                    is ResultWrapper.Empty -> {
                        progressBar.visibility = View.GONE

                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    is ResultWrapper.Failure -> {
                        progressBar.visibility = View.GONE

                        Toast.makeText(requireContext(), it.title, Toast.LENGTH_SHORT).show()
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

                        setRvFollowing(data)

                    }
                    else -> {
                        progressBar.visibility = View.GONE
                        Toast.makeText(requireContext(), "Server dalam perbaikan", Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }
    }

    private fun setRvFollowing(followers: List<DetailUserResponse>){
        val rvGithubUserAdapter = RvAllUserAdapter()
        binding.rvGithubUsers.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = rvGithubUserAdapter
        }

        rvGithubUserAdapter.addListUser(followers)
    }


}