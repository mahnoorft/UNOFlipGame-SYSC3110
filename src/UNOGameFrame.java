import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 *  This class represents the user interface and visualization of the application (view),
 *  adhering to the Model-View-Controller (MVC) architectural pattern.
 */

public class UNOGameFrame extends JFrame implements UNOGameHandler {
    UNOGameModel game;
    UNOGameController controller;
    JPanel mainPanel, playerCardsPanel, topCardPanel, buttonPanel,  winRoundPanel;
    JButton drawCardButton, endTurnButton, newRoundButton, callUNOButton;
    JLabel winRoundMessage,winRoundMessagePoints, playerNameField, statusBar;
    JMenuBar menuBar;
    JMenu gameMenu;
    ArrayList<JButton> cardButtonList;

    private static final String IMAGES_FOLDER_PATH = "images/"; // Path to the images folder

    public UNOGameFrame( UNOGameModel game) {
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

        // set up panel for buttons representing displayed cards
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        // set up scroll pane for current player's displayed cards
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

        //set status bar
        statusBar = new JLabel("Welcome to UNO...");
        statusBar.setBounds(20, 100, 200, 30);
        statusBar.setFont(new Font(Font.DIALOG,Font.BOLD, 11));
        mainPanel.add(statusBar);

        //created image icons for current player's displayed cards and then added buttons to them
        String imagePath = IMAGES_FOLDER_PATH + "card_back.png";
        // Create an ImageIcon using the specified image path
        ImageIcon icon = new ImageIcon(getClass().getResource(imagePath));
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
        callUNOButton = new JButton("Call UNO");
        callUNOButton.setEnabled(false);
        UNOButtonColour();
        callUNOButton.setBounds(20,270,100,40);
        mainPanel.add(callUNOButton);

        //adding end turn button and buttons for displayed cards to buttons panel
        buttonPanel.add(drawPanel);
        buttonPanel.add(endPanel);

        //add components to main Panel
        mainPanel.add(scrollPane, BorderLayout.SOUTH);
        mainPanel.add(topCardPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.EAST);

        //add ActionListeners and initialize controller
        drawCardButton.addActionListener(controller);
        drawCardButton.setActionCommand("draw");
        endTurnButton.addActionListener(controller);
        endTurnButton.setActionCommand("end");
        callUNOButton.addActionListener(controller);
        callUNOButton.setActionCommand("call");
        newGame.addActionListener(controller);

        //set up round winner panel plus displayed message
        winRoundPanel = new JPanel(null);
        // Create and configure the message label
        winRoundMessage = new JLabel("Error",SwingConstants.CENTER);
        winRoundMessage.setBounds(0,100,800,200);
        winRoundMessage.setFont(new Font("Serif", Font.PLAIN, 36));
        winRoundMessage.setHorizontalAlignment(SwingConstants.CENTER);
        // Create and configure the message points label
        winRoundMessagePoints = new JLabel("Error",SwingConstants.CENTER);
        winRoundMessagePoints.setBounds(0,200,800,200);
        winRoundMessagePoints.setFont(new Font("Serif", Font.PLAIN, 36));
        winRoundMessagePoints.setHorizontalAlignment(SwingConstants.CENTER);

        // Add message labels to the round winner panel
        winRoundPanel.add(winRoundMessage);
        winRoundPanel.add(winRoundMessagePoints);

        // set size of round winner panel
        winRoundPanel.setSize(800, 600);
        // Create a "New Round" button
        newRoundButton = new JButton("New Round");
        newRoundButton.setBounds(300,400,200,80);
        newRoundButton.setFont(new Font("Monospaced Bold", Font.PLAIN, 24));
        newRoundButton.addActionListener(controller);
        newRoundButton.setActionCommand("new");
        // Add the "New Round" button to the round winner panel
        winRoundPanel.add(newRoundButton);
        winRoundPanel.setVisible(false);

        // Set the frame location to be centered on the screen
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setVisible(true);

    }

    /**
     * Changes color of UNO button to red when pressed
     */
    private void UNOButtonColour(){
        if (callUNOButton.isEnabled()){
            callUNOButton.setBackground(Color.RED);
        }
        else {
            callUNOButton.setBackground(null);
        }
    }

