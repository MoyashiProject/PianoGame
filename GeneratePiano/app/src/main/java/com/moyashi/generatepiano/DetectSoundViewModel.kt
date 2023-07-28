package com.moyashi.generatepiano

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DetectSoundViewModel: ViewModel() {
    private val _onkai = MutableLiveData("")
    val onkai: LiveData<String> = _onkai

    private val _onpu_height = MutableLiveData(0f)
    val onpu_height: LiveData<Float> = _onpu_height

    //今楽譜のどの配列の部分を弾いているのかを判別するためのMVVM
    private val _nowPlaying: MutableLiveData<Int> = MutableLiveData(0)
    val nowPlaying: LiveData<Int> = _nowPlaying
    fun setOnkai(value:String) {
        _onkai.value = value
    }
    fun setNowPlaying(value:Int){
        _nowPlaying.value = value
    }
    fun setOnpuHeight(value:Float){
        _onpu_height.value = value
    }
    private val _onput_height = MutableLiveData<Float>().apply {
        value = 0f
    }
}