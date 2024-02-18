package com.fajar.githubuserappdicoding.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fajar.githubuserappdicoding.domain.common.DynamicString
import com.fajar.githubuserappdicoding.domain.common.Resource
import com.fajar.githubuserappdicoding.domain.data.Repository
import com.fajar.githubuserappdicoding.domain.model.UserDetailInfo
import com.fajar.githubuserappdicoding.domain.usecase.GetUserReposOrFollowingOrFollowerUseCase
import com.fajar.githubuserappdicoding.presentation.uistate.UserDetailInfoUiState
import com.fajar.githubuserappdicoding.presentation.uiview.DetailActivity
import com.fajar.githubuserappdicoding.presentation.uiview.UserDetailInfoFragment
import com.fajar.githubuserappdicoding.presentation.util.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class UserDetailInfoVM @Inject constructor(
    getUserReposOrFollowingOrFollowerUseCase: GetUserReposOrFollowingOrFollowerUseCase,
    savedStateHandle: SavedStateHandle
    //args: Bundle
) : ViewModel() {

    private val username =
        savedStateHandle.get<String>(DetailActivity.EXTRA_USER)
            ?: throw Exception("Terjadi Kesalahan..")
    private val pos = savedStateHandle.get<Int>(UserDetailInfoFragment.EXTRA_POSITION)
        ?: throw Exception("Terjadi Kesalahan..")

    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    val uiState =
        getUserReposOrFollowingOrFollowerUseCase(username, getType(pos)).map { res ->
            setData(res)
        }.flowOn(Dispatchers.Default).stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            UserDetailInfoUiState(isLoading = true)
        )


    private fun getType(pagePosition: Int): Repository.DataType? {
        return when (pagePosition) {
            FragmentPos.GithubRepos.pos -> {
                null
            }

            FragmentPos.Followers.pos -> {
                Repository.DataType.FOLLOWER
            }

            FragmentPos.Following.pos -> {
                Repository.DataType.FOLLOWING
            }

            else -> throw Exception("Terjadi Kesalahan..")
        }
    }

    private suspend fun setData(resource: Resource<List<UserDetailInfo>>): UserDetailInfoUiState {
        return when (resource) {
            is Resource.Success -> {
                uiState.value.copy(
                    listItems = resource.data,
                    isLoading = false
                )
            }

            is Resource.Failure -> {
                _uiEvent.send(
                    UIEvent.ToastMessageEvent(
                        resource.message
                    )
                )
                uiState.value.copy(
                    isLoading = false
                )
            }

            is Resource.Error -> {
                _uiEvent.send(
                    UIEvent.ToastMessageEvent(
                        DynamicString(resource.e.message.toString())
                    )
                )
                uiState.value.copy(
                    isLoading = false
                )
                /*if (res.e is HttpException || res.e is SocketTimeoutException) {
                    delay(5000L)
                    getData(args)
                }*/
            }
        }
    }

    enum class FragmentPos(val pos: Int) {
        GithubRepos(0), Followers(1), Following(2)
    }

}