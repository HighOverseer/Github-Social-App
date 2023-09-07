package com.fajar.githubuserappdicoding.viewmodel

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fajar.githubuserappdicoding.R
import com.fajar.githubuserappdicoding.activity.DetailActivity
import com.fajar.githubuserappdicoding.domain.DynamicString
import com.fajar.githubuserappdicoding.domain.Repository
import com.fajar.githubuserappdicoding.domain.Resource
import com.fajar.githubuserappdicoding.domain.SingleEvent
import com.fajar.githubuserappdicoding.domain.StaticString
import com.fajar.githubuserappdicoding.uistate.DetailUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.SocketTimeoutException

class DetailVM(
    private val repos: Repository,
    args: Bundle
) : ViewModel() {

    private val _uiState = MutableLiveData(DetailUiState())
    val uiState: LiveData<DetailUiState> = _uiState

    private fun getUserProfile(args: Bundle) {
        viewModelScope.launch {
            val username = args.getString(DetailActivity.EXTRA_USER) ?: return@launch
            _uiState.value = _uiState.value?.copy(isLoading = true)
            when (val res = repos.getDetailUser(username)) {
                is Resource.Success -> {
                    val isUserFavorite = repos.isUserInFav(res.data.username)
                    _uiState.value = _uiState.value?.copy(
                        userProfile = res.data,
                        isLoading = false,
                        isUserFavorite = isUserFavorite
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
                        getUserProfile(args)
                    }
                }
            }
        }
    }

    fun toggleFavoriteStatus() {
        viewModelScope.launch(Dispatchers.IO) {
            val currUiState = _uiState.value ?: return@launch
            val user = currUiState.userProfile
            val newIsFavorite = !currUiState.isUserFavorite
            val toastMessage = if (newIsFavorite) {
                repos.addUserToFav(user.toUserFavorite())
                SingleEvent(StaticString(R.string.added_to_favorite_info, user.username))
            } else {
                repos.removeUserFromFav(user.username)
                SingleEvent(StaticString(R.string.removed_from_favorite_info, user.username))
            }
            val currIsFavorite = repos.isUserInFav(user.username)
            _uiState.postValue(
                _uiState.value?.copy(
                    isUserFavorite = currIsFavorite,
                    toastMessage = toastMessage
                )
            )
        }
    }

    init {
        getUserProfile(args)
    }
}