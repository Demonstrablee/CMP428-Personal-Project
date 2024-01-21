package Objects;

import java.awt.*;

public class Circle
{
	protected double x;
	protected double y;
	double r;
	
	protected int A = 0;
	
	double cosA = 1;
	double sinA = 0;
	
	
	
	public Circle(double x, double y, double r)
	{
		this.x = x;
		this.y = y;
		this.r = r;
	}
	
	public boolean hasCollidedWith(Line L)
	{
		double d = L.distanceTo(x, y);
		
		return d < r;
	}
	
	public void pushOutOf(Line L)
	{
		double d = L.distanceTo(x, y);
		
		double p = r - d;
		
		x += p * L.Nx;
		y += p * L.Ny;
		
	}
	
	public void moveBy(int dx, int dy)
	{
		x += dx;
		y += dy;
	}
	
	public void moveForward(int d) // move in the direction of a vector
	{
		x += d * cosA;
		y += d * sinA;
	}
	
	public void evade(double mx, double my)
	{
		turnAwayFrom(mx, my, 3);

		moveForward(5);
	}
	
	public void chase(double mx, double my)
	{
		turnToward(mx, my, 3);

		if((Math.abs(x - mx) > 60) || (Math.abs(y-my) > 60)) moveForward(5);
	}
	
	public void turnToward(double mx, double my, int dA)
	{
		double Nx = -sinA;
		double Ny =  cosA;
		
		double d = (mx - x) * Nx  + (my - y) * Ny;
		
		if (d > 6)  turnRight(dA);
		
		if (d < -6)  turnLeft(dA);
	}
	
	public void turnAwayFrom(double mx, double my, int dA)
	{
		double Nx = -sinA;
		double Ny =  cosA;
		
		double d = (mx - x) * Nx  + (my - y) * Ny;
		
		if (d > 6)  turnLeft(dA);
		
		if (d < -6)  turnRight(dA);
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
	
	
	public void draw(Graphics pen)
	{
        // no camera
        pen.drawOval((int)(x-r), (int)(y-r), (int)(2*r), (int)(2*r));
        pen.drawLine((int)(x), (int)(y ), (int)(x  + r * cosA), (int)(y  + r * sinA));


		// pen.drawOval((int)(x-r - Camera.x), (int)(y-r - Camera.y), (int)(2*r), (int)(2*r));
	
		// pen.drawLine((int)(x - Camera.x), (int)(y - Camera.y), (int)(x  - Camera.x + r * cosA), (int)(y  - Camera.y + r * sinA));
	}
	
	
	public boolean overlaps(Circle c)
	{
		double dx = x - c.x;
		double dy = y - c.y;
		
		double d2 = dx*dx + dy*dy;
		
		return d2 <= (r + c.r)*(r + c.r);
	}
}
