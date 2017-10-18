//
//  BattleViewController.swift
//  LetsSYSTAN
//
//  Created by 綿岡晃輝 on 2015/09/15.
//  Copyright (c) 2015年 綿岡晃輝. All rights reserved.
//

import UIKit
import AVFoundation

class BattleViewController: UIViewController {
    
    
    @IBOutlet weak var title_label: UILabel!
    @IBOutlet weak var player2_label: UILabel!
    @IBOutlet weak var player1_label: UILabel!
    @IBOutlet weak var lifePoint1_label: UILabel!
    @IBOutlet weak var lifePoint2_label: UILabel!
    @IBOutlet weak var phase1_label: UILabel!
    @IBOutlet weak var phase2_label: UILabel!
    @IBOutlet weak var one: UIButton!
    @IBOutlet weak var two: UIButton!
    @IBOutlet weak var three: UIButton!
    @IBOutlet weak var four: UIButton!
    @IBOutlet weak var five: UIButton!
    var push:Int = 0
    var choice:Int = 0
    
   
    @IBOutlet weak private var chance_switch: RAMPaperSwitch!
        @IBOutlet weak var chance_label: UILabel!
    
    var Sound:AVAudioPlayer!
    
    func setupAudioPlayerWithFile(file:NSString, type:NSString) -> AVAudioPlayer {
        let path:String = NSBundle.mainBundle().pathForResource(file as String, ofType: type as String)!
        let url = NSURL.fileURLWithPath(path)
        
        let audioPlayer:AVAudioPlayer? = try! AVAudioPlayer(contentsOfURL: url)
        
        return audioPlayer!
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
     
        Sound = setupAudioPlayerWithFile("1080p", type: "m4a")
        
        self.setupPaperSwitch()
        
        self.navigationController?.navigationBarHidden = true
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    private func setupPaperSwitch() {
        self.chance_switch.animationDidStartClosure = {(onAnimation: Bool) in}
    }
    
    @IBAction func one_button(sender: UIButton) {
        one.backgroundColor = UIColor.redColor()
        two.backgroundColor = UIColor.cyanColor()
        three.backgroundColor = UIColor.cyanColor()
        four.backgroundColor = UIColor.cyanColor()
        five.backgroundColor = UIColor.cyanColor()
        push = 1
        choice = 1
    }
    
    @IBAction func two_button(sender: UIButton) {
        one.backgroundColor = UIColor.cyanColor()
        two.backgroundColor = UIColor.redColor()
        three.backgroundColor = UIColor.cyanColor()
        four.backgroundColor = UIColor.cyanColor()
        five.backgroundColor = UIColor.cyanColor()
        push = 1
        choice = 2
    }

    @IBAction func three_button(sender: UIButton) {
        one.backgroundColor = UIColor.cyanColor()
        two.backgroundColor = UIColor.cyanColor()
        three.backgroundColor = UIColor.redColor()
        four.backgroundColor = UIColor.cyanColor()
        five.backgroundColor = UIColor.cyanColor()
        push = 1
        choice = 3
    }
   
    @IBAction func four_button(sender: UIButton) {
        one.backgroundColor = UIColor.cyanColor()
        two.backgroundColor = UIColor.cyanColor()
        three.backgroundColor = UIColor.cyanColor()
        four.backgroundColor = UIColor.redColor()
        five.backgroundColor = UIColor.cyanColor()
        push = 1
        choice = 4
    }
    
    @IBAction func five_button(sender: UIButton) {
        one.backgroundColor = UIColor.cyanColor()
        two.backgroundColor = UIColor.cyanColor()
        three.backgroundColor = UIColor.cyanColor()
        four.backgroundColor = UIColor.cyanColor()
        five.backgroundColor = UIColor.redColor()
        push = 1
        choice = 5
    }
    
    //正解ボタンを押した時
    @IBAction func correct_button(sender: UIButton) {
        
        
        if (chance_switch.on){
            switch choice {
            case 1:
                if phase1_label.text == "オフェンス" {
                    lifePoint1_label.text = String(Int(lifePoint1_label.text!)! - 50)
                }else {
                    lifePoint2_label.text = String(Int(lifePoint2_label.text!)! - 50)
                }
            case 2:
                if phase1_label.text == "オフェンス" {
                    lifePoint1_label.text = String(Int(lifePoint1_label.text!)! - 100)
                }else {
                    lifePoint2_label.text = String(Int(lifePoint2_label.text!)! - 100)
                }
            case 3:
                if phase1_label.text == "オフェンス" {
                    lifePoint1_label.text = String(Int(lifePoint1_label.text!)! - 150)
                }else {
                    lifePoint2_label.text = String(Int(lifePoint2_label.text!)! - 150)
                }
            case 4:
                if phase1_label.text == "オフェンス" {
                    lifePoint1_label.text = String(Int(lifePoint1_label.text!)! - 200)
                }else {
                    lifePoint2_label.text = String(Int(lifePoint2_label.text!)! - 200)
                }
            case 5:
                if phase1_label.text == "オフェンス" {
                    lifePoint1_label.text = String(Int(lifePoint1_label.text!)! - 250)
                }else {
                    lifePoint2_label.text = String(Int(lifePoint2_label.text!)! - 250)
                }
            default:
                push = 0
            }
        }else{
        switch choice {
        case 1:
            if phase1_label.text == "オフェンス" {
              lifePoint1_label.text = String(Int(lifePoint1_label.text!)! - 100)
            }else {
              lifePoint2_label.text = String(Int(lifePoint2_label.text!)! - 100)
            }
        case 2:
            if phase1_label.text == "オフェンス" {
                lifePoint1_label.text = String(Int(lifePoint1_label.text!)! - 200)
            }else {
                lifePoint2_label.text = String(Int(lifePoint2_label.text!)! - 200)
            }
        case 3:
                if phase1_label.text == "オフェンス" {
                    lifePoint1_label.text = String(Int(lifePoint1_label.text!)! - 300)
                }else {
                    lifePoint2_label.text = String(Int(lifePoint2_label.text!)! - 300)
            }
        case 4:
            if phase1_label.text == "オフェンス" {
                lifePoint1_label.text = String(Int(lifePoint1_label.text!)! - 400)
            }else {
                lifePoint2_label.text = String(Int(lifePoint2_label.text!)! - 400)
            }
        case 5:
                if phase1_label.text == "オフェンス" {
                    lifePoint1_label.text = String(Int(lifePoint1_label.text!)! - 500)
                }else {
                    lifePoint2_label.text = String(Int(lifePoint2_label.text!)! - 500)
            }
        default:
            push = 0
        }
        }
        
        if push == 1 {
        if phase1_label.text == "オフェンス"{
          phase1_label.text = "ディフェンス"
          phase1_label.backgroundColor = UIColor.whiteColor()
          phase2_label.text = "オフェンス"
          phase2_label.backgroundColor = UIColor.redColor()
        }else{
            phase1_label.text = "オフェンス"
            phase1_label.backgroundColor = UIColor.redColor()
            phase2_label.text = "ディフェンス"
            phase2_label.backgroundColor = UIColor.whiteColor()
        }
        }
        one.backgroundColor = UIColor.cyanColor()
        two.backgroundColor = UIColor.cyanColor()
        three.backgroundColor = UIColor.cyanColor()
        four.backgroundColor = UIColor.cyanColor()
        five.backgroundColor = UIColor.cyanColor()
        
        push = 0
        choice = 0
        self.view.backgroundColor = UIColor.whiteColor()
        chance_label.textColor = UIColor.blackColor()
        chance_switch.on = false
        player1_label.textColor = UIColor.blackColor()
        player2_label.textColor = UIColor.blackColor()
        
        if(Int(lifePoint1_label.text!)! < 0){
          lifePoint1_label.text = "0"
        }else if (Int(lifePoint2_label.text!)! < 0){
          lifePoint2_label.text = "0"
        }
    }
    
    //不正解ボタンを押した時
    @IBAction func incorrect_button(sender: UIButton) {
        if push == 1 {
        if phase1_label.text == "ディフェンス" {
          lifePoint1_label.text = String(Int(lifePoint1_label.text!)! - 100)
        }else{
          lifePoint2_label.text = String(Int(lifePoint2_label.text!)! - 100)
        }
        if phase1_label.text == "オフェンス"{
            phase1_label.text = "ディフェンス"
            phase1_label.backgroundColor = UIColor.whiteColor()
            phase2_label.text = "オフェンス"
            phase2_label.backgroundColor = UIColor.redColor()
        }else{
            phase1_label.text = "オフェンス"
            phase1_label.backgroundColor = UIColor.redColor()
            phase2_label.text = "ディフェンス"
            phase2_label.backgroundColor = UIColor.whiteColor()
        }
        }
        one.backgroundColor = UIColor.cyanColor()
        two.backgroundColor = UIColor.cyanColor()
        three.backgroundColor = UIColor.cyanColor()
        four.backgroundColor = UIColor.cyanColor()
        five.backgroundColor = UIColor.cyanColor()
        
        push = 0
        choice = 0
        self.view.backgroundColor = UIColor.whiteColor()
        chance_label.textColor = UIColor.blackColor()
        chance_switch.on = false
        player1_label.textColor = UIColor.blackColor()
        player2_label.textColor = UIColor.blackColor()
        
        if(Int(lifePoint1_label.text!)! < 0){
            lifePoint1_label.text = "0"
        }else if (Int(lifePoint2_label.text!)! < 0){
            lifePoint2_label.text = "0"
        }    }
    
    
    //チャンススイッチ
    
    @IBAction func chanceMode(sender: RAMPaperSwitch) {
        if chance_switch.on{
          self.view.backgroundColor = UIColor.blackColor()
            chance_label.textColor = UIColor.whiteColor()
            player1_label.textColor = UIColor.whiteColor()
            player2_label.textColor = UIColor.whiteColor()
            Sound!.play()
        }else if !chance_switch.on{
          self.view.backgroundColor = UIColor.whiteColor()
            chance_label.textColor = UIColor.blackColor()
            player1_label.textColor = UIColor.blackColor()
            player2_label.textColor = UIColor.blackColor()
        }
    }
    
    
    

}
