package com.fajar.githubuserappdicoding.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.fajar.githubuserappdicoding.domain.Repository
import com.fajar.githubuserappdicoding.domain.SingleEvent
import kotlinx.coroutines.flow.distinctUntilChanged

class SplashVM(
    repository: Repository
) : ViewModel() {

    val themeState = repository.isUsingDarkTheme().distinctUntilChanged().asLiveData()

    private val _themeAlreadyChangedEvent = MutableLiveData<SingleEvent<Unit>>()
    val themeAlreadyChangedEvent: LiveData<SingleEvent<Unit>> = _themeAlreadyChangedEvent

    fun setThemeEvent() {
        _themeAlreadyChangedEvent.value = SingleEvent(Unit)
    }
}