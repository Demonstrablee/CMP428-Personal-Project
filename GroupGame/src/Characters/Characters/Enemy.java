package Characters.Characters;

import java.awt.Color;
import java.awt.Graphics;

import Characters.Sprite;


public class Enemy extends Sprite{
	static String pose [] = new String[] {"LAUGH"};

	// CARS Are only idle and dealer has two
	
	public Enemy(String type, int x, int y, int h, int w, int scale) {
		super(type,pose,2,0,"png",x, y, w, h,scale);
		c = Color.GRAY;

	}
	
	
	public void draw(Graphics pen) {
		
		super.draw(pen);
	}

}
