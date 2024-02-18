package com.fajar.githubuserappdicoding.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fajar.githubuserappdicoding.domain.usecase.CheckIsThemeDarkUseCase
import com.fajar.githubuserappdicoding.presentation.util.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashVM @Inject constructor(
    checkIsThemeDarkUseCase: CheckIsThemeDarkUseCase
) : ViewModel() {

    val isDarkThemeFlow = checkIsThemeDarkUseCase()
        .distinctUntilChanged()

    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var sendEventJob: Job? = null
    fun sendEvent(event: UIEvent) {
        sendEventJob?.cancel()
        sendEventJob = viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}