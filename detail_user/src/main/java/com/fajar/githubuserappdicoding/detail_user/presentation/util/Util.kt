package com.fajar.githubuserappdicoding.detail_user.presentation.util

import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.ViewHolder.getString(stringId: Int, args: String): String {
    return itemView.context.getString(stringId, args)
}

