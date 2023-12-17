package Characters.Characters;



import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;

import Objects.Camera;
import Objects.Lookup;
import Objects.Rect;

public class PlayerCharacter{
    // THESE POSES MUST BE IN THE CORRECT ORDER TO WORK IN THE ANIMATION CLASS {UP, DOWN, LEFT, RIGHT, IDLE} YOU Can name them whatever but the order contributes to what animation is played
    static String [] pose = new String[] {"IDLE"}; 
    Image player = Toolkit.getDefaultToolkit().getImage("GroupGame/src/images/YELLOWTAXI.png");
    
    int delta_x;
    int delta_y; // how much to adust the affine transformation translation by

    int x;
    int y;

    int [] player_x = new int []{40,-40,-40,40};
    int [] player_y = new int [] {-20,-20,20,20};

    int [] x_points  = new int[4];
    int [] y_points  = new int[4];
    int rotation_x;
    int rotation_y;
    int x_;
    int y_;


    int originX;
    int originY;
    AffineTransform playerRotate = new AffineTransform();


    // for rotation
    protected int A = 0;
	double cosA = 1;
	double sinA = 0;

    public PlayerCharacter(int x, int y, int w, int h) {


        this.delta_x = x;
        this.delta_y = y;
        originX = x;
        originY = y;


        //playerRotate.rotate(Math.toRadians(90));
        playerRotate.translate(200, 300); // place the player at the start
        //playerRotate.rotate(A);
   
    }

    public void moveForward(int d) // delta change 
	{
        x += d * cosA;
		y += d * sinA;

		delta_x = (int) (d * cosA);
		delta_y = (int) (d * sinA);
        
        playerRotate.translate(delta_x, 0);
        System.out.println(delta_x);
        System.out.println(delta_y);
	}
    public void turnLeft(int dA)
	{
		A -= dA; // in degrees
		
		if(A < 0)  A += 360;
       //playerRotate.translate(Camera.x, Camera.y);
		playerRotate.rotate(Math.toRadians(dA)); // turn by delta a degrees from where you are
		//playerRotate.translate(-Camera.x, -Camera.y);

        cosA = Lookup.cos[A];
		sinA = Lookup.sin[A];
	}
	
	public void turnRight(int dA)
	{
		A += dA;
		
		if(A > 359)   A -= 360;
        //playerRotate.translate(7, 7);
		playerRotate.rotate(Math.toRadians(-dA));

		cosA = Lookup.cos[A];
		sinA = Lookup.sin[A];
	}

    public void returnToOrigin(){

        // A = 0;
        // int dx = x-originX;
        // int dy = x-originY;
        // playerRotate.translate(originX-dx, originX-dy); // return to 0, 0


    }


    public void draw(Graphics pen){
            //super.draw(pen);
        
        Graphics2D pen2D = (Graphics2D) pen;// cast into 2d pen
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
        pen.setColor(Color.GREEN);
        pen.drawPolygon(y_points, x_points, 4);
        //pen2D.drawImage(player, x, y, null);
 
        pen2D.drawImage(player,playerRotate, null);
        
       

    }

    public int getX() {
        return 0;
    }


    
   
    

    
}
