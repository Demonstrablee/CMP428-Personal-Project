package Characters;

import java.awt.Graphics;


public class PlayerCharacter extends Character{

    
    public PlayerCharacter(int x, int y , int w, int h) {
        super(x,y,w,h);
            
        }
    public void moveBy(int dx, int dy){ 
        x += dx;
        y += dy;
    }
    @Override
    public void draw(Graphics pen){
            super.draw(pen);
    }

    public int getxPos() {
        return (int)x;
    }
    public void setxPos(int x) {
        this.x = x;
    }
    public int getyPos() {
        return (int)y;
    }
    public void setyPos(int y) {
        this.y = y;
    }
    

    
}
