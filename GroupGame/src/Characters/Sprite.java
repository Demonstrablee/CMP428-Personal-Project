package Characters;
import java.awt.*;

import Objects.Camera;
import Objects.Rect;

public class Sprite extends Rect
{
	Animation[] animation; // ARRAY OF ANIMATIONS
	
	final static int IDLE = 0;
	final static int UP = 1;
	final static int DN = 2;
	final static int LT = 3;
	final static int RT = 4;
	
	int currPose = IDLE; // starting pose
	
	/** Scale for adjusting the size of the image*/
	double scale = 1.4; 
	
	/** Whether or not to play an animation or just show a still */
	public boolean moving = true;
	
	public Sprite(String name,String gameName, String[] pose, int numFrames, int start, String filetype, int x, int y, int w, int h, int scale)
	{
		super(x, y, w, h); // location of the sprite
		
		this.scale = scale > 0 ? scale :1.4; // if the scale you enter is greater than zero 

		animation = new Animation[pose.length];  // animations to play
		
		// create an animation for each pose
		for(int i = 0; i < pose.length; i ++)
		{
			animation[i] = new Animation(name, gameName, pose[i], numFrames, start,  18, filetype);
		}
	}
	/**Change the animation to move to the left */
	public void goLT(int dx)
	{
		currPose = LT;
		
		moving = true;
		
		vx = -dx;
	}
	/**Change the animation to move to the right */
	public void goRT(int dx)
	{
		currPose = RT;
		
		moving = true;
		
		vx = dx;
	}
	/**Change the animation to move to the Up */
	public void goUP(int dy)
	{
		currPose = UP;
		super.goUP(dy);
		
		moving = true;
		
		vy = -dy;
	}
	/**Change the animation to move to the down */
	public void goDN(int dy)
	{
		currPose = DN;
		
		moving = true;
		
		vy = dy;
	}
	
	/**Draw the character with the current animation */
	public void draw(Graphics pen)
	{	
		Image temp;
		
		if (moving == false) // if you hare not moving

			temp = animation[currPose].getStaticImage(); // let the idle animation play when the character isnt moving
		
		else
		
			temp = animation[currPose].getCurrentImage();
	

		// scale the image
		 w = scale * temp.getWidth(null);
		 h = scale * temp.getHeight(null);
			
		pen.drawImage(temp, (int)(x), (int)(y), (int)w, (int)h, null);
		
		pen.setColor(c); // use the color set for each character type
		//super.draw(pen);
        //pen.drawRect((int)(x), (int)(y), (int)w, (int)h);
		
	}
	
}