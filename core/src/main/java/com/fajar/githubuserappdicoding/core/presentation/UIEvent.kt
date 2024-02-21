package com.fajar.githubuserappdicoding.core.presentation

import com.fajar.githubuserappdicoding.core.domain.common.Event
import com.fajar.githubuserappdicoding.core.domain.common.StringRes

sealed class UIEvent : Event() {
    data class ToastMessageEvent(val message: StringRes) : UIEvent()

    data object OnThemeChangedEvent : UIEvent()
}