package com.ikrima.practice.dicoding.githubuserappdicoding.ui.contents.githubusers.features.detailuser.fragments

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
import com.ikrima.practice.dicoding.githubuserappdicoding.databinding.FragmentFollowersBinding
import com.ikrima.practice.dicoding.githubuserappdicoding.ui.contents.githubusers.adapters.RvAllUserAdapter
import com.ikrima.practice.dicoding.githubuserappdicoding.ui.contents.githubusers.viewmodel.GitHubUserViewModel
import com.ikrima.practice.dicoding.githubuserappdicoding.utils.helper.ResultWrapper


class FollowersFragment : Fragment() {


    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: GitHubUserViewModel
    private lateinit var service: GitHubUserApiServices


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        settingViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFollowersBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeFollowers()
    }


    private fun settingViewModel() {
        viewModel = ViewModelProvider(this)[GitHubUserViewModel::class.java]
        service = ApiConfig.getApiClientGitHubUser()!!.create(GitHubUserApiServices::class.java)

        viewModel.apply {
            setGitHubApiService(service)
            getFollowers(USERNAME, "followers")
        }

    }

    private fun subscribeFollowers() {
        binding.apply {
            viewModel.listFollowers.observe(viewLifecycleOwner) {
                when (it) {
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

                        setRvFollowers(data)

                    }
                    else -> {
                        progressBar.visibility = View.GONE
                        Toast.makeText(
                            requireContext(), "Server dalam perbaikan", Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            }
        }
    }

    private fun setRvFollowers(followers: List<DetailUserResponse>) {
        val rvGithubUserAdapter = RvAllUserAdapter()
        binding.rvGithubUsers.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = rvGithubUserAdapter
        }

        rvGithubUserAdapter.addListUser(followers)
    }


    companion object {
        var USERNAME = ""
    }

}