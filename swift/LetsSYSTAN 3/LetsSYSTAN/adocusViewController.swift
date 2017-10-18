//
//  adocusViewController.swift
//  LetsSYSTAN
//
//  Created by 綿岡晃輝 on 2015/09/21.
//  Copyright © 2015年 綿岡晃輝. All rights reserved.
//

import UIKit
import AVFoundation

class adocusViewController: UIViewController {
    
    
    
    func transition(){
        let storyboard:UIStoryboard = UIStoryboard(name:"Main",bundle:nil)
        let next:UIViewController = storyboard.instantiateViewControllerWithIdentifier("TitleViewController") as UIViewController
        next.modalTransitionStyle = UIModalTransitionStyle.CrossDissolve
        self.presentViewController(next,animated:true, completion:nil)
    }

    override func viewDidLoad() {
        super.viewDidLoad()
        
                NSTimer.scheduledTimerWithTimeInterval(3.0, target:self, selector: Selector("transition"),userInfo:nil,repeats:false)

       
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
