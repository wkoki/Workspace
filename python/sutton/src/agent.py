# -*- coding: utf-8 -*- #
import random

class agent(object):
    def __init__(self):
        self.values = [[0, 0, 0, 0] * 10000]

    def reward(self, x):
        ans = 1.0
        for i in range(x):
            ans *= 0.1

        return ans

    def chooseAction(self, state):
        sum = 0.0
