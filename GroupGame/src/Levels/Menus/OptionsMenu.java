package Levels.Menus;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;

import Levels.Managers.Level2;
import fonts.fontsRegistry;


public class OptionsMenu extends Level2 implements ActionListener {

    JLabel title = new JLabel("OPTIONS");

    // Labels
    JLabel resoLabel = new JLabel("1280 x 720"); // TEMP VAL
    JLabel musicLabel = new JLabel("BGM");
    JLabel diaLabel = new JLabel("DIALOGUE");

    //Subtitles
    JRadioButton subs = new JRadioButton("Subtitles");
    JRadioButton colorBlindMode = new JRadioButton("Color Blindness Mode");

    //Buttons
    JButton resoR = new JButton();
    JButton resoL = new JButton();
    JButton confirmButton = new JButton("Set");
    JButton backButton;
    String resolutions [] = new String[] {"1280 x 720", "1920 x 1080", "2560 x 1440"}; 
    int currRes = 0;

    //Sliders
    JSlider musicSlider = new JSlider(0,100);
    JSlider diaSlider = new JSlider(0,100);

    GridBagConstraints constraints = new GridBagConstraints(); // constraints you will add to each element
    
    JPanel pane = new JPanel();

    Font arcade = fontsRegistry.arcadePixel.deriveFont(30f);

    public OptionsMenu(JButton backButton){
        super("optionsMenu"); // no enterance exit logic just using card manager in levelbuilder
        
        //BACKGROUND
        pane.setBounds(0, 0, 1280, 720);
        pane.setLayout(new GridBagLayout());
        pane.setBackground(new Color(90,70,60,170));
        
        //Get Buttons
        this.backButton = backButton;
        backButton.setText("BACK");

        //TITLE
        constraints.anchor = GridBagConstraints.PAGE_START;
        constraints.gridx = 1;
        constraints.gridy = 0;
        title.setFont(arcade.deriveFont(60f));
        title.setForeground(Color.white);
        pane.add(title, constraints);

        //SUBTITLES
        constraints.anchor = GridBagConstraints.LINE_START;
        constraints.gridx = 1;
        constraints.gridy = 1;
        subs.setFocusable(false);
        subs.setFont(arcade);
        subs.setForeground(Color.WHITE);
        pane.add(subs, constraints);

        //ACESSABILITY
        constraints.anchor = GridBagConstraints.LINE_START;
        constraints.gridx = 1;
        constraints.gridy = 2;
        colorBlindMode.setFocusable(false);
        colorBlindMode.setFont(arcade);
        colorBlindMode.setForeground(Color.WHITE);
        pane.add(colorBlindMode, constraints);

        // RESOLUTION ADJUST
        resoL.addActionListener(this);
        resoL.setFocusable(false);
        resoR.addActionListener(this);
        resoR.setFocusable(false);

        constraints = new GridBagConstraints();
        constraints.gridx = 2;
        constraints.gridy = 3;
        resoR.setFocusable(false);
        pane.add(resoR, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 3;
        resoLabel.setFont(arcade);
        resoLabel.setForeground(Color.WHITE);
        pane.add(resoLabel, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 3;
        resoL.setFocusable(false);
        pane.add(resoL, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 4;
        confirmButton.setFocusable(false);
        pane.add(confirmButton, constraints);

        // VOLUME ADJUST
        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 5;
        musicLabel.setFont(arcade);
        musicLabel.setForeground(Color.white);
        pane.add(musicLabel, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 6;
        musicSlider.setFocusable(false);
        pane.add(musicSlider, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 7;
        diaLabel.setFont(arcade);
        diaLabel.setForeground(Color.WHITE);
        pane.add(diaLabel, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 8;
        diaSlider.setFocusable(false);
        pane.add(diaSlider, constraints);


        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 9;
        // set not focusable in the array that created the buttons
        pane.add(this.backButton, constraints);

        add(pane);
    }

   
     @Override
    public void paintComponent(Graphics pen){  //method for painting called with repaint method
        super.paintComponent(pen);
       

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();

        if(button == resoL){
            currRes += currRes > 0 ? -1 : 0;

        }else if(button == resoR ){
            currRes += currRes < resolutions.length -1 ?  1 : 0;
        }

        resoLabel.setText(resolutions[currRes]);
    }
}
