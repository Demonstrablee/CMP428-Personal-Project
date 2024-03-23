package Levels.GameLevels;
import javax.swing.*;


import Characters.Characters.Enemy;
import Levels.Managers.Level2;
import Objects.Camera;
import Objects.Wall;
import fonts.fontsRegistry;
import java.awt.*;
import java.awt.event.KeyEvent;



public class LongTripDrift extends Level2 { 
 
    //SCORE
    int damageScore = 100; // how damaged it the players car
    JLabel scoreLabel = new JLabel("Damage: -$"+ damageScore, SwingConstants.CENTER); // Label showing players cars damage
    
    
    String assetDir = "GroupGame/src/images/LTD/";
    Image map = Toolkit.getDefaultToolkit().getImage(assetDir+"GTA1_HD_Liberty_City MAP CROPPED.png");
    final int MAP_WIDTH = 15280;
    final int MAP_HEIGHT = 11720;
    Enemy.InnerEnemy [] carEnemies = Enemy.InnerEnemy.genCarEnemys(1);
    Image overpass = Toolkit.getDefaultToolkit().getImage(assetDir+"GTA1 Overpasses/GTA1 2 Lane Over Pass.png");
    //protected static Player p1 = new Player(950,630, 90, 50);

    //ACTIONS
    Action upAction;
    Action downAction;
    Action leftAction;
    Action rightAction;

     // Movement vars
     boolean[] pressing = new boolean[1024];

    static final int UP = KeyEvent.VK_UP;
    static final int DN = KeyEvent.VK_DOWN;
    static final int LT = KeyEvent.VK_LEFT;
    static final int RT = KeyEvent.VK_RIGHT;
    static final int Q = KeyEvent.VK_Q;

    static final int W = KeyEvent.VK_W;
    static final int A = KeyEvent.VK_A;
    static final int S = KeyEvent.VK_S;
    static final int D = KeyEvent.VK_D;

    public LongTripDrift(JButton [] exitButton){
        super(null, null, "Long Trip");

        setLayout(null);

        // then make the frame HUGE so we zoom in  setBounds(-10, 0, 20280, 17720);
        setBounds(-10, 0, MAP_WIDTH, MAP_HEIGHT); // SO IMPORTANT THE LEVEL WONT APPEAR UNLESS THESE ARE SET
        
        //BACKGROUND
        setBgWImage(map);

        //add(p1);

    
        // place boundarys on the entire map
        wall = new Wall[]{new Wall(10,90,8000,50),//top
            new Wall(50,0,90,11375), // LEFT
            new Wall(10,11375,900,50),//BOTTOM NEAR FINISH
            new Wall(630, 320, 630, 310) // BUILDINGS
            };

        for(Wall wallnum: wall ){
                //System.out.println("Wall locations: ("+ wallnum.x + ", "+ wallnum.y +") wall width: "+ wallnum.w +" wall height: "+ wallnum.h);
        }
        
        // Adding Score Label to Screen
        scoreLabel.setBackground(Color.white);
        scoreLabel.setOpaque(true);
        scoreLabel.setBounds((int)(100 -Camera.x), (int)(0 -Camera.y),250,60);
        scoreLabel.setFont(fontsRegistry.arcadePixel);
        scoreLabel.setBorder(BorderFactory.createLineBorder(Color.black, 5));
        add(scoreLabel);

        
        
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
   

  
    // Move the Player
     

    // Colisions  
        // if (wall != null)
        // for(int i = 0; i < wall.length; i++){
        //     //System.out.println("Location of Wall " + i +" x: "+ wall[i].x + " y: "+ wall[i].y);
        //     if(p1.overlaps(wall[i])){
        //         System.out.println("Pushing player out of wall "+ i);
        //         p1.pushedOutOf(wall[i]);
        //         }      
        //     }

    //Player Damage

    if (carEnemies != null){
        Enemy.InnerEnemy e1 = carEnemies[0]; // temporary a loop will kill the player immediately
            if (p1.overlaps(e1)){
                //p1.setColor(Color.GREEN);
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

    

 