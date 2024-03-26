package Levels.GameLevels;

import javax.swing.*;

import Characters.Characters.Enemy;
import Levels.Managers.Level2;
import Objects.Camera;
import Objects.Rect;
import Objects.Wall;
import fonts.fontsRegistry;
import java.awt.*;

public class LongTripDrift extends Level2 {

    // FINAL VARS
    final int MAP_WIDTH = 15280;
    final int MAP_HEIGHT = 11720;

    // PLAYER DAMAGE SCORE
    int damageScore = 100; // how damaged it the players car
    JLabel scoreLabel = new JLabel("Damage: -$" + damageScore, SwingConstants.CENTER); 
    
    //ASSETS
    String assetDir = "GroupGame/src/images/LTD/";
    Image map = Toolkit.getDefaultToolkit().getImage(assetDir + "GTA1_HD_Liberty_City MAP CROPPED.png");
    Image overpass = Toolkit.getDefaultToolkit().getImage(assetDir + "GTA1 Overpasses/GTA1 2 Lane Over Pass.png");
    Image[] overpasses;
    Rect[] building; // buildings
    // Player from level class

    // ACTIONS
    Action upAction;
    Action downAction;
    Action leftAction;
    Action rightAction;

    // Objects
    Enemy.InnerEnemy[] carEnemies = Enemy.InnerEnemy.genCarEnemys(1);

    // place boundarys on the entire map
    Wall [] wall = new Wall[] { new Wall(10, 90, 8000, 50), // top
        new Wall(50, 0, 90, 11375), // LEFT
        new Wall(10, 11375, 900, 50), // BOTTOM NEAR FINISH
        new Wall(630, 320, 630, 310) // BUILDINGS
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
    }

    @Override
    public void reset() {
        Camera.returnToOrigin(); // reset the camera and player starting positions
        // p1.returnToOrigin();
    }

    @Override
    public void paintComponent(Graphics pen) { // method for painting
        super.paintComponent(pen);// component that does the painting

        // Move the Player

        // Colisions
        // if (wall != null)
        // for(int i = 0; i < wall.length; i++){
        // //System.out.println("Location of Wall " + i +" x: "+ wall[i].x + " y: "+
        // wall[i].y);
        // if(p1.overlaps(wall[i])){
        // System.out.println("Pushing player out of wall "+ i);
        // p1.pushedOutOf(wall[i]);
        // }
        // }

        // Player Damage

        if (carEnemies != null) {
            Enemy.InnerEnemy e1 = carEnemies[0]; // temporary a loop will kill the player immediately
            if (p1.overlaps(e1)) {
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

        // DRAW CARS
        for (Enemy.InnerEnemy carEn : carEnemies) {
            carEn.draw(pen);
        }
        for (Wall walls : wall) {
            walls.setColor(Color.ORANGE);
            walls.draw(pen);
        }

        // Over Passes
        pen.drawImage(overpass, (int) (2249 - Camera.x), (int) (3280 - Camera.y), 720, 295, null);

    }

}
