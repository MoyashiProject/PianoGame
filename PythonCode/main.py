from music21 import *
import numpy
import matplotlib
import scipy
import random

#乱数生成
random_num = [-1 for _ in range(8)]
random_num[0] = 0
for i in range(1,8):
    random_num[i] = random.randint(1,7)
    if i >= 1: #前の音符と同じ音符がかぶらないようにする
        while True:
            if random_num[i] != random_num[i-i]:
                break
            else:
                random_num[i] = random.randint(1,7)

##なんか最初のおまじない
stream_right = stream.Part()
