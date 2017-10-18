//
//  ViewController.swift
//  todo
//
//  Created by wataokakoki on 2017/04/09.
//  Copyright © 2017年 wataokakoki. All rights reserved.
//

import UIKit

class ViewController: UIViewController, UITableViewDataSource, UITableViewDelegate {

    // ToDoを格納した配列
    var todoList = [String]()

    @IBOutlet weak var tableView: UITableView!

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    // +ボタンをタップした時に呼ばれる処理
    @IBAction func tapAddButton(_ sender: Any) {
        // アラートダイアログを生成
        let alertController = UIAlertController(title: "TODO追加",
                                                message: "TODOを入力してください",
                                                preferredStyle: UIAlertControllerStyle.alert)
        //テキストエリアを追加
        alertController.addTextField(configurationHandler: nil)
        //OKボタンを追加
        let okAction = UIAlertAction(title: "OK",
                                     style: UIAlertActionStyle.default) { (action: UIAlertAction) in
            // OKボタンがタップされた時の処理
            if let textField = alertController.textFields?.first {
                // tODの配列に入力値を挿入。先頭に挿入する
                self.todoList.insert(textField.text!, at: 0)

                // テーブルに行が追加されたことをテーブルに通知
                self.tableView.insertRows(at: [IndexPath(row: 0, section: 0)],
                                          with: UITableViewRowAnimation.right)
            }
        }
        //OKボタンを追加
        alertController.addAction(okAction)

        //CANCELボタンがタップされた時の処理
        let cancelButton = UIAlertAction(title: "CANCEL",
                                         style: UIAlertActionStyle.cancel,
                                         handler: nil)
        //CANCELボタンを追加
        alertController.addAction(cancelButton)

        // アラートダイアログを表示
        present(alertController, animated: true, completion: nil)
    }

    // テーブルの行数を返却する
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // TODOの配列の長さを返却する
        return todoList.count
    }

    //テーブルの行ごとのセルを返却する
    func tableView(_ tableView: UITableView,
                   cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        // Storyboardで指定したtodoCell識別子を利用して再利用可能なセルを取得する
        let cell = tableView.dequeueReusableCell(withIdentifier: "todoCell", for: indexPath)
        //行番号にあったTODOのタイトルを取得
        let todoTitle = todoList[indexPath.row]
        //セルのラベルにTodoのタイトルをセット
        cell.textLabel?.text = todoTitle
        return cell
    }
}

