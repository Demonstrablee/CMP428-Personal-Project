package Levels.GameLevels;
import Levels.Managers.Level2;
import Levels.Managers.SimpleSoundPlayer;
import fonts.fontsRegistry;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import Characters.Characters.Enemy;

public class Baccano extends Level2 implements ActionListener { 
    JLabel title = new JLabel("BACCANO");
    String assetDir = "GroupGame/src/images/BACCANO/"; 
    Image table = Toolkit.getDefaultToolkit().getImage(assetDir+"casinoTablebg_red.jpeg");
    Image casinoPepe = Toolkit.getDefaultToolkit().getImage(assetDir+"casinoPeople.jpeg");

    Enemy dealer = new Enemy("DuckHuntDog",this.getLevelName(),400,30, 0,0, 15);
    
    //LABELS
        JLabel dealersLabel= new JLabel("Dealers Doubles");
        JLabel playersLabel= new JLabel("Players Doubles");
    //PANELS
    JPanel playersHand = new JPanel();
    JPanel playersDoubles = new JPanel();
    JPanel dealersDoubles = new JPanel();

    JPanel dealerPlayArea = new JPanel();// dont really need
   
    JButton dealerDie1Button = new JButton();
    JButton dealerDie2Button = new JButton();
    JButton dealerCardButton = new JButton();


    JPanel gameOPanel = new JPanel();
    JLabel gameOLabel = new JLabel("WASS", SwingConstants.CENTER);
    JButton restartButton = new JButton();

    //IMAGES
    String diceDir = "GroupGame/src/images/BACCANO/Dice/";
    ImageIcon [] dice = new ImageIcon[6];

    File cardDir = new File("GroupGame/src/images/BACCANO/Playing Cards");
    File [] cardFile = cardDir.listFiles();
    ImageIcon [] card = new ImageIcon[cardFile.length];

    //DRAW CARD BUTTON
    JButton drawButton = new JButton();
    Image cardBImage = Toolkit.getDefaultToolkit().getImage("GroupGame/src/images/BACCANO/card-back2.png").getScaledInstance(100, 150, Image.SCALE_SMOOTH); 
    ImageIcon cardBack = new ImageIcon(cardBImage);

    //RANDOM GENERATOR
    Random rand = new Random();
    int diceRoll1 = 0;
    int diceRoll2 = 0;

    //Game State Variables
    JButton [] playerCards = new JButton [52]; // oversized array (52 CARDS IN A DECK)
    int playerCardsSize = 9; // CANNOT START AT ZERO (LOSE CONDITION)
    JButton [] dealerCards = new JButton[52]; // oversized array (never gonna see this)
    int dealerCardsSize = 9; // CANNOT START AT ZERO (WIN CONDITION)

    JButton [] playerDice = new JButton[6];
    JButton [] dealerDice = new JButton[6];

    // TRACKS ARRAY ABOVE HOW FULL
    int playerDiceDub = 0;
    int dealerDiceDub = 0;


    JLabel turnLabel = new JLabel("PLAYERS TURN", SwingConstants.CENTER);

    //FONTS
    Font arcadeFont = fontsRegistry.arcadePixel;
        
    //Over Status
    private final int NOT_OVER = 0;
    private final int PLAYERS_TURN = 1;
    private final int DEALERS_TURN = 2;
    private final int WIN = 3;
    private final int LOSE = 4;
     
    int activePlayState = 1;  // 0- Start ; 1- Players Turn, 2- Dealers Turn, 3 - WIN OVER, 4- LOSE OVER


