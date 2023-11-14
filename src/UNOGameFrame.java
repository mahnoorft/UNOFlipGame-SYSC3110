import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UNOGameFrame extends JFrame implements UNOGameHandler {
    UNOGame game;
    UNOGameController controller;
    JPanel mainPanel, playerCardsPanel, topCardPanel, buttonPanel, leftPanel, winRoundPanel;
    JButton drawCardButton, endTurnButton, newRoundButton, callUNOButton;
    JLabel winRoundMessage,winRoundMessagePoints, playerNameField;
    JMenuBar menuBar;
    JMenu gameMenu;
    ArrayList<ImageIcon> iconImages;
    ArrayList<JButton> cardButtonList;
    Card card;
    private static final String IMAGES_FOLDER_PATH = "src/images/"; // Path to the images folder

    public UNOGameFrame( UNOGame game) {
        super("UNO Flip Game!");
        this.game = game;
        this.cardButtonList = new ArrayList<>();
        controller = new UNOGameController(game, this);
        game.addUnoGameView(this);

        //initialize menu and menu item
        menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);
        gameMenu = new JMenu("Game");
        menuBar.add(gameMenu);
        JMenuItem newGame = new JMenuItem("New Game");
        newGame.setActionCommand("newGame");
        gameMenu.add(newGame);

        //set up the content pane
        mainPanel = new JPanel(new BorderLayout());
        this.add(mainPanel);
        playerCardsPanel = new JPanel(new FlowLayout());
        topCardPanel = new JPanel();
        topCardPanel.setBorder(BorderFactory.createEmptyBorder(10, 210, 120, 20)); // Add padding

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(playerCardsPanel);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        //Player Name Field
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        playerNameField = new JLabel();
        centerPanel.add(playerNameField);
        mainPanel.add(centerPanel, BorderLayout.NORTH);

        game.initializeGame();
        displayPlayerHand();
        displayTopCard();


        //initialize and add buttons to buttonPanel
        String imagePath = IMAGES_FOLDER_PATH + "card_back.png";
        ImageIcon icon = new ImageIcon(imagePath);
        JPanel drawPanel = new JPanel();
        drawPanel.setLayout(new BoxLayout(drawPanel, BoxLayout.Y_AXIS));
        drawCardButton = new JButton(icon);
        drawCardButton.setMargin(new Insets(0, 0, 0, 0));
        JLabel draw = new JLabel("Draw Card");
        drawPanel.add(drawCardButton);
        drawPanel.add(draw);
        drawPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 120, 20)); // Add padding

        //End Turn Button
        JPanel endPanel = new JPanel();
        endTurnButton = new JButton("End Turn");
        endTurnButton.setEnabled(false);
        endPanel.add(endTurnButton);
        endPanel.setBorder(BorderFactory.createEmptyBorder(0, 70, 10, 0)); // Add padding

        //Call UNO Button
        JPanel callPanel = new JPanel();
        callUNOButton = new JButton("Call UNO");
        callUNOButton.setEnabled(false);
        UNOButtonColour();
        callUNOButton.setBounds(20,270,100,40);
        mainPanel.add(callUNOButton);

        //adding to button panel
        buttonPanel.add(drawPanel);
        buttonPanel.add(endPanel);
        //adding to leftPanel

        //add components to main Panel
        mainPanel.add(scrollPane, BorderLayout.SOUTH);
        mainPanel.add(topCardPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.EAST);
        mainPanel.add(leftPanel, BorderLayout.WEST);

        //add ActionListeners and initialize controller
        drawCardButton.addActionListener(controller);
        drawCardButton.setActionCommand("draw");
        endTurnButton.addActionListener(controller);
        endTurnButton.setActionCommand("end");
        callUNOButton.addActionListener(controller);
        callUNOButton.setActionCommand("call");
        newGame.addActionListener(controller);

        winRoundPanel = new JPanel(null);
        winRoundMessage = new JLabel("Error",SwingConstants.CENTER);
        winRoundMessage.setBounds(0,100,800,200);
        winRoundMessage.setFont(new Font("Serif", Font.PLAIN, 36));
        winRoundMessage.setHorizontalAlignment(SwingConstants.CENTER);
        winRoundMessagePoints = new JLabel("Error",SwingConstants.CENTER);
        winRoundMessagePoints.setBounds(0,200,800,200);
        winRoundMessagePoints.setFont(new Font("Serif", Font.PLAIN, 36));
        winRoundMessagePoints.setHorizontalAlignment(SwingConstants.CENTER);

        winRoundPanel.add(winRoundMessage);
        winRoundPanel.add(winRoundMessagePoints);
        winRoundPanel.setSize(800, 600);
        newRoundButton = new JButton("New Round");
        newRoundButton.setBounds(300,400,200,80);
        newRoundButton.setFont(new Font("Monospaced Bold", Font.PLAIN, 24));
        newRoundButton.addActionListener(controller);
        newRoundButton.setActionCommand("new");

        winRoundPanel.add(newRoundButton);
        winRoundPanel.setVisible(false);

        //Edit the visuals of buttons
        //create action commands for when button is clicked

        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setVisible(true);


    }

    private void UNOButtonColour(){
        if (callUNOButton.isEnabled()){
            callUNOButton.setBackground(Color.RED);
        }
        else {
            callUNOButton.setBackground(null);
        }
    }

    private void displayPlayerHand() {
        playerCardsPanel.removeAll();
        playerNameField.setText(game.getCurrentPlayerName() + "'s Turn");

        List<String> cardNames = game.getCurrentPlayerCardNames();

        for (int i = 0; i< cardNames.size(); i++) {
            // Assuming cards are named with their respective COLOR_RANK.png
            String imagePath = IMAGES_FOLDER_PATH + cardNames.get(i);
            ImageIcon icon = new ImageIcon(imagePath);
            JButton button = new JButton(icon);
            button.addActionListener(controller);
            button.setActionCommand(""+i);
            cardButtonList.add(button);
            playerCardsPanel.add(button);
        }
        System.out.println(cardNames.size());

        // Revalidate and repaint the playerCardsPanel to reflect the changes
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void displayTopCard() {
        topCardPanel.removeAll();

        Card topCard = game.topCard;
        String imagePath = IMAGES_FOLDER_PATH + topCard.getColorLight().name() + "_"+ topCard.getRankLight().name() + ".png";
        ImageIcon icon2 = new ImageIcon(imagePath);
        JLabel label2 = new JLabel(icon2);
        topCardPanel.add(label2);

        // Revalidate and repaint the playerCardsPanel to reflect the changes
        topCardPanel.revalidate();
        topCardPanel.repaint();
    }

    /** Display the win round screen when a player wins the round
     * @param winner The name of the winner player.
     * @param totalPoints The total points the winner have.
     * @param points The amount of points the winner gained this round.
     * */
    public void winRoundScreen(String winner,int totalPoints,int points){
        if (totalPoints >= 500){
            winGameScreen(winner, totalPoints);
            return;
        }
        mainPanel.setVisible(false);
        winRoundPanel.setVisible(true);
        winRoundMessage.setText( winner + " wins the round" );
        winRoundMessagePoints.setText( "Points: " + totalPoints + " (+" + points+" points this round)");
        newRoundButton.setText("New Round");
        this.add(winRoundPanel);

    }

    /** Display the win game screen when a player wins the game
     * @param winner The name of the winner.
     * @param totalPoints The total points the winner have.
     * */
    public void winGameScreen(String winner,int totalPoints){
        mainPanel.setVisible(false);
        winRoundPanel.setVisible(true);
        winRoundMessage.setText( winner + " wins the game!" );
        winRoundMessagePoints.setText( "Points: " + totalPoints);
        newRoundButton.setText("Finish");
        this.add(winRoundPanel);
    }

    public void restartRoundScreen(){
        mainPanel.setVisible(true);
        winRoundPanel.setVisible(false);
        game.updateTurn();
        displayPlayerHand();
        displayTopCard();
    }

    private void drawCardDialog(Card card, Boolean canPlay, UNOGameEvent e) {
        System.out.println(card.toString2());
        ImageIcon icon = new ImageIcon(IMAGES_FOLDER_PATH + card.toString2() + ".png");
        JLabel cardLabel = new JLabel(icon);

        if (canPlay) {
            int input = JOptionPane.showConfirmDialog(null, cardLabel, "Do you want to play " + card.toString2() + " card?", JOptionPane.YES_NO_OPTION);
            System.out.println(input);

            if (input == JOptionPane.YES_OPTION) {
                System.out.println("Player Played the card");
                game.updateTopCard(card);
                displayTopCard();
                handlePlayCard(e);

            } else {
                System.out.println("Player did not play");
                game.updatePlayerHand(card);
                displayPlayerHand();
            }
        }else{
            JOptionPane.showMessageDialog(null, cardLabel, "You drew a " + card.toString2(), JOptionPane.INFORMATION_MESSAGE);
            game.updatePlayerHand(card);
            displayPlayerHand();
        }
    }

    public void wildDialog(){
        // Create panel
        JPanel panel = new JPanel();

        //Create Radio Buttons
        JRadioButton radioButton1 = new JRadioButton("BLUE");
        JRadioButton radioButton2 = new JRadioButton("GREEN");
        JRadioButton radioButton3 = new JRadioButton("RED");
        JRadioButton radioButton4 = new JRadioButton("YELLOW");

        //add colours to indicate colour
        radioButton1.setBackground(Color.CYAN);
        radioButton2.setBackground(Color.GREEN);
        radioButton3.setBackground(Color.red);
        radioButton4.setBackground(Color.yellow);

        //Creating Button Group
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(radioButton1);
        buttonGroup.add(radioButton2);
        buttonGroup.add(radioButton3);
        buttonGroup.add(radioButton4);

        //add buttons to panel
        panel.add(radioButton1);
        panel.add(radioButton2);
        panel.add(radioButton3);
        panel.add(radioButton4);

        // Show the option pane with the panel containing radio buttons
        int result = JOptionPane.showConfirmDialog(null, panel, "Select an Option", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);

        // Check the user's choice
        if (result == JOptionPane.OK_OPTION) {
            // Handle the selected option
            if (radioButton1.isSelected()) {
                game.chooseNewColor(Card.Color.BLUE);
            } else if (radioButton2.isSelected()) {
                game.chooseNewColor(Card.Color.GREEN);
            } else if (radioButton3.isSelected()) {
                game.chooseNewColor(Card.Color.RED);
            } else {
                game.chooseNewColor(Card.Color.YELLOW);
            }
        } else {
            System.out.println("Dialog canceled");
        }

    }

    @Override
    public void handleDrawCard(UNOGameEvent e) {
        Card card = e.getCard();
        Boolean canPlay = e.canPlay();
        displayPlayerHand();
        drawCardDialog(card, canPlay, e);

        //Cannot draw more cards
        drawCardButton.setEnabled(false);
        endTurnButton.setEnabled(true);
    }

    @Override
    public void handlePlayCard(UNOGameEvent e) {
        displayPlayerHand();
        displayTopCard();

        //callUNO
        if(game.getCurrentPlayerCardNames().size()==1){
            callUNOButton.setEnabled(true);
            UNOButtonColour();
        }

        //handle WILD, SKIP, REVERSE, +1, +2
        if (e.getCard().getColorLight() == Card.Color.WILD){
            wildDialog();
        }
        game.executeSpecialFunction(e.getCard());

        if(game.getCurrentPlayerCardNames().size()==0){
            int roundScore = game.calculateWinnerScore();
            int totalScore = game.getCurrentPlayer().getScore();
            winRoundScreen(game.getCurrentPlayerName(), totalScore, roundScore);
        }

        drawCardButton.setEnabled(false);
        endTurnButton.setEnabled(true);
    }

    @Override
    public void handleNextTurn(UNOGameEvent e) {

        if(callUNOButton.isEnabled()){
            game.applyCallPenalty();
            JOptionPane.showMessageDialog(this, "You didn't call UNO! Penalty: drew 2 cards");
        }

        displayPlayerHand();
        drawCardButton.setEnabled(true);
        endTurnButton.setEnabled(false);
        callUNOButton.setEnabled(false);
        UNOButtonColour();
    }

    @Override
    public void handleCallUNO(UNOGameEvent e) {
        JOptionPane.showMessageDialog(this, "Player "+game.getCurrentPlayerName()+" called UNO!");
        callUNOButton.setEnabled(false);
        UNOButtonColour();
    }
}
