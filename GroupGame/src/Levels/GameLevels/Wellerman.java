package Levels.GameLevels;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.Border;

import Levels.Managers.Level2;
import Objects.Wall;

public class Wellerman extends Level2 implements ActionListener{ 
    int x_origin = 0;
    int y_origin = 200;

    // grid descriptions
    int gridWidth = 10; // x
    int gridHeight = 7; // y
    JButton [][] grid = new JButton[gridWidth][gridHeight];
    boolean [][] pressedButton = new boolean[gridWidth][gridHeight];
    int [][] gridPipeType = new int[gridWidth][gridHeight];

    JLabel gridbg = new JLabel();

    JLabel scoreLabel = new JLabel("Score: ",SwingConstants.CENTER); //https://stackoverflow.com/questions/6810581/how-to-center-the-text-in-a-jlabel
    int scoreVal= 0;
    int distVal = 0;

    // positons for the source water pipe
    int startx;
    int starty;
    // pos for the end water pipe
    int endx;
    int endy;
    //hopper
    JButton [] hopper = new JButton[5];
    int [] hopperPipeType = new int [5];
    JLabel hopperbg = new JLabel(" ");

    // pipe designations (how they are read in)
    final int UPDN  = 14;
    final int RTLT = 5;

    final int EL_UPRT = 11;
    final int EL_UPLT = 12;
    final int EL_DNRT = 4;
    final int EL_DNLT = 8;

    final int WAY4 = 13;

    final int WAY3_UP = 7;
    final int WAY3_DN = 1;
    final int WAY3_LT = 10;
    final int WAY3_RT = 0;

    final int START_UP = 6;
    final int START_DN = 3;
    final int START_LT = 9;
    final int START_RT = 2;

    // pipe compatablity

    // [top,bottom,left,right]
    boolean compatSTRAIGHT[] = new boolean[]{true ,true ,false, false}; // UPDN ~RTLT  
    boolean compatELBOW1[] = new boolean[]{true,false,true,false}; //UPLEFT ~DOWN RIGHT
    boolean compatELBOW2[] = new boolean[]{false,true,false,true}; //UPRIGHT ~DOWN LEFT

    boolean compat3WAYUP[] = new boolean[]{true,false,true,true}; // compat3way UP ~ start/end dn
    boolean compat3WAYDN[] = new boolean[]{false,true,true,true}; // compat3way DN ~start/end up
    boolean compat3WAYRT[] = new boolean[]{true,true,false,true};  // compat3way rt ~ start/end left
    boolean compat3WAYLT[] = new boolean[]{true,true,true,false}; // compat3way lt ~start/end right

    boolean compat4way[] = new boolean[]{true,true,true,true}; 

   //TODO:  somethings wrong with one of the elbows and check the rest of the pipes for compatability
    boolean compatArray [][] = new boolean[][] {compat3WAYRT,compat3WAYDN,invert(compat3WAYLT),invert(compat3WAYUP),compatELBOW2,invert(compatSTRAIGHT),invert(compat3WAYUP),compat3WAYUP,invert(compatELBOW2),invert(compat3WAYRT),compat3WAYLT,compatELBOW2,compatELBOW1,compat4way,compatSTRAIGHT};
 
    // for a pipe to be compatable with another 
    // a top must meet the bottom of another pipe
    // a bottom must meet the top of another pipe
    // a left must meet the right of another pipe
    // a right must meet the left of another pipe
     

    // THE icons for the start and end pipes
    int START_ICON = 0;
    int END_ICON = 0;

    //Current Source for the water
    int [] currSource = new int[] {startx,starty};
    int currSourcePipeType = 0;
    int milisec = 15000; // 1 sec = 1000 milisec
    Timer waterTimer; // thank you bro code https://www.youtube.com/watch?v=0cATENiMsBE
    boolean isOver = false; // is the game over
   

    //Fonts
    Font arcadeFont;


