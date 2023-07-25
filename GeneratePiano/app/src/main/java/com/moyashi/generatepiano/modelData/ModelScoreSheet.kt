package com.moyashi.generatepiano.modelData

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.lang.reflect.Constructor

class ModelScoreSheet constructor(
    _right_hand: MutableList<String>,
    _left_hand: MutableList<String>
){
    val right_hand: MutableList<String>
    val left_hand: MutableList<String>
    init {
        right_hand = _right_hand
        left_hand = _left_hand
    }

}
