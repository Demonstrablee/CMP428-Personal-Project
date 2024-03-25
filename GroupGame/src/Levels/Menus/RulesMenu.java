package Levels.Menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

// import Objects.Wall;
import Levels.Managers.OverlayLevel;
import fonts.fontsRegistry;

public class RulesMenu extends OverlayLevel {

    JLabel title = new JLabel("HOW TO PLAY", SwingConstants.CENTER);

    JPanel background = new JPanel();
    JPanel pane = new JPanel(new GridBagLayout());

    String des1;

    int currMenu = 0; // which menu to display

    Font arcade = fontsRegistry.arcadePixel.deriveFont(60f);

    // Read in the text for the rules from the rules.txt
    File file1 = new File("GroupGame/src/rules/Baccano.txt");
    File file2 = new File("GroupGame/src/rules/LTD.txt");
    File file3 = new File("GroupGame/src/rules/Wellerman.txt");
    File rulesTexts[] = new File[] { file1, file2, file3 };

    JLabel rules = new JLabel();
    String rulesText = "<html> ";

    public RulesMenu(JButton back) {
        super(null, null, null);

        // BACKGROUND
        background.setBounds(0, 0, 1280, 720);
        background.setBackground(new Color(0, 0, 0, 170));
        add(background);

        // setBg("black01.jpg");
        setLayout(null);
        setBounds(0, 0, 1280, 720);

        pane.setBackground(new Color(100, 10, 10, 100));
        pane.setBounds(400, 0, 600, 700);

        add(pane, JLayeredPane.DRAG_LAYER);

        // adding components to the screen
        title.setFont(arcade);
        title.setForeground(Color.WHITE);
        constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.PAGE_START;
        constraints.gridx = 0;
        constraints.gridy = 0;
        pane.add(title, constraints);

        // RULES TEXT

        // rules.setFont(arcade);
        rules.setForeground(Color.WHITE);
        rules.setBackground(Color.black);
        rules.setOpaque(true);

        constraints = new GridBagConstraints();
        // constraints.anchor = GridBagConstraints.PAGE_START;
        constraints.gridx = 0;
        constraints.gridy = 1;
        // constraints.insets = new Insets(3, 5, 5, 5);
        //constraints.ipadx = 900; // expand the textbox out

        pane.add(rules, constraints);

        // Button Customization
        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.insets = new Insets(3, 5, 0, 5);
        back.setText("BACK");
        constraints.ipadx = 5;
        constraints.ipady = 3;
        pane.add(back, constraints);

        setMenu(0); // set default
    }

    public void setMenu(int menuSelection) {
        currMenu = menuSelection;

        try (Scanner sc1 = new Scanner(rulesTexts[currMenu])) {
            while (sc1.hasNextLine()) {
                rulesText += sc1.nextLine() + "<br>";

            }
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        }

        rules.setText(rulesText + " <html>"); // set the rules text
    }

    @Override
    public void paintComponent(Graphics pen) { // method for painting
        super.paintComponent(pen);

        pen.drawImage(bg, 0, 0, getWidth(), getHeight(), null);

    }

}
