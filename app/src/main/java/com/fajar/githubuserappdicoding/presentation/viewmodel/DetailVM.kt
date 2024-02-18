package com.fajar.githubuserappdicoding.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fajar.githubuserappdicoding.domain.common.DynamicString
import com.fajar.githubuserappdicoding.domain.common.Resource
import com.fajar.githubuserappdicoding.domain.model.User
import com.fajar.githubuserappdicoding.domain.usecase.CheckIsUserInFavoriteUseCase
import com.fajar.githubuserappdicoding.domain.usecase.GetDetailUserUseCase
import com.fajar.githubuserappdicoding.domain.usecase.ToggleFavoriteStatusUseCase
import com.fajar.githubuserappdicoding.presentation.uistate.DetailUiState
import com.fajar.githubuserappdicoding.presentation.uiview.DetailActivity
import com.fajar.githubuserappdicoding.presentation.util.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailVM @Inject constructor(
    private val toggleFavoriteStatusUseCase: ToggleFavoriteStatusUseCase,
    getDetailUserUseCase: GetDetailUserUseCase,
    checkIsUserInFavoriteUseCase: CheckIsUserInFavoriteUseCase,
    savedStateHandle: SavedStateHandle
    //args: save
) : ViewModel() {

    private val username = savedStateHandle.get<String>(DetailActivity.EXTRA_USER)
        ?: throw Exception("Terjadi Kesalahan..")

    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    val uiState: StateFlow<DetailUiState> = combine(
        getDetailUserUseCase(username),
        checkIsUserInFavoriteUseCase(username)
    ) { resDetailUser, isFavorite ->
        setUserProfile(resDetailUser, isFavorite)
    }.flowOn(Dispatchers.Default).stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        DetailUiState(isLoading = true)
    )

    private suspend fun setUserProfile(
        resourceDetailUser: Resource<User>,
        isUserFavorite: Boolean
    ): DetailUiState {
        return when (resourceDetailUser) {
            is Resource.Success -> {
                uiState.value.copy(
                    userProfile = resourceDetailUser.data,
                    isLoading = false,
                    isUserFavorite = isUserFavorite
                )
            }

            is Resource.Failure -> {
                sendEvent(
                    UIEvent.ToastMessageEvent(
                        resourceDetailUser.message
                    )
                )

                uiState.value.copy(
                    isLoading = false,
                    isUserFavorite = isUserFavorite
                )
            }

            is Resource.Error -> {
                sendEvent(
                    UIEvent.ToastMessageEvent(
                        DynamicString(resourceDetailUser.e.message.toString())
                    )
                )

                uiState.value.copy(
                    isLoading = false,
                    isUserFavorite = isUserFavorite
                )
                /*if (res.e is HttpException || res.e is SocketTimeoutException) {
                    delay(5000L)
                    getUserProfile(args)
                }*/
            }
        }
    }

    private suspend fun sendEvent(uiEvent: UIEvent) {
        val isEventSent = !uiState.value.isLoading

        if (!isEventSent) {
            _uiEvent.send(uiEvent)
        }
    }

    fun toggleFavoriteStatus() {
        viewModelScope.launch(Dispatchers.IO) {
            val currUiState = uiState.value
            _uiEvent.send(
                UIEvent.ToastMessageEvent(
                    toggleFavoriteStatusUseCase(
                        currUiState.userProfile,
                        currUiState.isUserFavorite
                    )
                )
            )

        }
    }

}