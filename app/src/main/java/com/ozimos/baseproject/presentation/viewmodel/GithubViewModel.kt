package com.ozimos.baseproject.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ozimos.baseproject.domain.PicsumDomain
import com.ozimos.baseproject.domain.UserDomain
import com.ozimos.baseproject.usecase.GithubUseCase
import com.ozimos.baseproject.util.Debouncer
import com.ozimos.baseproject.util.StateUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GithubViewModel @Inject constructor(private val useCase: GithubUseCase) : ViewModel() {


    private val _users = MutableLiveData<StateUtil<List<UserDomain>>>()
    val users: LiveData<StateUtil<List<UserDomain>>>
        get() = _users

    fun searchUser(query: String) {
        debounceSearch.submit(query)
    }

    private val debounceSearch by lazy {
        Debouncer<String>(delayMillis = 500L, onDebounce = { page ->
            _users.postValue(StateUtil.Loading())
            viewModelScope.launch {
                val result = useCase.searchUser(page)
                result.fold(
                    onSuccess = {
                        _users.postValue(StateUtil.Success(data = it))
                    },
                    onFailure = {
                        _users.postValue(
                            StateUtil.Error(
                                message = it.localizedMessage ?: "Unknown error"
                            )
                        )
                    }
                )
            }
        })
    }
}