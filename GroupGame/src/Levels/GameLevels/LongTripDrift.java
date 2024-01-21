package Levels.GameLevels;
import javax.swing.*;

import Characters.Characters.Player;
import Characters.Characters.Enemy;
import Levels.Managers.Level2;
import Objects.Camera;
import Objects.Wall;

import java.awt.*;
import java.io.File;


public class LongTripDrift extends Level2 { 
    GridBagConstraints constraints = new GridBagConstraints(); // constraints you will add to each element
    JLabel score = new JLabel("Score: "); // Label showing players cars damage
    int damageScore = 0; // how damaged it the players car
    
    String assetDir = "GroupGame/src/images/LTD/";
    Image map = Toolkit.getDefaultToolkit().getImage(assetDir+"GTA1_HD_Liberty_City MAP CROPPED.png");
    final int MAP_WIDTH = 15280;
    final int MAP_HEIGHT = 11720;
    Enemy.InnerEnemy [] carEnemies = Enemy.InnerEnemy.genCarEnemys(1);
    Image overpass = Toolkit.getDefaultToolkit().getImage(assetDir+"GTA1 Overpasses/GTA1 2 Lane Over Pass.png");
    //protected static Player p1 = new Player(950,630, 90, 50);

    public LongTripDrift(Level2 enter, Level2 exit){
        super(enter, exit, "Long Trip");
    

        //BACKGROUND
        setBgWImage(map);

        // place boundarys on the entire map
            wall = new Wall[]{new Wall(10,90,8000,50),//top
            new Wall(50,0,90,11375), // LEFT
            new Wall(10,11375,900,50),//BOTTOM NEAR FINISH
            new Wall(630, 320, 630, 310) // BUILDINGS
            };

            for(Wall wallnum: wall ){
                System.out.println("Wall locations: ("+ wallnum.x + ", "+ wallnum.y +") wall width: "+ wallnum.w +" wall height: "+ wallnum.h);
            }


        // then make the frame HUGE so we zoom in  setBounds(-10, 0, 20280, 17720);
        setBounds(-10, 0, MAP_WIDTH, MAP_HEIGHT); // SO IMPORTANT THE LEVEL WONT APPEAR UNLESS THESE ARE SET
        
       
        score.setBackground(Color.RED);
        score.setOpaque(true);
        score.setBounds((int)(100 -Camera.x), (int)(0 -Camera.y),60,60);
        score.add(this);


        //wall = new Wall[]{new Wall(0, 50, 1920, 80), new Wall(0, 500, 1920, 80)};
       
        //ENTERANCE AND EXITS
        // setLevelEnterPos(new int[] {200,490,100,25});
        // setLevelExitPos(new int[] {900,300,25,100});
        
        
        // Setting enemys array
         
        // constraints.gridx = 0;
        // constraints.gridy = 0;
        // add(title, constraints);

        
        System.out.println("Camera position x:"+ Camera.x + " y:"+ Camera.y);
    }

   @Override
    public void reset(){
        Camera.returnToOrigin(); // reset the camera and player starting positions
        //p1.returnToOrigin();
    }

    @Override
    public void paintComponent(Graphics pen){  //method for painting
        super.paintComponent(pen);//component that does the painting 
   




    // Colisions  
        if (wall != null)
        for(int i = 0; i < wall.length; i++){
            //System.out.println("Location of Wall " + i +" x: "+ wall[i].x + " y: "+ wall[i].y);
            if(p1.overlaps(wall[i])){
                System.out.println("Pushing player out of wall "+ i);
                p1.pushedOutOf(wall[i]);
                }      
            }

    //Player Damage

    if (carEnemies != null){
        Enemy.InnerEnemy e1 = carEnemies[0]; // temporary a loop will kill the player immediately
            if (p1.overlaps(e1)){
                p1.setColor(Color.GREEN);
                damageScore++; // damage score goes up
                System.out.println(damageScore);
               
            }

        
        

    }


       // Draw map
        pen.drawImage(bg,(int)(0 - Camera.x),(int)(0-Camera.y),getWidth(), getHeight(),null);

        // Then on top of it draw
        
        p1.draw(pen);

        for(Enemy.InnerEnemy carEn : carEnemies){
                    carEn.draw(pen);
                }
        for(Wall walls : wall){
            walls.setColor(Color.ORANGE);
            walls.draw(pen);
        }

        // So you can go under the over passes
        pen.drawImage(overpass,(int)(2249 - Camera.x),(int)(3280-Camera.y),720, 295,null);

    

    }
    }

    

 