package com.moyashi.generatepiano.backgroundTask

import android.util.Log

class GenerateScore {

    fun GenerateEasy(): MutableList<String> {
        var random_num = arrayOf(-1,-1,-1,-1,-1,-1,-1,-1)
        val sounds = arrayOf("C1", "D1", "E1", "F1", "G1", "A1", "B1", "C2", "D2", "E2", "F2", "G2", "A2", "B2", "C3", "D3", "E3", "F3", "G3", "A3", "B3", "C4", "D4", "E4", "F4", "G4", "A4", "B4", "C5", "D5", "E5", "F5", "G5", "A5", "B5", "C6")
        var stream_right = mutableListOf<String>()
        random_num[0] = 0
        for(i in 1..7){
            val range =(1..3)
            random_num[i] = range.random()
            if (i >= 1){
                while (true) {
                    if (random_num[i] != random_num[i - 1]) {
                        break
                    } else {
                        random_num[i] = range.random()
                    }
                }

            }
            if (i==7){
                while (random_num[i] == random_num[0] + 1){
                    random_num[i] = range.random()
                }
            }
        }

        //そのための右手
        for (i in 0..13){
            val mlist = mutableListOf<String>()
            for (j in 0..7){
                val onpu = sounds[random_num[j]+14 + i]
                mlist.add(onpu)
            }
            stream_right.addAll(mlist)
        }
        for (i in 0..13){
            val mlist = mutableListOf<String>()
            var x = 18
            for(j in 0..7){
                if (j==0){
                    val onpu = sounds[x + 14 -i]
                    mlist.add(onpu)
                }else{
                    val onpu = sounds[x-random_num[j] + 14 - i]
                    mlist.add(onpu)
                }
            }
            stream_right.addAll(mlist)
        }
        stream_right.add("C3")
        Log.d("完成",stream_right.toString())
        return stream_right
    }

}