    public Baccano(JButton exitButton){
        super(null, null, "BACCANO");
        setLayout(null);

        //LOAD IN

          //Load in the images
        for(int i = 0; i< dice.length; i++){
           System.out.println(diceDir+ (i+1) + ".png "+ i);
            Image d = Toolkit.getDefaultToolkit().createImage(diceDir+ (i+1) + ".png").getScaledInstance(90, 90, Image.SCALE_SMOOTH); ;
            dice[i] = new ImageIcon(d);
        }

        for(int i = 0; i< cardFile.length; i++){
            //System.out.println(cardFile[i] + " "+ i);
            Image c = Toolkit.getDefaultToolkit().createImage(cardFile[i]+"").getScaledInstance(100, 150, Image.SCALE_SMOOTH); ;
            card[i] = new ImageIcon(c);
        }


        // ADD PANELS
        playersHand.setBackground(Color.YELLOW);
        playersHand.setOpaque(false);
        playersHand.setBounds(100, 500, 970, 200);
        add(playersHand);


        playersDoubles.setLayout(new GridBagLayout());
        playersDoubles.setBackground(Color.GREEN);
        playersDoubles.setOpaque(false);
        playersDoubles.setBounds(20, 20, 100, 350);
        add(playersDoubles);

        dealersDoubles.setBackground(Color.RED);
        dealersDoubles.setOpaque(false);
        dealersDoubles.setLayout(new GridBagLayout());
        dealersDoubles.setBounds(880, 20, 400, 100);
        add(dealersDoubles);

        //DEALER PLAY AREA
        // dealerPlayArea.setBackground(Color.WHITE);
        // dealerPlayArea.setBounds(900, 140, 350, 240);
        // add(dealerPlayArea);
        
        //DEALER CARD
        dealerCardButton.setBounds(1035, 160, 100, 150);
        dealerCardButton.addActionListener(this);
        //drawButton.setBorderPainted(false);
        dealerCardButton.setIcon(card[0]);
        dealerCardButton.setVisible(false);
        add(dealerCardButton);


        // DEALER DICE
        dealerDie1Button.setBounds(990, 200, 90, 90);
        dealerDie1Button.setBorderPainted(false);
        dealerDie1Button.setIcon(dice[2]);
        add(dealerDie1Button);

        dealerDie2Button.setBounds(1090, 200, 90, 90);
        dealerDie2Button.setBorderPainted(false);
        dealerDie2Button.setIcon(dice[0]);
        add(dealerDie2Button);


        //DRAW CARD Button
        drawButton.setBounds(1130, 440, 100, 150);
        drawButton.addActionListener(this);
        //drawButton.setBorderPainted(false);

        drawButton.setIcon(cardBack);
        add(drawButton);

   
        //Current Persons Turn

        turnLabel.setBounds(140, 20, 200, 60);
        turnLabel.setFont(arcadeFont.deriveFont(25f));
        turnLabel.setForeground(new Color(139,0,0));
        turnLabel.setBackground(Color.RED);
        turnLabel.setBorder(BorderFactory.createLineBorder(new Color(230,194,0), 5));
        turnLabel.setOpaque(true);
        
        add(turnLabel);


        //GAME OVER PANEL
       
       gameOPanel.setLayout(new GridBagLayout());
       gameOPanel.setBounds(200,200,860,200);
       gameOPanel.setBackground(new Color(0,0,0,200));
       gameOPanel.setVisible(false);
       gameOLabel.setFont(arcadeFont.deriveFont(90f));
       
       gameOLabel.setForeground(Color.white);
       constraints = new GridBagConstraints();
       constraints.gridx = 0;
       constraints.gridy = 0;
       gameOPanel.add(gameOLabel,constraints);

       constraints = new GridBagConstraints();
       constraints.gridx = 0;
       constraints.gridy = 1;
       restartButton.setText("RESTART");
       restartButton.addActionListener(this); 
      
    
       gameOPanel.add(restartButton,constraints);

       constraints = new GridBagConstraints();
       constraints.gridx = 0;
       constraints.gridy = 2;
       exitButton.setText("ESCAPE");
       gameOPanel.add(exitButton,constraints);
       add(gameOPanel);


          //Apply LABELS
          constraints = new GridBagConstraints();
          constraints.gridx = 0;
          playersLabel.setForeground(Color.white);
  
          playersDoubles.add(playersLabel,constraints);
  
          dealersLabel.setBounds(1050,20,200,10);
          dealersLabel.setForeground(Color.white);
  
          add(dealersLabel,JLayeredPane.DRAG_LAYER);
  
       // DOUBLE DICE
       for(int k = 0; k < playerDice.length; k++){
            playerDice[k] = new JButton();
            playerDice[k].setBorderPainted(false);
            playerDice[k].setPreferredSize(new Dimension(60, 55));
            playerDice[k].addActionListener(this);

            constraints = new GridBagConstraints();
            constraints.gridx = 0;
            constraints.gridy = k + 1;

            playersDoubles.add(playerDice[k],constraints);

            dealerDice[k] = new JButton();
            dealerDice[k].setBorderPainted(false);
            dealerDice[k].setPreferredSize(new Dimension(60, 55));

            constraints = new GridBagConstraints();
            constraints.gridx = k;
            constraints.gridy = 1;

            dealersDoubles.add(dealerDice[k],constraints);
        
       }

       //PLAYERS CARDS
        for(int h = 0; h< playerCardsSize; h++){
            playerCards[h] = new JButton();
            playerCards[h].setPreferredSize((new Dimension(100, 150)) );
            playerCards[h].setIcon(card[h]); //TODO MAKE RANDOM  CARDS FILL A PLAYERS HAND and prob give them tool tips
            playersHand.add(playerCards[h]);
        }



       // ACTIVATE what might need to be activated


        
    }
   
