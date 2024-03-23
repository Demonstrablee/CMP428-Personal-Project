package Levels.GameLevels;

import Levels.Managers.Level2;

import fonts.fontsRegistry;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Arrays;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import Characters.Characters.Enemy;

public class Baccano extends Level2 implements ActionListener {
    JLabel title = new JLabel("BACCANO");
    String assetDir = "GroupGame/src/images/BACCANO/";
    Image table = Toolkit.getDefaultToolkit().getImage(assetDir + "casinoTablebg_red.jpeg");
    Image casinoPepe = Toolkit.getDefaultToolkit().getImage(assetDir + "casinoPeople.jpeg");

    Enemy dealer = new Enemy("DuckHuntDog", this.getLevelName(), 400, 30, 0, 0, 15); // SUMMONING THE DEALER

    // LABELS
    JLabel dealersLabel = new JLabel("Dealers Doubles");
    JLabel playersLabel = new JLabel("Players Doubles");
    // PANELS
    JPanel playersHand = new JPanel();
    JPanel playersDoubles = new JPanel();
    JPanel dealersDoubles = new JPanel();

    JPanel dealerPlayArea = new JPanel();// dont really need

    JButton playDie1Button = new JButton();
    JButton playDie2Button = new JButton();
    JButton dealerCardButton = new JButton(); // when the dealer plays a card show what it is

    // GAME OVER PANE
    JPanel gameOPanel = new JPanel();
    JLabel gameOLabel = new JLabel("WASS", SwingConstants.CENTER);
    JButton restartButton = new JButton();

    // IMAGES
    String diceDir = "GroupGame/src/images/BACCANO/Dice/";
    ImageIcon[] dice = new ImageIcon[6];

    String cardDir = "GroupGame/src/images/BACCANO/Playing Cards copy/";
    // File [] cardFile = cardDir.listFiles();
    ImageIcon[] card = new ImageIcon[16];

    // DRAW CARD BUTTON
    JButton drawButton = new JButton();
    Image cardBImage = Toolkit.getDefaultToolkit().getImage("GroupGame/src/images/BACCANO/card-back2.png")
            .getScaledInstance(100, 150, Image.SCALE_SMOOTH);
    ImageIcon cardBack = new ImageIcon(cardBImage);

    // CARD TYPES //https://www.youtube.com/watch?v=LxXWTXOny3A&t=18s
    String deck[] = new String[] { "CA", "CJ", "CK", "CQ", "DA", "DJ", "DK", "DQ", "HA", "HJ", "HK", "HQ", "SA", "SJ",
            "SK", "SQ" };
    // { 0, 1, 2, 3, 4, 5, 6, 7 ,8 ,9 ,10 ,12 ,13, ,14 ,15}

    // RANDOM GENERATOR
    Random rand = new Random();
    int diceRoll1 = 0;
    int diceRoll2 = 0;

    // Game State Variables
    JButton[] playerCards = new JButton[9]; // oversized array (52 CARDS IN A DECK)
    int playerCardsSize = 6; // CANNOT START AT ZERO (LOSE CONDITION)
    JButton[] dealerCards = new JButton[9]; // oversized array (never gonna see this)
    int dealerCardsSize = 8; // CANNOT START AT ZERO (WIN CONDITION)

    JButton[] playerDice = new JButton[6];
    JButton[] dealerDice = new JButton[6];

    // TRACKS ARRAY ABOVE HOW FULL
    int playerDiceDub = 0;
    int dealerDiceDub = 0;

    JLabel turnLabel = new JLabel("PLAYERS TURN", SwingConstants.CENTER);

    // TIMER FOR DEALERS ACTIONS
    public Timer dealerActionTimer; // thank you bro code https://www.youtube.com/watch?v=0cATENiMsBE

    // FONTS
    Font arcadeFont = fontsRegistry.arcadePixel;

    // Over Status
    private final int NOT_OVER = 0;
    private final int PLAYERS_TURN = 1;
    private final int DEALERS_TURN = 2;
    private final int WIN = 3;
    private final int LOSE = 4;
    private final int TIE = 5;

    int activePlayState = PLAYERS_TURN; // 0- Start ; 1- Players Turn, 2- Dealers Turn, 3 - WIN OVER, 4- LOSE OVER 5-
                                        // TIE

    Dictionary<String, Integer> carding = new Hashtable<>();

