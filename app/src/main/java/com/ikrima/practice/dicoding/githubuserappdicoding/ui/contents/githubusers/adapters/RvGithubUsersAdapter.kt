package com.ikrima.practice.dicoding.githubuserappdicoding.ui.contents.githubusers.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.ikrima.practice.dicoding.githubuserappdicoding.databinding.ItemGithubUserBinding
import com.ikrima.practice.dicoding.githubuserappdicoding.ui.contents.githubusers.activities.DetailGithubUserActivity
import com.ikrima.practice.dicoding.githubuserappdicoding.ui.contents.githubusers.data.ProfileModel

class RvGithubUsersAdapter : RecyclerView.Adapter<RvGithubUsersAdapter.RvGithubUsersViewHolder>() {


    private var listGithubUsers = ArrayList<ProfileModel>()


    fun addGithubUsers(list : List<ProfileModel>) {
        this.listGithubUsers.clear()
        this.listGithubUsers.addAll(list)
    }


    inner class RvGithubUsersViewHolder(private val binding : ItemGithubUserBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(position: Int) {
            binding.apply {
                val listNow = listGithubUsers[position]

                tvItemGithubUserName.text = listNow.name
                tvItemGithubUserUsername.text = listNow.username

                // setting image
                val imgResource = itemView.context.resources.getIdentifier(listNow.avatar, null, itemView.context.packageName)
                val res = AppCompatResources.getDrawable(itemView.context, imgResource)
                civImageGithubUser.setImageDrawable(res)

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvGithubUsersViewHolder {
        return RvGithubUsersViewHolder(
            ItemGithubUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RvGithubUsersViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return listGithubUsers.size
    }
}