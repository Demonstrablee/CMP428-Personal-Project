package Levels.Managers;

import java.awt.*;
import javax.swing.JLayeredPane;
import Characters.Characters.Car;
import Objects.Camera;

/** Initialize a level inhertiting from JPanel */
public abstract class Level extends JLayeredPane {

    // the file names of paths will change base on this
    protected String os = System.getProperty("os.name"); // check os of user

    // Level Vars
    private String name;
    protected static Car p1 = new Car((int) Camera.x, (int) Camera.y);

    // Constraints
    protected GridBagConstraints constraints = new GridBagConstraints();

    // CONSTRUCTORS
    public Level(String name) {
        setLayout(null);
        setVisible(false);
        setBounds(0, 0, 1280, 720);
        this.name = name;
    }

    @Override
    public void paintComponent(Graphics pen) {
        super.paintComponent(pen);
    }

    /** Reset the level custom reset for each level */
    public void reset() {

    }

    /** pause the level */
    public void pause() {

    }

    /** continue the game */
    public void resume() {

    }
    // GET LEVEL OBJECTS

    @Override
    public String toString() {
        return this.name;

    }

    /** Get the name of the level */
    public String getLevelName() {
        return this.name;
    }
}
