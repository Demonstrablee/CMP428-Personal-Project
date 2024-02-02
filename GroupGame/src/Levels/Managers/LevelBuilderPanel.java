package Levels.Managers;

import Characters.Characters.Enemy;
import Characters.Characters.Player;
import Levels.GameLevels.LongTripDrift;

import Levels.GameLevels.Baccano;
import Levels.GameLevels.Wellerman;
import Levels.Menus.GameOverMenu;
import Levels.Menus.GameSelectMenu;
import Levels.Menus.OptionsMenu;
import Levels.Menus.PausePhoneMenu;
import Levels.Menus.SaveMenu;
import Levels.Menus.TitleScreen;
import Objects.Camera;
import Objects.Wall;
import fonts.fontsRegistry;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Graphics;

import java.awt.event.*;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;

import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.border.Border;

;

/** Initialize a level inhertiting from JPanel*/
public class LevelBuilderPanel extends JLayeredPane implements KeyListener, Runnable, ActionListener, MouseListener{
    
    Thread t;
 
    //ACTIONS
    Action upAction;
    Action downAction;
    Action leftAction;
    Action rightAction;
    
    //Buttons
   
    static JButton [] titleButtons = new JButton[3];
    static JButton [] pauseMButtons = new JButton[5];
    static JButton [] toPauseButtons = new JButton[2];
    static JButton [] gameOverButtons = new JButton[3];
    static JButton [] gameSelectButtons = new JButton[4]; // the games you can select from 

    static  JButton [] longTripDriftB = new JButton[2]; // buttons to exit
    JButton wellermanExitB;
    JButton baccanoExitB;

    //Layout
    CardLayout cLayout0 = new CardLayout();

    
    //Objects
    Player p1 = Level2.p1; // so all levels can share same character
    Wall [] wall; // get walls for refrence and collison detection
    

    Enemy [] enemies;
   
    //Menus
    static TitleScreen titleScreen; // 0
    static PausePhoneMenu pauseMenu; //1
    static OptionsMenu optionsMenu; // 2
    static SaveMenu saveMenu; //3
    static GameOverMenu gameOverMenu;
    static GameSelectMenu gameSelectMenu;

    //Levels
    static Wellerman wellerman; //4
    static LongTripDrift longTripDrift; //5
    static Baccano baccano;  // 6
    
    // Movement vars
    boolean[] pressing = new boolean[1024];

    /**States when the game is paused state (happens while in any menu)*/
    boolean isPaused; 
    /** States whether or not the game is over  */
    boolean isOver = false;

    static final int UP = KeyEvent.VK_UP;
    static final int DN = KeyEvent.VK_DOWN;
    static final int LT = KeyEvent.VK_LEFT;
    static final int RT = KeyEvent.VK_RIGHT;
    static final int Q = KeyEvent.VK_Q;

    static final int W = KeyEvent.VK_W;
    static final int A = KeyEvent.VK_A;
    static final int S = KeyEvent.VK_S;
    static final int D = KeyEvent.VK_D;
    
    /**the currrent level that is being displayed  */
    Level2 currLevel; // for paintmethod
   
  
    /** the current non-menu level selected in the game */
    Level2 gameRoom;
    /** boolean represneting if the options menu will direct to the title screen or the pause menu */
    boolean titleOrPause;

   

    public void init()
        {   
           //setDoubleBuffered(true); (redundant)

           System.out.println("Init method in LevelBuilder activated");


           // MUST HAPPEN BEFORE LOAD ELEMENTS (needed for games to load)
           fontsRegistry.registerFonts(); // register the fonts used in the games 
            
           loadElements();
           
           addKeyListener(this);
 

           //addMouseListener(this);

           requestFocus();
            
           t = new Thread(this);
            
           t.start();
            
           

        }

