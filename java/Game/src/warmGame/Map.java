package warmGame;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Map {

	private static final String dirName = "map";

	//プレイヤーの座標と大きさの基準
	private int mapx, mapy;

	//プレイヤーの進行方向
	//private int direction;

	public Map(){
		mapx = -4960;
		mapy = -4960;
		mapSetup();
	}

	ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
	private void mapSetup()  {
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

	public int getMapX(){
		return mapx;
	}

	public int getMapY(){
		return mapy;
	}


	//進行方向に合わせてプレイヤーの座標を更新するメソッド
	public void continueMove(Player pl, ArrayList<ColoredPoint> cplist, ArrayList<Body> bodies){

		switch(pl.getPlayerDx1()){
			case 0:	if(pl.getPlayerDy1() == 0) break;
					else if(pl.getPlayerDy1() == 10){
						if(mapy > -9200){
							mapy = mapy-10;
							for(ColoredPoint cp: cplist){
								cp.y = cp.y-10;
							}
						}
					}
					else if(pl.getPlayerDy1() == -10){
						if(mapy < 0){
							mapy = mapy+10;
							for(ColoredPoint cp: cplist){
								cp.y = cp.y+10;
							}
						}
					}
					break;

			case 3: if(pl.getPlayerDy1() == 8){
	 					if(mapx > -9200 && mapy > -9200){
	 						mapx = mapx-3;
	 						mapy = mapy-8;
	 						for(ColoredPoint cp: cplist){
	 							cp.x = cp.x-3;
	 							cp.y = cp.y-8;
	 						}
	 					}
	 				}
	 				else if(pl.getPlayerDy1() == -8){
	 					if(mapx > -9200 && mapy < 0){
	 						mapx = mapx-3;
	 						mapy = mapy+8;
	 						for(ColoredPoint cp: cplist){
	 							cp.x = cp.x-3;
	 							cp.y = cp.y+8;
	 						}
	 					}
	 				}
					break;

			case -3: if(pl.getPlayerDy1() == 8){
	 					if(mapx < 0 && mapy > -9200){
	 						mapx = mapx+3;
	 						mapy = mapy-8;
	 						for(ColoredPoint cp: cplist){
	 							cp.x = cp.x+3;
	 							cp.y = cp.y-8;
	 						}
	 					}
	 				}
	 				else if(pl.getPlayerDy1() == -8){
	 					if(mapx < 0 && mapy < 0){
	 						mapx = mapx+3;
	 						mapy = mapy+8;
	 						for(ColoredPoint cp: cplist){
	 							cp.x = cp.x+3;
	 							cp.y = cp.y+8;
	 						}
	 					}
	 				}
					break;

			case 7: if(pl.getPlayerDy1() == 7){
			 	 		if(mapx > -9200 && mapy > -9200){
			 	 			mapx = mapx-7;
			 	 			mapy = mapy-7;
			 	 			for(ColoredPoint cp: cplist){
			 	 				cp.x = cp.x-7;
			 	 				cp.y = cp.y-7;
			 	 			}
			 	 		}
			 		}
			 		else if(pl.getPlayerDy1() == -7){
			 			if(mapx > -9200 && mapy < 0){
			 				mapx = mapx-7;
			 				mapy = mapy+7;
			 				for(ColoredPoint cp: cplist){
			 					cp.x = cp.x-7;
			 					cp.y = cp.y+7;
			 				}
			 			}
			 		}
			 		break;

			case -7: if(pl.getPlayerDy1() == 7){
			 	 		if(mapx < 0 && mapy > -9200){
			 	 			mapx = mapx+7;
			 	 			mapy = mapy-7;
			 	 			for(ColoredPoint cp: cplist){
			 	 				cp.x = cp.x+7;
			 	 				cp.y = cp.y-7;
			 	 			}
			 	 		}
					}
					else if(pl.getPlayerDy1() == -7){
						if(mapx < 0 && mapy < 0){
							mapx = mapx+7;
							mapy = mapy+7;
							for(ColoredPoint cp: cplist){
								cp.x =cp.x+7;
								cp.y =cp.y+7;
							}
						}
					}
			 		break;

			case 8: if(pl.getPlayerDy1() == 3){
	 	 				if(mapx > -9200 && mapy > -9200){
	 	 					mapx = mapx-8;
	 	 					mapy = mapy-3;
	 	 					for(ColoredPoint cp: cplist){
	 	 						cp.x = cp.x-8;
	 	 						cp.y = cp.y-3;
	 	 					}
	 	 				}
					}
					else if(pl.getPlayerDy1() == -3){
						if(mapx > -9200 && mapy < 0){
							mapx = mapx-8;
							mapy = mapy+3;
							for(ColoredPoint cp: cplist){
								cp.x =cp.x-8;
								cp.y =cp.y+3;
							}
						}
					}
	 				break;

			case -8: if(pl.getPlayerDy1() == 3){
						if(mapx < 0 && mapy > -9200){
							mapx = mapx+8;
							mapy = mapy-3;
	 	 					for(ColoredPoint cp: cplist){
	 	 						cp.x = cp.x+8;
	 	 						cp.y = cp.y-3;
	 	 					}
	 	 				}
					}
					else if(pl.getPlayerDy1() == -3){
						if(mapx < 0 && mapy < 0){
							mapx = mapx+8;
							mapy = mapy+3;
							for(ColoredPoint cp: cplist){
								cp.x =cp.x+8;
								cp.y =cp.y+3;
							}
						}
					}
	 				break;

			case 10: if(pl.getPlayerDy1() == 0){
						if(mapx > -9200){
							mapx = mapx-10;
							for(ColoredPoint cp: cplist){
								cp.x = cp.x-10;
							}
						}
					}
	 				break;

			case -10: if(pl.getPlayerDy1() == 0){
	 	 				if(mapx < 0){
	 	 					mapx = mapx+10;
	 	 					for(ColoredPoint cp: cplist){
	 	 						cp.x = cp.x+10;
	 	 					}
	 	 				}
					}
	 				break;

			default: break;
		}
		try {
			if(bodies.size() > 0){
				bodies.get(0).headBodyMove(pl);

				for(int i=0; i<bodies.size()-1; i++){
					bodies.get(i+1).bodyMove(bodies.get(i));
						Thread.sleep(1);
					}
				}
			}
		catch (InterruptedException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

	}
}
