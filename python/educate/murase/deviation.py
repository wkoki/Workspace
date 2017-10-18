# -*- coding: utf-8 -*- #

import xlrd
import math


def deviation(x, list):
    sum = 0
    ave = 0
    disp = 0
    std_dev = 0

    for i in list:
        sum += i

    ave = sum / len(list)

    for i in list:
        disp += math.fabs(i - ave)

    std_dev = math.sqrt(disp)


    return ((x - ave)*10/std_dev) + 50

def print_dev(x):
    book = xlrd.open_workbook('data.xlsx')
    sheet = book.sheet_by_index(0)

    data = [[0 for i in range(sheet.ncols)] for j in range(sheet.nrows)]
    name_list = []
    points_list = []

    for row in range(sheet.nrows):
        for col in range(sheet.ncols):
            data[row][col]=sheet.cell(row, col).value

    for i in range(sheet.nrows):
        name_list.append(data[i][0])
        points_list.append(data[i][1])

    print(name_list[x])
    print(deviation(points_list[x], points_list))


# メインルーチン
a = input("please input: ")
print_dev(int(a))