        /** Loads all the elements of the game, creating buttons menus, and levels, and adding 
         * them to the JPanel of the LevelBuilder Class
         */
        public void loadElements(){
             
            setBounds(0, 0, 1280, 720); // display size
       
          
            // initiallizing buttons
            String [] buttonN = new String[] {"RESUME", "OPTIONS", "QUIT", "RETURN TO TITLE", "BACK", "SAVE GAME", "START", "TRY AGAIN","BACCANO!", "WELLERMAN","LONG TRIP DRIFT" };
            createButton(new String [] {buttonN[6],buttonN[1],buttonN[2]}, titleButtons); // TITLE SCREEN
            createButton(new String [] {buttonN[0],buttonN[5],buttonN[1],buttonN[3], buttonN[2]}, pauseMButtons); //PAUSE MENU 
            createButton(new String [] {buttonN[4],buttonN[4]}, toPauseButtons); // SAVE AND OPTIONS
            createButton(new String [] {buttonN[7],buttonN[3],buttonN[2]}, gameOverButtons);
            createButton(new String [] {buttonN[8],buttonN[9],buttonN[10],buttonN[4]}, gameSelectButtons);
           
            createButton(new String[] {buttonN[2],buttonN[2]},longTripDriftB);

            //Intializing
            titleScreen = new TitleScreen(titleButtons);
            pauseMenu = new PausePhoneMenu(pauseMButtons);
            optionsMenu = new OptionsMenu(toPauseButtons[1]);
            saveMenu = new SaveMenu(toPauseButtons[0]); 
            gameOverMenu = new GameOverMenu(gameOverButtons);
            gameSelectMenu = new GameSelectMenu(gameSelectButtons);


            wellermanExitB = createButton();
            wellerman = new Wellerman(wellermanExitB);

            baccanoExitB = createButton();
            baccano = new Baccano(baccanoExitB);

            longTripDrift = new LongTripDrift(longTripDriftB);
      

            //ADDING Levels To Level builder Panel
            // OVERLAY menu
            add(pauseMenu);

            // Menus (curlevel)
            add(titleScreen);
            add(optionsMenu);
            add(saveMenu);
            add(gameOverMenu);
            add(gameSelectMenu);

            // game levels (game room)
            add(wellerman);
            add(longTripDrift);
            add(baccano);
            
               //instances
        // upAction = new UpAction();
        // downAction = new DownAction();
        // leftAction = new LeftAction();
        // rightAction = new RightAction();

        // p1.getInputMap().put(KeyStroke.getKeyStroke('w'),"upAction"); //assign the up action key to the keyword "upaction"
        // p1.getActionMap().put("upAction",upAction); // when"upAction" keyword is triggred do the action in the var called upAction

        // p1.getInputMap().put(KeyStroke.getKeyStroke('s'),"downAction"); //assign the up action key to the keyword "upaction"
        // p1.getActionMap().put("downAction",upAction); // when"upAction" keyword is triggred do the action in the var called upAction
            
        // p1.getInputMap().put(KeyStroke.getKeyStroke('a'), "leftAction");

		// p1.getActionMap().put("leftAction", leftAction);

		// p1.getInputMap().put(KeyStroke.getKeyStroke('d'), "rightAction");

		// p1.getActionMap().put("rightAction", rightAction);

            // Game State variables AT START
            currLevel = baccano; // which room to draw currLevel and levLevel index are one to one (default: titleScreen)
            gameRoom = wellerman; // track of the ingame rooms that player traverses with p1 (default; wellereman)

            isPaused = false; // is the game paused or not (default: true)
            titleOrPause = false; // at game start options goes to pause menu (default: true)
            isOver = false; // is the game over? (only have to change this for gameover window debug) (default: false)
             
            currLevel.setVisible(true);
            
        }

      
    @Override
        public void run() {
            System.out.println("Game Started");
            while(true){ //game loop
                 
                gameLoop();

                repaint(); // draw the panel

                try{
                    Thread.sleep(15);

                }catch(Exception e){

                }  
            }
             
            
        }



        public void gameLoop(){
                   
                    //MOVEMENT
                    
                    if (pressing[UP] || pressing[W]) {
                       // System.out.println("Camera position x:"+ Camera.x + " y:"+ Camera.y);

                        Camera.moveForward(5); 
                       // p1.moveForward(2);
                    }
                    if (pressing[DN] || pressing[S]) {
                        //System.out.println("Camera position x:"+ Camera.x + " y:"+ Camera.y);
                        Camera.moveForward(-5); 
                        //p1.moveForward(-2);
                    }
                    if (pressing[RT] || pressing[D]) {
                        //System.out.println("Camera position x:"+ Camera.x + " y:"+ Camera.y);
                        Camera.turnBy(5);
                   
                        p1.turnRight(5);
                    }
                        
                    //if(p1.getX() > 500)Camera.goRT(3);}

                    if (pressing[LT]|| pressing[A]) {
                        //System.out.println("Camera position x:"+ Camera.x + " y:"+ Camera.y);
                        Camera.turnBy(-5); 
                        p1.turnLeft(5);
                    }

                   
                   
        }
    @Override
        public void paint(Graphics pen){ // WOW rather than paint AWT make this paintComponent and everything is good  
            pen.clearRect(0, 0, getWidth(), getHeight());
            super.paint(pen);
                
            
	    }
    
        /** Create the buttons that will be in the menus  */
        public void createButton(String [] arr, JButton [] buttons){
            JButton nButton;
            Border border = BorderFactory.createBevelBorder(0);
            
            for (int i = 0 ; i < arr.length; ++i){
                nButton = new JButton(arr[i]);
                nButton.addActionListener(this);
                nButton.setFocusable(false);

                //Button Stylings

                nButton.setBorder(border);
                nButton.setBackground(Color.LIGHT_GRAY);
        
                nButton.setOpaque(true);
                buttons[i] = nButton; // add button to array
            }
        }
        // make a singular button // mostly for exiting screens
        public JButton createButton(){
        JButton button = new JButton();
        button.addActionListener(this);
        button.setFocusable(false);

        return button;
        }
       
     //KEY LISTENER
    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //System.out.println(e.getKeyChar());  

        pressing[e.getKeyCode()] = true;
       
