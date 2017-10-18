# -*- coding:utf-8 -*-

'''
@author: Koki Wataoka
'''

import time
import random
import copy

class Othello:

    def play(self):
        board = Board()
        player1 = computer = Computer(BLACK, "PC")
        player2 = user = User(WHITE, "あなた")
        #player1 = user = User(BLACK, "あなた")
        #player2 = computer = Computer(WHITE, "PC")

        turn = 0
        hand1 = hand2 = None
        while board.is_playable() and not hand1 == hand2 == "pass":
            turn += 1
            print("TURN = %s" % turn)

            print(board)
            hand1 = player1.play(board)
            print("%sの手: %s" % (player1.name, hand1))

            print(board)
            hand2 = player2.play(board)
            print("%sの手: %s" % (player2.name, hand2))

        self.show_result(board)

    def show_result(self, board):
        print("----------RESULT----------")
        print(board)
        
