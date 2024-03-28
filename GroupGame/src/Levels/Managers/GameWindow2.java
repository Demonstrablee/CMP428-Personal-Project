package Levels.Managers;

import javax.swing.JFrame;

import Characters.Characters.Dealer;
import Characters.Characters.Car;

import Objects.Rect;

import Objects.Wall;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Toolkit;

public class GameWindow2 extends JFrame implements KeyListener, Runnable, ActionListener {
    Thread t;

    static String os = System.getProperty("os.name"); // check os of user

    public static int track = 1; // control the music playing
    public static boolean buttonPress = false;

    // Screen Managment Variables
    int currResMode = 1; // change to change the resolution
    int prevResMode = 1; // what to revert to
    boolean fullScreen = false; // use full screen?

    /// Managers
    // SimpleScreenManager screen = new SimpleScreenManager(); // the screen manager
    LevelBuilderPanel lbPane = new LevelBuilderPanel();

    DisplayMode displayFullScreenModes[] = {
            new DisplayMode(640, 480, 32, DisplayMode.REFRESH_RATE_UNKNOWN),
            new DisplayMode(1280, 720, 32, DisplayMode.REFRESH_RATE_UNKNOWN),
            new DisplayMode(1920, 1080, 32, DisplayMode.REFRESH_RATE_UNKNOWN),
            new DisplayMode(2560, 1440, 32, DisplayMode.REFRESH_RATE_UNKNOWN),

    };

    final int[][] winRes = { { 640, 480 }, { 1280, 720 }, { 1920, 1080 }, { 2560, 1440 } };

    // App icon

    static String appFolder = os == "Mac" ? "GroupGame/src/images/appIcon/" : "GroupGame\\src\\images\\appIcon\\";

    /**
     * Get the path to the image folder for the game file path changes depending on
     * the users Operation System
     * 
     * @return the appFolder
     */
    static public String getAppFolder() {
        return appFolder;
    }

    public Image appIcon = Toolkit.getDefaultToolkit().getImage("GroupGame/src/images/appIcon/paper_plane.png");

    // Movement Vars
    boolean[] pressing = new boolean[1024];

    // Levels

    public GameWindow2() {

        this.setIconImage(appIcon); // set the app icon for the game
                                    // https://stackoverflow.com/questions/209812/how-do-i-change-the-default-application-icon-in-java

        setTitle("¡VOLER!");

        // contentPane = this.getLayeredPane();

        setSize(winRes[currResMode][0], winRes[currResMode][1]);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setResizable(false);
        setVisible(true);

        getContentPane().add(lbPane);

        lbPane.init();// comment in and out to allow for updates to post or just run with F5

        // init(); // manages the music
        // SimpleSoundPlayer.playSoundForever("GroupGame/src/music/TitleScreenTheme.wav");
        // // only works on mac

    }

    public void init() {

        addKeyListener(this);

        requestFocus();

        t = new Thread(this);

        t.start();

    }

    @Override // Music management
    public void run() {

        while (true) { // TODO the song is going to play out totally until it is finished then switch
            // button pressing sound
            if (buttonPress == true)
                SimpleSoundPlayer.playSound("GroupGame/src/music/actionSounds/click (pixabay).wav");

            switch (track) {
                case 1:// Title screen
                    SimpleSoundPlayer.playSound("GroupGame/src/music/as-smart-as-a-cat (pixabay).wav"); // only works on
                                                                                                        // mac
                    break;

                case 2:// baccano
                    SimpleSoundPlayer.playSound("GroupGame/src/music/gangsta-music-theme-kriss (pixabay).wav"); // only
                                                                                                                // works
                                                                                                                // on
                                                                                                                // mac
                    break;

                case 3:// Long trip drift
                    SimpleSoundPlayer.playSound("GroupGame/src/music/The Best Jazz Club In New Orleans (pixabay).wav");
                    break;

                case 4: // wellerman
                    SimpleSoundPlayer.playSound("GroupGame/src/music/space-chillout (pixabay).wav");
                    break;

                default:
                    SimpleSoundPlayer.playSound("GroupGame/src/music/actionSounds/click (pixabay).wav"); // only works
                                                                                                         // on mac
                    break;
            }

            repaint();

            try {
                Thread.sleep(15);

            } catch (Exception e) {

            }

        }

    }

    /**
     * @return the user Opeation System
     */
    public static String getOs() {
        return os;
    }

    @Override
    public void paint(Graphics pen) {
        // pen.clearRect(0, 0, getWidth(), getHeight());
        // setContentPane(lbPane);
        // contentPane.paintComponents(pen);
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
