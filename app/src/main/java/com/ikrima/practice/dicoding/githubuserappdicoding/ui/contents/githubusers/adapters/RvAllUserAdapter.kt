package com.ikrima.practice.dicoding.githubuserappdicoding.ui.contents.githubusers.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ikrima.practice.dicoding.githubuserappdicoding.data.responses.DetailUserResponse
import com.ikrima.practice.dicoding.githubuserappdicoding.databinding.ItemGithubUserBinding
import com.ikrima.practice.dicoding.githubuserappdicoding.ui.contents.githubusers.features.detailuser.DetailGithubUserActivity
import com.ikrima.practice.dicoding.githubuserappdicoding.utils.uiutils.UIUtils.loadImage


class RvAllUserAdapter : RecyclerView.Adapter<RvAllUserAdapter.RvSearchUserViewHolder>() {

    private val listSearchUser = ArrayList<DetailUserResponse>()

    fun addListUser(list : List<DetailUserResponse>) {
        this.listSearchUser.clear()
        this.listSearchUser.addAll(list)
    }

    inner class RvSearchUserViewHolder(private val binding : ItemGithubUserBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(position : Int) {
            binding.apply {
                val listNow = listSearchUser[position]

                tvItemGithubUserUsername.text = listNow.username

                civImageGithubUser.loadImage(listNow.avatarURL, itemView.context, progressBar)

                itemGithubUserLayout.setOnClickListener {
                    val i = Intent(itemView.context, DetailGithubUserActivity::class.java)
                    i.apply {
                        putExtra("githubUserData", listNow)
                    }
                    itemView.context.startActivity(i)
                }

            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvSearchUserViewHolder {
        return RvSearchUserViewHolder(
            ItemGithubUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RvSearchUserViewHolder, position: Int) = holder.bind(position)

    override fun getItemCount(): Int = listSearchUser.size
}