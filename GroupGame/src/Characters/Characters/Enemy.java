package Characters.Characters;

import java.awt.Color;
import java.awt.Graphics;

import Characters.Sprite;


public class Enemy extends Sprite{
	static String pose [] = new String[] {"IDLE","ATTACKRT","ATTACKLT","RT","LT"};
	
	public Enemy(String type, int x, int y, int h, int w) {
		super(type,pose,6,0,"png",x, y, w, h);
		c = Color.GRAY;

	}
	
	
	public void draw(Graphics pen) {
		
		super.draw(pen);
	}

}
