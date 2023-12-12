package Characters.Characters;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;

import javax.swing.JPanel;

import Objects.Circle;
import Objects.Lookup;


public class PlayerCharacter{
    // THESE POSES MUST BE IN THE CORRECT ORDER TO WORK IN THE ANIMATION CLASS {UP, DOWN, LEFT, RIGHT, IDLE} YOU Can name them whatever but the order contributes to what animation is played
    static String [] pose = new String[] {"IDLE"}; 
    Image player = Toolkit.getDefaultToolkit().getImage("GroupGame/src/images/YELLOWTAXI.png");
    
    int x;
    int y; 
    int [] player_x = new int []{40,-40,-40,40};
    int [] player_y = new int [] {-20,-20,20,20};

    int [] x_points  = new int[4];
    int [] y_points  = new int[4];
    
    AffineTransform playerRotate = new AffineTransform();
    int x_;
    int y_;

    int rotation_x;
    int rotation_y;

    // for rotation
    protected int A = 0;
	double cosA = 1;
	double sinA = 0;

    public PlayerCharacter(int x, int y, int w, int h) {
     
        this.x = x;
        this.y = y;

        

        // adjust points rotate then 
         
       // super("YELLOWTAXI",pose,1,0,"png",x, y, w, h);
        //c = Color.RED;
    }

    public void moveForward(int d)
	{
		x += d * cosA;
		y += d * sinA;

        System.out.println(x);
        System.out.println(y);
	}
    public void turnLeft(int dA)
	{
		A -= dA;
		
		if(A < 0)  A += 360;
		
		cosA = Lookup.cos[A];
		sinA = Lookup.sin[A];
	}
	
	public void turnRight(int dA)
	{
		A += dA;
		
		if(A > 359)   A -= 360; 
		
		cosA = Lookup.cos[A];
		sinA = Lookup.sin[A];
	}


    public void draw(Graphics pen){
            //super.draw(pen);
        for (int i = 0; i < 4; i++){
        
            x_ = player_x[i];
            y_ = player_y[i];
            
            //FIRST ROTATE
            rotation_x = (int) ((x_ * cosA) - (y_ * sinA));
            rotation_y = (int) ((x_ * sinA) + (y_ * cosA));

            //THEN TRANSLATE
            x_points[i] = rotation_x + x;
            y_points[i] = rotation_y + y;
        }
        pen.drawPolygon(y_points, x_points, 4);
        
        playerRotate.translate(x, y); // place the player at the start
        playerRotate.rotate(A);
        
        Graphics2D pen2D = (Graphics2D) pen;
        // graphics.rotate(A,x,y);
        pen2D.drawImage(player,playerRotate, null);
        
        // graphics.rotate(-A,x,y); // RETURN THE PEN BACK TO OG

    }

    
   
    

    
}
