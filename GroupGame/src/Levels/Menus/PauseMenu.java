
package Levels.Menus;
import javax.swing.*;

import Levels.Managers.OverlayLevel;
import fonts.fontsRegistry;
import java.awt.*;



public class PauseMenu extends OverlayLevel{ 
    JLabel title = new JLabel("PAUSE");
    JButton[] pauseMButtons;
    Image phoneOutline;
    JPanel panel = new JPanel();

    public PauseMenu(JButton [] pauseButtons){ // This is the Phone
        super("pauseMenu");

        //BACKGROUND
    
        setBounds(0,0,1280,720); // set the bounds of the 
        setLayout(null);

        //Panel settings

        panel.setBounds(0,0,1280,720);
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(0,0,0,170));

        //title edits
        title.setFont(fontsRegistry.arcadePixel.deriveFont(70f));
        title.setForeground(Color.WHITE);

        // adding components to the screen
        this.pauseMButtons = pauseButtons;
        setVisible(false);
        //Adding elements to the panel
        constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.PAGE_START;
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(title,constraints);
     
        int i = 1;
        for(JButton button : this.pauseMButtons){ 
                constraints = new GridBagConstraints();  
                constraints.gridx = 0;
                constraints. gridy = i;
                constraints.insets = new Insets(3, 5, 5, 5);
                panel.add(button, constraints);
                i++;
            }


        add(panel);
    }
   
    

    @Override
    public void paintComponent(Graphics pen){  //method for painting
        super.paintComponent(pen);
       // pen.clearRect(0, 0, getWidth(), getHeight());
        
        //Draw Background
        //pen.drawImage(bg,getWidth()/2 - 250,0,500, getHeight(),null);
    
        // pen.drawRect(getWidth()/2 - 250,0,500,getHeight());
        //Draw Buttons and Title
        // title.repaint();
        // for(JButton button : pauseMButtons){
        //     button.repaint();
            
        // }
       
        
    }

    
}
