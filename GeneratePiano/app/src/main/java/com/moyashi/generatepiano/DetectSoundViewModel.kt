package com.moyashi.generatepiano

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DetectSoundViewModel: ViewModel() {
    private val _counter = MutableLiveData(0)
    val counter: LiveData<Int> = _counter

    fun setValue(value:Int) {
        _counter.value = value
    }
    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text
}