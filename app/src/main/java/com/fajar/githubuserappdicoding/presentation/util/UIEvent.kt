package com.fajar.githubuserappdicoding.presentation.util

import com.fajar.githubuserappdicoding.domain.common.Event
import com.fajar.githubuserappdicoding.domain.common.StringRes

sealed class UIEvent : Event() {
    data class ToastMessageEvent(val message: StringRes) : UIEvent()

    data object OnThemeChangedEvent : UIEvent()
}