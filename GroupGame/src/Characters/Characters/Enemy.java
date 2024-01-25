package Characters.Characters;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.util.Random;

import Characters.Sprite;
import Objects.Camera;
import Objects.Rect;


public class Enemy extends Sprite{
	static String pose [][] = new String[][] {{"LAUGH"},{"IDLE"}};
    static File [] carImages = new File("GroupGame/src/images/LTD/cars").listFiles(); // all possible cars in the game
	// CARS Are only idle and dealer has laugh 

	 
	
	public Enemy(String type,String gameName, int x, int y, int h, int w, int scale) {
		super(type,gameName, pose[0] ,2,0,"png",x, y, w, h,scale);
		c = Color.GRAY;

	}


 
	
	
	
	public void draw(Graphics pen) {
		
		super.draw(pen);
	}

	public static class InnerEnemy extends Rect{
		static Random genRand = new Random();
		static Image [] carPic ; // pics for the cars
		static Enemy.InnerEnemy [] enemyCars; // coordinates
		public InnerEnemy(int x, int y, int w, int h){
				super(x, y, w, h);
				
			}

			public static InnerEnemy[] genCarEnemys(int numEnemyCars){
				carPic = new Image[numEnemyCars];
				enemyCars = new Enemy.InnerEnemy[numEnemyCars]; // just to start with 20 emeny cars
	 
				for (int i = 0; i < numEnemyCars; i++){
					System.out.println(carImages[genRand.nextInt(carImages.length)]);
					carPic[i] = Toolkit.getDefaultToolkit().getImage(carImages[genRand.nextInt(carImages.length)] + "");
					enemyCars[i] = new Enemy.InnerEnemy(950,630,50,90);
				}

				return enemyCars;
		}

		public void draw(Graphics pen){
			Image player = Toolkit.getDefaultToolkit().getImage("GroupGame/src/images/LTD/cars/blacklambo.png");
		 

			for(int k = 0; k < carPic.length; ++k){

				super.draw(pen);
				pen.drawImage(carPic[k], (int)(enemyCars[k].x - Camera.x), (int)(enemyCars[k].y - Camera.y), null);
			}
		}
			
	}
}
