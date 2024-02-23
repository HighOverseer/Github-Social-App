package com.fajar.githubuserappdicoding.core.presentation

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.fajar.githubuserappdicoding.core.R
import com.fajar.githubuserappdicoding.core.domain.common.StringRes
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun CircleImageView.loadImage(context: Context, avatar: String) {
    Glide.with(context)
        .load(avatar)
        .placeholder(R.color.teal_200)
        .into(this)
}

fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun showToast(context: Context, message: StringRes) {
    Toast.makeText(context, message.getValue(context), Toast.LENGTH_SHORT).show()
}

fun makeToast(context: Context, message: StringRes): Toast {
    return Toast.makeText(context, message.getValue(context), Toast.LENGTH_SHORT)
}

/*fun showToast(context: Context, singleEventMessage: SingleEvent<StringRes>) {
    singleEventMessage.getContentIfNotHandled()?.let {
        Toast.makeText(
            context,
            it.getValue(context),
            Toast.LENGTH_SHORT
        ).show()
    }
}*/

fun <T> LifecycleOwner.collectLatestOnLifeCycleStarted(
    stateFlow: StateFlow<T>,
    onCollectLatest: suspend (T) -> Unit
) {
    this.lifecycleScope.launch {
        this@collectLatestOnLifeCycleStarted.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            stateFlow.collectLatest(action = onCollectLatest)
        }
    }
}

fun <T> LifecycleOwner.collectLatestOnLifeCycleStarted(
    flow: Flow<T>,
    onCollectLatest: suspend (T) -> Unit
) {
    this.lifecycleScope.launch {
        this@collectLatestOnLifeCycleStarted.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collectLatest(action = onCollectLatest)
        }
    }
}

fun <T> LifecycleOwner.collectChannelFlowOnLifecycleStarted(
    flow: Flow<T>,
    onCollect: suspend (T) -> Unit
) {
    this.lifecycleScope.launch {
        this@collectChannelFlowOnLifecycleStarted.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            withContext(Dispatchers.Main.immediate) {
                flow.collect(onCollect)
            }
        }
    }
}


fun Context.getDrawableRes(drawableResId: Int): Drawable? {
    return AppCompatResources.getDrawable(this, drawableResId)
}


fun changeTheme(isDarkTheme: Boolean) {
    if (isDarkTheme) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    } else {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}
