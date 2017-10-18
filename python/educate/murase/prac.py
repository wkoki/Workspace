# -*- coding: utf-8 -*-
import random
import math


def pi():
    count = 0
    for i in range(10000):
        x = random.random()
        y = random.random()
        if (x*x + y*y)<1:
            count += 1

    return count * 4/ 10000
