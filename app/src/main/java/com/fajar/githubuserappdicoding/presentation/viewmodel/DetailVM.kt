package com.fajar.githubuserappdicoding.presentation.viewmodel

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.fajar.githubuserappdicoding.R
import com.fajar.githubuserappdicoding.domain.common.DynamicString
import com.fajar.githubuserappdicoding.domain.common.Resource
import com.fajar.githubuserappdicoding.domain.common.SingleEvent
import com.fajar.githubuserappdicoding.domain.common.StaticString
import com.fajar.githubuserappdicoding.domain.data.Repository
import com.fajar.githubuserappdicoding.domain.mapper.DataMapper
import com.fajar.githubuserappdicoding.domain.usecase.CheckIsUserInFavoriteUseCase
import com.fajar.githubuserappdicoding.domain.usecase.GetDetailUserUseCase
import com.fajar.githubuserappdicoding.domain.usecase.ToggleFavoriteStatusUseCase
import com.fajar.githubuserappdicoding.presentation.uistate.DetailUiState
import com.fajar.githubuserappdicoding.presentation.uiview.DetailActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.SocketTimeoutException
import javax.inject.Inject

@HiltViewModel
class DetailVM @Inject constructor(
    private val getDetailUserUseCase: GetDetailUserUseCase,
    private val toggleFavoriteStatusUseCase: ToggleFavoriteStatusUseCase,
    checkIsUserInFavoriteUseCase: CheckIsUserInFavoriteUseCase,
    args: Bundle
) : ViewModel() {

    private val username = args.getString(DetailActivity.EXTRA_USER) ?: throw Exception("Terjadi Kesalahan..")

    private val _uiState = MediatorLiveData(DetailUiState())
    val uiState: LiveData<DetailUiState> = _uiState

    private val isUserFavoriteStatus = checkIsUserInFavoriteUseCase(username)
        .asLiveData()

    private fun getUserProfile(args: Bundle) {
        viewModelScope.launch {
            _uiState.value = _uiState.value?.copy(isLoading = true)
            when (val res = getDetailUserUseCase(username)) {
                is Resource.Success -> {
                    _uiState.value = _uiState.value?.copy(
                        userProfile = res.data,
                        isLoading = false,
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
            val toastMessage = toggleFavoriteStatusUseCase(
                currUiState.userProfile,
                currUiState.isUserFavorite
            )
            _uiState.postValue(
                _uiState.value?.copy(
                    toastMessage = toastMessage
                )
            )
        }
    }

    init {
        _uiState.addSource(isUserFavoriteStatus){
            _uiState.value = _uiState.value?.copy(isUserFavorite = it)
        }
        getUserProfile(args)

    }
}