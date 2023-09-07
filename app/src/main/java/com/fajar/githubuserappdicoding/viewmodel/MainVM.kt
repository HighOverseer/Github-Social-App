package com.fajar.githubuserappdicoding.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.fajar.githubuserappdicoding.R
import com.fajar.githubuserappdicoding.domain.DynamicString
import com.fajar.githubuserappdicoding.domain.Repository
import com.fajar.githubuserappdicoding.domain.Resource
import com.fajar.githubuserappdicoding.domain.SingleEvent
import com.fajar.githubuserappdicoding.domain.StaticString
import com.fajar.githubuserappdicoding.uiaction.MainUiAction
import com.fajar.githubuserappdicoding.uistate.FavoriteState
import com.fajar.githubuserappdicoding.uistate.MainUIState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.SocketTimeoutException

class MainVM(
    private val repository: Repository
) : ViewModel() {

    companion object {
        private const val DELAY_MILLIS = 750L
    }

    private var currListFavoriteLiveData = repository.getListUserFavorites()

    val themeState = repository.isUsingDarkTheme().distinctUntilChanged().asLiveData()

    private val _uiState = MediatorLiveData<MainUIState>()
    val uiState: LiveData<MainUIState> = _uiState


    private var job: Job? = null

    fun sendAction(action: MainUiAction) {
        when (action) {
            is MainUiAction.SearchUser -> {
                job?.cancel()
                job = searchUser(action.query)
            }

            is MainUiAction.SearchingUser -> {
                job?.cancel()
                job = searchUser(
                    action.chunkedQuery, DELAY_MILLIS
                )
            }

            MainUiAction.ClickUser -> {
                job?.cancel()
            }

            MainUiAction.RestoreJobRes -> {
                _uiState.value = _uiState.value?.copy(isLoading = false)
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
            currListFavoriteLiveData = repository.getListUserFavorites()
            _uiState.addSource(currListFavoriteLiveData) { newListUserFavorite ->
                val listUserPreview = newListUserFavorite.map { it.toUserPreview() }
                val currUiState = _uiState.value
                _uiState.value = currUiState?.copy(
                    listUserPreview = listUserPreview,
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
            repository.changeTheme()
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
            currListFavoriteLiveData = repository.getListUserFavorites()
            _uiState.addSource(currListFavoriteLiveData) { newListUserFavorite ->
                val listUserPreview = newListUserFavorite.map { it.toUserPreview() }
                val currUiState = _uiState.value
                _uiState.value = currUiState?.copy(
                    listUserPreview = listUserPreview,
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

        delay(delayMillis)

        val currIsInFavoriteList = _uiState.value?.favoriteState?.newIsFavoriteList ?: return@launch

        _uiState.value = uiState.value?.copy(
            query = query,
            isLoading = true
        )

        if (currIsInFavoriteList) {
            _uiState.removeSource(currListFavoriteLiveData)
            currListFavoriteLiveData = repository.searchUserUserInFavorite(query)
            _uiState.addSource(currListFavoriteLiveData) { newListUserFavorite ->
                val listUserPreview = newListUserFavorite.map { it.toUserPreview() }
                val currUiState = _uiState.value
                _uiState.value = currUiState?.copy(
                    listUserPreview = listUserPreview,
                    isLoading = false,
                    favoriteState = FavoriteState(
                        newIsFavoriteList = true,
                        oldIsFavoriteList = true
                    )
                )
            }
        } else {
            when (val res = repository.searchUser(query)) {
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
                    if (res.e is HttpException || res.e is SocketTimeoutException) {
                        delay(5000L)
                        sendAction(MainUiAction.SearchUser(query))
                    }
                }
            }

        }
    }

    init {
        _uiState.value = MainUIState()
    }


}