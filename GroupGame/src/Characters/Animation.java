package Characters;
import java.awt.*;
/**
    The Animation class manages a series of images (frames) and
    the amount of time to display each frame.
*/
public class Animation {
	/** Array that store images in array for animation */
	private Image[] image;

	private int current = 0; // current image

	/** how long the animation is */
	private int duration; 

	/**time between showing each image*/
	private int delay;
	/** image to start playing animation at */
	private int start = 0;

	public Animation(String name, String gameName, String pose, int numFrames, int start, int duration, String type) {
		this.start = start;

		this.duration = duration; 
		this.delay = duration;

		image = new Image[numFrames];

		// add the images to the array
		for (int i = 0; i < numFrames; i++) {  

			// System.out.println("GroupGame/src/images/"+ name +"/" + name + "_" + pose +
			// "/"+ name +"_" +pose +"_"+ i + "." + type);
			image[i] = Toolkit.getDefaultToolkit().getImage("GroupGame/src/images/" + gameName + "/" + name + "/" + name
					+ "_" + pose + "/" + name + "_" + pose + "_" + i + "." + type);

		}

	}

	public Image getStaticImage() {
		return image[0];
	}

	public Image getCurrentImage() // playing image
	{
		delay--; // decrement delay so that we can get to zero 

		if (delay == 0) { //once we get to zero
			current++; // change the current image

			if (current == image.length) // if we get to the end of all the images
				current = start; // go back to the stating image

			delay = duration; // restart the delay
		}

		return image[current]; // return the current image to display
	}

}