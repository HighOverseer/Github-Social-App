package com.fajar.githubuserappdicoding.detail_user.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fajar.githubuserappdicoding.core.domain.common.DynamicString
import com.fajar.githubuserappdicoding.core.domain.common.Resource
import com.fajar.githubuserappdicoding.core.domain.data.Repository
import com.fajar.githubuserappdicoding.core.domain.model.UserDetailInfo
import com.fajar.githubuserappdicoding.core.presentation.UIEvent
import com.fajar.githubuserappdicoding.detail_user.domain.GetUserReposOrFollowingOrFollowerUseCase
import com.fajar.githubuserappdicoding.detail_user.presentation.uistate.UserDetailInfoUiState
import com.fajar.githubuserappdicoding.detail_user.presentation.uiview.DetailActivity
import com.fajar.githubuserappdicoding.detail_user.presentation.uiview.UserDetailInfoFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn

class UserDetailInfoVM(
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