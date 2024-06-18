package com.ozimos.baseproject.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ozimos.baseproject.domain.UserDomain
import com.ozimos.baseproject.usecase.GithubUseCase
import com.ozimos.baseproject.util.StateUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailUserViewModel @Inject constructor(private val useCase: GithubUseCase) : ViewModel() {


    private val _user = MutableLiveData<StateUtil<UserDomain>>()
    val user: LiveData<StateUtil<UserDomain>>
        get() = _user

    fun getDetail(username: String) {
        _user.postValue(StateUtil.Loading())
        viewModelScope.launch {
            val result = useCase.getDetailUser(username)
            result.fold(
                onSuccess = {
                    _user.postValue(StateUtil.Success(data = it))
                },
                onFailure = {
                    _user.postValue(
                        StateUtil.Error(
                            message = it.localizedMessage ?: "Unknown error"
                        )
                    )
                }
            )
        }
    }
}