   @Override
    public void reset(){
        
    }

    public void rollDice(){
        diceRoll1 = rand.nextInt(1,6); //1-6 inclusive die (the indexes are 0-5)
        diceRoll2 = rand.nextInt(1,6); //1-6 inclusive

        dealerDie1Button.setIcon(dice[diceRoll1]);
        dealerDie1Button.setToolTipText(diceRoll1+ 1+ ""); //index is one less than the value of each die

        dealerDie2Button.setIcon(dice[diceRoll2]);
        dealerDie2Button.setToolTipText(diceRoll2+ 1+ "");

        if(diceRoll1 == diceRoll2){
            
            ImageIcon die = new ImageIcon(dice[diceRoll1].getImage().getScaledInstance(60, 65, Image.SCALE_SMOOTH));
            
            switch (activePlayState) { //TODO make a simple way to determine which die needs to be filled with the doubles
                case PLAYERS_TURN: // this works but its meh
                if(playerDiceDub < 6){
                    if(playerDiceDub < 2){
                        playerDice[0].setIcon(die);
                        playerDice[0].setToolTipText(diceRoll1+1+""); //index is one less than the value of each die
                        
                        playerDice[1].setIcon(die);
                        playerDice[1].setToolTipText(diceRoll1+1+"");
                    }else if(playerDiceDub < 4){
                        playerDice[2].setIcon(die);
                        playerDice[2].setToolTipText(diceRoll1+1+""); //index is one less than the value of each die
                        
                        playerDice[3].setIcon(die);
                        playerDice[3].setToolTipText(diceRoll1+1+"");
                    }else{
                        playerDice[4].setIcon(die);
                        playerDice[4].setToolTipText(diceRoll1+1+""); //index is one less than the value of each die
                        
                        playerDice[5].setIcon(die);
                        playerDice[5].setToolTipText(diceRoll1+1+"");
                    }
                        playerDiceDub+=2;
                    }
                    break;
            
                case DEALERS_TURN:
                if(dealerDiceDub < 6){
                    if(dealerDiceDub < 2){
                        dealerDice[0].setIcon(die);
                        dealerDice[0].setToolTipText(diceRoll1+1+""); //index is one less than the value of each die
                        
                        dealerDice[1].setIcon(die);
                        dealerDice[1].setToolTipText(diceRoll1+1+"");
                    }else if(dealerDiceDub < 4){
                        dealerDice[2].setIcon(die);
                        dealerDice[2].setToolTipText(diceRoll1+1+""); //index is one less than the value of each die
                        
                        dealerDice[3].setIcon(die);
                        dealerDice[3].setToolTipText(diceRoll1+1+"");
                    }else{
                        dealerDice[4].setIcon(die);
                        dealerDice[4].setToolTipText(diceRoll1+1+""); //index is one less than the value of each die
                        
                        dealerDice[5].setIcon(die);
                        dealerDice[5].setToolTipText(diceRoll1+1+"");
                    }
                        dealerDiceDub+=2;
                    }
                    break;
            }
        }

    }