    /**
     * Displays current's player's cards in the player cards panel south of the screen
     */
    private void displayPlayerHand() {
        playerCardsPanel.removeAll();
        playerNameField.setText(game.getCurrentPlayerName() + "'s Turn");

        List<String> cardNames = game.getCurrentPlayerCardNames();

        for (int i = 0; i< cardNames.size(); i++) {
            // Assuming cards are named with their respective COLOR_RANK.png
            // get the path to the images
            /**
             * ----------------------------------------------------------------Eric--------------------------------------
             */
            String imagePath;
            ImageIcon icon;
            JButton button;

            imagePath = IMAGES_FOLDER_PATH + cardNames.get(i);
            icon = new ImageIcon(getClass().getResource(imagePath));
            button = new JButton(icon);

            button.addActionListener(controller);
            button.setActionCommand(""+i);
            cardButtonList.add(button);
            playerCardsPanel.add(button);
        }

        // Revalidate and repaint the playerCardsPanel to reflect the changes
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    /**
     * Displays the top card of the deck center of the screen
     */
    private void displayTopCard() {
        topCardPanel.removeAll();

        Card topCard = game.topCard;

        // Display the appropriate side of the card based on the player's hand
        JLabel label;
        String imagePath;

        if (game.isCurrentSideLight()) {
            imagePath = IMAGES_FOLDER_PATH + topCard.getColor(true).name() + "_" + topCard.getRank(true).name() + ".png";
        } else {
            imagePath = IMAGES_FOLDER_PATH + topCard.getColor(false).name() + "_" + topCard.getRank(false).name() + ".png";
        }

        ImageIcon icon = new ImageIcon(getClass().getResource(imagePath));
        label = new JLabel(icon);

        topCardPanel.add(label);

        // Revalidate and repaint the topCardPanel to reflect the changes
        topCardPanel.revalidate();
        topCardPanel.repaint();
    }




    /** Displays the win round screen when a player wins the round
     * @param winner The name of the winner player.
     * @param totalPoints The total points the winner have.
     * @param points The amount of points the winner gained this round.
     * */
    public void winRoundScreen(String winner,int totalPoints,int points){
        // Check if the winning player has accumulated enough points to win the game
        if (totalPoints >= 500){
            // If so, display the game winner screen and exit the method
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

    /** Displays the win game screen when a player wins the game
     * @param winner The name of the winner.
     * @param totalPoints The total points the winner have.
     * */
    public void winGameScreen(String winner,int totalPoints){
        mainPanel.setVisible(false);
        winRoundPanel.setVisible(true);
        winRoundMessage.setText( winner + " wins the game!" );
        winRoundMessagePoints.setText( "Points: " + totalPoints);
        this.add(winRoundPanel);
    }

    /**
     * Starts a new round after a game round is done
     */
    public void restartRoundScreen(){
        mainPanel.setVisible(true);
        winRoundPanel.setVisible(false);
        game.updateTurn();
        displayPlayerHand();
        displayTopCard();
    }

    public void updateStatusBar(String function) {
        if (function != null){
            statusBar.setText(game.getCurrentPlayerName() + " played " + function);
        }
    }

    /**
     * Displays a dialog for drawing a card, allowing the player to decide whether to play the card or not.
     *
     * @param card The card to be drawn.
     * @param canPlay A boolean indicating whether the player can play the drawn card.
     * @param e The UNOGameEvent associated with the game.
     */
    private void drawCardDialog(Card card, Boolean canPlay, UNOGameEvent e) {
        ImageIcon icon = new ImageIcon(getClass().getResource(IMAGES_FOLDER_PATH + card.toString2() + ".png"));
        JLabel cardLabel = new JLabel(icon);

        if (canPlay) {
            int input = JOptionPane.showConfirmDialog(null, cardLabel, "Do you want to play " + card.toString2() + " card?", JOptionPane.YES_NO_OPTION);

            if (input == JOptionPane.YES_OPTION) {
                game.updateTopCard(card);
                displayTopCard();
                handlePlayCard(e);

            } else {
                game.updatePlayerHand(card);
                displayPlayerHand();
            }
        }else{
            JOptionPane.showMessageDialog(null, cardLabel, "You drew a " + card.toString2(), JOptionPane.INFORMATION_MESSAGE);
            game.updatePlayerHand(card);
            displayPlayerHand();
        }
    }

    /**
     * Displays a dialog for the player to choose a color when playing a Wild card.
     * The dialog contains radio buttons for selecting among the colors (BLUE, GREEN, RED, YELLOW).
     * Updates the game with the chosen color.
     */
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
                statusBar.setText(game.getCurrentPlayerName() + " played WILD, new colour is BLUE");
            } else if (radioButton2.isSelected()) {
                game.chooseNewColor(Card.Color.GREEN);
                statusBar.setText(game.getCurrentPlayerName() + " played WILD, new colour is GREEN");
            } else if (radioButton3.isSelected()) {
                game.chooseNewColor(Card.Color.RED);
                statusBar.setText(game.getCurrentPlayerName() + " played WILD, new colour is RED");
            } else {
                game.chooseNewColor(Card.Color.YELLOW);
                statusBar.setText(game.getCurrentPlayerName() + " played WILD, new colour is YELLOW");
            }
        } else {
            System.out.println("Dialog canceled");
        }

    }

