package com.ozimos.baseproject.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ozimos.baseproject.domain.PicsumDomain
import com.ozimos.baseproject.usecase.UseCase
import com.ozimos.baseproject.util.StateUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(private val useCase: UseCase) : ViewModel() {

    private val _photos = MutableLiveData<StateUtil<List<PicsumDomain>>>()
    val photos: LiveData<StateUtil<List<PicsumDomain>>>
        get() = _photos

    private var page = 1

    fun initPicture() {
        getListPhoto(page)
    }

    fun loadMorePicture() {
        if (page < 5) {
            page++
            getListPhoto(page)
        }
    }

    private fun getListPhoto(page : Int) {
        _photos.postValue(StateUtil.Loading())
        viewModelScope.launch {
            val result = useCase.getListPhoto(page)
            result.fold(
                onSuccess = {
                    _photos.postValue(StateUtil.Success(data = it))
                },
                onFailure = {
                    _photos.postValue(
                        StateUtil.Error(
                            message = it.localizedMessage ?: "Unknown error"
                        )
                    )
                }
            )
        }
    }
}