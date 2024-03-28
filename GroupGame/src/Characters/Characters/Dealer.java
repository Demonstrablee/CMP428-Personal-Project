package Characters.Characters;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;


import Characters.Sprite;


public class Dealer extends Sprite {
	static String pose[] = new String[] {"LAUGH"};

	public Dealer(String type, int x, int y, int h, int w, int scale) {
		super(type, "Baccano", pose, 2, 0, "png", x, y, w, h, scale);

	}

	public void draw(Graphics pen) {
		super.draw(pen);
	}

}
