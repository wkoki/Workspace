package warmGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class GamePanel extends JPanel implements Runnable{
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
	JButton btn1 = new JButton("StartPanelに移動");
	JButton btn2 = new JButton("⬅︎");
	JButton btn3 = new JButton("➡︎︎");
	JButton btn4 = new JButton("⬆︎");
	JButton btn5 = new JButton("⬇︎");
	JButton btn6 = new JButton("大きなるで");
	JButton btn7 = new JButton("小さなるで");
    MainFrame mframe;
    GamePanel gpanel;
    String str;

    Map map = new Map();
    Button button = new Button();

    ArrayList<Point> points = new ArrayList<Point>();
    ArrayList<ColoredPoint> cplist = new ArrayList<ColoredPoint>();
	ColoredPoint cp;
    int cpindex = 0;
    int rmvindex = -1;
    static final Color[] colors = new Color[] { Color.BLUE, Color.RED, Color.GREEN };

    //Point p = new Point(100, 100);
    //ColoredPoint cp = new ColoredPoint(Color.RED, p);

    Timer timer;
    int time, tx, ty, maxtime;
    private boolean gameFlag;				//ゲーム進行フラグ

    Player pl = new Player();

    static final int plSize = 20;
    static final int mag = 1;       //大きくなる時の倍率的なやつ

    public GamePanel(MainFrame m,String s){
        mframe = m;
        str = s;
        this.setName("gpanel");
        this.setLayout(null);
        this.setSize(800, 800);

        //ここから先はGamePanelの文字配置とボタン配置
        JLabel paneltitle = new JLabel("これは"+getClass().getCanonicalName()+"クラスのパネルです");
        paneltitle.setBounds(0, 5, 400, 40);
        this.add(paneltitle);

        btn1.setBounds(325, 700, 150, 40);
        btn1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                gameFlag = false;
            }
        });

    	this.add(btn1);

        //gameStart();
    }

    public void gameStart(){
    	gameInit();									//変数の初期化
		timer = new Timer(3000);						//タイマーの設定（1０秒)
		new Thread(this).start();					//ゲーム開始
		timer.start();								//秒タイマー開始
		pl.playerInit();
		//repaint();
    }

    public void gameInit(){
    	tx = 400;									//このへんの変数の初期化は特に意味はなし
    	ty = 10;
    	time = 3000;
    	maxtime = time;
    	gameFlag = true;							//ゲームオーバーならfalse

    	for(int i=0; i<5000; i++){
    		cp = new ColoredPoint(map);
    		cplist.add(cp);
    	}

    }

    /**メインの処理（スレッド処理）*/

	public void run() {

		while (gameFlag == true) {					//gameFlagがfalesになるまで繰り返し

			try {
				Thread.sleep(40);					//20ミリ秒スリープ

				time = timer.getTime();				//残り時間の取得

				//if(time%10 == 0)
				//	System.out.println("残り"+time/10+"秒");			//時間表示

				if(time==0) gameFlag = false;		//残り時間が０ならゲーム終了



				repaint();
			}
			catch (InterruptedException ex) {
				System.err.println(ex);
			}
			catch(ConcurrentModificationException ex){

			}
		}
		pc();
	}

	//進行方向にしたがってプレイヤーの画像を表示するメソッド(　repaint()時に自動的に使用される　)
	public void paint(Graphics g) {
		pl.continueMove(map, cplist, pl.getBodies());	//プレイヤーの挙動

		if(cplist != null){
			for(ColoredPoint cp: cplist){
				if(cp.overlapWith(pl)){
					pl.grow();
					rmvindex = cpindex;
					break;
				}
				cpindex++;
			}
		}
		cpindex = 0;

		if(rmvindex >= 0){
			cplist.remove(rmvindex);
			rmvindex = -1;
		}

		if((maxtime-time)%100 == 0){
			for(int i=0; i<100; i++){
				cp = new ColoredPoint(map);
	    		cplist.add(cp);
			}
		}

		super.paint(g);

		g.drawImage(map.images.get(0), map.getMapX(), map.getMapY(), 10000, 10000, null);

		if(cplist!= null) {
            for(ColoredPoint cp: cplist) {
                cp.paint(g, false);
            }
        }

		if(pl.getBodies().size() > 0){
			for(int i=pl.getBodies().size()-1; i>=0; i--){
				if(0 < pl.getBodies().get(i).getBodyX() && pl.getBodies().get(i).getBodyX() < 800 && 0 < pl.getBodies().get(i).getBodyY() && pl.getBodies().get(i).getBodyY() < 800)
					g.drawImage(pl.bodyimages.get(i), pl.getBodies().get(i).getBodyX(), pl.getBodies().get(i).getBodyY(), plSize+mag*pl.getNowSize(), plSize+mag*pl.getNowSize(), null);
			}
		}

		g.drawImage(pl.headimages.get(0), pl.getPlayerX(), pl.getPlayerY(), plSize+mag*pl.getNowSize(), plSize+mag*pl.getNowSize(), null);

		g.drawImage(button.images.get(4), 325, 700, 150, 40, null);
	}

	//スタート画面に戻るメソッド
    public void pc(){
        mframe.PanelChange((JPanel)this, mframe.PanelNames[0]);
    }

    public void processClick(Point p){
    	pl.move(p);
    }
}

