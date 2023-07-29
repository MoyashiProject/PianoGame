package com.moyashi.generatepiano

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DetectSoundViewModel: ViewModel() {
    private val _onkai = MutableLiveData("")
    val onkai: LiveData<String> = _onkai

    private val _onkaiEn = MutableLiveData("")
    val onkaiEn: LiveData<String> = _onkaiEn

    private val _onpu_height = MutableLiveData(0f)
    val onpu_height: LiveData<Float> = _onpu_height

    //今楽譜のどの配列の部分を弾いているのかを判別するためのMVVM
    private val _nowPlaying: MutableLiveData<Int> = MutableLiveData(0)
    val nowPlaying: LiveData<Int> = _nowPlaying

    //音声を収音するかどうかの判定を行うMVVM
    private val _isCollectSound: MutableLiveData<Boolean> = MutableLiveData(false)
    val isCollectSound: LiveData<Boolean> = _isCollectSound
    fun setOnkai(value:String) {
        _onkai.value = value
    }

    fun setOnkaiEn(value:String){
        _onkaiEn.value = value
    }

    fun setNowPlaying(){
        _nowPlaying.value = nowPlaying.value?.plus(1);
    }
    fun setBackNowPlaying(){
        _nowPlaying.value = nowPlaying.value?.minus(1)
    }

    fun setOnDetecting(){
        _isCollectSound.value = true
    }
    fun setOffDetectiong(){
        _isCollectSound.value = false
    }
    fun isCurrentDetecting(): Boolean? {
        return isCollectSound.value
    }

}