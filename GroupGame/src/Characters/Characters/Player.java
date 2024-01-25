package Characters.Characters;
import java.awt.Color;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;

import java.awt.Toolkit;
import java.awt.geom.AffineTransform;

import javax.swing.*;
import Objects.Lookup;
import Objects.Rect;




public class Player extends JComponent{
    // THESE POSES MUST BE IN THE CORRECT ORDER TO WORK IN THE ANIMATION CLASS {UP, DOWN, LEFT, RIGHT, IDLE} YOU Can name them whatever but the order contributes to what animation is played
    static String [] pose = new String[] {"IDLE"}; 
    Image player = Toolkit.getDefaultToolkit().getImage("GroupGame/src/images/LTD/ORANGECAR/ORANGECAR_IDLE/ORANGECAR_IDLE_0.png");
    int x;
    int y;

    int [] player_x = new int []{40,-40,-40,40};
    int [] player_y = new int [] {-20,-20,20,20};

    // int [] player_x = new int []{990,910,910,990};
    // int [] player_y = new int [] {610,610,650,650};

    int [] x_points  = new int[4];
    int [] y_points  = new int[4];
    int rotation_x;
    int rotation_y;
    int x_;
    int y_;

    // Polygon representation of the player
    Polygon playerPoly = new Polygon(x_points, x_points, 4);

    int originX;
    int originY;

    AffineTransform playerTransform = new AffineTransform();


    // for rotation
    protected int A = 0;
	double cosA = 1;
	double sinA = 0;

    public Player(int x, int y, int w, int h) {
        super();

        originX = x;
        originY = y;


        playerTransform.translate(x, y); // place the player at the start

   
    }
    public boolean overlaps(Rect r){

        
        //return playerPoly.intersects((int)r.getX() - Camera.x, (int)r.getY() - Camera. y,(int)r.getWidth(), (int)r.getHeight());
    return playerPoly.intersects((int)r.getX(), (int)r.getY(),(int)r.getWidth(), (int)r.getHeight());
  
    }

    public void moveForward(int d) // delta change 
	{
        x += (int)(d * cosA);
		y += (int)(d * sinA);

		// delta_x = (int) (d * cosA);
		// delta_y = (int) (d * sinA);

      
     
        playerTransform.translate(d, 0); // works when (1,0) (affine transform seems to do the vector stuf itself)
        
        // print 
        System.out.println("Player A: "+ A);   
        for(int i = 0;i <4; i++){
            System.out.println("Vetex of Polygon Player: "+ x_points[i] + ", "+ y_points[i]);
        }
	}
 

    public void turnLeft(int dA)
	{
		A -= dA; // in degrees for polygon
		
		if(A < 0)  A += 360;
 

        cosA = Lookup.cos[A];
		sinA = Lookup.sin[A];

        //Rotate Player image
		playerTransform.rotate(-Lookup.radianMeasureOf[dA]); // turn by delta a degrees from where you are
	   
        //Then Translate
       // playerRotate.translate(-4, 0);
       

        //print
         System.out.println("Player A: "+ A);
         for(int i = 0;i <4; i++){
            System.out.println("Vetex of Polygon Player: "+ x_points[i] + ", "+ y_points[i]);
        }
    
	}
	
	public void turnRight(int dA)
	{
		A += dA;
		
		if(A > 359)   A -= 360;

        cosA = Lookup.cos[A];
		sinA = Lookup.sin[A];

        //Rotate Player image
		playerTransform.rotate(Lookup.radianMeasureOf[dA]);

        //Then Translate
        //playerRotate.translate(4, 0);

        //print
        System.out.println("Player A: "+ A);
        for(int i = 0;i <4; i++){
            System.out.println("Vetex of Polygon Player: "+ x_points[i] + ", "+ y_points[i]);
        }
     
       
	 
	}


    /* Return the player to the origin  */
    public void returnToOrigin(){

        // A = 0;
        // int dx = x-originX;
        // int dy = x-originY;
        // playerRotate.translate(originX-dx, originX-dy); // return to 0, 0


    }


    public void draw(Graphics pen){
        //this.setColor(Color.GREEN);
        
        Graphics2D pen2D = (Graphics2D) pen; // cast into 2d pen (needed for affine transformation)
       
        for (int i = 0; i < 4; i++){
        
            x_ = player_x[i];
            y_ = player_y[i];
            
            //FIRST ROTATE
            rotation_x = (int) ((x_ * cosA) - (y_ * sinA));
            rotation_y = (int) ((x_ * sinA) + (y_ * cosA));

            //THEN TRANSLATE
            x_points[i] = (int) (rotation_x + x + 60);
            y_points[i] = (int)(rotation_y + y + 70);

            // x_points[i] = (int)(rotation_x + x);
            // y_points[i] = (int)(rotation_y + y);
             


        }
        // Point to the updated polygon points for the players Polygon 
        playerPoly.xpoints = x_points;
        playerPoly.ypoints = y_points;



        pen.setColor(Color.red);
       // pen.drawRect((int)x, (int)y, (int)w, (int)h);

        pen.setColor(Color.GREEN);

        // Draw players polygon
        pen.drawPolygon(playerPoly);
        //pen.drawPolygon(x_points, y_points, 4);

        // Draw image of the player
        pen2D.drawImage(player,playerTransform, null);
        
       

    }



    
   
    

    
}
