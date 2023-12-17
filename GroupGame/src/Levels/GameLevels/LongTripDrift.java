package Levels.GameLevels;
import javax.swing.*;
import Characters.Characters.Enemy;
import Levels.Managers.Level2;
import Objects.Camera;
import Objects.Wall;

import java.awt.*;


public class LongTripDrift extends Level2 { 
    GridBagConstraints constraints = new GridBagConstraints(); // constraints you will add to each element
    JLabel title = new JLabel("Long Trip Drift"); 
     
    //JLayer<JPanel> jlayer = new JLayer<JPanel>(this, clouds);
    Image map = Toolkit.getDefaultToolkit().getImage("GroupGame/src/images/GTA1_HD_Liberty_City MAP.png");

    public LongTripDrift(Level2 enter, Level2 exit){
      
        super(enter, exit, "Long Trip");
        //this.add(jlayer);

        //BACKGROUND
    
        setBgWImage(map);

       //setBg("GTA1_HD_Liberty_City MAP.png"); 
        // place boundarys on the entire map
            wall = new Wall[]{new Wall(10,10,10,10)};

        // then make the frame HUGE so we zoom in
       setBounds(-10, 0, 20280, 17720); // SO IMPORTANT THE LEVEL WONT APPEAR UNLESS THESE ARE SET
        
        //wall = new Wall[]{new Wall(0, 50, 1920, 80), new Wall(0, 500, 1920, 80)};
       
        //ENTERANCE AND EXITS
        setLevelEnterPos(new int[] {200,490,100,25});
        setLevelExitPos(new int[] {900,300,25,100});
        
        
        // Setting enemys array
         
        // constraints.gridx = 0;
        // constraints.gridy = 0;
        // add(title, constraints);
        
    }
   @Override
    public void reset(){
        Camera.returnToOrigin(); // reset the camera and player starting positions
        p1.returnToOrigin();
    }

    @Override
    public void paintComponent(Graphics pen){  //method for painting
        super.paintComponent(pen);//component that does the painting 
   
        
        //pen.clearRect(0, 0, getWidth(), getHeight());
       pen.drawImage(bg,(int)(0 - Camera.x),(int)(0-Camera.y),getWidth(), getHeight(),null);
       
        p1.draw(pen);
  
        // for(Wall walls : wall){
        //     walls.setColor(Color.ORANGE);
        //     walls.draw(pen);
        // }
    
        
       // draw enterance and exits
        // dRectEnter.draw(pen);
        // dRectEx.draw(pen);

    }
    }

    

 