    //Images 
    String assetDir = "GroupGame/src/images/WELLERMAN/";
    Image bg = Toolkit.getDefaultToolkit().getImage(assetDir+ "Clouds 2/1.png");
    Image fg1 = Toolkit.getDefaultToolkit().getImage(assetDir+"Clouds 2/2.png");
    Image fg2Cloud = Toolkit.getDefaultToolkit().getImage(assetDir+ "Clouds 2/3.png");
    Image fg3 = Toolkit.getDefaultToolkit().getImage(assetDir+ "Clouds 2/4.png");
    Image spaceBg = Toolkit.getDefaultToolkit().getImage(assetDir +"Space Background 1280_720 green.png");

    //BUTTON IMAGES
    File emptyDir = new File(assetDir + "PIPES/EMPTYPIPES");
    File [] emptyPipes = emptyDir.listFiles();
    ImageIcon [] emptyPipeIcons = new ImageIcon[emptyPipes.length];

    File fullDir = new File(assetDir+ "PIPES/FULLPIPES");
    File [] fullPipes = fullDir.listFiles();
    ImageIcon [] fullPipeIcons = new ImageIcon[emptyPipes.length];
    
    Random gen = new Random(); // num generator

    //SCORE PANE
        JPanel scoreBoard = new JPanel();

    //TODO: GAME OVER
        JPanel gameOPanel = new JPanel();

    //Grid
        JPanel gridPane = new JPanel();

    // Hopper
        JPanel hopperPane = new JPanel();


    




