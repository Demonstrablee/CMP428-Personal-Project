package Levels.GameLevels;
import java.awt.*;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import Characters.Characters.Student;
import Levels.Managers.Level2;
import Objects.Wall;

public class Wellerman extends Level2{ 
    int x_origin = 0;
    int y_origin = 200;

    // grid descriptions
    int gridWidth = 10;
    int gridHeight = 7;
    JButton [][] grid = new JButton[gridWidth][gridHeight];
    JLabel gridbg = new JLabel();

    JLabel SCORE = new JLabel("score: "); 
  

    //hopper
    JButton [][] hopper = new JButton[1][5];
    JLabel hopperbg = new JLabel(" ");
    int straight;

    //Images 
    Image bg = Toolkit.getDefaultToolkit().getImage("GroupGame/src/images/Clouds 2/1.png");
    Image fg1 = Toolkit.getDefaultToolkit().getImage("GroupGame/src/images/Clouds 2/2.png");
    //Image fg2 = Toolkit.getDefaultToolkit().getImage("GroupGame/src/images/Clouds 2/3.png");
    Image fg3 = Toolkit.getDefaultToolkit().getImage("GroupGame/src/images/Clouds 2/4.png");
 
    public Wellerman(Level2 enter, Level2 exit){
        super(enter, exit, "wellerman");

        setBg("bg_classroom02.jpg");
        setBackground(Color.BLACK);
        setBounds(0,0,1280,720);
        


        //Create HOPPER
          for (int row = 0; row < 5; row++){
                hopper[0][row] = new JButton();
                hopper[0][row].setPreferredSize(new Dimension(40,30));
                constraints = new GridBagConstraints();
                constraints.gridx = 0;
                constraints.gridy= row;
                constraints.ipadx = 30;
                constraints.ipady = 30;
                constraints.insets = new Insets(-1, -1, -1, 50);
                add(hopper[0][row], constraints); // put on screen

            }

        // CREATE GRID

        // constraints = new GridBagConstraints();
        // gridbg.setPreferredSize(new Dimension(500, 500));
        // gridbg.setOpaque(true);
        // gridbg.setBackground(Color.RED);

        // add(gridbg);

        for (int col = 0; col < gridWidth; col++){
             for (int row = 0; row < gridHeight; row++){
                grid[col][row] = new JButton();
                grid[col][row].setPreferredSize(new Dimension(40,30));
                constraints = new GridBagConstraints();
                constraints.gridx = col + 1;
                constraints.gridy= row;
                constraints.ipadx = 30;
                constraints.ipady = 40;
                constraints.insets = new Insets(-1, -1, -1, -1);
                add(grid[col][row], constraints); // put on screen

            }
        }



     
      
        // add students to 
       //students = new Student[]{stacy, marcus};

        // Level Exit Set
        setLevelExitPos(new int[] {800,490,100,25});
        
        // Set Walls
        wall = new Wall[]{new Wall(100, 500, 1080, 80), //bottom
            new Wall(100, 50, 1080, 80), //top
            new Wall(100, 50, 80, 530), // left
            new Wall(1100, 50, 80, 530)}; //right

   
    }
    

   
    public void paintComponent(Graphics pen){  //method for painting
        super.paintComponent(pen);
        //pen.clearRect(0, 0, getWidth(), getHeight());
        
 
            pen.drawImage(this.bg,x_origin-x_origin,y_origin-y_origin ,getWidth(), getHeight(),null);
            pen.drawImage(this.fg1,97,53 ,1085, 530,null);
            //pen.drawImage(this.fg2, + x_origin, + y_origin ,getWidth(), 530,null);
            pen.drawImage(this.fg3,97,53 ,1085, 530,null);
        
        
        // for(Wall walls : wall){
        //     walls.setColor(Color.GREEN);
        //     walls.draw(pen);
        // }

  
    }

    


  

    
}
 