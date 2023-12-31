package Characters;
import Levels.Managers.GameWindow2;
/**
    The Animation class manages a series of images (frames) and
    the amount of time to display each frame.
*/
import java.awt.*;

public class Animation
{
	private Image[] image;
	
	private int current = 0;
	
	private int duration;
	private int delay;
	
	private int start = 0;
	
	public Animation(String name,String pose, int count, int start, int duration, String type)
	{
		this.start    = start;
		
		this.duration = duration;
		delay         = duration;
		
		
		image = new Image[count];
		

		for(int i = 0; i < count; i++)
         
		{
			if (GameWindow2.getOs().contains("Mac")){ // if on mac
            	System.out.println("GroupGame/src/images/"+ name +"/" + name + "_" + pose + "/"+ name +"_" +pose +"_"+ i + "." + type);
				image[i] = Toolkit.getDefaultToolkit().getImage("GroupGame/src/images/"+ name +"/" + name + "_" + pose + "/"+ name +"_" +pose +"_"+ i + "." + type);}
			else{ // if you are on windows
				//System.out.println("GroupGame\\srcimages\\"+ name +"\\" + name + "_" + pose + "\\"+ name +"_" +pose +"_"+ i + "." + type);
				image[i] = Toolkit.getDefaultToolkit().getImage("GroupGame\\srcimages\\"+ name +"\\" + name + "_" + pose + "\\"+ name +"_" +pose +"_"+ i + "." + type);
		
			}
		}
	}
	
	
	public Image getStaticImage()
	{
		return image[0];
	}

	public Image getCurrentImage()
	{
		delay--;
		
		if(delay == 0)
		{
			current++;
			
			if(current == image.length)  current = start;
			
			delay = duration;
		}
		
		return image[current];
	}
	

}