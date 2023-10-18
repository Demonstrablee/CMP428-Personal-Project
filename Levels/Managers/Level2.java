package Levels.Managers;

import Objects.Wall;

import java.awt.*;

/** Initialize a level inhertiting from JPanel*/
public abstract class Level2{
    //Level Vars
    private Level enterance = null; // node ahead
    private Level exit = null; // node behind
    
    protected Wall [] wall = {new Wall(100, 10, 600, 80),new Wall(150, 500, 100, 150),new Wall(790, 70, 100, 150)};

    public Wall[] getWalls() {
        return wall;
    }



    // background
    protected Image bg;
    


    public Level2(Wall [] wall){
        this.wall = wall; // pas refrence to wall into Level
    }

    public void draw(Graphics pen){
           
	}
        
    public void update (Graphics pen){

    }

         

    // getters and setters
    public Level getEnterance() {
        return enterance;
    }
    public Level getExit() {
        return exit;
    }  

    public void setEnterance(Level enterance) {
        this.enterance = enterance;
    }
    public void setExit(Level exit) {
        this.exit = exit;
    }
     //KEY LISTENER


    public Image getBg() {
        return bg;
    }

    

    /** Set the background image for a level */
    public void setBg(String path) {
       bg = Toolkit.getDefaultToolkit().getImage(path);;
    }
   
}
