package Levels.Menus;
import javax.swing.*;

import Levels.Managers.Level2;
import fonts.fontsRegistry;

import java.awt.*;



public class TitleScreen extends Level2{ 
    Font tiFont = fontsRegistry.titleFont;
    
    JLabel title = new JLabel("¡VOLER!");
    
    JPanel board = new JPanel();
    JLabel overlay = new JLabel(" ");

    JButton[] titleButtons;
    public TitleScreen(JButton[] menuButtons){
       
        super(null,null, "titleScreen");
        setLayout(null);

        //BACKGROUND
      

        setBg("partybackground.gif");
        setBounds(0, 0, 1280, 720);
        


        // overlay
    
        //board.setOpaque(false);
        overlay.setBackground(new Color(190,190,190, 150));
       //board.setBorder(BorderFactory.createDashedBorder(Color.GREEN, 7, 1,1,true));
        overlay.setBounds(0,0,1290,800);
        add(overlay);

        // border pane
        board.setLayout(new GridBagLayout());
        //board.setOpaque(false);
        board.setBackground(new Color(190,190,190, 30));
        board.setBorder(BorderFactory.createDashedBorder(Color.GRAY, 7, 1,1,true));
        board.setBounds(130,50,1030,600);
        add(board);



        //Set Font
        title.setFont(tiFont.deriveFont(100f));
        title.setForeground(Color.RED);
        
        // Getting the buttons for the Menu
        this.titleButtons = menuButtons;

        //Adding buttons to panel
        constraints = new GridBagConstraints();  
        constraints.gridx = 0;
        constraints. gridy = 0;
        board.add(title, constraints);

        int i = 1;
        for(JButton button : menuButtons){ 
                constraints = new GridBagConstraints();  
                constraints.gridx = 0;
                constraints. gridy = i;
                constraints.ipadx = 30;
                constraints.ipady = 3;
                constraints.insets = new Insets(3, 5, 5, 5);
                board.add(button, constraints);
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
