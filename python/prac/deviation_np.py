# -*- coding:utf-8 -*- #

import xlrd
import numpy as np

def deviation(x, list):
    return  np.round_(50+10*(x-np.average(x))/np.std(x))

def print_dev(x):
    book = xlrd.open_workbook('data.xlsx')
    sheet = book.sheet_by_index(0)
    data = [[0 for i in range(sheet.ncols)] for j in range(sheet.nrows)]
    for row in range(sheet.nrows):
        for col in range(sheet.ncols):
            data[row][col] = sheet.cell(row, col).value

    print(data[x][1])
    print(deviation(data[x][0], data[:][1]))

print_dev(0)
