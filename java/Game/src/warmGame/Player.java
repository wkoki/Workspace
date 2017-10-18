package warmGame;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Player {

	//プレイヤーの座標と大きさの基準
	private int px, py, dpx1, dpy1, dpx0, dpy0, psize, initnum, eatnum;
	static final int plSize = 20;
	static final int mag = 1;

	ArrayList<Body> bodies;

	//それぞれプレイヤーの頭と体の画像を格納してあるフォルダの名前
	private static final String dirName1 = "head";
	private static final String dirName2 = "body";

	public Player(){
		px = 400;
		py = 400;
		dpx1 = 0;
		dpy1 = -10;
		dpx0 = dpx1;
		dpy0 = dpy1;
		psize = 1;
		initnum = 10;
		eatnum = 0;
		imageSetup();
		bodies = new ArrayList<Body>();
	}

	public void playerInit(){
		addImage();
		bodies.add(new Body(this));

		for(int i=0; i<initnum-1; i++){
			addImage();
			bodies.add(new Body(bodies.get(bodies.size()-1)));
		}
	}

	//プレイヤーの頭の画像を登録するリストと体の画像を登録するリスト
	ArrayList<BufferedImage> headimages = new ArrayList<BufferedImage>();
	ArrayList<BufferedImage> bodyimages = new ArrayList<BufferedImage>();

	//headフォルダに格納されている頭の画像を全てリストに登録するメソッド
	private void imageSetup()  {
		File iconDir = new File(dirName1); // dirName 相当のFileを作成し
		if(!iconDir.exists() || !iconDir.isDirectory()) return;
		// directory が存在した場合、その各ファイルに対して
		for(File file: iconDir.listFiles()) {
			try { // 画像としての取り込みを計り
				BufferedImage img = ImageIO.read(file);
				if(img != null) // うまく言ったものを images に追加
					headimages.add(img);
			} catch (IOException e) { // 失敗したら、その旨表示。
				System.err.println("Can't read image: " + file);
			}
		}
	}

	//bodyフォルダに格納されている体の画像を順次リストに登録していくためのメソッド
	public void addImage()  {
		File iconDir = new File(dirName2); // dirName 相当のFileを作成し
		if(!iconDir.exists() || !iconDir.isDirectory()) return;
			// directory が存在した場合、その各ファイルに対して
			for(File file: iconDir.listFiles()) {
				try { // 画像としての取り込みを計り
					BufferedImage img = ImageIO.read(file);
					if(img != null) // うまく言ったものを images に追加
						bodyimages.add(img);
				} catch (IOException e) { // 失敗したら、その旨表示。
					System.err.println("Can't read image: " + file);
				}
			}
	}

	public int getPlayerX(){
		return px - (plSize+mag*psize)/2;
	}

	public int getPlayerY(){
		return py - (plSize+mag*psize)/2;
	}

	public int getPlayerDx1(){
		return dpx1;
	}

	public int getPlayerDy1(){
		return dpy1;
	}

	public int getPlayerDx0(){
		return dpx0;
	}

	public int getPlayerDy0(){
		return dpy0;
	}

	public int getNowSize(){
		return psize;
	}

	public int getInitNum(){
		return initnum;
	}

	public ArrayList<Body> getBodies(){
		return bodies;
	}

	//画像サイズの基準を増加させつつ、新しい体のパーツ画像をリストに登録するメソッド
	public void grow(){
		eatnum++;
		System.out.println(eatnum);
		if(eatnum%10 == 0){
			addImage();
			if(bodies.size() == 0)
				bodies.add(new Body(this));
			else if(bodies.size() > 0)
				bodies.add(new Body(bodies.get(bodies.size()-1)));
		}

		if(eatnum%15 == 0){
			psize++;
			if(bodies.size() > 0){
				for(int i=0; i<bodies.size()-1; i++)
					bodies.get(i).bodyImageZoom();
			}
		}
	}

	//画像サイズの基準を減少させるメソッド
	public void reductionImage(){
		if(psize > -15)				//画像の初期サイズが80, 増加減少分を5に設定しているため画像サイズが０以下にならないように条件分を設定
			psize--;
	}

	//進行方向に合わせてプレイヤーの座標を更新するメソッド
	public void continueMove(Map map, ArrayList<ColoredPoint> cplist, ArrayList<Body> bodies){
		map.continueMove(this, cplist, bodies);
	}

	public void move(Point p){
		if(p.x > px + plSize+mag*psize && p.y > py && p.y < py + plSize+mag*psize){
			dpx0 = dpx1;
			dpy0 = dpy1;
			dpx1 = 10;
			dpy1 = 0;
		}
		else if(p.x < px && p.y > py && p.y < py + plSize+mag*psize){
			dpx0 = dpx1;
			dpy0 = dpy1;
			dpx1 = -10;
			dpy1 = 0;
		}
		else if(p.y > py + plSize+mag*psize && p.x > px && p.x < px + plSize+mag*psize){
			dpx0 = dpx1;
			dpy0 = dpy1;
			dpx1 = 0;
			dpy1 = 10;
		}
		else if(p.y < py && p.x > px && p.x < px + plSize+mag*psize){
			dpx0 = dpx1;
			dpy0 = dpy1;
			dpx1 = 0;
			dpy1 = -10;
		}
		else if(0 < Math.atan2(p.y-(py+(plSize+mag*psize)/2), p.x-(px+(plSize+mag*psize)/2))*180/Math.PI && Math.atan2(p.y-(py+(plSize+mag*psize)/2), p.x-(px+(plSize+mag*psize)/2))*180/Math.PI < 30){
			dpx0 = dpx1;
			dpy0 = dpy1;
			dpx1 = 8;
			dpy1 = 3;
		}
		else if(30 <= Math.atan2(p.y-(py+(plSize+mag*psize)/2), p.x-(px+(plSize+mag*psize)/2))*180/Math.PI && Math.atan2(p.y-(py+(plSize+mag*psize)/2), p.x-(px+(plSize+mag*psize)/2))*180/Math.PI < 60){
			dpx0 = dpx1;
			dpy0 = dpy1;
			dpx1 = 7;
			dpy1 = 7;
		}
		else if(60 <= Math.atan2(p.y-(py+(plSize+mag*psize)/2), p.x-(px+(plSize+mag*psize)/2))*180/Math.PI && Math.atan2(p.y-(py+(plSize+mag*psize)/2), p.x-(px+(plSize+mag*psize)/2))*180/Math.PI < 90){
			dpx0 = dpx1;
			dpy0 = dpy1;
			dpx1 = 3;
			dpy1 = 8;
		}
		else if(90 < Math.atan2(p.y-(py+(plSize+mag*psize)/2), p.x-(px+(plSize+mag*psize)/2))*180/Math.PI && Math.atan2(p.y-(py+(plSize+mag*psize)/2), p.x-(px+(plSize+mag*psize)/2))*180/Math.PI < 120){
			dpx0 = dpx1;
			dpy0 = dpy1;
			dpx1 = -3;
			dpy1 = 8;
		}
		else if(120 <= Math.atan2(p.y-(py+(plSize+mag*psize)/2), p.x-(px+(plSize+mag*psize)/2))*180/Math.PI && Math.atan2(p.y-(py+(plSize+mag*psize)/2), p.x-(px+(plSize+mag*psize)/2))*180/Math.PI < 150){
			dpx0 = dpx1;
			dpy0 = dpy1;
			dpx1 = -7;
			dpy1 = 7;
		}
		else if(150 <= Math.atan2(p.y-(py+(plSize+mag*psize)/2), p.x-(px+(plSize+mag*psize)/2))*180/Math.PI && Math.atan2(p.y-(py+(plSize+mag*psize)/2), p.x-(px+(plSize+mag*psize)/2))*180/Math.PI < 180){
			dpx0 = dpx1;
			dpy0 = dpy1;
			dpx1 = -8;
			dpy1 = 3;
		}
		else if(-180 < Math.atan2(p.y-(py+(plSize+mag*psize)/2), p.x-(px+(plSize+mag*psize)/2))*180/Math.PI && Math.atan2(p.y-(py+(plSize+mag*psize)/2), p.x-(px+(plSize+mag*psize)/2))*180/Math.PI < -150){
			dpx0 = dpx1;
			dpy0 = dpy1;
			dpx1 = -8;
			dpy1 = -3;
		}
		else if(-150 <= Math.atan2(p.y-(py+(plSize+mag*psize)/2), p.x-(px+(plSize+mag*psize)/2))*180/Math.PI && Math.atan2(p.y-(py+(plSize+mag*psize)/2), p.x-(px+(plSize+mag*psize)/2))*180/Math.PI < -120){
			dpx0 = dpx1;
			dpy0 = dpy1;
			dpx1 = -7;
			dpy1 = -7;
		}
		else if(-120 <= Math.atan2(p.y-(py+(plSize+mag*psize)/2), p.x-(px+(plSize+mag*psize)/2))*180/Math.PI && Math.atan2(p.y-(py+(plSize+mag*psize)/2), p.x-(px+(plSize+mag*psize)/2))*180/Math.PI < -90){
			dpx0 = dpx1;
			dpy0 = dpy1;
			dpx1 = -3;
			dpy1 = -8;
		}
		else if(-90 < Math.atan2(p.y-(py+(plSize+mag*psize)/2), p.x-(px+(plSize+mag*psize)/2))*180/Math.PI && Math.atan2(p.y-(py+(plSize+mag*psize)/2), p.x-(px+(plSize+mag*psize)/2))*180/Math.PI < -60){
			dpx0 = dpx1;
			dpy0 = dpy1;
			dpx1 = 3;
			dpy1 = -8;
		}
		else if(-60 <= Math.atan2(p.y-(py+(plSize+mag*psize)/2), p.x-(px+(plSize+mag*psize)/2))*180/Math.PI && Math.atan2(p.y-(py+(plSize+mag*psize)/2), p.x-(px+(plSize+mag*psize)/2))*180/Math.PI < -30){
			dpx0 = dpx1;
			dpy0 = dpy1;
			dpx1 = 7;
			dpy1 = -7;
		}
		else if(-30 <= Math.atan2(p.y-(py+(plSize+mag*psize)/2), p.x-(px+(plSize+mag*psize)/2))*180/Math.PI && Math.atan2(p.y-(py+(plSize+mag*psize)/2), p.x-(px+(plSize+mag*psize)/2))*180/Math.PI < 0){
			dpx0 = dpx1;
			dpy0 = dpy1;
			dpx1 = 8;
			dpy1 = -3;
		}

		if(bodies.size() >= 1)
			bodies.get(0).headBodyVector(this);		//体の先頭(頭の次)は頭にしたがって動く

		if(bodies.size() > 1){
			for(int i=0; i<bodies.size()-1; i++)	//体の先頭よりあとは一つ手前の体の動きにしたがって動く
				bodies.get(i+1).bodyVector(bodies.get(i));
		}
	}

}
