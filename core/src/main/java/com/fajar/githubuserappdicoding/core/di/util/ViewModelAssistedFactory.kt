package com.fajar.githubuserappdicoding.core.di.util

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

interface ViewModelAssistedFactory<T : ViewModel> {
    fun create(savedStateHandle: SavedStateHandle): T

}