package Levels.Managers;

import Characters.Characters.Car;
import Levels.GameLevels.LongTripDrift;
import Levels.GameLevels.Baccano;
import Levels.GameLevels.Wellerman;
import Levels.Menus.RulesMenu;
import Levels.Menus.GameSelectMenu;
import Levels.Menus.OptionsMenu;
import Levels.Menus.PauseMenu;
import Levels.Menus.TitleScreen;
import Objects.Camera;
import fonts.fontsRegistry;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.border.Border;

;

/** Initialize a level inhertiting from JPanel */
public class LevelBuilderPanel extends JLayeredPane implements KeyListener, Runnable, ActionListener {

    Thread t;

    // ACTIONS
    Action upAction;
    Action downAction;
    Action leftAction;
    Action rightAction;

    // Buttons

    static JButton[] titleButtons = new JButton[3];
    static JButton[] pauseMButtons = new JButton[5];
    static JButton optionsPauseButton = new JButton();
    static JButton rulesButton = new JButton();
    static JButton[] gameSelectButtons = new JButton[4]; // the games you can select from

    static JButton longTripDriftB; // button to exit
    JButton wellermanExitB;
    JButton baccanoExitB;

    // Layout
    CardLayout cLayout0 = new CardLayout();

    // Objects
    Car p1 = Level.p1; // so all levels can share same character

    // Menus
    static TitleScreen titleScreen;
    static PauseMenu pauseMenu;
    static OptionsMenu optionsMenu;
    static RulesMenu rulesMenu;
    static GameSelectMenu gameSelectMenu;

    // Levels
    static Wellerman wellerman;
    static LongTripDrift longTripDrift;
    static Baccano baccano;

    // Movement vars
    boolean[] pressing = new boolean[1024];

    /** States when the game is paused state (happens while in any menu) */
    boolean isPaused;

    // Movement Variables
    static final int UP = KeyEvent.VK_UP;
    static final int DN = KeyEvent.VK_DOWN;
    static final int LT = KeyEvent.VK_LEFT;
    static final int RT = KeyEvent.VK_RIGHT;
    static final int Q = KeyEvent.VK_Q;

    static final int W = KeyEvent.VK_W;
    static final int A = KeyEvent.VK_A;
    static final int S = KeyEvent.VK_S;
    static final int D = KeyEvent.VK_D;

    /** the currrent level that is being displayed */
    Level currLevel; // for paintmethod

    /** the current non-menu level selected in the game */
    Level gameRoom;
    /**
     * boolean that determines if the options menu will direct to the title screen
     * or
     * the pause menu
     */
    boolean titleOrGame;

    // Button Stylings
    Border border = BorderFactory.createLineBorder(Color.RED);

    public void init() {

        System.out.println("Init method in LevelBuilder activated");

        // MUST HAPPEN BEFORE LOAD ELEMENTS (needed for games to load)
        fontsRegistry.registerFonts(); // register the fonts used in the games

        loadElements();

        addKeyListener(this);

        requestFocus();

        t = new Thread(this);

        t.start();

    }

