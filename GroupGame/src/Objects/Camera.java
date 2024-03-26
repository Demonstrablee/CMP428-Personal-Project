package Objects;

public class Camera {
	public static double x = 1280/2;
	public static double y = 720/2;
	static double z = 0;

	static int A = 0;

	public static void turnBy(int dA) {
		A += dA;
	}

	public static void moveForward(int d) {
		x += d * Math.cos(A * Math.PI / 180);
		y += d * Math.sin(A * Math.PI / 180);
	}

	public static void goLT(double dx) {
		x -= dx;
	}

	public static void goRT(double dx) {
		x += dx;
	}

	public static void goIN(double dz) {
		z += dz;
	}

	public static void goOT(double dz) {
		z -= dz;
	}

	public static void goUP(double dy) {
		y -= dy;
	}

	public static void goDN(double dy) {
		y += dy;
	}

	public static void returnToOrigin() {
		x = 1280/2;
		y = 720/2;
		z = 0;
	}
}
