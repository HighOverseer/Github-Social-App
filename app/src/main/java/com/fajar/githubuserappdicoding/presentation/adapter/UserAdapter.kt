package com.fajar.githubuserappdicoding.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fajar.githubuserappdicoding.databinding.ItemLayoutBinding
import com.fajar.githubuserappdicoding.domain.model.UserPreview
import com.fajar.githubuserappdicoding.presentation.util.loadImage

class UserAdapter(
    private val users: List<UserPreview>,
    private inline val onItemGetClicked: (username: String) -> Unit
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {


    inner class UserViewHolder(
        val binding: ItemLayoutBinding,
        clickedAtPosition: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                clickedAtPosition(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding) { adapterPosition ->
            onItemGetClicked(users[adapterPosition].username)
        }
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = users[position]
        holder.binding.apply {
            civItemAvatar.loadImage(
                holder.itemView.context,
                currentUser.avatarUrl
            )
            tvUsername.text = currentUser.username
            tvGithubUrl.text = currentUser.githubUrl
        }
    }

    override fun getItemCount() = users.size
}