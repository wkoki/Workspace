# -*- coding: utf-8 -*- #

class Env(object):
    def __init__(self):
        # ----- Setting ----- #
        self.test = True


        # ----- Attribute ----- #
        if self.test:
            self.n_state = 4
            self.length = 1
            self.width = 4
            self.start_point = 0
            self.goal_point = 3
            self.maze = [0, 0, 0, 0]
        else:
            self.n_state = 54
            self.length = 6
            self.width = 9
            self.start_point = 18
            self.goal_point = 8
            self.maze = [0, 0, 0, 0, 0, 0, 0, 1, 0,
                         0, 0, 1, 0, 0, 0, 0, 1, 0,
                         0, 0, 1, 0, 0, 0, 0, 1, 0,
                         0, 0, 1, 0, 0, 0, 0, 0, 0,
                         0, 0, 0, 0, 0, 1, 0, 0, 0,
                         0, 0, 0, 0, 0, 0, 0, 0, 0]

        def start(self):
            return self.start_point

        def goal(self):
            return self.goal_point

        #一つ上の状態の環境を返す。(0:通路, 1:障害物or環境外)
        def up_env(state):
            if (state < width):
                return 1
            else:
                return self.maze[state - self.width]

        #一つ上の状態の番号を返す。(-1は環境外)
        def up_state(state):
            if (state < width):
                return -1
            else:
                state - self.width

        #一つ右の状態の環境を返す。(0:通路, 1:障害物or環境外)
        def right_env(state):
            if (state % self.width == self.width - 1):
                return 1
            else:
                return self.maze[state + 1]

        #一つ右の状態の番号を返す。(-1は環境外)
        def right_state(state):
            if (state % self.width == self.width - 1):
                return -1
            else:
                return state + 1
        #一つ下の状態の環境を返す。(0:通路, 1:障害物or環境外)
        def down_env(state):
            down = state + self.width
            if (down >= self.n_state):
                return 1
            else:
                return self.maze[down]

        #一つ下の状態の番号を返す。(-1は環境外)
        def down_state(state):
            down = state + self.width
            if (down >= self.n_state):
                return -1
            else:
                return down

        #一つ左の状態の環境を返す。(0:通路, 1:障害物or環境外)
        def left_env(state):
            if not(state % self.width):
                return 1
            else:
                return self.maze[state - 1]

        #一つ左の状態の番号を返す。(-1は環境外)
        def left_state(state):
            if not(state % width):
                return -1
            else:
                state - 1
