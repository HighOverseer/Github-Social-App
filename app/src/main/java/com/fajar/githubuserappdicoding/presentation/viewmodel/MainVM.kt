package com.fajar.githubuserappdicoding.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
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
import com.fajar.githubuserappdicoding.domain.usecase.SearchUserInFavoriteUseCase
import com.fajar.githubuserappdicoding.domain.usecase.SearchUserUseCase
import com.fajar.githubuserappdicoding.presentation.uiaction.MainUiAction
import com.fajar.githubuserappdicoding.presentation.uistate.FavoriteState
import com.fajar.githubuserappdicoding.presentation.uistate.MainUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainVM @Inject constructor(
    private val searchUserUseCase: SearchUserUseCase,
    private val searchUserInFavoriteUseCase: SearchUserInFavoriteUseCase,
    private val changeThemePrefUseCase: ChangeThemePrefUseCase,
    checkIsThemeDarkUseCase: CheckIsThemeDarkUseCase
) : ViewModel() {

    companion object {
        private const val DELAY_MILLIS = 750L
    }

    private var currListFavoriteLiveData = searchUserInFavoriteUseCase().asLiveData()

    val themeState = checkIsThemeDarkUseCase()

    private val _uiState = MediatorLiveData<MainUIState>()
    val uiState: LiveData<MainUIState> = _uiState


    private var job: Job? = null

    private val completionHandler = { cause:Throwable? ->
        if (cause != null) {
            _uiState.value = _uiState.value?.copy(isLoading = false)
        }
    }

    fun sendAction(action: MainUiAction) {
        when (action) {
            is MainUiAction.SearchUser -> {
                job?.cancel()
                job = searchUser(action.query)
            }

            is MainUiAction.SearchingUser -> {
                job?.cancel()
                job  = searchUser(
                    action.chunkedQuery, DELAY_MILLIS
                )
            }
            MainUiAction.ClearSearchList -> {
                job?.cancel()
                val currIsInFavoriteList =
                    _uiState.value?.favoriteState?.newIsFavoriteList ?: return
                showOrClearListBasedOnFavoriteStatus(currIsInFavoriteList)
            }

            MainUiAction.ChangeMode -> {
                job?.cancel()
                _uiState.value?.let {
                    val newIsFavoriteList = !it.favoriteState.newIsFavoriteList
                    changeListUserSource(newIsFavoriteList)
                }
            }

            MainUiAction.ChangeTheme -> {
                changeThemePref()
            }
        }
    }

    private fun showOrClearListBasedOnFavoriteStatus(isInFavoriteList: Boolean) {
        if (isInFavoriteList) {
            _uiState.value = _uiState.value?.copy(
                query = "",
                listUserPreview = emptyList(),
                isLoading = true,
                favoriteState = FavoriteState(
                    newIsFavoriteList = true,
                    oldIsFavoriteList = true
                )
            )


            _uiState.removeSource(currListFavoriteLiveData)
            currListFavoriteLiveData = searchUserInFavoriteUseCase().asLiveData()
            _uiState.addSource(currListFavoriteLiveData) { newListUserFavorite ->
                val currUiState = _uiState.value
                _uiState.value = currUiState?.copy(
                    listUserPreview = newListUserFavorite,
                    isLoading = false
                )
            }
        } else {
            _uiState.value = _uiState.value?.copy(
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

    private fun changeThemePref() {
        viewModelScope.launch {
            changeThemePrefUseCase()
        }
    }

    private fun changeListUserSource(isInFavoriteList: Boolean) {
        if (isInFavoriteList) {
            _uiState.value = _uiState.value?.copy(
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
            _uiState.removeSource(currListFavoriteLiveData)
            currListFavoriteLiveData = searchUserInFavoriteUseCase().asLiveData()
            _uiState.addSource(currListFavoriteLiveData) { newListUserFavorite ->
                val currUiState = _uiState.value
                _uiState.value = currUiState?.copy(
                    listUserPreview = newListUserFavorite,
                    isLoading = false,
                    favoriteState = FavoriteState(
                        newIsFavoriteList = true,
                        oldIsFavoriteList = true
                    )
                )
            }
        } else {
            _uiState.removeSource(currListFavoriteLiveData)
            _uiState.value = MainUIState(
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

    private fun searchUser(query: String, delayMillis: Long = 0L) = viewModelScope.launch {
        coroutineContext.job.invokeOnCompletion(completionHandler)

        delay(delayMillis)

        val currIsInFavoriteList = _uiState.value?.favoriteState?.newIsFavoriteList ?: return@launch

        _uiState.value = uiState.value?.copy(
            query = query,
            isLoading = true
        )

        if (currIsInFavoriteList) {
            _uiState.removeSource(currListFavoriteLiveData)
            currListFavoriteLiveData = searchUserInFavoriteUseCase(query).asLiveData()
            _uiState.addSource(currListFavoriteLiveData) { newListUserFavorite ->
                val currUiState = _uiState.value
                _uiState.value = currUiState?.copy(
                    listUserPreview = newListUserFavorite,
                    isLoading = false,
                    favoriteState = FavoriteState(
                        newIsFavoriteList = true,
                        oldIsFavoriteList = true
                    )
                )
            }
        } else {
            when (val res = searchUserUseCase(query)) {
                is Resource.Success -> {
                    _uiState.value = uiState.value?.copy(
                        listUserPreview = res.data,
                        isLoading = false,
                        favoriteState = FavoriteState(
                            newIsFavoriteList = false,
                            oldIsFavoriteList = false
                        )
                    )
                }

                is Resource.Failure -> {
                    _uiState.value = _uiState.value?.copy(
                        toastMessage = SingleEvent(res.message),
                        isLoading = false
                    )
                }

                is Resource.Error -> {
                    _uiState.value = _uiState.value?.copy(
                        toastMessage = SingleEvent(
                            DynamicString(res.e.message.toString())
                        ),
                        isLoading = false
                    )
                    /*if (res.e is HttpException || res.e is SocketTimeoutException) {
                        delay(5000L)
                        sendAction(MainUiAction.SearchUser(query))
                    }*/
                }
            }

        }
    }


    init {
        _uiState.value = MainUIState()
    }


}