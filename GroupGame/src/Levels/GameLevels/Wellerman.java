package Levels.GameLevels;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import javax.swing.JLabel;

import Levels.Managers.Level2;
import Objects.Wall;

public class Wellerman extends Level2 implements ActionListener{ 
    int x_origin = 0;
    int y_origin = 200;

    // grid descriptions
    int gridWidth = 10; // x
    int gridHeight = 7; // y
    JButton [][] grid = new JButton[gridWidth][gridHeight];

    JLabel gridbg = new JLabel();

    JLabel SCORE = new JLabel("score: "); 
  

    int startx;
    int starty;
    int endx;
    int endy;
    //hopper
    JButton [] hopper = new JButton[5];
    JLabel hopperbg = new JLabel(" ");

    // pipe designations (how they are read in)
    final int UPDN  = 14;
    final int RTLT = 8;

    final int EL_UPRT = 7;
    final int EL_UPLT = 4;
    final int EL_DNRT = 12;
    final int EL_DNLT = 11;

    final int WAY4 = 13;

    final int WAY3_UP = 10;
    final int WAY3_DN = 3;
    final int WAY3_LT = 6;
    final int WAY3_RT = 2;

    final int START_UP = 5;
    final int START_DN = 1;
    final int START_LT = 9;

    //all in order
    // final int START_RT = 0;
    // final int START_DN = 1;
    // final int WAY3_RT = 2;
    // final int WAY3_DN = 3;
    // final int EL_UPLT = 4;
    // final int START_UP = 5;
    // final int WAY3_LT = 6;
    // final int EL_UPRT = 7;
    // final int RTLT = 8;
    // final int START_LT = 9;
    // final int WAY3_UP = 10;
    // final int EL_DNLT = 11;
    // final int EL_DNRT = 12;
    // final int WAY4 = 13;
    // final int UPDN  = 14;



    //Images 
    Image bg = Toolkit.getDefaultToolkit().getImage("GroupGame/src/images/Clouds 2/1.png");
    Image fg1 = Toolkit.getDefaultToolkit().getImage("GroupGame/src/images/Clouds 2/2.png");
    Image fg2Cloud = Toolkit.getDefaultToolkit().getImage("GroupGame/src/images/Clouds 2/3.png");
    Image fg3 = Toolkit.getDefaultToolkit().getImage("GroupGame/src/images/Clouds 2/4.png");
 
    //BUTTON IMAGES
    File emptyDir = new File("GroupGame/src/images/PIPES/EMPTYPIPES");
    File [] emptyPipes = emptyDir.listFiles();
    ImageIcon [] emptyPipeIcons = new ImageIcon[emptyPipes.length];

    File fullDir = new File("GroupGame/src/images/PIPES/FULLPIPES");
    File [] fullPipes = fullDir.listFiles();
    ImageIcon [] fullPipeIcons = new ImageIcon[emptyPipes.length];
    
    Random gen = new Random(); // num generator

