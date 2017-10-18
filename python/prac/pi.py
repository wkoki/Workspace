# -*- coding:utf-8 -*- #
import random
import math

def pi():
    cnt = 0
    n = 100000

    for i in range(n):
        x = random.random()
        y = random.random()
        if (x*x + y*y) < 1:
            cnt += 1

    return 4*cnt/n

def pi2():
    n = 100000000
    ans = 0
    for i in range(1, n):
        print(ans*4)
        if (i%2 == 1):
            ans += 1/(i*2 - 1)
        else:
            ans -= 1/(i*2 - 1)



pi2()
