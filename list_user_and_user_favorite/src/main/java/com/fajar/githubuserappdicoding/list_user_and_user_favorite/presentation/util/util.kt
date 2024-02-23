package com.fajar.githubuserappdicoding.list_user_and_user_favorite.presentation.util

import android.content.res.Configuration
import android.content.res.Resources
import android.util.TypedValue

fun checkIsUsingDarkTheme(res: Resources): Boolean {
    return when (
        res.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
    ) {
        Configuration.UI_MODE_NIGHT_YES -> true
        else -> false
    }
}

fun Float.toDp(res: Resources): Float {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, res.displayMetrics)
}