    public Wellerman(Level2 enter, Level2 exit){
        super(enter, exit, "wellerman");

        //setBg("bg_classroom02.jpg");
        setBackground(Color.BLACK);
        setBounds(0,0,1280,720);
        

        // generate image icons for all pipes
        for(int i = 0; i < emptyPipes.length; ++i){
            System.out.println(emptyPipes[i]+ " "+ i);
            if(emptyPipes[i].getName().equals(".DS_Store")){emptyPipes[i].delete();} // these ds file are messing up the arrays offsetting them
            Image pipe = Toolkit.getDefaultToolkit().getImage(emptyPipes[i]+ "").getScaledInstance(55, 60, Image.SCALE_SMOOTH);
            ImageIcon pipeIcon = new ImageIcon(pipe);
            emptyPipeIcons[i] = pipeIcon; // store image icon in this icon array
        }

        for(int i = 0; i < fullPipes.length; ++i){
            //System.out.println(fullPipes[i]+ " "+ i);
            if(emptyPipes[i].getName().equals(".DS_Store")){emptyPipes[i].delete();} // these ds file are messing up the arrays offsetting them
            Image pipe = Toolkit.getDefaultToolkit().getImage(fullPipes[i]+ "").getScaledInstance(55, 60, Image.SCALE_SMOOTH);
            ImageIcon pipeIcon = new ImageIcon(pipe);
            fullPipeIcons[i] = pipeIcon; // store image icon in this icon array
        }



        //Create HOPPER
          for (int row = 0; row < 5; row++){
                hopper[row] = new JButton();
                hopper[row].setPreferredSize(new Dimension(40,30));
                hopper[row].addActionListener(this);
                constraints = new GridBagConstraints();
                constraints.gridx = 0;
                constraints.gridy= row;
                constraints.ipadx = 30;
                constraints.ipady = 30;
                constraints.insets = new Insets(-1, -1, -1, 50);
                hopper[row].setEnabled(false);// cant click on any button but the last one
                if(row == 4) hopper[row].setEnabled(true);
                add(hopper[row], constraints); // put on screen

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
                grid[col][row].addActionListener(this);
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

        //start up operations
        placeStartandEnd();
        fillHopper();


    }
        /** put the start position on the grid */
      public void placeStartandEnd(){ //TODO fix the fact that start and end could be in the same square or connected
        startx = gen.nextInt(0,gridWidth);
        starty = gen.nextInt(0, gridHeight);
        System.out.println("Start is at: X- "+ startx +" Y- "+ starty);
        
             
            grid[startx][starty].setIcon(emptyPipeIcons[START_UP]);
            grid[startx][starty].setDisabledIcon(emptyPipeIcons[START_UP]);
            grid[startx][starty].setEnabled(false); // you cant click on it accidently change it
         System.out.println("Start is at: X- "+ startx +" Y- "+ starty);
        
        endx = gen.nextInt(0,gridWidth);
        endy = gen.nextInt(0, gridHeight);
            grid[endx][endy].setIcon(emptyPipeIcons[START_UP]);
            grid[endx][endy].setDisabledIcon(emptyPipeIcons[START_UP]);
            grid[endx][endy].setEnabled(false); // you cant click on it accidently change it
        
    }

     public void fillHopper(){
        for(int i= 0; i< hopper.length; i++){
            hopper[i].setIcon(emptyPipeIcons[gen.nextInt(15)]); // fill the hopper with random pipes
        }
        
     }
   
     public void placePipe(JButton hopperInput, JButton gridButton){
        ImageIcon pipeIc = (ImageIcon) hopperInput.getIcon(); // take the icon
        gridButton.setIcon(pipeIc);
        shiftHopper(); // shift the elements down by one
     }
     private void shiftHopper(){ // TODO some reason the hopper is populating with all of the same pipes in all but the first slot
        for(int i= 0; i< hopper.length-1; ++i){ // but in game its placing diffrent pieces???
            hopper[i+1].setIcon((ImageIcon)hopper[i].getIcon()); // shift the images down by one
        
        }
        hopper[0].setIcon(emptyPipeIcons[gen.nextInt(15)]); // add some new element to the hopper


     }

    @Override
     public void reset(){
        
     }
    public void paintComponent(Graphics pen){  //method for painting
        super.paintComponent(pen);
        //pen.clearRect(0, 0, getWidth(), getHeight());
        
 
            pen.drawImage(this.bg,x_origin-x_origin,y_origin-y_origin ,getWidth(), getHeight(),null);
            pen.drawImage(this.fg1,97,53 ,1085, 530,null);
            pen.drawImage(this.fg2Cloud, + x_origin, + y_origin ,getWidth(), 530,null);
            pen.drawImage(this.fg3,97,53 ,1085, 530,null);
        
        
        // for(Wall walls : wall){
        //     walls.setColor(Color.GREEN);
        //     walls.draw(pen);
        // }

  
    }



    @Override
    public void actionPerformed(ActionEvent e) {
       JButton buttonClick = (JButton) e.getSource();
        placePipe(hopper[4], buttonClick);

    }

    


  

    
}
 