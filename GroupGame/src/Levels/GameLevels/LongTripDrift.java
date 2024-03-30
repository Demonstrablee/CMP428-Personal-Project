package Levels.GameLevels;

import javax.swing.*;

import Characters.Characters.Car;
import Levels.Managers.Level;
import Objects.Camera;
import Objects.Rect;
import Objects.Wall;
import fonts.fontsRegistry;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class LongTripDrift extends Level implements ActionListener {

    // FINAL VARS
    final int MAP_WIDTH = 15280;
    final int MAP_HEIGHT = 11720;

    // PLAYER DAMAGE SCORE
    int damageScore = 0; // how damaged it the players car
    JLabel scoreLabel = new JLabel("Damage: -$" + damageScore, SwingConstants.CENTER);

    // ASSETS
    String assetDir = "GroupGame/src/images/LTD/";
    Image map = Toolkit.getDefaultToolkit().getImage(assetDir + "GTA1_HD_Liberty_City MAP CROPPED.png");
    Image overpass = Toolkit.getDefaultToolkit().getImage(assetDir + "GTA1 Overpasses/GTA1 2 Lane Over Pass.png");
    Image[] overpasses;
    Rect[] building; // buildings
    // Player from level class

    // Objects
    int TOTAL_CARS = 1; // NUM OF ENEMY CARS
    int CAR_OPTIONS = Car.getNumAvailableCars(); // there are 20 possible cars to choose from
    Car[] enemyCar = new Car[TOTAL_CARS];

    // RECEIPT (END SCREEN)
    JPanel finalBillPane = new JPanel(new GridBagLayout());
    JLabel billTitle = new JLabel("RECEIPT", SwingConstants.CENTER);
    int carsHit = 0; // how many cars you hit/ were hit by
    int timeElapsed = 0; // how long it took to return the car in seconds
    int hrsElapsed = 0;
    int minElapsed = 0;
    int secElapsed = 0;
    JLabel carsHitLabel = new JLabel("CARS HIT: " + carsHit);
    JLabel timeElapsedLabel = new JLabel();
    JLabel finalScoreLabel = new JLabel();
    JLabel totalsTitle = new JLabel("------------TOTALS------------");
    JLabel thankYouLabel = new JLabel("THANK YOU!", SwingConstants.CENTER);
    JLabel companyLabel = new JLabel("MURPHY RENTALS INC.", SwingConstants.CENTER);
    JLabel dealerAgentLabel = new JLabel("YOUR AGENT: FRED");
    JLabel agentsMessageLabel = new JLabel("A MESSAGE FROM YOUR AGENT:", SwingConstants.CENTER);
    JLabel messageLabel = new JLabel(" ", SwingConstants.CENTER);

    Timer timePassed; // every sec add a second to the to the time
    // random Attributes for car generation
    Random rand = new Random();
    int randX;
    int randY;
    int randCar;

    // RESET BUTTON

    Image rentalAgency = Toolkit.getDefaultToolkit().getImage("GroupGame/src/images/LTD/car-rental.png")
            .getScaledInstance(64, 64, Image.SCALE_SMOOTH);
    ImageIcon rAIcon = new ImageIcon(rentalAgency);
    JButton restartButton = new JButton("REPLAY");
    JButton rentalLogo = new JButton(rAIcon);

    // FONT
    Font arcade = fontsRegistry.arcadePixel.deriveFont(30f);

    // Place boundarys on the entire map
    Wall[] wall = new Wall[] { new Wall(10, 90, 8000, 50), // top
            new Wall(50, 0, 90, 11375), // LEFT
            new Wall(10, 11375, 900, 50), // BOTTOM NEAR FINISH
    };

    // GAME STATE VARIABLES
    final int NOT_OVER = 0;
    final int PAUSED = 1;
    final int COMPLETE = 2;
    int activeState = NOT_OVER;

    // LOCATION OF THE GOAL POST (CAR RENTAL) (PROBABLY WILL BE A RECTANGLE)
    int goalX = 0;
    int goalY = 0;
    Rect carRental = new Rect(goalX, goalY, 100, 100);

    public LongTripDrift(JButton exitButton) {
        super("Long Trip Drift");

        // then make the frame HUGE so we zoom in setBounds(-10, 0, 20280, 17720);
        setBounds(-10, 0, MAP_WIDTH, MAP_HEIGHT); // SO IMPORTANT THE LEVEL WONT APPEAR UNLESS THESE ARE SET

        // Adding Score Label to Screen
        scoreLabel.setBackground(Color.white);
        scoreLabel.setOpaque(true);
        scoreLabel.setBounds((int) (680 - Camera.x), (int) (370 - Camera.y), 250, 60);
        scoreLabel.setFont(arcade);
        scoreLabel.setBorder(BorderFactory.createLineBorder(Color.black, 5));
        add(scoreLabel);

        // ADD RECEIPT
        finalBillPane.setBounds(420, 100, 500, 600);
        finalBillPane.setBackground(new Color(178, 190, 181, 250));
        finalBillPane.setVisible(false);
        add(finalBillPane);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        billTitle.setFont(arcade.deriveFont(50f));
        constraints.anchor = GridBagConstraints.WEST;
        finalBillPane.add(billTitle, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(0, 350, 0, 0);
        // Button Stylings
        rentalLogo.setFocusable(false);
        rentalLogo.setOpaque(false);
        rentalLogo.setBorder(null);
        finalBillPane.add(rentalLogo, constraints);

        //
        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 1;
        companyLabel.setFont(arcade);
        finalBillPane.add(companyLabel, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 2;
        dealerAgentLabel.setFont(arcade.deriveFont(20f));
        finalBillPane.add(dealerAgentLabel, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 3;
        totalsTitle.setFont(arcade.deriveFont(35f));
        finalBillPane.add(totalsTitle, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 4;
        timeElapsedLabel.setFont(arcade);
        finalBillPane.add(timeElapsedLabel, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 5;
        carsHitLabel.setFont(arcade);
        finalBillPane.add(carsHitLabel, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 6;
        finalScoreLabel.setFont(arcade);

        finalBillPane.add(finalScoreLabel, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 7;
        thankYouLabel.setFont(arcade.deriveFont(45f));
        finalBillPane.add(thankYouLabel, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 8;
        agentsMessageLabel.setFont(arcade.deriveFont(20f));
        finalBillPane.add(agentsMessageLabel, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 9;
        messageLabel.setFont(arcade);
        finalBillPane.add(messageLabel, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 10;
        constraints.insets = new Insets(5, 0, 5, 5);
        constraints.ipadx = 100;
        constraints.ipady = 10;
        restartButton.setBorder(exitButton.getBorder());
        restartButton.setOpaque(true);
        restartButton.setBackground(Color.LIGHT_GRAY);
        restartButton.addActionListener(this);
        restartButton.setFocusable(false);

        finalBillPane.add(restartButton, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 11;
        constraints.ipadx = 50;
        constraints.ipady = 10;
        constraints.insets = new Insets(5, 0, 5, 5);
        exitButton.setText("EXIT");
        finalBillPane.add(exitButton, constraints);

        // System.out.println("Camera position x:" + Camera.x + " y:" + Camera.y);
        // System.out.print(String.format("TIME ELAPSED\n %s", timeElapsed));
        // GENERATE ENEMY CARS
        for (int i = 0; i < enemyCar.length; i++) {
            randX = rand.nextInt(0, 200);
            randY = rand.nextInt(0, 200);

            randX = 85;
            randY = 24;
            randCar = rand.nextInt(0, CAR_OPTIONS);
            enemyCar[i] = new Car(randX, randY, 0);
        }

        timePassed = new Timer(1000, addTime);
        // timePassed.start(); // has to start on button press in level builder
    }

    private ActionListener addTime = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            timeElapsed++; // make time elapsed go up by 1
            hrsElapsed = timeElapsed / 3600; // 1hr == 3600 sec 60sec * 60 = 3600
            minElapsed = (timeElapsed % 3600) / 60;
            secElapsed = (timeElapsed % 3600) % 60;
            System.out.printf("TIME ELAPSED  %2.2s:%2.2s:%2.2s \n", hrsElapsed, minElapsed, secElapsed);
        }
    };

    private int calcTotalBill() {
        int total = 0;
        // consider how many cars were hit (seperate from damage delt)
        total += carsHit * 50; // $50 per car hit

        // consider time taken (hrs * 100)(min x 5) (sec x 0)
        total += hrsElapsed * 100;
        total += minElapsed * 5;
        // total = secElapsed * 0; // dont need this

        // add the damage delt to the car
        total += damageScore;

        if (total >= 1000) // 1k +
            messageLabel.setText("Whats your deal DUDE???!!");
        else if (total >= 600) //
            messageLabel.setText("MY RIMS MANN!! Last Favor, EVER!");
        else if (total >= 300)
            messageLabel.setText(" Not too bad its wild out here :)");
        else if (total >= 100)
            messageLabel.setText(" Nice Nice, come by anytime :) ");
        else if (total == 0)
            messageLabel.setText(" Just like New! Good on you! ");
        return total;
    }

    @Override
    public void reset() {
        Camera.returnToOrigin(); // reset the camera and player starting positions
        p1.returnToOrigin();
        activeState = NOT_OVER;
        finalBillPane.setVisible(false);
        damageScore = 0;
        scoreLabel.setText("Damage: -$" + damageScore);
        hrsElapsed = 0;
        minElapsed = 0;
        secElapsed = 0;
        carsHit = 0;
        timePassed.start();
    }

    @Override
    public void pause() {
        activeState = PAUSED;
        // stop timer
        timePassed.stop();

        // 1. halt enemy car movement

        // 2. in levelbuilder halt updown left right movement (done)

    }

    @Override
    public void resume() {
        activeState = NOT_OVER;
        timePassed.start(); // resume timer
        // 2. in levelbuilder ACCEPT updown left right movement (done)

    }

    @Override
    public void paintComponent(Graphics pen) { // method for painting
        super.paintComponent(pen);// component that does the painting

        if (activeState == NOT_OVER && activeState != PAUSED) {
            // COLLISIONS
            for (int i = 0; i < wall.length; i++) {
                // if player colides into wall
                // place player outside the wall
                // increment damage: you just ran into a wall
                // updateDamage();
                System.out.println("PLAYER just ran into a wall, dealer Fred is not going to like this...");
            }

            // Player Damage
            for (int i = 0; i < enemyCar.length; i++) {
                if (p1.isColliding(enemyCar[i]) == true) {
                    p1.setColor(Color.ORANGE);
                    updateDamage();
                    carsHit++; // CAR WAS HIT
                    // System.out.println("NEW BILL: $" + damageScore);

                } else
                    p1.setColor(Color.GREEN);
            }

            // check if the game is over
            activeState = playerAtDestination() == true ? COMPLETE : NOT_OVER;

        } else if (activeState == COMPLETE) { // game is over
            timePassed.stop(); // stop timer
            // update all the labels
            timeElapsedLabel.setText(
                    String.format("%-23s %02d: %02d: %02d", "TIME ELAPSED: ", hrsElapsed, minElapsed, secElapsed));
            carsHitLabel.setText(String.format("%-45s %03d", "CARS HIT: ", carsHit));
            finalScoreLabel.setText(String.format("%-37s %,6d", "TOTAL COST: ", calcTotalBill()));
            finalBillPane.setVisible(true);
        }

        // MOVE ENEMYS (follow the player)
        for (Car enemy : enemyCar) {
            enemy.follow(p1.x, p1.y);
        }

        // DRAW MAP
        // X - LEFT (POS) RIGHT(NEGA) (-6000) CHECK and guessing
        // Y - UP (POS) DOWN(NEGA) (-5450)
        pen.drawImage(map, (int) (-6000 - Camera.x), (int) (-5450 - Camera.y), getWidth(), getHeight(), null);

        // DRAW PLAYER
        p1.draw(pen);

        // DRAW CARS (ON TOP OF MAP)
        for (int i = 0; i < enemyCar.length; i++) {
            enemyCar[i].drawEnemy(pen);
        }
        for (Wall walls : wall) {
            walls.setColor(Color.ORANGE);
            walls.draw(pen);
        }

        // Over Passes
        pen.drawImage(overpass, (int) (2249 - Camera.x), (int) (3280 - Camera.y), 720, 295, null);

    }

    /** Checks if the player has reached the car rental */
    private boolean playerAtDestination() {
        return carRental.contains(p1.x, p1.y);
    }

    private void updateDamage() {
        damageScore += 10;
        scoreLabel.setText("Damage: -$" + damageScore);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton buttonClicked = (JButton) e.getSource();

        if (buttonClicked == restartButton) { // reset the game
            reset();
        }
    }

}
