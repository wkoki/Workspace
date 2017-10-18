package warmGame;

public class Timer extends Thread {

	private int count=0;

	/**コンストラクタ*/
	public Timer(int msec) {

		//super(); は省略
		count = msec;
	}

	/**ループ処理のスレッド*/
	public void run() {

		while (count > 0) {

			try {
				Thread.sleep(100);   //0.1sec
			}
			catch (InterruptedException ex) {

				System.err.println(ex);
			}
			count--;
		}
	}

	/**残り時間の取得*/
	public int getTime() {
		return count;
	}

	/**mainメソッド*/
	public static void main(String[] args) {

		Timer timer = new Timer(10);
		timer.start();

		while (timer.getTime() > 0) {
			System.out.println("残り" + (double)(timer.getTime()/10) + "秒");
		}
	}
}
