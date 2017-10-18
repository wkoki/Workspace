//
//  RAMPaperSwitch.swift
//  LetsSYSTAN
//
//  Created by 綿岡晃輝 on 2015/09/22.
//  Copyright © 2015年 綿岡晃輝. All rights reserved.
//

import UIKit

class RAMPaperSwitch: UISwitch {
    @IBInspectable var duration: Double = 0.35  //duration(アニメーション速度)を0.35に設定
    
    var animationDidStartClosure = {(onAnimation:Bool) -> Void in}                //BoolのonAnimationを入れると内部処理
    var animationDidStopClosure = {(onAnimation:Bool, finished:Bool) -> Void in}  //BoolのonAmimationとfinishedを入れると内部処理
    
    private var shape: CAShapeLayer! = CAShapeLayer()  //CAShapeLayerは図を描くために各種プロパティが用意されている
    private var radius: CGFloat = 0.0                  //半径を0に設定
    private var oldState = false                       //oldStateをfalseに設定
    
    override var on/*over*/: Bool {
        didSet(oldValue) {           //onのBoolが変えられた時、onの元々のBoolをoldValueに代入し、
          oldState = on/*over*/      //変更後のonのBoolをoldStateに代入
        }
    }
    
    override func setOn(on/*引数*/: Bool, animated: Bool) {
        let changed:Bool = on/*引数*/ != self.on/*over*/
        super.setOn(on/*引数*/, animated: animated)
        
        if changed {               //スイッチがonの時、
            if animated {
                 switchChanged()   //アニメーションを許可されていればswitchChangedを実行、
            }else{
                 showShapeIfNeed() //許可されていなければshowShapeIfNeedを実行
            }
        }
    }
    
    //shape(円)のレイアウト設定
    override func layoutSubviews() {
        let x:CGFloat = max(frame.midX, superview!.frame.size.width - frame.midX);              //最も大きい数値を返すmax関数
        let y:CGFloat = max(frame.midY, superview!.frame.size.height - frame.midY);             //最も大きい数値を返すmax関数
        radius = sqrt(x*x + y*y);                                                               //座標(x,y)の左上からの距離
        shape.frame = CGRectMake(frame.midX - radius, frame.midY - radius, radius*2, radius*2)  //大きさを直径に設定
        shape.anchorPoint = CGPointMake(0.5, 0.5);                                              //アンカーポジション(物体の位置を特定する点)を中心に決定
        shape.path = UIBezierPath(ovalInRect: CGRectMake(0, 0, radius*2, radius*2)).CGPath      //円弧の設定
    }
    
    override func awakeFromNib() {
        //onTintColorがnilでないならアンラップ、nilなら緑色を代入
        var shapeColor:UIColor = (onTintColor != nil) ? onTintColor! : UIColor.blackColor()
        
        layer.borderWidth = 0.5                            //枠線の太さ
        layer.borderColor = UIColor.whiteColor().CGColor;  //枠線の色
        layer.cornerRadius = frame.size.height / 2;        //角丸の離心率
        
        shape.fillColor = shapeColor.CGColor               //円をshapeColor色に設定
        shape.masksToBounds = true                         //枠線をはみ出すと表示されない
        
        superview?.layer.insertSublayer(shape, atIndex: 0) //
        superview?.layer.masksToBounds = true              //
        
        showShapeIfNeed()     //
        
        addTarget(self, action: "switchChanged", forControlEvents: UIControlEvents.ValueChanged)  //
        
        super.awakeFromNib()  //
    }
    
    
    private func showShapeIfNeed() {
        //transformで形の変形
        //on==true(switchがon)ならば最大まで拡大、on==falseならば最小まで縮小
        shape.transform = on/*over*/ ? CATransform3DMakeScale(1.0, 1.0, 1.0) : CATransform3DMakeScale(0.0001, 0.0001, 0.0001)
    }
    
    internal func switchChanged() {
        if on/*over*/ == oldState {  //変更が無いならば、関数終了
            return;
        }
        
        //順次的に実行されるので、以下は変更があった場合
        oldState = on/*over*/
       
        if on/*over*/ {  //スイッチがオフ→オン
            CATransaction.begin()
            shape.removeAnimationForKey("scaleDown") //スケールダウンする
            let scaleAnimation:CABasicAnimation = animateKeyPath(
                "transform",
                fromValue: NSValue(CATransform3D: CATransform3DMakeScale(0.0001, 0.0001, 0.0001)),
                toValue: NSValue(CATransform3D: CATransform3DMakeScale(1.0, 1.0, 1.0)),
                timing:kCAMediaTimingFunctionEaseIn);
            
            shape.addAnimation(scaleAnimation, forKey: "scaleUp")
            CATransaction.commit();
            
        }else{          //スイッチがオン→オフ
            CATransaction.begin()
            shape.removeAnimationForKey("scaleup") //スケールアップする
            let scaleAnimation:CABasicAnimation = animateKeyPath(
                "transform",
                fromValue: NSValue(CATransform3D: CATransform3DMakeScale(1.0, 1.0, 1.0)),
                toValue:NSValue(CATransform3D: CATransform3DMakeScale(0.0001, 0.0001, 0.0001)),
                timing:kCAMediaTimingFunctionEaseOut);
            
            shape.addAnimation(scaleAnimation, forKey: "scaleDown")
            CATransaction.commit();
        }
    }
    
    
    /*キーパス,初期値,終了値,速度変化を代入し、アニメーションの型animationを返す*/
    private func animateKeyPath(codeOfKeyPath: String, fromValue from: AnyObject, toValue to: AnyObject, timing timingFunction: String) -> CABasicAnimation {
        let animation:CABasicAnimation = CABasicAnimation(keyPath: codeOfKeyPath)
        animation.fromValue = from               //開始時の値はform
        animation.toValue = to                   //終了時の値はto
        animation.repeatCount = 1                //繰り返ししない
        animation.timingFunction = CAMediaTimingFunction(name: timingFunction) //速度変化はtimingFuncion
        animation.removedOnCompletion = false    //アニメーション終了時
        animation.fillMode = kCAFillModeForwards //その状態を保持する
        animation.duration = duration;           //半径をdurationに設定
        animation.delegate = self                //アニメーション終了時animaitonDidStopを呼び出す?
        
        return animation;
    }
    
    //CAAnimation delegate
    
    override func animationDidStart(anim: CAAnimation) {
        animationDidStartClosure(on/*over*/)
    }
    
    override func animationDidStop(anim: CAAnimation, finished flag: Bool) {
        animationDidStopClosure(on/*over*/,flag)
    }
    
}
