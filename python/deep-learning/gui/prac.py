#! /usr/bin/python3
# -*- coding: utf-8 -*-

import sys
from PyQt5.QtWidgets import QApplication, QWidget, QPushButton

class MyWidget(QWidget):
    def __init__(self):
        super().__init__()
        self.init_ui()
        self.show()
        
    def init_ui(self):
        self.button = QPushButton('button', self)
        # トグル式にする
        self.button.setCheckable(True)
        # シグナル・スロットの設定
        self.button.toggled.connect(self.slot_button_toggled)
    
    def slot_button_toggled(self, checked):
        """ ボタンがトグルされたときのスロット """
        if checked:
            self.button.setText('Checked')
        else:
            self.button.setText('Not Checked')
        
if __name__ == '__main__':
    app = QApplication(sys.argv)
    window = MyWidget()
    sys.exit(app.exec_())
