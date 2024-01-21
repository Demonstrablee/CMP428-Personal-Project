package Levels.Menus;
import javax.swing.*;

import Levels.Managers.Level2;

import java.awt.*;



public class GameSelectMenu extends Level2{ 
    JLabel title = new JLabel("Choose a Game");
    String [] tooltips = new String[]{
    "Enter the Casino!  Face off in the game of memory against a slightly familiar foe to see whos memory is the best! ",
    "Well well well theres a leak and you've got to fix it, Take up a wrench against time and forge a path before the water destroys another one of your clients homes!",
    "Back from a trip to Cancun, the last thing to do is to return your rental, but it's just your luck you've got to drive through the U.S's #1 state for reckless driving, try your best to return your car in working order in Long Trip Drift! "

    };
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
                if (i< 4)button.setToolTipText(tooltips[i-1]);
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

            
            //for(JButton button : titleButtons){button.repaint();}
        }
    
        
  
   
   
    

    
}
