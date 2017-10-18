//
//  TitleViewController.swift
//  LetsSYSTAN
//
//  Created by 綿岡晃輝 on 2015/09/21.
//  Copyright © 2015年 綿岡晃輝. All rights reserved.
//

import UIKit
import AVFoundation

class TitleViewController: UIViewController {
    
    var TitleCall:AVAudioPlayer!
    
    func setupAudioPlayerWithFile(file:NSString,type:NSString) -> AVAudioPlayer{
        let path:String = NSBundle.mainBundle().pathForResource(file as String, ofType: type as String)!
        let url = NSURL.fileURLWithPath(path)
        let audioPlayer:AVAudioPlayer? = try! AVAudioPlayer(contentsOfURL: url)
        
        return audioPlayer!
    }

    override func viewDidLoad() {
        super.viewDidLoad()
        
        TitleCall = setupAudioPlayerWithFile("let", type: "m4a")
        TitleCall.play()
        
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