    /**
     * Handles the draw card action in the UNO game.
     *
     * @param e The UNOGameEvent associated with the draw card action.
     */
    @Override
    public void handleDrawCard(UNOGameEvent e) {
        Card card = e.getCard();
        Boolean canPlay = e.canPlay();
        displayPlayerHand();
        if(!game.getCurrentPlayer().isBot()){
            drawCardDialog(card, canPlay, e);
            statusBar.setText(game.getCurrentPlayerName() + " drew a card");
        }

        //Cannot draw more cards
        drawCardButton.setEnabled(false);
        endTurnButton.setEnabled(true);
    }

    /**
     * Handles the play card action in the UNO game.
     * Displays the updated player's hand and the top card on the discard pile.
     * Enables the UNO call button if the current player has only one card left.
     */
    @Override
    public void handlePlayCard(UNOGameEvent e) {
        displayPlayerHand();
        displayTopCard();

        //call UNO button if the current player has only one card left
        if(game.getCurrentPlayerCardNames().size()==1){
            callUNOButton.setEnabled(true);
            UNOButtonColour();
            if(game.getCurrentPlayer().isBot()){
                callUNOButton.doClick();
            }
        }

        String specialCard = game.executeSpecialFunction(e.getCard());
        updateStatusBar(specialCard);
        if(!game.getCurrentPlayer().isBot()) {
            //handle WILD, SKIP, REVERSE, +1, +2 cards if human player
            if (e.getCard().getColor(game.isCurrentSideLight()) == Card.Color.WILD) {
                wildDialog();
            }
        }

        // Check for a round winner and display the round results if the current player has no more cards
        if(game.getCurrentPlayerCardNames().size()==0){
            int roundScore = game.calculateWinnerScore();
            int totalScore = game.getCurrentPlayer().getScore();
            winRoundScreen(game.getCurrentPlayerName(), totalScore, roundScore);
        }
        // Disable the draw card button and enable the end turn button
        drawCardButton.setEnabled(false);
        endTurnButton.setEnabled(true);
    }

    /**
     * Handles the transition to the next turn in the UNO game.
     * @param e The UNOGameEvent associated with the next turn.
     */
    @Override
    public void handleNextTurn(UNOGameEvent e) {
        // Apply penalty if UNO call button was not used and display a message
        if(callUNOButton.isEnabled()){
            game.applyCallPenalty();
            JOptionPane.showMessageDialog(this, "You didn't call UNO! Penalty: drew 2 cards");
        }

        displayPlayerHand();
        // Enable the draw card button and disable the end turn button
        drawCardButton.setEnabled(true);
        endTurnButton.setEnabled(false);
        // Reset UNO call button's state and color
        callUNOButton.setEnabled(false);
        UNOButtonColour();
        statusBar.setText("");

    }
    /**
     * Handles the UNO call action in the UNO game.
     * @param e The UNOGameEvent associated with the UNO call action.
     */
    @Override
    public void handleCallUNO(UNOGameEvent e) {
        // Display a message indicating that the current player has called UNO
        JOptionPane.showMessageDialog(this, "Player "+game.getCurrentPlayerName()+" called UNO!");
        statusBar.setText(game.getCurrentPlayerName() + " called UNO");
        // Disable the UNO call button and update its color
        callUNOButton.setEnabled(false);
        UNOButtonColour();
    }
}
