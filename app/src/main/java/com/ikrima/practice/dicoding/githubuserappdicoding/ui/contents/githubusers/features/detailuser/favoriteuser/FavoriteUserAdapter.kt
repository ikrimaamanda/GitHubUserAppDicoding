package com.ikrima.practice.dicoding.githubuserappdicoding.ui.contents.githubusers.features.detailuser.favoriteuser

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ikrima.practice.dicoding.githubuserappdicoding.data.responses.DetailUserResponse
import com.ikrima.practice.dicoding.githubuserappdicoding.databinding.ItemGithubUserBinding
import com.ikrima.practice.dicoding.githubuserappdicoding.ui.contents.githubusers.features.detailuser.DetailGithubUserActivity
import com.ikrima.practice.dicoding.githubuserappdicoding.utils.helper.FavoriteUserDiffCallback
import com.ikrima.practice.dicoding.githubuserappdicoding.utils.uiutils.UIUtils.loadImage

class FavoriteUserAdapter : RecyclerView.Adapter<FavoriteUserAdapter.FavoriteUserViewHolder>() {

    private val listFavUser = ArrayList<DetailUserResponse>()

    fun setListFavUser(listFavUser : List<DetailUserResponse>) {
        val diffCallback = FavoriteUserDiffCallback(this.listFavUser, listFavUser)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFavUser.clear()
        this.listFavUser.addAll(listFavUser)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class FavoriteUserViewHolder(private val binding : ItemGithubUserBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(favUser : DetailUserResponse) {
            with(binding) {
                tvItemGithubUserUsername.text = favUser.username
                civImageGithubUser.loadImage(favUser.avatarURL, itemView.context, progressBar)

                itemGithubUserLayout.setOnClickListener {
                    val i = Intent(itemView.context, DetailGithubUserActivity::class.java)
                    i.putExtra("githubUserData", favUser)
                    itemView.context.startActivity(i)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteUserViewHolder {
        return FavoriteUserViewHolder(
            ItemGithubUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FavoriteUserViewHolder, position: Int) {
        return holder.bind(listFavUser[position])
    }

    override fun getItemCount(): Int {
        return listFavUser.size
    }

}