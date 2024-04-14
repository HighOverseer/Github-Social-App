package com.fajar.githubuserappdicoding.list_user_and_user_favorite.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fajar.githubuserappdicoding.core.databinding.ItemLayoutBinding
import com.fajar.githubuserappdicoding.core.domain.model.UserPreview
import com.fajar.githubuserappdicoding.core.presentation.loadImage

class ListUserAdapter(
    private inline val onItemGetClicked: (username: String) -> Unit
) : ListAdapter<UserPreview, ListUserAdapter.UserViewHolder>(diffUtil) {


    class UserViewHolder(
        val binding: ItemLayoutBinding,
        clickedAtPosition: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                clickedAtPosition.invoke(adapterPosition)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding) { adapterPosition ->
            onItemGetClicked.invoke(getItem(adapterPosition).username)
        }
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = getItem(position)
        holder.binding.apply {
            civItemAvatar.loadImage(
                holder.itemView.context,
                currentUser.avatarUrl
            )
            tvUsername.text = currentUser.username
            tvGithubUrl.text = currentUser.githubUrl
        }
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<UserPreview>() {
            override fun areItemsTheSame(oldItem: UserPreview, newItem: UserPreview): Boolean {
                return oldItem.username == newItem.username
            }

            override fun areContentsTheSame(oldItem: UserPreview, newItem: UserPreview): Boolean {
                return oldItem == newItem
            }
        }
    }
}