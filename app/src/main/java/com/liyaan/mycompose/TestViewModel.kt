package com.liyaan.mycompose

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class TestViewModel:ViewModel() {
    private val _index = MutableLiveData(0)
    private val _data = MutableStateFlow(0)

    val index:LiveData<Int> = _index
    val data: MutableStateFlow<Int> = _data
    fun onIndexChange(newIndex:Int){
        _index.value = newIndex
    }

    fun onDataChange(newIndex:Int){
        _data.value = newIndex
    }
}