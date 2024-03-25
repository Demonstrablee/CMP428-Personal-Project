package Levels.Menus;

import javax.swing.*;
import javax.swing.border.Border;

import Levels.Managers.Level2;
import fonts.fontsRegistry;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

public class GameSelectMenu extends Level2 implements MouseListener {
    JLabel title = new JLabel("Choose a Game");
    JButton[] menuButton;
    // https://stackoverflow.com/questions/1090098/newline-in-jlabel
    String[] descriptions = new String[] {
            "<html>Enter the Casino!  Face off in the game of wits against <br/>a slightly familiar foe to see who will go bust! </html>",
            "<html> As a plumber of the U.S international Space Station your countries astronauts need you. Take up a wrench against time and lay a path before outer space is wasted!</html>",
            "<html>Back from a trip to Cancun, the last thing to do is to return your rental, but it's just your luck you've got to drive through the U.S's #1 state for reckless driving, try your best to return your car in working order in Long Trip Drift!</html>"

    };
    Font arcade = fontsRegistry.arcadePixel.deriveFont(60f);
    JPanel pane = new JPanel();
    JPanel buttonPane = new JPanel();
    JLabel imagePane = new JLabel();
    JLabel description = new JLabel("Select a Game and Get Playing!", SwingConstants.CENTER);

    File thumbDir = new File("GroupGame/src/images/gameThumbs");
    File[] thumFile = thumbDir.listFiles();
    ImageIcon[] thumbNails = new ImageIcon[thumFile.length];

    Image gradientBack = Toolkit.getDefaultToolkit().getImage("GroupGame/src/images/backgrounds/GameSelect_Bgr.png");
    ImageIcon thumbNailImage;

    public GameSelectMenu(JButton[] menuButtons) {

        super(null, null, "gameSelect");
        this.menuButton = menuButtons;
        setLayout(null);

        // setBackground(new Color(253,208,23));
        Border border1 = BorderFactory.createEtchedBorder(1);
        pane.setBorder(border1);
        pane.setBounds(40, 0, 1280, 720);
        // pane.setBackground(new Color (0,0,0,100));
        pane.setOpaque(true);

        // add(pane,JLayeredPane.DEFAULT_LAYER);

        // Getting thumbnails
        for (int i = 0; i < thumFile.length; i++) {
            // System.out.println(thumFile[i]+ "");
            Image thumbImg = Toolkit.getDefaultToolkit().getImage(thumFile[i] + "").getScaledInstance(750, 430,
                    Image.SCALE_SMOOTH);
            ImageIcon thumbIcon = new ImageIcon(thumbImg);
            thumbNails[i] = thumbIcon;
        }

        // Adding title to panel

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        title.setBounds(30, 0, 450, 100);
        title.setFont(arcade);
        title.setForeground(Color.WHITE);
        add(title, constraints);

        // BUTTONS
        Border border2 = BorderFactory.createDashedBorder(Color.WHITE, 10, 3, 3, true);
        buttonPane.setBorder(border2);
        buttonPane.setBackground(Color.GRAY);
        buttonPane.setLayout(new GridBagLayout());
        buttonPane.setBounds(20, 80, 450, 750);
        buttonPane.setVisible(true);
        add(buttonPane, JLayeredPane.PALETTE_LAYER);

        // Image

        imagePane.setBackground(Color.GREEN);
        imagePane.setBounds(500, 80, 750, 430);
        imagePane.setOpaque(false);
        add(imagePane, JLayeredPane.PALETTE_LAYER); // https://stackoverflow.com/questions/19125707/simplest-way-to-set-image-as-jpanel-background

        // Description
        Border bevelBorder = BorderFactory.createBevelBorder(3);
        description.setBorder(bevelBorder);
        description.setBounds(500, 530, 750, 150);
        description.setBackground(Color.GRAY);
        description.setOpaque(false);
        description.setFont(fontsRegistry.optimusPrecepts.deriveFont(20f));
        description.setForeground(Color.white);
        add(description, JLayeredPane.PALETTE_LAYER);

        int i = 0; // col

        for (JButton button : menuButton) {

            constraints = new GridBagConstraints(); // TODO MAKE THEM APPEAR IN ROWS 2 X 2
            constraints.gridx = 0;
            constraints.gridy = i;

            // System.out.printf("(%d,%d) \n", i,k);
            // Give buttons square shape
            constraints.ipadx = 10;
            constraints.ipady = 10;
            constraints.insets = new Insets(3, 5, 5, 5);
            buttonPane.add(button, constraints);
            button.addMouseListener(this);
            i++;

        }

    }

    @Override
    public void paintComponent(Graphics pen) { // method for painting
        super.paintComponent(pen);
        pen.drawImage(gradientBack, 0, 0, null);

    }

    @Override
    public void mouseClicked(MouseEvent e) { // https://www.youtube.com/watch?v=jptf1Wd_omw
        // Mouse button is clicked and relased on a component

    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Mouse button is clicked on a component

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Mouse button is released on a component

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Mouse enters a components area
        JButton button = (JButton) e.getSource();

        if (button == menuButton[0]) {
            // System.out.println("Button Entered 0");
            imagePane.setIcon(thumbNails[2]);// Error till I add the image to the folder
            description.setText(descriptions[0]);

        }
        if (button == menuButton[1]) {
            // System.out.println("Button Entered 0");
            imagePane.setIcon(thumbNails[1]);
            description.setText(descriptions[1]);

        }
        if (button == menuButton[2]) {
            // System.out.println("Button Entered 0");
            imagePane.setIcon(thumbNails[0]);
            description.setText(descriptions[2]);

        }

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Mouse exits a components area
        description.setText("Select a Game and Get Playing!");
    }

}
