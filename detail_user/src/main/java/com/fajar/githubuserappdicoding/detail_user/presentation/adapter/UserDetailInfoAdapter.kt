package com.fajar.githubuserappdicoding.detail_user.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fajar.githubuserappdicoding.core.databinding.ItemLayoutBinding
import com.fajar.githubuserappdicoding.core.domain.model.GithubRepos
import com.fajar.githubuserappdicoding.core.domain.model.UserDetailInfo
import com.fajar.githubuserappdicoding.core.domain.model.UserPreview
import com.fajar.githubuserappdicoding.core.presentation.loadImage
import com.fajar.githubuserappdicoding.detail_user.R
import com.fajar.githubuserappdicoding.detail_user.databinding.ReposItemLayoutBinding
import com.fajar.githubuserappdicoding.detail_user.presentation.util.getString

class UserDetailInfoAdapter(
    private val items: List<UserDetailInfo>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class FollowersFollowingVH(
        val binding: ItemLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root)

    class GithubReposVH(
        val binding: ReposItemLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh = when (items[0]) {
            is UserPreview -> {
                val binding = ItemLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                FollowersFollowingVH(binding)
            }

            else -> {
                val binding = ReposItemLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                GithubReposVH(binding)
            }
        }
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val currItem = items[position]) {
            is UserPreview -> {
                val vh = holder as FollowersFollowingVH
                vh.binding.apply {
                    civItemAvatar.loadImage(
                        vh.itemView.context,
                        currItem.avatarUrl
                    )
                    tvUsername.text = currItem.username
                    tvGithubUrl.text = currItem.githubUrl
                }
            }

            is GithubRepos -> {
                val vh = holder as GithubReposVH
                vh.binding.apply {
                    tvReposName.text = currItem.name
                    tvForkCount.text = vh.getString(
                        R.string.fork_count_info,
                        currItem.forksCount
                    )
                    tvWatcherCount.text = vh.getString(
                        R.string.watcher_count_info,
                        currItem.watchersCount
                    )
                }
            }
        }
    }

    override fun getItemCount() = items.size
}