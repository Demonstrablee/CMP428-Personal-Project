package Levels.GameLevels;
import Levels.Managers.Level2;

import java.awt.*;
import java.util.Random;

import javax.swing.JLabel;

import Characters.Characters.Enemy;

public class Baccano extends Level2 { 
    JLabel title = new JLabel("BACANO");
    Image table = Toolkit.getDefaultToolkit().getImage("GroupGame/src/images/casinoTablebg_red.jpeg");
    Enemy dealer = new Enemy("DuckHuntDog",400,30, 0,0, 15);
    
    public Baccano(Level2 enter, Level2 exit){
        super(enter, exit, "bacano");
        
        //BACKGROUND
        setBackground(Color.BLACK); // set pane to black
        setBg("casinoPeople.jpeg");

        setBounds(0, 0, 1280, 720);

        //Level Exit and Enterance Set

        // constraints.gridx = 0;
        // constraints.gridy = 0;
        // add(title, constraints);
        
    }
   
   @Override
    public void reset(){
        
    }

   
    public void paintComponent(Graphics pen){  //method for painting
        super.paintComponent(pen);//component that does the painting 
        Graphics2D pen2D = (Graphics2D) pen;
        //Draw Background
        pen.drawImage(bg,0,-100,getWidth(), getHeight(),null);
        
        //Draw the dealer
        dealer.draw(pen);
        //draw the table
        pen.drawImage(table,0,400,getWidth(), 600,null);
       

        //Table outline
        pen2D.setColor(new Color(144, 84, 47));
        pen2D.setStroke(new BasicStroke(25)); // thicker pen
        pen2D.drawRect(0, 400, getWidth(), 600);
    }
}
 