        if(e.getKeyCode() == KeyEvent.VK_P){ // if P is pressed pause the game
            if(!isPaused){
                
                pauseMenu.setVisible(true);
                isPaused = true; // pause the game

                System.out.println("Paused via key press");
            
            }else{ // go back to the game
                currLevel.setVisible(false); // should be pause menu
                isPaused = false; // pause the game
                System.out.println("UnPaused via key press");
        }
    }


    }
    @Override
    public void keyReleased(KeyEvent e) {
       pressing[e.getKeyCode()] = false;
        //p1.moving = false;
    }


    @Override
    public void mouseClicked(MouseEvent e) {

         }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) { 
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    private void changeLevel(Level2 level){
        currLevel.setVisible(false); // make previous room invisible
        currLevel = level; // last game room (currLevel is what is used to draw the levels in the paint method) change levels
        currLevel.setVisible(true); // make current room visible
         if(!(level.getLevelName()).equals("pauseMenu")){ // so the overlay of the pause menu doesnt draw over all the levels 
                pauseMenu.setVisible(false);
            }
        
    }


    @Override
    public void actionPerformed(ActionEvent e) {
       
        Object buttonClicked = e.getSource();
        GameWindow2.buttonPress = true;
          
    if (buttonClicked == titleButtons[0] || buttonClicked == wellermanExitB || buttonClicked == baccanoExitB || buttonClicked == longTripDriftB[0] ||  buttonClicked == longTripDriftB[1]  ){ // go to game select
            changeLevel(gameSelectMenu); 
            GameWindow2.track = 1; // change the music playing 
            gameRoom.setVisible(false); // important to make whatever game is active stop showing up
            isPaused = true;  
              
    }
		else if(buttonClicked == pauseMButtons[0]|| buttonClicked == gameOverButtons[0]) {// go to resume
            changeLevel(gameRoom);// last game room (currLevel is what is used to draw the levels in the paint method) change levels
 
                
            // which level to switch to based on what the last game room you were in
            isPaused = false;
            titleOrPause = false; //game started make option menu go to the pause menu
            isOver = false;
        } 
       else if(buttonClicked == pauseMButtons[1]){ // go to save 
            changeLevel(saveMenu);
     
        }
       else if(buttonClicked == titleButtons[1] || buttonClicked == pauseMButtons[2]){// go to options
            changeLevel(optionsMenu);
     
       }
       else if(buttonClicked == pauseMButtons[3] || buttonClicked == toPauseButtons[1] && titleOrPause || buttonClicked == gameOverButtons[1]|| buttonClicked == gameSelectButtons[3]){ // back to title screen
            changeLevel(titleScreen);
           
            // reset player health
           
            isPaused = true;
            isOver = false;
            titleOrPause = true; //game ended make pause menu go to the title menu
        }
       else if(buttonClicked == pauseMButtons[4]|| buttonClicked == titleButtons[2]|| buttonClicked == gameOverButtons[2] ){ // quit
            System.exit(0);
        } 
       else if(buttonClicked == toPauseButtons[0]||(buttonClicked == toPauseButtons[1] && !titleOrPause)){ // pause screen
            pauseMenu.setVisible(true);
            isPaused = true; // pause the game
            System.out.println("Paused");
        } 
        else if(buttonClicked == gameSelectButtons[0]){// start up baccano
            changeLevel(baccano);
            GameWindow2.track = 2; // change the music playing 
            gameRoom = baccano; 
            gameRoom.reset();
            isPaused = false;

        }else if (buttonClicked == gameSelectButtons[1]){ // start up wellerman
            changeLevel(wellerman);
            GameWindow2.track = 4; // change the music playing 
            gameRoom = wellerman;
            gameRoom.reset();
            wellerman.waterTimer.start(); // so timer doesnt go on in the title scren
            isPaused = false;

        }else if (buttonClicked == gameSelectButtons[2]){ // start up long trip cliff
            changeLevel(longTripDrift);
            GameWindow2.track = 3; // change the music playing 
            gameRoom = longTripDrift;
            gameRoom.reset();
            isPaused = false;

        }
       
        GameWindow2.buttonPress = false; // so the sound doesnt keep looping

        
        }

    //     //KEY BINDINGS TO CONTROL CHARACTER MOVEMENT
    // public class UpAction extends AbstractAction{ //https://www.youtube.com/watch?v=IyfB0u9g2x0&t=636s

    //     @Override
    //     public void actionPerformed(ActionEvent e) {
    //       System.out.println("UP ARROW WAS PRESSED");
    //     }
        
    // }

    // public class DownAction extends AbstractAction{

    //     @Override
    //     public void actionPerformed(ActionEvent e) {
    //         System.out.println("DOWN ARROW WAS PRESSED");
    //     }
        
    // }

    // public class RightAction extends AbstractAction{

    //     @Override
    //     public void actionPerformed(ActionEvent e) {
    //         System.out.println("RIGHT ARROW WAS PRESSED");
    //     }
       
    // }

    // public class LeftAction extends AbstractAction{

    //     @Override
    //     public void actionPerformed(ActionEvent e) {
    //         System.out.println("LEFT ARROW WAS PRESSED");
    //     }
        
    // }

  
}
