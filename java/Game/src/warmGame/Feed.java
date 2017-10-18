package warmGame;
import java.awt.*;


public class Feed extends Frame{
  /**
	 *
	 */
	private static final long serialVersionUID = 1L;

public static final void main(final String[] args) throws InterruptedException{
    //ウィンドウ生成
    Frame app=new Frame();
    //タイトル設定
    app.setTitle("Title");
    //ウィンドウサイズ設定(タイトルや枠も含んだサイズ)
    app.setSize(2000, 800);
    //キャンバスを配置
    fstCanvas cvs=new fstCanvas();
    app.add(cvs);
    //ウィンドウ表示
    app.setVisible(true);
    //無限ループ
    while (true){
      //画面再描画
      cvs.repaint();
     Thread.sleep(100);
    }
  }

  static class fstCanvas extends Canvas{
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public void paint(Graphics g){
      //座標用変数
      int x, y;
      //カラー用変数
      int red, green, blue;

      //ランダムに座標を生成
      x = (int)(Math.random() * 2000);
      y = (int)(Math.random() * 800);

      //ランダムにカラーを生成
      red = (int)(Math.random() * 255);
      green = (int)(Math.random() * 255);
      blue = (int)(Math.random() * 255);

      //色を変える
      g.setColor(new Color(red, green, blue));

      //点を描画する
      g.fillArc(x, y, 15, 15, 0, 360);
    }

    //updateメソッドを乗っ取って，画面クリアを防ぐ
    public void update(Graphics g){
        paint(g);
    }
  }
}