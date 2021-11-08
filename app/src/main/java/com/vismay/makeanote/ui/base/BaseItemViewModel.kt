package com.vismay.makeanote.ui.base

import androidx.lifecycle.MutableLiveData

abstract class BaseItemViewModel<T : Any> : BaseViewModel() {

    val data: MutableLiveData<T> = MutableLiveData<T>()

    fun onManuallyCleared() = onCleared()

    fun updateData(data: T) {
        this.data.postValue(data)
    }
}