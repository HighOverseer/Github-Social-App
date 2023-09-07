package com.fajar.githubuserappdicoding.domain

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.fajar.githubuserappdicoding.R
import de.hdodenhof.circleimageview.CircleImageView

fun CircleImageView.loadImage(context: Context, avatar: String) {
    Glide.with(context)
        .load(avatar)
        .placeholder(R.color.teal_200)
        .into(this)
}

fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun showToast(context: Context, singleEventMessage: SingleEvent<StringRes>) {
    singleEventMessage.getContentIfNotHandled()?.let {
        Toast.makeText(
            context,
            it.getValue(context),
            Toast.LENGTH_SHORT
        ).show()
    }
}

fun ViewHolder.getString(stringId: Int, args: String): String {
    return itemView.context.getString(stringId, args)
}

fun Float.toDp(res: Resources): Float {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, res.displayMetrics)
}

fun Context.getDrawableRes(drawableResId: Int): Drawable? {
    return AppCompatResources.getDrawable(this, drawableResId)
}

fun checkIsUsingDarkTheme(res: Resources): Boolean {
    return when (
        res.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
    ) {
        Configuration.UI_MODE_NIGHT_YES -> true
        else -> false
    }
}


fun changeTheme(isDarkTheme: Boolean) {
    if (isDarkTheme) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    } else {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}
