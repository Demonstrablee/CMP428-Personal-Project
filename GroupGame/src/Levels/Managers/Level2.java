package Levels.Managers;
import java.awt.*;
import javax.swing.JLayeredPane;
import Characters.Characters.Player;
/** Initialize a level inhertiting from JPanel*/
public abstract class Level2 extends JLayeredPane{

    // the file names of paths will change base on this
    protected String os = System.getProperty("os.name"); // check os of user
    
    //Level Vars
    private String name;
    protected static Player p1 = new Player(850,240, 90, 50);

    // Constraints
    protected GridBagConstraints constraints = new GridBagConstraints();

    

//CONSTRUCTORS
    public Level2(String name){
        setLayout(null);
        setVisible(false);
        setBounds(0, 0, 1280, 720);
        this.name = name;
    }



 @Override
    public void paintComponent(Graphics pen){
           super.paintComponent(pen);
	}

    /**Reset the level custom reset for each level */
    public void reset(){
        
    }


//GET LEVEL OBJECTS
 
    @Override
    public String toString(){
        return this.name;

    }

    /** Get the name of the level */
    public String getLevelName() {
       return this.name;
    }
}
