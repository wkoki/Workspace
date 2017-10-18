package warmGame;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Button {
	private static final String dirName = "button";

	public Button(){
		buttonSetup();
	}

	ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
	private void buttonSetup()  {
		File iconDir = new File(dirName); // dirName 相当のFileを作成し
		if(!iconDir.exists() || !iconDir.isDirectory()) return;
		// directory が存在した場合、その各ファイルに対して
		for(File file: iconDir.listFiles()) {
			try { // 画像としての取り込みを計り
				BufferedImage img = ImageIO.read(file);
				if(img != null) // うまく言ったものを images に追加
					images.add(img);
			} catch (IOException e) { // 失敗したら、その旨表示。
				System.err.println("Can't read image: " + file);
			}
		}
	}
}
