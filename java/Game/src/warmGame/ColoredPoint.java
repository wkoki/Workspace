package warmGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
//import java.awt.geom.Area;

public class ColoredPoint extends Point {
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
	Color color;
    Polygon poly;
    static final int normalSize = 4;
    static final int plSize = 20;
    static final int mag1 = 1;

    /**
     * コンストラクタ
     * @param c 色
     * @param p 座標
     */
    public ColoredPoint(Color c, Point p) {
        super(p);
        this.color = c;
    }

    public ColoredPoint(Map map){
    	int x, y;
    	int red, green, blue;

    	//ランダムに座標を生成
        x = (int)(Math.random() * 10000) + map.getMapX();
        y = (int)(Math.random() * 10000) + map.getMapY();

        //ランダムにカラーを生成
        red = (int)(Math.random() * 255);
        green = (int)(Math.random() * 255);
        blue = (int)(Math.random() * 255);

        this.x = x;
        this.y = y;
        this.color = new Color(red, green, blue);
        /*
    	Point p = new Point(x, y);
    	Color c = new Color(red, green, blue);
    	*/
    }

    /**
     * 表示用メソッド
     * @param g
     * @param selected
     */
    void paint(Graphics g, boolean selected) {
        g.setColor(selected? brightColor(color): color);
        g.fillOval(this.x-normalSize, this.y-normalSize, normalSize*2, normalSize*2);
		g.setColor(Color.WHITE);
		g.drawOval(this.x-normalSize, this.y-normalSize, normalSize*2, normalSize*2);

    }

    /**
     * toString() つまりこのクラスを文字表現するときの挙動を再定義
     */
    @Override
    public String toString() {
        return "CP(" + super.toString() +", " + color +")";
    }

    /**
     * 選択時の色生成用メソッド
     * @param c もとの色
     * @return 明るくした色（白との間）
     */
    public static Color brightColor(Color c) {
        return new Color(c.getRed()/2+128, c.getGreen()/2+128, c.getBlue()/2+128);
    }

    //プレイヤーと点の重なりの判定を実装予定
    public boolean overlapWith(Player pl){
    	if((pl.getPlayerX() + plSize+mag1*pl.getNowSize() > this.x - normalSize) && (pl.getPlayerX() < this.x + normalSize)){
    		if((pl.getPlayerY() + plSize+mag1*pl.getNowSize() > this.y - normalSize) && (pl.getPlayerY() < this.y + normalSize))
    			return true;
    	}
    	return false;
    }

}
