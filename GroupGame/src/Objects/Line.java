package Objects;

import java.awt.Graphics;

public class Line
{
	double Ax;
	double Ay;
	
	double Bx;
	double By;
	
	double Nx;
	double Ny;
	
	double c;
	
	
	public Line(double Ax, double Ay, double Bx, double By)
	{
		this.Ax = Ax;
		this.Ay = Ay;
		
		this.Bx = Bx;
		this.By = By;
		
		double dx = Ax - Bx;
		double dy = Ay - By;
		
		double mag = Math.sqrt(dx*dx + dy*dy);
		
		Nx = -dy / mag;
		Ny =  dx / mag;
		
		c = - Ax * Nx - Ay * Ny;
	}
	
	public double distanceTo(double Px, double Py)
	{
		return Px*Nx + Py*Ny + c;
	}
	
	public void draw(Graphics pen)
	{
        pen.drawLine((int)(Ax), (int)(Ay), (int)(Bx), (int)(By));
		//pen.drawLine((int)(Ax - Camera.x), (int)(Ay - Camera.y), (int)(Bx - Camera.x), (int)(By - Camera.y));
	}
	

}
