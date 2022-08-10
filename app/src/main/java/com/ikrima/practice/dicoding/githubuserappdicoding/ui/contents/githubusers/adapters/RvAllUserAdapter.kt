package com.ikrima.practice.dicoding.githubuserappdicoding.ui.contents.githubusers.adapters

import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.ikrima.practice.dicoding.githubuserappdicoding.R
import com.ikrima.practice.dicoding.githubuserappdicoding.data.responses.DetailUserResponse
import com.ikrima.practice.dicoding.githubuserappdicoding.databinding.ItemGithubUserBinding
import com.ikrima.practice.dicoding.githubuserappdicoding.ui.contents.githubusers.features.detailuser.DetailGithubUserActivity


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

                Glide.with(itemView.context)
                    .load(listNow.avatarURL)
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