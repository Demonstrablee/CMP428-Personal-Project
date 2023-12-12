package Levels.Menus;
import javax.swing.*;

import Levels.Managers.Level2;

import java.awt.*;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;


public class GameSelectMenu extends Level2{ 
    JLabel title = new JLabel("Choose a Game");

    public GameSelectMenu(JButton[] menuButtons){
       
        super(null,null, "gameSelect");
        //setDoubleBuffered(true);
        
        //BACKGROUND
      
        //setBg("partybackground.gif");
        setBackground(new Color(253,208,23));
        setBounds(0, 0, 1280, 720);
       
        
        // Getting the buttons for the Menu
  

        //Adding buttons to panel
        constraints = new GridBagConstraints();  
        constraints.gridx = 0;
        constraints. gridy = 0;
        add(title, constraints);

        int i = 1;
        for(JButton button : menuButtons){ 
                constraints = new GridBagConstraints();  
                constraints.gridx = 0;
                constraints. gridy = i;
                constraints.ipadx = 30;
                constraints.ipady = 3;
                constraints.insets = new Insets(3, 5, 5, 5);
                add(button, constraints);
                i++;
            }
        



    }
    @Override
        public void paintComponent(Graphics pen){  //method for painting
            super.paintComponent(pen);
            //pen.clearRect(0, 0, getWidth(), getHeight());
            pen.drawImage(bg,0,0,getWidth(), getHeight(),null);
            
            //repaint(100,100,4000,4000);
            
            //for(JButton button : titleButtons){button.repaint();}
        }
    
        
  
   
   
    

    
}