    /**
     * Loads all the elements of the game, creating buttons menus, and levels, and
     * adding
     * them to the JPanel of the LevelBuilder Class
     */
    public void loadElements() {

        setBounds(0, 0, 1280, 720); // display size

        // initiallizing buttons
        String[] buttonN = new String[] { "RESUME", "OPTIONS", "QUIT", "RETURN TO TITLE", "BACK", "SAVE GAME", "START",
                "RULES", "BACCANO!", "WELLERMAN", "LONG TRIP DRIFT" };
        createButton(new String[] { buttonN[6], buttonN[1], buttonN[2] }, titleButtons); // TITLE SCREEN
        createButton(new String[] { buttonN[0], buttonN[1], buttonN[3], buttonN[7], buttonN[2] }, pauseMButtons); // PAUSE-MENU

        optionsPauseButton = createButton(); // OPTIONS
        rulesButton = createButton(); // Rules Menu
        longTripDriftB = createButton();

        createButton(new String[] { buttonN[8], buttonN[9], buttonN[10], buttonN[4] }, gameSelectButtons);

        // Intializing
        titleScreen = new TitleScreen(titleButtons);
        pauseMenu = new PauseMenu(pauseMButtons);
        optionsMenu = new OptionsMenu(optionsPauseButton);

        rulesMenu = new RulesMenu(rulesButton);
        gameSelectMenu = new GameSelectMenu(gameSelectButtons);

        wellermanExitB = createButton();
        wellerman = new Wellerman(wellermanExitB);

        baccanoExitB = createButton();
        baccano = new Baccano(baccanoExitB);

        longTripDrift = new LongTripDrift(longTripDriftB);

        // ADDING Levels To Level builder Panel
        // OVERLAY menu
        add(pauseMenu);

        // Menus (curlevel)
        add(titleScreen);
        add(optionsMenu, JLayeredPane.DRAG_LAYER);

        add(rulesMenu);
        add(gameSelectMenu);

        // game levels (game room)
        add(wellerman);
        add(longTripDrift);
        add(baccano);

        // Game State variables AT START

        currLevel = titleScreen; // which room to draw currLevel and levLevel index are one to one (default:
        // titleScreen)
        gameRoom = longTripDrift; // track of the in game rooms that player traverses with p1 (default;
        // wellereman)

        isPaused = true; // is the game paused or not (default: true)
        titleOrGame = true; // at game start options goes to pause menu (default: true)

        currLevel.setVisible(true);

    }

    @Override
    public void run() {
        System.out.println("Game Started");
        while (true) { // game loop

            gameLoop();

            repaint(); // draw the panel

            try {
                Thread.sleep(15);

            } catch (Exception e) {

            }
        }

    }

    public void gameLoop() {

        if (isPaused == false) {
            if (pressing[UP] || pressing[W]) {
                // System.out.println("Camera position x:"+ Camera.x + " y:"+ Camera.y);
                Camera.moveForward(5);

                // p1.moveForward(2);
            }
            if (pressing[DN] || pressing[S]) {
                // System.out.println("Camera position x:"+ Camera.x + " y:"+ Camera.y);
                Camera.moveForward(-5);

                // p1.moveForward(-2);
            }
            if (pressing[RT] || pressing[D]) {
                // System.out.println("Camera position x:"+ Camera.x + " y:"+ Camera.y);
                Camera.turnBy(5);
                p1.turnRight(5);

            }

            if (pressing[LT] || pressing[A]) {
                // System.out.println("Camera position x:"+ Camera.x + " y:"+ Camera.y);
                Camera.turnBy(-5);
                p1.turnLeft(5);

            }
        }
    }

    @Override
    public void paint(Graphics pen) { // WOW rather than paint AWT make this paintComponent and everything is good
        pen.clearRect(0, 0, getWidth(), getHeight());
        super.paint(pen);

    }

    /** Create the buttons that will be in the menus */
    public void createButton(String[] arr, JButton[] buttons) {
        JButton nButton;
        for (int i = 0; i < arr.length; ++i) {
            nButton = new JButton(arr[i]);
            nButton.addActionListener(this);
            nButton.setFocusable(false);

            // Button Stylings

            nButton.setBorder(border);
            nButton.setBackground(Color.LIGHT_GRAY);

            nButton.setOpaque(true);
            buttons[i] = nButton; // add button to array
        }
    }

    // make a singular button // mostly for exiting screens
    public JButton createButton() {
        JButton button = new JButton();

        // Button Stylings
        button.setBorder(border);
        button.setBackground(Color.LIGHT_GRAY);
        button.setOpaque(true);

        button.addActionListener(this);
        button.setFocusable(false); // STOP FROM stealing focus from keyboard inputs
        button.setOpaque(true);

        return button;
    }

    // KEY LISTENER
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        // System.out.println(e.getKeyChar());

        pressing[e.getKeyCode()] = true;