    public Baccano(JButton exitButton) {
        super(null, null, "BACCANO");
        setLayout(null);

        // LOAD IN

        System.out.println("--------------------------- BACCANO LOAD IN --------------------------- ");
        // Load in the images
        for (int i = 0; i < dice.length; i++) {
            // System.out.println(diceDir+ (i+1) + ".png "+ i);
            Image d = Toolkit.getDefaultToolkit().createImage(diceDir + (i + 1) + ".png").getScaledInstance(90, 90,
                    Image.SCALE_SMOOTH);
            ;
            dice[i] = new ImageIcon(d);
        }

        for (int i = 0; i < card.length; i++) {
            System.out.println(cardDir + "0" + (i + 1) + ".png " + i);
            Image c = Toolkit.getDefaultToolkit().createImage(cardDir + "0" + (i + 1) + ".png").getScaledInstance(100,
                    150, Image.SCALE_SMOOTH);
            ;
            card[i] = new ImageIcon(c);
        }

        carding.put("CA", 0);
        carding.put("CJ", 1);
        carding.put("CK", 2);
        carding.put("CQ", 3);

        carding.put("DA", 4);
        carding.put("DJ", 5);
        carding.put("DK", 6);
        carding.put("DQ", 7);

        carding.put("HA", 8);
        carding.put("HJ", 9);
        carding.put("HK", 10);
        carding.put("HQ", 11);

        carding.put("SA", 12);
        carding.put("SJ", 13);
        carding.put("SK", 14);
        carding.put("SQ", 15);

        // ADD PANELS
        playersHand.setBackground(Color.YELLOW);
        // playersHand.setOpaque(false);
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

        // DEALER PLAY AREA
        // dealerPlayArea.setBackground(Color.WHITE);
        // dealerPlayArea.setBounds(900, 140, 350, 240);
        // add(dealerPlayArea);

        // DEALER CARD
        dealerCardButton.setBounds(1035, 160, 100, 150);
        dealerCardButton.addActionListener(this);
        // drawButton.setBorderPainted(false);
        dealerCardButton.setIcon(card[0]);
        dealerCardButton.setVisible(false);
        add(dealerCardButton);

        // DEALER DICE
        playDie1Button.setBounds(990, 200, 90, 90);
        playDie1Button.setBorderPainted(false);
        playDie1Button.setIcon(dice[2]);
        add(playDie1Button);

        playDie2Button.setBounds(1090, 200, 90, 90);
        playDie2Button.setBorderPainted(false);
        playDie2Button.setIcon(dice[0]);
        add(playDie2Button);

        // DRAW CARD Button
        drawButton.setBounds(1130, 440, 100, 150);
        drawButton.addActionListener(this);
        drawButton.setToolTipText("DRAW"); // to stop the errors in the action listner and explain the buttons use
        // drawButton.setBorderPainted(false);

        drawButton.setIcon(cardBack);
        add(drawButton);

        // Current Persons Turn

        turnLabel.setBounds(140, 20, 200, 60);
        turnLabel.setFont(arcadeFont.deriveFont(25f));
        turnLabel.setForeground(new Color(139, 0, 0));
        turnLabel.setBackground(Color.RED);
        turnLabel.setBorder(BorderFactory.createLineBorder(new Color(230, 194, 0), 5));
        turnLabel.setOpaque(true);

        add(turnLabel);

        // GAME OVER PANEL

        gameOPanel.setLayout(new GridBagLayout());
        gameOPanel.setBounds(200, 200, 860, 200);
        gameOPanel.setBackground(new Color(0, 0, 0, 200));
        gameOPanel.setVisible(false);
        gameOLabel.setFont(arcadeFont.deriveFont(90f));

        gameOLabel.setForeground(Color.white);
        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        gameOPanel.add(gameOLabel, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 1;
        restartButton.setText("RESTART");
        restartButton.addActionListener(this);

        gameOPanel.add(restartButton, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 2;
        exitButton.setText("ESCAPE");
        gameOPanel.add(exitButton, constraints);
        add(gameOPanel);

        // Apply LABELS
        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        playersLabel.setForeground(Color.white);

        playersDoubles.add(playersLabel, constraints);

        dealersLabel.setBounds(1050, 20, 200, 10);
        dealersLabel.setForeground(Color.white);

        add(dealersLabel, JLayeredPane.DRAG_LAYER);

        // DOUBLE DICE
        for (int k = 0; k < playerDice.length; k++) {
            playerDice[k] = new JButton();
            playerDice[k].setBorderPainted(false);
            playerDice[k].setPreferredSize(new Dimension(60, 55));
            playerDice[k].addActionListener(this);

            constraints = new GridBagConstraints();
            constraints.gridx = 0;
            constraints.gridy = k + 1;

            playersDoubles.add(playerDice[k], constraints);

            dealerDice[k] = new JButton();
            dealerDice[k].setBorderPainted(false);
            dealerDice[k].setPreferredSize(new Dimension(60, 55));

            constraints = new GridBagConstraints();
            constraints.gridx = k;
            constraints.gridy = 1;

            dealersDoubles.add(dealerDice[k], constraints);

        }

        // INTITALIZE THE PLAYERS AND THE DEALERS CARDS
        // PLAYERS CARDS
        for (int h = 0; h < playerCards.length; h++) {
            playerCards[h] = new JButton();
            playerCards[h].setPreferredSize((new Dimension(100, 150)));
            playerCards[h].addActionListener(this);

            int cardVal = rand.nextInt(0, 16); // pos in deck array (card and deck arrays should correspond)

            if (h < playerCardsSize) {
                playerCards[h].setIcon(card[cardVal]);
                playerCards[h].setToolTipText(deck[cardVal]);
                playersHand.add(playerCards[h]); // add to hand if you
            } else {
                playerCards[h].setToolTipText("NA");
                playersHand.add(playerCards[h]);
            }
        }

        // DEALERS CARDS
        for (int h = 0; h < dealerCards.length; h++) {
            dealerCards[h] = new JButton();
            dealerCards[h].setPreferredSize((new Dimension(100, 150)));
            dealerCards[h].addActionListener(this);

            int cardVal = rand.nextInt(0, 16); // pos in deck array (card and deck arrays should correspond)
            if(h < dealerCardsSize){
                dealerCards[h].setIcon(card[cardVal]);
                dealerCards[h].setToolTipText(deck[cardVal]);
                // dealer doensn't have a hand that the player can see
            }else{
                dealerCards[h].setToolTipText("NA");
            }
        }

        // ACTIVATE what might need to be activated

        System.out.println("--------------------------- BACCANO LOAD IN %100 --------------------------- ");

    }

    @Override
    public void reset() {

    }

    public void rollDice() {
        diceRoll1 = rand.nextInt(1, 6); // 1-6 inclusive die (the indexes are 0-5)
        diceRoll2 = rand.nextInt(1, 6); // 1-6 inclusive

        playDie1Button.setIcon(dice[diceRoll1]);
        playDie1Button.setToolTipText(diceRoll1 + 1 + ""); // index is one less than the value of each die

        playDie2Button.setIcon(dice[diceRoll2]);
        playDie2Button.setToolTipText(diceRoll2 + 1 + "");
        System.out.print("DICE ROLLED...");
        if (diceRoll1 == diceRoll2) {

            ImageIcon die = new ImageIcon(dice[diceRoll1].getImage().getScaledInstance(60, 65, Image.SCALE_SMOOTH));
            System.out.println("ROLLED DOUBLES!");
            switch (activePlayState) { // TODO make a simple way to determine which die needs to be filled with the
                                       // doubles
                case PLAYERS_TURN: // this works but its meh
                    if (playerDiceDub < 6) {
                        if (playerDiceDub < 2) {
                            playerDice[0].setIcon(die);
                            playerDice[0].setToolTipText(diceRoll1 + 1 + ""); // index is one less than the value of
                                                                              // each die

                            playerDice[1].setIcon(die);
                            playerDice[1].setToolTipText(diceRoll1 + 1 + "");
                        } else if (playerDiceDub < 4) {
                            playerDice[2].setIcon(die);
                            playerDice[2].setToolTipText(diceRoll1 + 1 + ""); // index is one less than the value of
                                                                              // each die

                            playerDice[3].setIcon(die);
                            playerDice[3].setToolTipText(diceRoll1 + 1 + "");
                        } else {
                            playerDice[4].setIcon(die);
                            playerDice[4].setToolTipText(diceRoll1 + 1 + ""); // index is one less than the value of
                                                                              // each die

                            playerDice[5].setIcon(die);
                            playerDice[5].setToolTipText(diceRoll1 + 1 + "");
                        }
                        playerDiceDub += 2;
                    }
                    break;

                case DEALERS_TURN:
                    if (dealerDiceDub < 6) {
                        if (dealerDiceDub < 2) {
                            dealerDice[0].setIcon(die);
                            dealerDice[0].setToolTipText(diceRoll1 + 1 + ""); // index is one less than the value of
                                                                              // each die

                            dealerDice[1].setIcon(die);
                            dealerDice[1].setToolTipText(diceRoll1 + 1 + "");
                        } else if (dealerDiceDub < 4) {
                            dealerDice[2].setIcon(die);
                            dealerDice[2].setToolTipText(diceRoll1 + 1 + ""); // index is one less than the value of
                                                                              // each die

                            dealerDice[3].setIcon(die);
                            dealerDice[3].setToolTipText(diceRoll1 + 1 + "");
                        } else {
                            dealerDice[4].setIcon(die);
                            dealerDice[4].setToolTipText(diceRoll1 + 1 + ""); // index is one less than the value of
                                                                              // each die

                            dealerDice[5].setIcon(die);
                            dealerDice[5].setToolTipText(diceRoll1 + 1 + "");
                        }
                        dealerDiceDub += 2;
                    }
                    break;
            }
        } else {
            System.out.println("NO DICE!! SORRY!");
            System.out.println("-----------------------------------------------------");
        }

    }

    private void whoWon() {
        int playerScore = 0;
        int dealerScore = 0;

        // Check the total value of the doubles for the player
        for (int i = 0; i < 6; i++) {
            if (playerDice[i].getToolTipText() != null) {
                System.out.println(playerDice[i].getToolTipText());
                playerScore += Integer.parseInt(playerDice[i].getToolTipText()); // pull the interger value from the
                                                                                 // dice
            }
            // and the doubles for the dealer
            if (dealerDice[i].getToolTipText() != null) {
                System.out.println(dealerDice[i].getToolTipText());
                dealerScore += Integer.parseInt(dealerDice[i].getToolTipText()); // pull the interger value from the
                                                                                 // dice
            }

        }

        // determine who won the game

        activePlayState = playerScore > dealerScore ? WIN : LOSE;
        activePlayState = playerScore == dealerScore ? TIE : activePlayState; // checking if the game is a tie

        System.out.println("Player Score: " + playerScore);
        System.out.println("Dealer Score: " + dealerScore);
    }

    public void paintComponent(Graphics pen) { // method for painting
        super.paintComponent(pen);// component that does the painting
        Graphics2D pen2D = (Graphics2D) pen;
        // Draw Background

        pen.drawImage(casinoPepe, 0, -100, getWidth(), getHeight(), null);

        // Draw the dealer
        dealer.draw(pen);
        // draw the table
        pen.drawImage(table, 0, 400, getWidth(), 600, null);

        // Table outline
        pen2D.setColor(new Color(144, 84, 47));
        pen2D.setStroke(new BasicStroke(25)); // thicker pen
        pen2D.drawRect(0, 400, getWidth(), 600);

        // DID YOU WIN?
        // GOAL: Fill up your board first and have a higher total than the opponent

        if (activePlayState == DEALERS_TURN || activePlayState == PLAYERS_TURN) { // if we are still playing the game
            if (playerDiceDub == 6 || dealerDiceDub == 6) { /// CRUCIAL: CHECK IF THEY FILLED THEIR DICE UP
                whoWon();

            } else if (playerCardsSize == 0) { // PLAYER LOSES
                activePlayState = LOSE;

            } else if (dealerCardsSize == 0) { // PLAYER WINS
                activePlayState = WIN;
            }

        }
        // CHANGING PLAYSTATES
        switch (activePlayState) {
            case PLAYERS_TURN:
                turnLabel.setText("PLAYERS TURN");
                break;

            case DEALERS_TURN:
                turnLabel.setText("DEALERS TURN");
                dealerPlays(); // dealer takes turn TODO needs a bit of a timer since action is too fast
                break;

            case WIN:
                gameOPanel.setBackground(new Color(0, 200, 0, 200));
                gameOLabel.setText("YOU WIN");
                gameOPanel.setVisible(true);

                playDie1Button.setVisible(false);
                playDie2Button.setVisible(false);
                dealerCardButton.setVisible(false);

                break;
            case LOSE:
                gameOPanel.setBackground(new Color(200, 0, 0, 200));
                gameOLabel.setText("YOU LOSE");
                gameOPanel.setVisible(true);

                playDie1Button.setVisible(false);
                playDie2Button.setVisible(false);
                dealerCardButton.setVisible(false);
            case TIE:
                gameOPanel.setBackground(new Color(200, 200, 0, 200));
                gameOLabel.setText("TIE");
                gameOPanel.setVisible(true);

                playDie1Button.setVisible(false);
                playDie2Button.setVisible(false);
                dealerCardButton.setVisible(false);
                break;

            default: // game is ongoing
                gameOPanel.setVisible(false);
                break;
        }

    }

    private void drawCard() {
        System.out.println("CARD DRAWN");
    }

    private void playCardAction(JButton cardButton) {
        switch (cardButton.getToolTipText()) {
            case "HQ": // Queens: Pull a random card from the other players deck
            case "SQ":
            case "DQ":
            case "CQ":

                if (activePlayState == PLAYERS_TURN)
                    queenAction(dealerCards, dealerCardsSize, playerCards, playerCardsSize);
                else
                    queenAction(playerCards, playerCardsSize, dealerCards, dealerCardsSize);

                break;

            case "HK":// Kings : swap hands with other player
            case "SK":
            case "DK":
            case "CK":

                System.out.println("PLAYERS SWAP CARDS");

                System.out.print("Players Cards: ");
                printCards(playerCards);
                System.out.print("Dealer Cards: ");
                printCards(dealerCards);

                kingAction();

                System.out.print("Players Cards: ");
                printCards(playerCards);
                System.out.print("Dealer Cards: ");
                printCards(dealerCards);

                break;
            case "HJ":// Jacks : steal one of opponets doubles
            case "SJ":
            case "DJ":
            case "CJ":
                if (activePlayState == PLAYERS_TURN)
                    jackAction(playerCards, dealerCards);
                else
                    jackAction(dealerCards, playerCards);
                break;

            case "HA": // HEARTS DIAMONDS ACE
            case "DA": // roll 2 dice
                aceHeartsDiamondsAction();
                break;

            case "CA": // CLUB SPADES ACE
            case "SA":
                // KNOCK OUT OTHER PLAYERS DOUBLES
                aceClubSpadeAction();
                break;
            default: // draw card
                drawCard();
                break;
        }
    }

    private void dealerPlays() {
        int action = rand.nextInt(1, 4); // 1- (action 1) 2- (action 2) 3- (action 3)

        switch (action) {
            case 1:// ACTION 1: DRAW CARD TO END TURN
                System.out.println("THE DEALER DRAWS A CARD");
                drawCard();
                break;

            case 2: // ACTION 2: PLAY CARD
                System.out.println("THE DEALER PLAYS A CARD");
                int num = rand.nextInt(0, dealerCardsSize);
                System.out.println("DEALERS CARD PLAYED # " + num);
                playCardAction(dealerCards[num]);
                break;

            case 3: // TEMPORARYACTION 3: ROLL DICE
                System.out.println("THE DEALER ROLLS THE DICE");
                rollDice();
                break;
        }

        // return back to players turn
        activePlayState = PLAYERS_TURN;
        System.out.println("-----------------------------------------------------");
        System.out.println("Draw Card Turn Ended. PLAYERS TURN");

    }

    private void checkPlayersDiceFull(JButton buttonClicked) { // TODO want to be able to update the players dice after
                                                               // they are full
        if (playerDiceDub == 6) { // when all the die are full
            for (int i = 0; i < playerDice.length; i++) {
                if (playDie1Button.getToolTipText().equals(playDie2Button.getToolTipText())) {// if there are doubles
                    ImageIcon die = new ImageIcon(
                            dice[diceRoll1].getImage().getScaledInstance(60, 65, Image.SCALE_SMOOTH));
                    if (buttonClicked == playerDice[0] || buttonClicked == playerDice[1]) { // if a die in a pair is
                                                                                            // clicked
                        playerDice[0].setIcon(die);
                        playerDice[0].setToolTipText(playDie1Button.getToolTipText());

                        playerDice[1].setIcon(die);
                        playerDice[1].setToolTipText(playDie1Button.getToolTipText());
                        System.out.println("DICE [0] AND [1] UPDATED");
                    } else if (buttonClicked == playerDice[2] || buttonClicked == playerDice[3]) {
                        playerDice[2].setIcon(die);
                        playerDice[2].setToolTipText(playDie1Button.getToolTipText());

                        playerDice[3].setIcon(die);
                        playerDice[3].setToolTipText(playDie1Button.getToolTipText());
                        System.out.println("DICE [2] AND [3] UPDATED");
                    } else if (buttonClicked == playerDice[4] || buttonClicked == playerDice[5]) {
                        playerDice[4].setIcon(die);
                        playerDice[4].setToolTipText(playDie1Button.getToolTipText());

                        playerDice[5].setIcon(die);
                        playerDice[5].setToolTipText(playDie1Button.getToolTipText());
                        System.out.println("DICE [4] AND [5] UPDATED");
                    }
                }
            }
        }

    }

    // ACTION METHODS (so player and dealer can do them)
    private void aceHeartsDiamondsAction() {
        System.out.println("ACE OF HEARTS OR DIAMONDS IS PLAYED");
        // SimpleSoundPlayer.playSound("GroupGame/src/music/actionSounds/rolling-dice-pixelbay.wav");
        // // Cant run music here
        // SimpleSoundPlayer.playSound("GroupGame/src/music/actionSounds/flipcard-(pixelbay).wav");
        rollDice();

    }

    private void aceClubSpadeAction() { // knock out another players doubles
        System.out.println("ACE OF CLUBS OR SPADES IS PLAYED");

    }

    private void kingAction() { // Swap your hands with a player
        System.out.println("KING IS PLAYED");
        String[] tempTips = new String[dealerCards.length];// always 9 
        String tip;
        int tempSize = playerCardsSize;
       
        for (int i = 0; i < dealerCards.length; i++) {
            tip = dealerCards[i].getToolTipText(); // take dealer text
            tempTips[i] = playerCards[i].getToolTipText(); //  get player text  
            playerCards[i].setToolTipText(tip); //SET PLAYER TOOL TIP TO dealers
            dealerCards[i].setToolTipText(tempTips[i]); // change dealers tootip text
        }
        

        updateDisplayed();

        // SWAP SIZES AFTER
        playerCardsSize = dealerCardsSize;
        dealerCardsSize = tempSize;

    }

    private void queenAction(JButton[] theif, int theifSize, JButton[] vict, int victSize) { // Pull a random card from
                                                                                             // other players hand
        // choose a random card
        int cardStolenIndex = rand.nextInt(victSize); // 0 to however many cards the victim has
        System.out.println("QUEEN IS PLAYED");

        // pull that out of one players hand and put it into the others
        if (theifSize != theif.length) { // if your array is not full (if it is you get NOTHING! Your fault)
            theif[theifSize + 1] = vict[cardStolenIndex]; // give the theif the card

            // restructure vict's array
            for (int i = cardStolenIndex; i < victSize; ++i) { // TODO MAY CAUSE OUT OF BOUNDS CHECK IT
                vict[i] = vict[i + 1]; // shift array elements down one
            }
            victSize = victSize - 1; // lost one card

        } else {
            System.out.println("YOUR DECK IS FULL. YOU GET NOTHING, LOSE YOUR TURN");
        }

    }

    private void jackAction(JButton[] theifsDoubles, JButton[] victDoubles) { // steal opponetns doubles
        System.out.println("JACK IS PLAYED");
        int doublesIndex = rand.nextInt(6);
        switch (doublesIndex) {
            case 0:
            case 1:
                //
                break;

            case 2:
            case 3:

                break;

            case 4:
            case 5:

                break;
        }
    }

    /* Prints the card tool tips for each card in a deck */
    private void printCards(JButton[] cards) {
        System.out.print("{");
        for (int i = 0; i < cards.length; i++) {
            if (cards[i] == null)
                break; // leave the loop everything after is null
            System.out.print(cards[i].getToolTipText() + ",");

        }

        System.out.println(" }");
    }
    private void updateDisplayed(){
        String tip;
        int ic = 0;
        for (int i = 0; i< playerCards.length; i++){
            tip = playerCards[i].getToolTipText();
            if(tip.equals("NA")){
                playerCards[i].setIcon(null); //remove the displayed Icon
            }else{
                ic = carding.get(tip); //get num for icon from dictionary
                playerCards[i].setIcon(card[ic]); // set player card icon to icon for dealers card
                //playerCards[i].setToolTipText(tip); // update for players card tooltips
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton buttonClicked = (JButton) e.getSource();

        // check which card to play and play it
        playCardAction(buttonClicked);

        // After the action is completed change whos turn it is (might not need turnary
        // since this button clicked only manages player actions)
        // activePlayState = DEALERS_TURN; //( only here for testing)

        System.out.println("-----------------------------------------------------");
        System.out.println("Draw Card Turn Ended. DEALERS TURN");

    }

}