    public Wellerman(Level2 enter, Level2 exit){
        super(enter, exit, "wellerman");
        setLayout(null); // so I can use the pos from the set bounds functions to place compnents all over the panel

        try { //https://www.youtube.com/watch?v=43duJsYmhxQ 
            arcadeFont = Font.createFont(Font.TRUETYPE_FONT, new File("GroupGame/src/fonts/PixelifySans-VariableFont_wght.ttf")).deriveFont(30f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment(); //get the graphics for the computer 
            ge.registerFont(arcadeFont); // register the font for use
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

             // generate image icons for all pipes
        for(int i = 0; i < emptyPipes.length; ++i){
            System.out.println(emptyPipes[i]+ " "+ i);
            if(emptyPipes[i].getName().equals(".DS_Store")){emptyPipes[i].delete();} // these ds file are messing up the arrays offsetting them
            Image pipe = Toolkit.getDefaultToolkit().getImage(emptyPipes[i]+ "").getScaledInstance(55, 60, Image.SCALE_SMOOTH);
            ImageIcon pipeIcon = new ImageIcon(pipe);
            emptyPipeIcons[i] = pipeIcon; // store image icon in this icon array
        }

        for(int i = 0; i < fullPipes.length; ++i){
            System.out.println(fullPipes[i]+ " "+ i);
            if(fullPipes[i].getName().equals(".DS_Store")){fullPipes[i].delete();} // these ds file are messing up the arrays offsetting them
            Image pipe = Toolkit.getDefaultToolkit().getImage(fullPipes[i]+ "").getScaledInstance(55, 60, Image.SCALE_SMOOTH);
            ImageIcon pipeIcon = new ImageIcon(pipe);
            fullPipeIcons[i] = pipeIcon; // store image icon in this icon array
        }

   // SCORE

        scoreLabel.setText("SCORE: "+ scoreVal + "  DIST: "+ distVal);
        scoreLabel.setFont(arcadeFont);
       // scoreLabel.setFont(new Font("Serif", Font.PLAIN,20) );
        scoreLabel.setBackground(Color.RED);
        scoreLabel.setOpaque(true);
  
        scoreBoard.setLayout(new GridBagLayout());
        scoreBoard.setBounds(200,0,860,100);
        constraints = new GridBagConstraints();
        constraints.ipadx = 500;
        constraints.ipady = 40;
        
        //constraints.insets = new Insets(100, 10, 10, 10);

        scoreBoard.add(scoreLabel,constraints);
        scoreBoard.setBackground(Color.GRAY);
        scoreBoard.setVisible(true);

        add(scoreBoard);



      //GRID
        gridPane.setBounds(200, 100, 860, 550);
        gridPane.setOpaque(false);
        gridPane.setBackground(Color.RED);
  

        constraints = new GridBagConstraints();
        Border gridBorder = BorderFactory.createLineBorder(Color.BLUE,10);
        gridPane.setBorder(gridBorder);
        gridPane.setLayout(new GridBagLayout());
        add(gridPane);

        //HOPPER
        hopperPane.setBounds(70, -40, 100, 500);
        hopperPane.setLayout(new GridBagLayout());
        hopperPane.setOpaque(false);
        add(hopperPane);
        // CREATE GRID

       
        // gridbg.setPreferredSize(new Dimension(500, 500));
        // gridbg.setOpaque(true);
        // gridbg.setBackground(Color.GRAY);

      

       //add(gridPane);

        for (int col = 0; col < gridWidth; col++){
             for (int row = 0; row < gridHeight; row++){
                grid[col][row] = new JButton();
                grid[col][row].setPreferredSize(new Dimension(40,30));
                grid[col][row].addActionListener(this);
                
                //Constraints
                constraints = new GridBagConstraints();
                constraints.gridx = col + 1;
                constraints.gridy= row;
                constraints.ipadx = 30;
                constraints.ipady = 40;
                constraints.insets = new Insets(-1, -1, -1, -1);
                //TODO: Add some design elements to the buttons to make them look better

                gridPane.add(grid[col][row], constraints); // put on screen
                pressedButton[col][row] = false; // populate array with false (button not pressed)

            }
        }



         //Create HOPPER
         for (int row = 0; row < 5; row++){
                hopper[row] = new JButton();
                hopper[row].setPreferredSize(new Dimension(50,45));
           
                constraints = new GridBagConstraints();
                constraints.gridx = 0;
                constraints.gridy= row;
                constraints.ipadx = 30;
                constraints.ipady = 30;
                //constraints.insets = new Insets(-1, -1, -1, 50);
                hopper[row].setEnabled(false);// cant click on any button but the last one
                if(row == 4) hopper[row].setEnabled(true);
                hopperPane.add(hopper[row], constraints); // put on screen

            }

      
        //start up operations
        placeStartandEnd();
        fillHopper();
        waterTimer = new Timer(milisec, waterRises); // initiate the timer
        //waterTimer.start(); // start the water filling!

        //GAME OVER PANEL
        gameOPanel.setBounds(0,0,300,150);
        gameOPanel.setVisible(true);
        //add(gameOPanel);
          

    }
        /** put the start position on the grid */
      public void placeStartandEnd(){  
        boolean startPlaced = false;
        boolean endPlaced = false;
         

        while (startPlaced == false) {
            startx =  4;//gen.nextInt(0,gridWidth);
            starty =  0;//gen.nextInt(0, gridHeight);
            
            // place the start pipe 
            if(startx <= gridWidth && starty == 0){
                START_ICON = START_UP;
                startPlaced = true;
            }
            else if(startx <  gridWidth && starty == gridHeight-1){
                START_ICON = START_DN;
                startPlaced = true;
            }
            
            else if(startx ==  gridWidth-1 && starty < gridHeight){
                START_ICON = START_RT;
                startPlaced = true;
            }

            else if(startx == 0  && starty < gridHeight){
                START_ICON = START_LT;
                startPlaced = true;
            }
        }
        
        grid[startx][starty].setIcon(emptyPipeIcons[START_ICON]);
        grid[startx][starty].setDisabledIcon(emptyPipeIcons[START_ICON]);
        gridPipeType[startx][starty] = START_ICON; // kinda pipe at this location
        grid[startx][starty].setEnabled(false); // you cant click on it accidently change it
        System.out.println("Start is at: X- "+ startx +" Y- "+ starty);
        
        // place the end pipe
        while (endPlaced == false) {
            
            endx =  gen.nextInt(0,gridWidth);
            endy =  gen.nextInt(0, gridHeight);
            
            // place the start pipe 
            if(endx != startx || endy != starty){ // makes sure the start and the end positions arent the same
                if(endx <= gridWidth && endy == 0){
                    END_ICON = START_UP;
                    endPlaced = true;
                }
                else if(endx <  gridWidth && endy == gridHeight-1){
                    END_ICON = START_DN;
                    endPlaced = true;
                }
                
                else if(endx ==  gridWidth-1 && endy < gridHeight){
                    END_ICON = START_RT;
                    endPlaced = true;
                }

                else if(endx == 0  && endy < gridHeight){
                    END_ICON = START_LT;
                    endPlaced = true;
                }
            }
        }
        grid[endx][endy].setIcon(emptyPipeIcons[END_ICON]); // make icon the end icon
        grid[endx][endy].setDisabledIcon(emptyPipeIcons[END_ICON]); // make disable icon end icon
        gridPipeType[endx][endy] = END_ICON; // what kinda pipe is at this location
        grid[endx][endy].setEnabled(false); // disable so you cant click on it accidently change it
        System.out.println("End is at: X- "+ endx +" Y- "+ endy);
        // set the source pipe
        currSource[0] = startx;
        currSource[1] = starty;
    }

     public void fillHopper(){
        int hoppElem = 0;
        while(hopper.length > hoppElem){
            int pipeNum = gen.nextInt(15);
            switch(pipeNum){ // make sure none of the start pipes are in the hopper
                case 6:
                case 3: 
                case 9:
                case 2:
                    break;
                default:
                hopper[hoppElem].setIcon(emptyPipeIcons[pipeNum]);
                hopperPipeType[hoppElem] = pipeNum; // number associated with the that image icon
                hoppElem++;
            
            //hopper[i].setIcon(emptyPipeIcons[gen.nextInt(15)]); // fill the hopper with random pipes
            }
        }
        
     }
   
     public void placePipe(JButton hopperInput, JButton gridButton){
        ImageIcon pipeIc = (ImageIcon) hopperInput.getIcon(); // take the icon
        gridButton.setIcon(pipeIc);
        for (int col = 0; col < gridWidth; col++){ // find the button pressed in the grid
            for (int row = 0; row < gridHeight; row++){
             if(gridButton == grid[col][row]){
                updateScore(col,row);
                pressedButton[col][row] = true; // ONLY AFTER you update the score set the button pressed corresponding location to true
                gridPipeType[col][row] = hopperPipeType[4]; // the pipe at index four is the one always deployed onto the grid
                System.out.println(hopperPipeType[4]);
                break; // get out of the loop
             }
            }
        }

        
        shiftHopper(); // shift the elements down by one
     }
     private void shiftHopper(){  
 
       JButton [] hopperTemp =  new JButton[]{new JButton(),new JButton(),new JButton(),new JButton(),new JButton()};
       int [] hoppNumTemp = new int[5];
       boolean hoppFull = false;

        for(int i= 0; i< hopper.length-1; ++i){ // shift the hopper down 1 store this into a temp
            hopperTemp[i + 1].setIcon(hopper[i].getIcon());
            
            hoppNumTemp[i+1] = hopperPipeType[i];
            
        }
         for(int i= 0; i< hopper.length; ++i){ // make the og hopper icons change
            hopper[i].setIcon(hopperTemp[i].getIcon());

        }

        while(hoppFull == false){
            int pipeNum = gen.nextInt(15);
            switch(pipeNum){ // make sure none of the start pipes are in the hopper
                case 6:
                case 3: 
                case 9:
                case 2:
                    break;
                default:
                hopper[0].setIcon(emptyPipeIcons[pipeNum]); // fill hopper with random pipe
                hoppNumTemp[0] = pipeNum; // set index 0 the top to the new num associated with that pipe
                hoppFull = true;
            }

        }



        hopperPipeType = hoppNumTemp; // change pointer of array to new array
        hopper[4].setIcon(hopperTemp[4].getIcon()); // change active pipe
        
    }


       ActionListener waterRises = new ActionListener() { // action to be preformed 
            public void actionPerformed(ActionEvent e) {
                waterFlow();
        }

    };

    private void setSource(int x, int y){
        currSource[0] = x;
        currSource[1] = y;
        
    }


    //**flip to the alternate full pipe image icon of the pipe at the locaction (x,y) on the grid *//
     private void flipToFullPipeAt(int x, int y){ 
        grid[x][y].setDisabledIcon(fullPipeIcons[gridPipeType[x][y]]); // full pipe ICONS array INDEX CANNOT BE NEGATIVE
        grid[x][y].setEnabled(false); // disable the button so you cant click it
        gridPipeType[currSource[0]][currSource[1]] = -1; // make this so rge previous pipe is set to -1 water cant back flow here
        // gridPipeType[x][y] = -1; // make this so water cant back flow here
        setSource(x, y); // change what the water source block is
        distVal++; // increset the distance traveled
     }
     private void waterFlow(){ // change the empyt pipe for a full pipe
        // only in the positions where the button has been pressed and is connected to the source
        //if(currSource[0]== startx && currSource[1]== starty && gridPipeType[startx][starty] != -1) // only runs at the begining TODO: ERROR STILL ON RERUN
            //flipToFullPipeAt(currSource[0],currSource[1]); // start pipe fill then whaterver the source is so on
        // if the button next to the source has been pressed
        //to the right of the source 
        if((currSource[0]+ 1 >= 0) && (currSource[0]+ 1 < gridWidth) && pressedButton[currSource[0]+ 1][currSource[1]]== true && gridPipeType[currSource[0]+ 1][currSource[1]] != -1 
        && compatable(compatArray[gridPipeType[currSource[0]][currSource[1]]][2],compatArray[gridPipeType[currSource[0]+ 1][currSource[1]]][3])){ 
            System.out.println("Current Source of Type: "+ gridPipeType[currSource[0]][currSource[1]]);
            System.out.println("Checking Pipe to the right of source of Type: "+ gridPipeType[currSource[0]+1][currSource[1]]);
                flipToFullPipeAt(currSource[0]+1,currSource[1]);
                System.out.println("Pipe found pipe to the right source at x:  "+  (currSource[0]) + " y: " + currSource[1]);
                

            
            
        } // to the left of the source if not at a boundary
        else if((currSource[0]- 1 >= 0) && (currSource[0]- 1 < gridWidth) && (pressedButton[currSource[0]- 1][currSource[1]]== true) && gridPipeType[currSource[0]- 1][currSource[1]] != -1
        && compatable(compatArray[gridPipeType[currSource[0]][currSource[1]]][3],compatArray[gridPipeType[currSource[0]-1][currSource[1]]][2])){         // checking pipe compatablity
            System.out.println("Current Source of Type: "+ gridPipeType[currSource[0]][currSource[1]]);
            System.out.println("Checking Pipe to the left of source of Type: "+ gridPipeType[currSource[0]-1][currSource[1]]);
    
                flipToFullPipeAt(currSource[0]-1,currSource[1]);
                System.out.println("Pipe found to the left of source at x:  "+  (currSource[0]) + " y: " + currSource[1]); // curent source already modded
            
        }
        //below
        else if((currSource[1]+ 1 >= 0) && (currSource[1]+ 1 < gridHeight) && pressedButton[currSource[0]][currSource[1]+ 1]== true && gridPipeType[currSource[0]][currSource[1]+1] != -1
        && compatable(compatArray[gridPipeType[currSource[0]][currSource[1]]][1],compatArray[gridPipeType[currSource[0]][currSource[1]+1]][0])){
            System.out.println("Current Source of Type: "+ gridPipeType[currSource[0]][currSource[1]]);
            System.out.println("Checking Pipe below source of Type: "+ gridPipeType[currSource[0]][currSource[1]+ 1]);

            // checking pipe compatablity
                flipToFullPipeAt(currSource[0],currSource[1] + 1);
                System.out.println("Pipe found below source at x:  "+ currSource[0] + " y: " +(currSource[1]));
        }
        // above
        else if((currSource[1]- 1 >= 0) && (currSource[1]- 1 < gridHeight) && pressedButton[currSource[0]][currSource[1] -1]== true && gridPipeType[currSource[0]][currSource[1]- 1] != -1
        && compatable(compatArray[gridPipeType[currSource[0]][currSource[1]]][0],compatArray[gridPipeType[currSource[0]][currSource[1]-1]][1])){
            System.out.println("Current Source of Type: "+ gridPipeType[currSource[0]][currSource[1]]);
            System.out.println("Checking Pipe above source of Type: "+ gridPipeType[currSource[0]][currSource[1]- 1]);
       
                flipToFullPipeAt(currSource[0],currSource[1]- 1);
                System.out.println("Pipe found above source at x:  "+ currSource[0] + " y: " + (currSource[1]));
            
        }else{ // source stays the same

            System.out.println("Pipe not Found: GAME OVER");
            isOver = true;
        }
        
        System.out.println("Blub, blub, the water is rising!");
     }


        private void finalScore(){
        
    }
    private void updateScore(int col, int row){
        // if the player places a pipe on a square already occupied
         
        if(pressedButton[col][row] == true){ 
            scoreVal-= 100;}
        else{
            scoreVal+= 100;
        }
         
     }

    private void clearBoard(){
          for (int col = 0; col < gridWidth; col++){
             for (int row = 0; row < gridHeight; row++){
                grid[col][row].setIcon(null); // get rid of the icon on the board
            }
        }
    }

    @Override
     public void reset(){  
        clearBoard();
        scoreVal = 0; // reset the players score
        //resetScore();
     }
     // invert
    private boolean [] invert(boolean [] arr1){
        boolean arr2 [] = new boolean [arr1.length];
        for (int i = 0; i < arr2.length; i++){
            arr2[i] = !arr1[i];
        }
        return arr2;
    }

    // check if the pipes are compatable 
    private boolean compatable(boolean bool1, boolean bool2){
        if(bool1 == false || bool2 == false){
            return false;
        }
        return true;
    }

    public void paintComponent(Graphics pen){  //method for painting
        super.paintComponent(pen);
        //pen.clearRect(0, 0, getWidth(), getHeight());


 
            pen.drawImage(spaceBg,x_origin-x_origin,y_origin-y_origin ,getWidth(), getHeight(),null);
          
           // pen.drawImage(this.bg,x_origin-x_origin,y_origin-y_origin ,getWidth(), getHeight(),null);
           // pen.drawImage(this.fg1,97,53 ,1085, 530,null);
           // pen.drawImage(this.fg2Cloud, + x_origin, + y_origin ,getWidth(), 530,null);
            //pen.drawImage(this.fg3,97,53 ,1085, 530,null);

           // gameOPanel.setVisible(isOver);
            // if(isOver){
            //     gameOPanel.setVisible(true);
            //     add(gameOPanel); // ERROR
            //     waterTimer.stop();

            // }
        

  
    }



    @Override
    public void actionPerformed(ActionEvent e) {
       JButton buttonClick = (JButton) e.getSource();
        placePipe(hopper[4], buttonClick); // will handel score updates too
        scoreLabel.setText("SCORE: "+ scoreVal + "  DIST: " + distVal);
        waterFlow();
    }

    


  

    
}
 