        if (e.getKeyCode() == KeyEvent.VK_P && currLevel != titleScreen && currLevel != gameSelectMenu && optionsMenu.isVisible() == false) { // if P is
                                                                                                          // pressed
                                                                                                          // pause the
                                                                                                          // game
            if (!isPaused) {
                gameRoom.pause(); // pause the timer so wa
                pauseMenu.setVisible(true);
                isPaused = true; // pause the game
                titleOrGame = false; // game has already started (gonna need to go back to game)
                System.out.println("Paused via key press");

            } else { // go back to the game
                gameRoom.resume();
                pauseMenu.setVisible(false);
                isPaused = false; // pause the game
                System.out.println("UnPaused via key press");
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressing[e.getKeyCode()] = false;
        // p1.moving = false;
    }

    private void changeLevel(Level level) {
    
        currLevel.setVisible(false); // make previous room invisible
        currLevel = level; // last game room (currLevel is what is used to draw the levels in the paint
                           // method) change levels
        currLevel.setVisible(true); // make current room visible
        if (!(level.getLevelName()).equals("pauseMenu")) { // so the overlay of the pause menu doesnt draw over all the
                                                           // levels
            pauseMenu.setVisible(false);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Object buttonClicked = e.getSource();
        GameWindow2.buttonPress = true;

        if (buttonClicked == titleButtons[0] || buttonClicked == wellermanExitB || buttonClicked == baccanoExitB
                || buttonClicked == longTripDriftB) { // go to game select
            changeLevel(gameSelectMenu);
            GameWindow2.track = 1; // change the music playing
            gameRoom.setVisible(false); // important to make whatever game is active stop showing up
            isPaused = true;

        } else if (buttonClicked == pauseMButtons[0]) {// go to resume
            changeLevel(gameRoom);// last game room (currLevel is what is used to draw the levels in the paint
                                  // method) change levels
            gameRoom.resume();
            // which level to switch to based on what the last game room you were in
            isPaused = false;
            titleOrGame = false; // game started make option menu go to the game with pause mnenu overlay

        }

        else if (buttonClicked == titleButtons[1] || buttonClicked == pauseMButtons[1]) {// go to options
            // changeLevel(optionsMenu);
            optionsMenu.setVisible(true);
            pauseMenu.setVisible(false);

        } else if (buttonClicked == pauseMButtons[2] || buttonClicked == optionsPauseButton && titleOrGame
                || buttonClicked == gameSelectButtons[3]) { // back to title screen
            optionsMenu.setVisible(false);
            changeLevel(titleScreen);
            // reset player health
            isPaused = true;
            titleOrGame = true; // game ended make pause menu go to the title menu
        } else if (buttonClicked == pauseMButtons[4] || buttonClicked == titleButtons[2]) { // quit
            System.exit(0);
        } else if ((buttonClicked == optionsPauseButton && !titleOrGame) || buttonClicked == rulesButton) { // go to
                                                                                                            // pause
                                                                                                            // screen
            optionsMenu.setVisible(false);
            rulesMenu.setVisible(false);

            pauseMenu.setVisible(true);
            isPaused = true; // pause the game
            System.out.println("Paused");
        } else if (buttonClicked == gameSelectButtons[0]) {// start up baccano
            changeLevel(baccano);
            GameWindow2.track = 2; // change the music playing
            rulesMenu.setMenu(0);
            gameRoom = baccano;
            gameRoom.reset();
            isPaused = false;

        } else if (buttonClicked == gameSelectButtons[1]) { // start up wellerman
            changeLevel(wellerman);
            GameWindow2.track = 4; // change the music playing
            gameRoom = wellerman;
            rulesMenu.setMenu(2);
            gameRoom.reset();
            wellerman.resume(); // so timer doesnt go on in the title scren
            isPaused = false;

        } else if (buttonClicked == gameSelectButtons[2]) { // start up long trip drift
            changeLevel(longTripDrift);
            GameWindow2.track = 3; // change the music playing
            gameRoom = longTripDrift;
            rulesMenu.setMenu(1);
            gameRoom.reset();
            longTripDrift.resume();
            isPaused = false;

        } else if (buttonClicked == pauseMButtons[3]) { // show the rules
            pauseMenu.setVisible(false);
            rulesMenu.setVisible(true);
        }

        GameWindow2.buttonPress = false; // so the sound doesnt keep looping

    }

}
