package Characters.Characters;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import Objects.Camera;
import Objects.Lookup;
import Objects.Wall;

public class Car {
    // PLAYER and other Car IMAGES
    private BufferedImage player;
    private static File cardDir = new File("GroupGame/src/images/LTD/cars");
    private static File[] carFiles = cardDir.listFiles(); // get all the car files from the directory
    BufferedImage[] carImages = new BufferedImage[carFiles.length];

    // points for colision polygon
    int[] player_x = new int[] { 55, -55, -55, 55 }; // custom dimensions for the orange car and the cars that fit this
                                                     // shape
    int[] player_y = new int[] { -25, -25, 30, 30 };

    int[] x_points = new int[4];
    int[] y_points = new int[4];

    // points for for polygon rotation
    int rotation_x;
    int rotation_y;
    int x_;
    int y_;

    // get the center of the image for the player
    int centerX;
    int centerY;

    // POSITION
    int x = 0;
    int y = 0;

    int distFromX = 0;
    int distFromY = 0;

    AffineTransform tx = new AffineTransform();

    // for rotation
    protected int A = 0;
    double cosA = 1;
    double sinA = 0;

    public Car(int x, int y) {
        this.x = x;
        this.y = y;

        // GET THE PLAYERS and all the Cars as BuffredImages (Gotta use Buffred Image)
        try {
            player = ImageIO.read(new File("GroupGame/src/images/LTD/cars/orgCar.png"));

            for (int i = 0; i < carFiles.length; i++) { // make buffred images out of all the cars in the directory
                if (carFiles[i].getName().equals(".DS_Store")) {
                    carFiles[i].delete();
                } // DS.Store files messing up reading in files
                System.out.println(carFiles[i].getName());
                carImages[i] = ImageIO.read(carFiles[i]);
            }
            System.out.println("All Cars Images Sucessfully Loaded");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        // GET THE CENTERS OF THE PLAYER IMAGE
        centerX = player.getWidth(null) / 2;
        centerY = player.getHeight(null) / 2;

        // Start the player at the origin
        tx.translate(x, y); // WORLD ORIGIN

        // TESTING
        // Timer timer = new Timer(70, e -> {
        // angle = Math.toRadians(5); // Adjust rotation speed

        // });
        // timer.start();
    }

    public Car(int x, int y, int carSelect) { // for the enemy cars
        this(x, y);
        player = carImages[carSelect];

        centerX = player.getWidth(null) / 2;
        centerY = player.getHeight(null) / 2;

        // tx.translate(x , y); // WORLD ORIGIN
    }

    public boolean isColliding(Wall wall) { // colision with walls
       
        return false;
    }

    // guided help by chat gpt
    public boolean isColliding(Car otherCar) {

        for (int i = 0; i < this.numVertices(); i++) {
            int[] p1 = this.getVertex(i);
            int[] p2 = this.getVertex((i + 1) % numVertices());

            for (int j = 0; j < otherCar.numVertices(); j++) {
                int[] x1 = otherCar.x_points;
                int[] y2 = otherCar.getVertex((j + 1) % otherCar.numVertices());

                if (doIntersect(p1, p2, x1, y2)) {
                    return true;
                }
            }
        }
        return false;
    }

    // Helper method to check if two line segments intersect
    public static boolean doIntersect(int[] p1, int[] q1, int[] p2, int[] q2) { // help of chat gpt
        int o1 = orientation(p1, q1, p2);
        int o2 = orientation(p1, q1, q2);
        int o3 = orientation(p2, q2, p1);
        int o4 = orientation(p2, q2, q1);

        // General case
        if (o1 != o2 && o3 != o4)
            return true;

        // Special Cases
        if (o1 == 0 && onSegment(p1, p2, q1))
            return true;
        if (o2 == 0 && onSegment(p1, q2, q1))
            return true;
        if (o3 == 0 && onSegment(p2, p1, q2))
            return true;
        return o4 == 0 && onSegment(p2, q1, q2);
    }

    private static int orientation(int[] p, int[] q, int[] r) {
        int val = (q[1] - p[1]) * (r[0] - q[0]) -
                (q[0] - p[0]) * (r[1] - q[1]);

        if (val == 0)
            return 0; // Collinear
        return (val > 0) ? 1 : 2; // Clockwise or Counterclockwise
    }

    private static boolean onSegment(int[] p, int[] q, int[] r) {
        return q[0] <= Math.max(p[0], r[0]) &&
                q[0] >= Math.min(p[0], r[0]) &&
                q[1] <= Math.max(p[1], r[1]) &&
                q[1] >= Math.min(p[1], r[1]);
    }

    public int numVertices() {
        return x_points.length;
    }

    public int[] getVertex(int i) {
        int[] vertex = new int[] { x_points[i], y_points[i] };
        return vertex;
    }

    public void moveForward(int d) // delta change
    {
        // how far from the origin have I
        distFromX += (int) (d * cosA);
        distFromY += (int) (d * sinA);

        tx.translate(d, 0); // works when (1,0) (affine transform seems to do the
        // vector stuf itself)

        // print
        System.out.println("Player A: " + A);
        for (int i = 0; i < 4; i++) {
            System.out.println("Vetex of Polygon Player: " + x_points[i] + ", " + y_points[i]);
        }
    }

    public void turnLeft(int dA) {
        A -= dA; // in degrees for polygon

        if (A < 0)
            A += 360;

        cosA = Lookup.cos[A];
        sinA = Lookup.sin[A];

        // Rotate Player image
        tx.rotate(-Lookup.radianMeasureOf[dA], centerX, centerY); // turn by delta a degrees from where you are

        // print
        System.out.println("Player A: " + A);
        for (int i = 0; i < 4; i++) {
            System.out.println("Vetex of Polygon Player: " + x_points[i] + ", " + y_points[i]);
        }

    }

    public void turnToward(double mx, double my, int dA) // professors code for turning a circle
	{
		double Nx = -sinA;
		double Ny =  cosA;
		

		double dist = (mx - x) * Nx  + (my - y) * Ny;
		

		if (dist > 6)  turnRight(dA);
		
		if (dist < -6)  turnLeft(dA);
	}
    public void follow(int playerX, int playerY) {
       
        //turn towards the player
        turnToward(playerY, playerX, 3);

        // move towards the player if they are in range
        if((Math.abs(x - playerX) > 60) || (Math.abs(y-playerY) > 60)) moveForward(5);
    }

    public void turnRight(int dA) {
        A += dA;

        if (A > 359)
            A -= 360;

        cosA = Lookup.cos[A];
        sinA = Lookup.sin[A];

        // Rotate Player image

        tx.rotate(Lookup.radianMeasureOf[dA], centerX, centerY);

        // print
        System.out.println("Player A: " + A);
        for (int i = 0; i < 4; i++) {
            System.out.println("Vetex of Polygon Player: " + x_points[i] + ", " + y_points[i]);
        }

    }

    /** Return the player to the origin */
    public void returnToOrigin() {
        A = 0;
        tx.translate(-distFromX, -distFromY); // return to origin
        distFromX = 0;
        distFromY = 0;

    }

    public void draw(Graphics pen) {
        Graphics2D pen2D = (Graphics2D) pen; // cast into 2d pen (needed for affine transformation)

        // Update PolyGon Positon
        for (int i = 0; i < 4; i++) {

            x_ = player_x[i];
            y_ = player_y[i];

            // FIRST ROTATE
            rotation_x = (int) ((x_ * cosA) - (y_ * sinA));
            rotation_y = (int) ((x_ * sinA) + (y_ * cosA));

            // THEN TRANSLATE offsets so that the polygon will fit into car
            x_points[i] = (int) (rotation_x + x); // forward back(neg)
            y_points[i] = (int) (rotation_y + y); // up down(pos)

        }

        // Draw image of the player
        pen2D.drawImage(player, tx, null);

        // Draw players polygon
        pen.setColor(Color.GREEN);
        pen.drawPolygon(x_points, y_points, 4);

    }

    public void drawEnemy(Graphics pen) {
        Graphics2D pen2D = (Graphics2D) pen; // cast into 2d pen (needed for affine transformation)

        // Update PolyGon Positon
        for (int i = 0; i < 4; i++) {

            x_ = player_x[i];
            y_ = player_y[i];

            // FIRST ROTATE
            rotation_x = (int) ((x_ * cosA) - (y_ * sinA));
            rotation_y = (int) ((x_ * sinA) + (y_ * cosA));

            // THEN TRANSLATE offsets so that the polygon will fit into car
            x_points[i] = (int) (rotation_x + x); // forward back(neg)
            y_points[i] = (int) (rotation_y + y); // up down(pos)

        }

        // Draw image of the player
         
        pen2D.drawImage(player, tx, null);

        // Draw players polygon
        pen.setColor(Color.GREEN);
        pen.drawPolygon(x_points, y_points, 4);

    }

    /** Ge the number of car models that can be chosen from */
    public static int getNumAvailableCars() {
        return carFiles.length;
    }

    /** change the player car image */
    public void setPlayerCar(int carSelect) {
        player = carImages[carSelect];
    }

}
