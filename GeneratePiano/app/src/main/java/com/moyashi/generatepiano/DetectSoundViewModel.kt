package com.moyashi.generatepiano

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DetectSoundViewModel: ViewModel() {
    private val _onkai = MutableLiveData("")
    val counter: LiveData<String> = _onkai

    private val _onpu_height = MutableLiveData(0f)
    val onpu_height: LiveData<Float> = _onpu_height
    fun setOnkai(value:String) {
        _onkai.value = value
    }
    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }

    fun setOnpuHeight(value:Float){
        _onpu_height.value = value
    }
    private val _onput_height = MutableLiveData<Float>().apply {
        value = 0f
    }
    val text: LiveData<String> = _text
}