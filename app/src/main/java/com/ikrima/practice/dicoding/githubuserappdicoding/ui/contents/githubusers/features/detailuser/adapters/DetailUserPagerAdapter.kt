package com.ikrima.practice.dicoding.githubuserappdicoding.ui.contents.githubusers.features.detailuser.adapters

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ikrima.practice.dicoding.githubuserappdicoding.ui.contents.githubusers.features.detailuser.fragments.FollowersFragment
import com.ikrima.practice.dicoding.githubuserappdicoding.ui.contents.githubusers.features.detailuser.fragments.FollowingFragment

class DetailUserPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = ITEM_COUNT

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowersFragment()
            1 -> fragment = FollowingFragment()
        }
        return fragment as Fragment
    }

    companion object {
        private const val ITEM_COUNT = 2
    }
}