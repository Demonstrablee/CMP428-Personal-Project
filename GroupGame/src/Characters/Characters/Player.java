package Characters.Characters;

import java.awt.Color;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;

import Objects.Camera;
import Objects.Lookup;
import Objects.Rect;

public class Player extends Rect {
    // PLAYER IMAGE
    BufferedImage player;

    // points for colision polygon
    int[] player_x = new int[] { 55, -55, -55, 55 }; // custom dimensions for the orange car and the cars that fit this shape
    int[] player_y = new int[] { -25, -25, 30, 30 };

    int[] x_points = new int[4];
    int[] y_points = new int[4];

    // points for for polygon rotation
    int rotation_x;
    int rotation_y;
    int x_;
    int y_;

    // Polygon representation of the player
    Polygon playerPoly = new Polygon(x_points, x_points, 4);

    // get the center of the image for the player
    int centerX;
    int centerY;

    AffineTransform tx = new AffineTransform();

    // for rotation
    protected int A = 0;
    double cosA = 1;
    double sinA = 0;

    public Player(int x, int y, int w, int h) {
        super(x, y, w, h);

        try {// GET THE PLAYERS IMAGE (Gotta use Buffred Image)
            player = ImageIO.read(new File("GroupGame/src/images/LTD/ORANGECAR/ORANGECAR_IDLE/ORANGECAR_IDLE_0.png"));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        // GET THE CENTERS OF THE PLAYER IMAGE
        centerX = player.getWidth(null) / 2;
        centerY = player.getHeight(null) / 2;

        // Start the player at the origin
        tx.translate(Camera.x, Camera.y); // WORLD ORIGIN

        // TESTING
        // Timer timer = new Timer(70, e -> {
        // angle = Math.toRadians(5); // Adjust rotation speed

        // });
        // timer.start();
    }

    public boolean overlaps(Rect r) {

        return playerPoly.intersects((int) r.getX(), (int) r.getY(), (int) r.getWidth(), (int) r.getHeight());

    }

    public void moveForward(int d) // delta change
    {
        // x += (int)(d * cosA);
        // y += (int)(d * sinA);

        // delta_x = (int) (d * cosA);
        // delta_y = (int) (d * sinA);

        // tx.translate(d, 0); // works when (1,0) (affine transform seems to do the
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

        // A = 0;
        // int dx = x-originX;
        // int dy = x-originY;
        // playerRotate.translate(originX-dx, originX-dy); // return to 0, 0

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
            x_points[i] = (int) (rotation_x + x - 160); // forward back(neg)
            y_points[i] = (int) (rotation_y + y + 146); // up down(pos)

            // x_points[i] = (int)(rotation_x + x);
            // y_points[i] = (int)(rotation_y + y);

        }
        // Point to the updated polygon points for the players Polygon
        playerPoly.xpoints = x_points;
        playerPoly.ypoints = y_points;


        // Draw image of the player
        pen2D.drawImage(player, tx, null);
        pen.setColor(Color.GREEN);
        // Draw players polygon
        pen.drawPolygon(playerPoly);

    }

}
