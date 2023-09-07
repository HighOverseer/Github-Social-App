package com.fajar.githubuserappdicoding.viewmodel

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fajar.githubuserappdicoding.activity.DetailActivity
import com.fajar.githubuserappdicoding.domain.DynamicString
import com.fajar.githubuserappdicoding.domain.Repository
import com.fajar.githubuserappdicoding.domain.Resource
import com.fajar.githubuserappdicoding.domain.SingleEvent
import com.fajar.githubuserappdicoding.fragment.UserDetailInfoFragment
import com.fajar.githubuserappdicoding.uistate.UserDetailInfoUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.SocketTimeoutException

class UserDetailInfoVM(
    private var repository: Repository,
    args: Bundle
) : ViewModel() {

    private val _uiState = MutableLiveData(UserDetailInfoUiState())
    val uiState: LiveData<UserDetailInfoUiState> = _uiState

    private fun getData(args: Bundle) {
        viewModelScope.launch {
            val username = args.getString(DetailActivity.EXTRA_USER) ?: return@launch
            val pos = args.getInt(UserDetailInfoFragment.EXTRA_POSITION)
            _uiState.value = _uiState.value?.copy(isLoading = true)
            val res = when (pos) {
                FragmentPos.GithubRepos.pos -> {
                    repository.getListGithubRepos(username)
                }

                FragmentPos.Followers.pos -> {
                    repository.getUserFollowersFollowing(username, Repository.DataType.FOLLOWER)
                }

                FragmentPos.Following.pos -> {
                    repository.getUserFollowersFollowing(username, Repository.DataType.FOLLOWING)
                }

                else -> return@launch
            }
            when (res) {
                is Resource.Success -> {
                    _uiState.value = _uiState.value?.copy(
                        listItems = res.data,
                        isLoading = false
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
                        getData(args)
                    }
                }
            }

        }
    }

    enum class FragmentPos(val pos: Int) {
        GithubRepos(0), Followers(1), Following(2)
    }

    init {
        getData(args)
    }

}