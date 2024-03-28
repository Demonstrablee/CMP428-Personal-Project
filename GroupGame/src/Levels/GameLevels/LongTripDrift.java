package Levels.GameLevels;

import javax.swing.*;

import Characters.Characters.Car;
import Characters.Characters.Dealer;
import Levels.Managers.Level2;
import Objects.Camera;
import Objects.Rect;
import Objects.Wall;
import fonts.fontsRegistry;
import java.awt.*;
import java.util.Random;

public class LongTripDrift extends Level2 {

    // FINAL VARS
    final int MAP_WIDTH = 15280;
    final int MAP_HEIGHT = 11720;

    // PLAYER DAMAGE SCORE
    int damageScore = 100; // how damaged it the players car
    JLabel scoreLabel = new JLabel("Damage: -$" + damageScore, SwingConstants.CENTER);

    // ASSETS
    String assetDir = "GroupGame/src/images/LTD/";
    Image map = Toolkit.getDefaultToolkit().getImage(assetDir + "GTA1_HD_Liberty_City MAP CROPPED.png");
    Image overpass = Toolkit.getDefaultToolkit().getImage(assetDir + "GTA1 Overpasses/GTA1 2 Lane Over Pass.png");
    Image[] overpasses;
    Rect[] building; // buildings
    // Player from level class

    // Objects
    int TOTAL_CARS = 1; // NUM OF ENEMY CARS
    int CAR_OPTIONS = Car.getNumAvailableCars(); // there are 20 possible cars to choose from
    Car[] enemyCar = new Car[TOTAL_CARS];

    // random Attributes for car generation
    Random rand = new Random();
    int randX;
    int randY;
    int randCar;

    // Place boundarys on the entire map
    Wall[] wall = new Wall[] { new Wall(10, 90, 8000, 50), // top
            new Wall(50, 0, 90, 11375), // LEFT
            new Wall(10, 11375, 900, 50), // BOTTOM NEAR FINISH
    };

    public LongTripDrift(JButton[] exitButton) {
        super("Long Trip Drift");

        // then make the frame HUGE so we zoom in setBounds(-10, 0, 20280, 17720);
        setBounds(-10, 0, MAP_WIDTH, MAP_HEIGHT); // SO IMPORTANT THE LEVEL WONT APPEAR UNLESS THESE ARE SET

        // Adding Score Label to Screen
        scoreLabel.setBackground(Color.white);
        scoreLabel.setOpaque(true);
        scoreLabel.setBounds((int) (100 - Camera.x), (int) (0 - Camera.y), 250, 60);
        scoreLabel.setFont(fontsRegistry.arcadePixel);
        scoreLabel.setBorder(BorderFactory.createLineBorder(Color.black, 5));
        add(scoreLabel);

        System.out.println("Camera position x:" + Camera.x + " y:" + Camera.y);

        // GENERATE EMEMY CARS
        for (int i = 0; i < enemyCar.length; i++) {
            randX = rand.nextInt(0, 200);
            randY = rand.nextInt(0, 200);

            randX = 85;
            randY = 24;
            randCar = rand.nextInt(0, CAR_OPTIONS);
            enemyCar[i] = new Car(randX, randY, 0);
        }
    }

    @Override
    public void reset() {
        Camera.returnToOrigin(); // reset the camera and player starting positions
        p1.returnToOrigin();
    }

    @Override
    public void paintComponent(Graphics pen) { // method for painting
        super.paintComponent(pen);// component that does the painting

        // COLLISIONS

        for (int i = 0; i < wall.length; i++) {
            // if player colides into wall
            // place player out side the wall
        }

        // Player Damage
        for (int i = 0; i < enemyCar.length; i++) {
            if (p1.isColliding(enemyCar[i])) {
                // p1.setColor(Color.GREEN);
                damageScore++; // damage score goes up
                System.out.println(damageScore);

            }
        }

        // DRAW MAP
        // X - LEFT (POS) RIGHT(NEGA) (-6000) CHECK and guessing
        // Y - UP (POS) DOWN(NEGA) (-5450)
        pen.drawImage(map, (int) (-6000 - Camera.x), (int) (-5450 - Camera.y), getWidth(), getHeight(), null);

        // DRAW PLAYER
        p1.draw(pen);

        // DRAW CARS (ON TOP OF MAP)
        for (int i = 0; i < enemyCar.length; i++) {
            enemyCar[i].drawEnemy(pen);
        }
        for (Wall walls : wall) {
            walls.setColor(Color.ORANGE);
            walls.draw(pen);
        }

        // Over Passes
        pen.drawImage(overpass, (int) (2249 - Camera.x), (int) (3280 - Camera.y), 720, 295, null);

    }
    @Override
    public void pause() {
        // 1. halt enemy car movement
        // 2. in levelbuilder halt updown left right movement (done)

    }

    

    public void moveEnemysForward() {
        enemyCar[0].moveForward(5);
    }
    public void moveEnemysBackward(){
        enemyCar[0].moveForward(-5);
    }

    public void turnEnemysLeft() {
        enemyCar[0].turnLeft(5);
    }
    public void turnEnemysRight(){
        enemyCar[0].turnRight(5);
    }


}
