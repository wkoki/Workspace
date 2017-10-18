package warmGame;

/*
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
*/
public class Body {

	private int bx, by, dbx1, dby1, dbx0, dby0, bsize;
	static final int plSize = 20;
	static final int mag1 = 1;
	static final int mag2 = 30;

	public Body(Player pl){
		bx = pl.getPlayerX() - pl.getPlayerDx1()*(plSize+mag1*pl.getNowSize())/mag2;
		by = pl.getPlayerY() - pl.getPlayerDy1()*(plSize+mag1*pl.getNowSize())/mag2;
		dbx1 = pl.getPlayerDx1();
		dby1 = pl.getPlayerDy1();
		bsize = pl.getNowSize();
	}

	public Body(Body body){
		bx = body.getBodyX() - body.getBodyDx1()*(plSize+mag1*body.getBodySize())/mag2;
		by = body.getBodyY() - body.getBodyDy1()*(plSize+mag1*body.getBodySize())/mag2;
		dbx1 = body.getBodyDx1();
		dby1 = body.getBodyDy1();
		bsize = body.getBodySize();
	}

	public int getBodyX(){
		return bx;
	}

	public int getBodyY(){
		return by;
	}

	public int getBodyDx1(){
		return dbx1;
	}

	public int getBodyDy1(){
		return dby1;
	}

	public int getBodyDx0(){
		return dbx0;
	}

	public int getBodyDy0(){
		return dby0;
	}

	public int getBodySize(){
		return bsize;
	}

	public void bodyImageZoom(){
		this.bsize++;
	}

	public void headBodyVector(Player pl){
		this.dbx0 = this.dbx1;					//自身の古いベクトルを保存
		this.dby0 = this.dby1;

		this.dbx1 = pl.getPlayerDx0();			//ベクトルを自分より前のパーツの古いベクトルに更新
		this.dby1 = pl.getPlayerDy0();
	}

	public void bodyVector(Body body){
		this.dbx0 = this.dbx1;					//自身の古いベクトルを保存
		this.dby0 = this.dby1;

		this.dbx1 = body.dbx0;			//ベクトルを自分より前のパーツの古いベクトルに更新
		this.dby1 = body.dby0;
	}

	public void headBodyMove(Player pl){
		this.bx = pl.getPlayerX() - pl.getPlayerDx1()*(plSize+mag1*pl.getNowSize())/mag2;
		this.by = pl.getPlayerY() - pl.getPlayerDy1()*(plSize+mag1*pl.getNowSize())/mag2;

		this.dbx0 = this.dbx1;										//自身の古いベクトルを保持
		this.dby0 = this.dby1;

		this.dbx1 = pl.getPlayerDx1();
		this.dby1 = pl.getPlayerDy1();

		this.bsize = pl.getNowSize();
	}

	public void bodyMove(Body body){
		this.bx = body.getBodyX() - body.getBodyDx0()*(plSize+mag1*body.getBodySize())/mag2;			//ベクトルにしたがって進む
		this.by = body.getBodyY() - body.getBodyDy0()*(plSize+mag1*body.getBodySize())/mag2;

		this.dbx0 = this.dbx1;										//自身の古いベクトルを保持
		this.dby0 = this.dby1;

		this.dbx1 = body.getBodyDx0();							//ベクトルを自分より前のパーツの古いベクトルに更新
		this.dby1 = body.getBodyDy0();

		this.bsize = body.getBodySize();
	}
}
