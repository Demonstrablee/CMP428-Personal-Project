package Levels.Managers;


import Objects.Rect;
import Objects.Wall;

import java.awt.*;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;


import Characters.Characters.Enemy;
import Characters.Characters.Player;


//import Characters.Characters.PlayerCharacter;

/** Initialize a level inhertiting from JPanel*/
public abstract class Level2 extends JLayeredPane{

    // the file names of paths will change base on this
    protected String os = System.getProperty("os.name"); // check os of user
    
    //Level Vars
    private Level2 enterance = null; // node ahead
    private Level2 exit = null; // node behind
    int [] lEnterP = new int [4]; // describes the rect that acts as the entrance for the level
    int [] lExitP = new int[4];
    protected Rect dRectEx = null;
    protected Rect dRectEnter = null;
    protected static Player p1 = new Player(850,240, 90, 50);


    // Constraints
    protected GridBagConstraints constraints = new GridBagConstraints();

    //Background
    protected Image bg;
    String bgPath;
    String name;

    //Level Objects
    protected Wall [] wall;


    protected Enemy [] enemies;

//CONSTRUCTORS
    public Level2(Level2 enter, Level2 exit, String name){
        setLayout(new GridBagLayout());
        setBackground(Color.BLACK);
        setVisible(false);

        this.enterance = enter;
        this.exit = exit;
        this.name = name;
         
    }



 @Override
    public void paintComponent(Graphics pen){
           super.paintComponent(pen);
	}

    // getters and setters

    //ENTERANCE GETTERS
    /**
     * @return the Level represntation of the enterance
     */
    public Level2 getEnterance() {
        return enterance;
    }  
    /**
     * Returns the array of the x position, y position, width and hight 
     * that describe the enterance for the level
     *  @return the array of postion and size values for the levels entrance
     */
    public int[] getLevelEntrancePos() {
            return lEnterP;
    }

    /**Get the rectangle that represents the levels enterance 
     * @returns null if the rectangle is not defined
    */
    public Rect getEnterRect(){
    
        return dRectEnter;
    }





//EXIT GETTERS
    /**
     * @return the Level represntation of the exit
     */
    public Level2 getExit() {
        return this.exit;
    }  
      /**
     * Returns the array of the x position, y position, width and hight 
     * that describe the exit for the level
     *  @return the array of postion and size values for the levels entrance
     */
    public int[] getLevelExitPos() {
            return lExitP;
        }
  
    /**Get the rectangle that represents the levels exit 
     * @returns null if the rectangle is not defined
    */
    public Rect getExitRect(){
        return dRectEx; // stil return the null
        
        //return dRectEx;
    } 

    public void reset(){
        
    }


//SETTERS
    /** Set the level representation of the levels enterance */
    public void setEnterance(Level2 enterance) {
        this.enterance = enterance;
    } 
    /**
     * Set the location of the enterance point in the level dRectEnter will be
     * created from these points
     * @param lEnterP
    */
    public void setLevelEnterPos(int[] lEnterP) {
        this.lEnterP = lEnterP;
        dRectEnter = new Rect(lEnterP[0], lEnterP[1], lEnterP[2], lEnterP[3]);
        //dRectEnter = new Sprite("DOOR", new String[]{"IDLE"},1, 0, "png", lExitP[0], lExitP[1], lExitP[2], lExitP[3]);
        dRectEnter.setColor(Color.cyan);
    }

    /** Set the level representation of the levels exit */
    public void setExit(Level2 exit) {
        this.exit = exit;
    }

    /**
     * Set the location of the exit point in the level dRectEx will be
     * created from these points
     * @param lExitP
    */
    public void setLevelExitPos(int[] lExitP) {
        this.lExitP = lExitP;
        dRectEx = new Rect(lExitP[0], lExitP[1], lExitP[2], lExitP[3]);
        //dRectEx = new Sprite("DOOR", new String[]{"IDLE"},1, 0, "png", lExitP[0], lExitP[1], lExitP[2], lExitP[3]);
        dRectEx.setColor(Color.MAGENTA);
    }



  

//GET LEVEL OBJECTS
    public Enemy [] getEnemies(){
        return enemies;
    }
  

    /** Get the array of walls for a the level */
    public Wall[] getWalls() {
        return wall;
    }


//SET LEVEL OBJECTS
    
    public Wall[] setWalls() {
        return wall;
    }


 /**
     * @return the levels name
     */
    public String getName() {
        return name;
    }



//FILE PATHS
  /** Set the background image for a level 
     * @param path file name
    */
    public void setBg(String path) {
        String pathFolder;
        if (os.contains("Mac"))pathFolder = "GroupGame/src/images/"; // mac
        else pathFolder ="GroupGame\\src\\images\\"; // windows
        
        bgPath = path;

        bg = Toolkit.getDefaultToolkit().getImage(pathFolder + path);
    }

    /** Set the background image for a level 
     * @param Image refrence to the image file
    */
    public void setBgWImage(Image background) {

        bg = background;
    }

    /** Get the file path of the background */
     public String getBgPath() {
        return bgPath;
    }


    @Override
    public String toString(){
        return this.name;

    }
}