    private void whoWon(){
        int playerScore = 0;
        int dealerScore = 0;

        // Check the total value of the doubles for the player
        for(int i = 0; i < 6; i++){
            if (playerDice[i].getToolTipText() != null){
                System.out.println(playerDice[i].getToolTipText());
                playerScore += Integer.parseInt(playerDice[i].getToolTipText()); // pull the interger value from the dice 
            }
             // and the doubles for the dealer
            if (dealerDice[i].getToolTipText()!= null){
                System.out.println(dealerDice[i].getToolTipText());
                dealerScore += Integer.parseInt(dealerDice[i].getToolTipText()); // pull the interger value from the dice 
            }

        }

        //determine who won the game
        activePlayState = playerScore > dealerScore ? WIN : LOSE;
        System.out.println("Player Score: " + playerScore); 
        System.out.println("Dealer Score: " + dealerScore); 
    }

   
    public void paintComponent(Graphics pen){  //method for painting
        super.paintComponent(pen);//component that does the painting 
        Graphics2D pen2D = (Graphics2D) pen;
        //Draw Background

        pen.drawImage(casinoPepe,0,-100,getWidth(), getHeight(),null);
        
        //Draw the dealer
        dealer.draw(pen);
        //draw the table
        pen.drawImage(table,0,400,getWidth(), 600,null);
       
       

        //Table outline
        pen2D.setColor(new Color(144, 84, 47));
        pen2D.setStroke(new BasicStroke(25)); // thicker pen
        pen2D.drawRect(0, 400, getWidth(), 600);

        //DID YOU WIN?
         // GOAL: Fill up your board first and have a higher total than the opponent
        
        if (activePlayState == DEALERS_TURN || activePlayState == PLAYERS_TURN ){ // if we are still playing the game
            if(playerDiceDub == 6 && dealerDiceDub !=6 ){
                whoWon();
                
            }else if(playerDiceDub != 6 && dealerDiceDub == 6){
                whoWon();
            }else if (playerCardsSize == 0){ // PLAYER LOSES
                activePlayState = LOSE;

            }else if (dealerCardsSize == 0){ // PLAYER WINS
                activePlayState = WIN;
            }
        
    }
        switch (activePlayState) {
            case PLAYERS_TURN:
                turnLabel.setText("PLAYERS TURN");
            break;

            case DEALERS_TURN:
                turnLabel.setText("DEALERS TURN");

            break;

            case WIN: 
                gameOPanel.setBackground(new Color(0,200,0,200));
                gameOLabel.setText("YOU WIN");
                gameOPanel.setVisible(true);

                dealerDie1Button.setVisible(false);
                dealerDie2Button.setVisible(false);
                dealerCardButton.setVisible(false);
                
                break;
           case LOSE: 
                gameOPanel.setBackground(new Color(200,0,0,200));
                gameOLabel.setText("YOU LOSE");
                gameOPanel.setVisible(true);

                dealerDie1Button.setVisible(false);
                dealerDie2Button.setVisible(false);
                dealerCardButton.setVisible(false);
          
            break;

            default: // game is ongoing
                gameOPanel.setVisible(false);
                break;
           }
           



    }
    private void drawCard(){}

    private void playCard(){

    }

    private void dealerPlays(){
        int action = rand.nextInt(1,4); // 1- (action 1) 2- (action 2) 3- (action 3)
        
            switch (action) {
                case 1:// ACTION 1: DRAW CARD TO END TURN
                    drawCard();
                break;

                case 2: //ACTION 2: PLAY CARD
                    playCard();
                break;

                case 3:  //ACTION 3: ROLL DICE
                    rollDice();
                break;

    
            }

       

      

    }



    
    @Override
    public void actionPerformed(ActionEvent e) {
       JButton buttonClicked = (JButton) e.getSource();

       if(buttonClicked == drawButton){
            activePlayState = (activePlayState == PLAYERS_TURN) ? DEALERS_TURN: PLAYERS_TURN;

            //SimpleSoundPlayer.playSound("GroupGame/src/music/actionSounds/rolling-dice-pixelbay.wav"); // Cant run music here
            rollDice();
            System.out.println("Draw Card Turn Ended. State ["+ activePlayState +"] is now Active " );
       }if(playerDiceDub == 6){ // when all the die are full 
           for (int i = 0; i < playerDice.length; i++){
            if(dealerDie1Button.getToolTipText().equals(dealerDie2Button.getToolTipText())){// if there are doubles
                ImageIcon die = new ImageIcon(dice[diceRoll1].getImage().getScaledInstance(60, 65, Image.SCALE_SMOOTH));
                if(buttonClicked == playerDice[0] || buttonClicked == playerDice[1]){ // if a die in a pair is clicked                        
                        playerDice[0].setIcon(die);
                        playerDice[0].setToolTipText(dealerDie1Button.getToolTipText());

                        playerDice[1].setIcon(die);
                        playerDice[1].setToolTipText(dealerDie1Button.getToolTipText());
                        System.out.println("DICE [0] AND [1] UPDATED");
                }else if(buttonClicked == playerDice[2] || buttonClicked == playerDice[3]){ 
                    playerDice[2].setIcon(die);
                    playerDice[2].setToolTipText(dealerDie1Button.getToolTipText());

                    playerDice[3].setIcon(die);
                    playerDice[3].setToolTipText(dealerDie1Button.getToolTipText());
                    System.out.println("DICE [2] AND [3] UPDATED");
                }else if(buttonClicked == playerDice[4] || buttonClicked == playerDice[5]){
                    playerDice[4].setIcon(die);
                    playerDice[4].setToolTipText(dealerDie1Button.getToolTipText());

                    playerDice[5].setIcon(die);
                    playerDice[5].setToolTipText(dealerDie1Button.getToolTipText());
                    System.out.println("DICE [4] AND [5] UPDATED");
            }
           }
        }
       }

    }
}
 