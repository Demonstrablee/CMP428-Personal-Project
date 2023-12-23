package Characters.Characters;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;

import Characters.Sprite;

import Objects.Lookup;
import Objects.Rect;


public class Player extends Rect{
    // THESE POSES MUST BE IN THE CORRECT ORDER TO WORK IN THE ANIMATION CLASS {UP, DOWN, LEFT, RIGHT, IDLE} YOU Can name them whatever but the order contributes to what animation is played
    static String [] pose = new String[] {"IDLE"}; 
    Image player = Toolkit.getDefaultToolkit().getImage("GroupGame/src/images/ORANGECAR/ORANGECAR_IDLE/ORANGECAR_IDLE_0.png");
    
    int delta_x;
    int delta_y; // how much to adust the affine transformation translation by

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


    int originX;
    int originY;
    AffineTransform playerRotate = new AffineTransform();


    // for rotation
    protected int A = 0;
	double cosA = 1;
	double sinA = 0;

    public Player(int x, int y, int w, int h) {
        //super(x + 200, y -300 , w, h);
        super(x, y, w, h);
       // super("ORANGECAR", pose, 1, 0, "png", x, y, w, h,0);

       
        this.delta_x = x ;
        this.delta_y = y ;
        originX = x;
        originY = y;


       
        playerRotate.translate(x, y); // place the player at the start
        //playerRotate.rotate(A);
   
    }

    public void moveForward(int d) // delta change 
	{
        x += d * cosA;
		y += d * sinA;

		delta_x = (int) (d * cosA);
		delta_y = (int) (d * sinA);
        
        playerRotate.translate(delta_x, delta_y);
        System.out.println(delta_x);
        System.out.println(delta_y);
	}

    public void turnLeft(int dA)
	{
		A -= dA; // in degrees
		
		if(A < 0)  A += 360;
 
		playerRotate.rotate(Math.toRadians(-dA)); // turn by delta a degrees from where you are
	    playerRotate.translate(-4, 0);
        

        cosA = Lookup.cos[A];
		sinA = Lookup.sin[A];
	}
	
	public void turnRight(int dA)
	{
		A += dA;
		
		if(A > 359)   A -= 360;

		playerRotate.rotate(Math.toRadians(dA));
        playerRotate.translate(4, 0);
        
		cosA = Lookup.cos[A];
		sinA = Lookup.sin[A];
	}

    public void returnToOrigin(){

        // A = 0;
        // int dx = x-originX;
        // int dy = x-originY;
        // playerRotate.translate(originX-dx, originX-dy); // return to 0, 0


    }

@Override
    public void draw(Graphics pen){
        super.draw(pen);
        
        Graphics2D pen2D = (Graphics2D) pen;// cast into 2d pen
       
        for (int i = 0; i < 4; i++){
        
            x_ = player_x[i];
            y_ = player_y[i];
            
            //FIRST ROTATE
            rotation_x = (int) ((x_ * cosA) - (y_ * sinA));
            rotation_y = (int) ((x_ * sinA) + (y_ * cosA));

            //THEN TRANSLATE
            // x_points[i] = rotation_x + x + 600;
            // y_points[i] = rotation_y + y + 250;

            x_points[i] = rotation_x + x;
            y_points[i] = rotation_y + y;

        }
        pen.setColor(Color.GREEN);
        pen.drawPolygon(x_points, y_points, 4);
        //pen2D.drawImage(player, x, y, null);
        //playerRotate.rotate(Math.toRadians(-3));
        //pen2D.drawRect(x, y, (int)w, (int)h);
        
        pen2D.drawImage(player,playerRotate, null);
        
       

    }



    
   
    

    
}
