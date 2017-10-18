## coding:utf-8
import random

def _show(value):
        if(value == 0):
            return "*"
        elif(value == 1):
            return " "
        else:
            return "◉"

def show(table):
    print("-------------")
    print("|{0}|{1}|{2}|{3}|{4}|{5}|".format(_show(table[0][0]),_show(table[0][1]),_show(table[0][2]),_show(table[0][3]),_show(table[0][4]),_show(table[0][5])))
    print("+ + + + + + +")
    print("|{0}|{1}|{2}|{3}|{4}|{5}|".format(_show(table[1][0]),_show(table[1][1]),_show(table[1][2]),_show(table[1][3]),_show(table[1][4]),_show(table[1][5])))
    print("+ + + + + + +")
    print("|{0}|{1}|{2}|{3}|{4}|{5}|".format(_show(table[2][0]),_show(table[2][1]),_show(table[2][2]),_show(table[2][3]),_show(table[2][4]),_show(table[2][5])))
    print("+ + + + + + +")
    print("|{0}|{1}|{2}|{3}|{4}|{5}|".format(_show(table[3][0]),_show(table[3][1]),_show(table[3][2]),_show(table[3][3]),_show(table[3][4]),_show(table[3][5])))
    print("+ + + + + + +")
    print("|{0}|{1}|{2}|{3}|{4}|{5}|".format(_show(table[4][0]),_show(table[4][1]),_show(table[4][2]),_show(table[4][3]),_show(table[4][4]),_show(table[4][5])))
    print("+ + + + + + +")
    print("|{0}|{1}|{2}|{3}|{4}|{5}|".format(_show(table[5][0]),_show(table[5][1]),_show(table[5][2]),_show(table[5][3]),_show(table[5][4]),_show(table[5][5])))
    print("-------------")


def decision(log, table):
    row = 0
    col = 0
    for table_row in table:
        for i in table_row:
            if(i == 2):
                now = [row, col]
            col += 1
        row += 1

    p = random.randint(0, 3)
    p = p/4


##----------Main Routine----------##
log = [[0.25, 0.25, 0.25, 0.25],
       [0.25, 0.25, 0.25, 0.25],
       [0.25, 0.25, 0.25, 0.25],
       [0.25, 0.25, 0.25, 0.25],
       [0.25, 0.25, 0.25, 0.25],
       [0.25, 0.25, 0.25, 0.25],
       [0.25, 0.25, 0.25, 0.25],
       [0.25, 0.25, 0.25, 0.25],
       [0.25, 0.25, 0.25, 0.25],
       [0.25, 0.25, 0.25, 0.25],
       [0.25, 0.25, 0.25, 0.25],
       [0.25, 0.25, 0.25, 0.25]]

table = [[0, 0, 0, 0, 0, 0],
         [0, 2, 1, 1, 1, 0],
         [0, 1, 0, 0, 1, 0],
         [0, 1, 0, 0, 1, 0],
         [0, 1, 1, 1, 1, 0],
         [0, 0, 0, 0, 0, 0]]


show(table)
