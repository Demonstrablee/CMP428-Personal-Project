package Characters.Characters;
// import Levels.Managers.Level;
import Levels.Managers.Level2;


import java.awt.*;

import Characters.Sprite;


public class Character extends Sprite{
    //private static int numStudent =+ 1; // track the student created
   

    public Character(String name, String[] pose, int imagecount, int start, String filetype, int x, int y, int w, int h, int scale){
        super(name, pose,imagecount, start, filetype, x, y, w, h, scale);
        


    }
  

    public void draw (Graphics pen){
        super.draw(pen);
    }




}
