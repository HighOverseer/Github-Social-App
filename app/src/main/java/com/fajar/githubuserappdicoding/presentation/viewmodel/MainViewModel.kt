package com.fajar.githubuserappdicoding.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.fajar.githubuserappdicoding.R
import com.fajar.githubuserappdicoding.domain.common.DynamicString
import com.fajar.githubuserappdicoding.domain.common.Resource
import com.fajar.githubuserappdicoding.domain.common.SingleEvent
import com.fajar.githubuserappdicoding.domain.common.StaticString
import com.fajar.githubuserappdicoding.domain.data.Repository
import com.fajar.githubuserappdicoding.domain.usecase.ChangeThemePrefUseCase
import com.fajar.githubuserappdicoding.domain.usecase.CheckIsThemeDarkUseCase
import com.fajar.githubuserappdicoding.domain.usecase.SearchUseCase
import com.fajar.githubuserappdicoding.presentation.uiaction.MainUiAction
import com.fajar.githubuserappdicoding.presentation.uistate.FavoriteState
import com.fajar.githubuserappdicoding.presentation.uistate.MainUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

@HiltViewModel
class MainViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase,
    private val changeThemePrefUseCase: ChangeThemePrefUseCase,
    checkIsThemeDarkUseCase: CheckIsThemeDarkUseCase
) : ViewModel() {
    companion object {
        private const val DELAY_MILLIS = 750L
    }

    val themeState = checkIsThemeDarkUseCase()

    private val uiAction = MutableStateFlow<MainUiAction?>(null)

    private val _uiState = MutableStateFlow(MainUIState())
    val uiState: StateFlow<MainUIState> = _uiState
    fun sendAction(action: MainUiAction) {
        uiAction.update {
            action
        }
    }
    private suspend fun onAction(action: MainUiAction) {
        when (action) {
            is MainUiAction.SearchUser -> {
                searchUser(action.query)
            }

            is MainUiAction.SearchingUser -> {
                searchUser(
                    action.chunkedQuery, DELAY_MILLIS
                )
            }

            MainUiAction.ClearSearchList -> {
                showOrClearListBasedOnFavoriteStatus()
            }

            MainUiAction.ChangeMode -> {
                changeListUserSource()
            }

            MainUiAction.ChangeTheme -> {
                changeThemePref()
            }
        }
    }

    private suspend fun showOrClearListBasedOnFavoriteStatus() {
        val newIsInFavoriteList = !_uiState.value.favoriteState.newIsFavoriteList
        if (newIsInFavoriteList) {
            _uiState.update {
                it.copy(
                    query = "",
                    listUserPreview = emptyList(),
                    isLoading = true,
                    favoriteState = FavoriteState(
                        newIsFavoriteList = true,
                        oldIsFavoriteList = true
                    )
                )
            }

            _uiState.emitAll(
                onSearchResult(true, "")
            )
        } else {
            _uiState.update {
                MainUIState(
                    query = "",
                    isLoading = false,
                    listUserPreview = emptyList(),
                    toastMessage = null,
                    favoriteState = FavoriteState(
                        newIsFavoriteList = false,
                        oldIsFavoriteList = false
                    )
                )
            }
        }
    }

    private suspend fun changeThemePref() {
        withContext(NonCancellable){
            changeThemePref()
        }
    }

    private suspend fun changeListUserSource() {
        val newIsInFavoriteList = !_uiState.value.favoriteState.newIsFavoriteList

        if (newIsInFavoriteList) {
            _uiState.update {
                MainUIState(
                    query = "",
                    listUserPreview = emptyList(),
                    isLoading = true,
                    toastMessage = SingleEvent(
                        StaticString(R.string.list_favorite_info)
                    ),
                    favoriteState = FavoriteState(
                        newIsFavoriteList = true,
                        oldIsFavoriteList = false
                    )
                )
            }
            _uiState.emitAll(
                onSearchResult(true, "")
            )
        } else {
            _uiState.update {
                MainUIState(
                    toastMessage = SingleEvent(
                        StaticString(R.string.list_non_favorite_info)
                    ),
                    favoriteState = FavoriteState(
                        newIsFavoriteList = false,
                        oldIsFavoriteList = true
                    )
                )
            }
        }
    }

    private suspend fun searchUser(query: String, delayMillis: Long = 0L) {
        delay(delayMillis)

        _uiState.update {
            it.copy(
                query = query,
                isLoading = true
            )
        }

        val currIsInFavoriteList = _uiState.value.favoriteState.newIsFavoriteList

        _uiState.emitAll(
            onSearchResult(currIsInFavoriteList, query)
        )

    }

    private fun onSearchResult(isInFavoriteList: Boolean, query: String = ""): Flow<MainUIState> {
        return searchUseCase(isInFavoriteList, query).map { res ->
            when (res) {
                is Resource.Success -> {
                    uiState.value.copy(
                        listUserPreview = res.data,
                        isLoading = false,
                        favoriteState = FavoriteState(
                            newIsFavoriteList = isInFavoriteList,
                            oldIsFavoriteList = isInFavoriteList
                        )
                    )
                }

                is Resource.Failure -> {
                    uiState.value.copy(
                        toastMessage = SingleEvent(res.message),
                        isLoading = false
                    )
                }

                is Resource.Error -> {
                    uiState.value.copy(
                        toastMessage = SingleEvent(
                            DynamicString(res.e.message.toString())
                        ),
                        isLoading = false
                    )
                }
            }
        }
    }

    private val completionHandler = { cause: Throwable? ->
        if (cause != null) {
            _uiState.value = _uiState.value.copy(isLoading = false)
        }
    }

    init {
        viewModelScope.launch(Dispatchers.Default) {
            uiAction.collectLatest { action ->
                coroutineContext.job.invokeOnCompletion(completionHandler)

                action?.let { onAction(it) }
            }
        }
    }

}