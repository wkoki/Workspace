# -*- coding: utf-8 -*- #

#---------- Board Class ----------#
class Board(object):
    def __init__(self):
        self.size = 5 # nxnの盤面
        self.line = 3 # n個並べれば勝ち
        self.data = [0 for i in range(self.size) for j in range(self.size)]

    def getEnv(self, row, col):
        return self.data[row][col]

    def setEnv(self, row, col, ox):
        if (self.data[row][col] == 0):
            self.data[x][y] = ox

    def _show(self, row, col):
        if self.data[row][col] == 0:
            return " "
        elif self.data[row][col] == 1:
            return "o"
        else:
            return "x"

    def show(self):
        for i in range(self.size):
            for j in range(self.size):
                print("+-", end=' ')
            print("+")

            for j in range(self.size):
                print("|" + str(self.data[i][j]))
            print("|")
