package Objects;

public class Lookup
{
	public static double[] cos = generateCos();
	public static double[] sin = generateSin();
	
	public static double[] generateCos()
	{
		double[] cos = new double[360];
		
		for(int A = 0; A < 360; A++)
		{	
			cos[A] = Math.cos(A * Math.PI / 180);
		}
		
		return cos;
	}

	public static double[] generateSin()
	{
		double[] sin = new double[360];
		
		for(int A = 0; A < 360; A++)
		{	
			sin[A] = Math.sin(A * Math.PI / 180);
		}
		
		return sin;
	}

}
