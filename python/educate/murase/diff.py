# -*- coding: utf-8 -*- #
import numpy as np

def diff(f, x):
    h = 1e-4
    return (f(x+h)-f(x-h)) / (2*h)

def f1(x):
    return x*x

def f2(x):
    return 2*x

def f3(x):
    return np.sin(x)


# Main routine
print(diff(f1, 3))
print(diff(f2, 3))
print(diff(f3, 3))
