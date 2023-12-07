import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 *  The UNOGameFrame class represents the user interface and visualization of the UNO Flip game.
 *  It adheres to the Model-View-Controller (MVC) architectural pattern. It's responsible for displaying
 *  the game state to the user and handling user interactions. This class interacts with the UNOGameModel
 *  and UNOGameController classes to update the game state and trigger
 * appropriate actions based on user input.
 *
 *  @author Areej Mahmoud 101218260
 *  @author Mahnoor Fatima 101192353
 *  @author Eric Cui 101237617
 *  @author Rama Alkhouli 101198025
 */

public class UNOGameFrame extends JFrame implements UNOGameHandler {
    UNOGameModel game;
    UNOGameController controller;
    JPanel mainPanel, playerCardsPanel, topCardPanel, buttonPanel,  winRoundPanel;
    JButton drawCardButton, endTurnButton, newRoundButton, callUNOButton;
    JLabel winRoundMessage,winRoundMessagePoints, playerNameField, statusBar;
    JMenuBar menuBar;
    JMenu gameMenu, editMenu;

    JMenuItem undoItem, redoItem;
    ArrayList<JButton> cardButtonList;

    UNOGameState gameState;

    Boolean isCardPlayed;
    Boolean isCardDrawn;

    public UNOGameFrame( UNOGameModel game) {
        super("UNO Flip Game!");
        this.game = game;
        this.cardButtonList = new ArrayList<>();
        controller = new UNOGameController(game, this);
        game.addUnoGameView(this);
        gameState = new UNOGameState(game);
        isCardPlayed = false;
        isCardDrawn = false;

        //initialize menu and menu item
        menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);
        gameMenu = new JMenu("Game");
        menuBar.add(gameMenu);
        JMenuItem newGame = new JMenuItem("New Game");
        newGame.setActionCommand("newGame");
        gameMenu.add(newGame);

        //initialize edit menu and Uno & redo menu items
        editMenu = new JMenu("Edit");
        menuBar.add(editMenu);

        undoItem = new JMenuItem("Undo");
        redoItem = new JMenuItem("Redo");

        undoItem.addActionListener(controller);
        undoItem.setActionCommand("Undo");

        redoItem.addActionListener(controller);
        redoItem.setActionCommand("Redo");

        editMenu.add(undoItem);
        editMenu.add(redoItem);


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

        System.out.println("Save in intialize before");
        game.saveGameState();
        System.out.println("Save in intialize after");


        undoItem.setEnabled(false);
        System.out.println("undo is grayed out now");


        //set status bar
        statusBar = new JLabel("Welcome to UNO...");
        statusBar.setBounds(20, 100, 250, 30);
        statusBar.setFont(new Font(Font.DIALOG,Font.BOLD, 11));
        mainPanel.add(statusBar);

        //created image icons for current player's displayed cards and then added buttons to them
        String imagePath = "images/card_back.png";
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
        winRoundMessage.setBounds(0,65,700,200);
        winRoundMessage.setFont(new Font("Serif", Font.PLAIN, 30));
        winRoundMessage.setHorizontalAlignment(SwingConstants.CENTER);
        // Create and configure the message points label
        winRoundMessagePoints = new JLabel("Error",SwingConstants.CENTER);
        winRoundMessagePoints.setBounds(0,125,700,200);
        winRoundMessagePoints.setFont(new Font("Serif", Font.PLAIN, 30));
        winRoundMessagePoints.setHorizontalAlignment(SwingConstants.CENTER);

        // Add message labels to the round winner panel
        winRoundPanel.add(winRoundMessage);
        winRoundPanel.add(winRoundMessagePoints);

        // set size of round winner panel
        winRoundPanel.setSize(700, 500);
        // Create a "New Round" button
        newRoundButton = new JButton("New Round");
        newRoundButton.setBounds(250,300,200,80);
        newRoundButton.setFont(new Font("Monospaced Bold", Font.PLAIN, 24));
        newRoundButton.addActionListener(controller);
        newRoundButton.setActionCommand("new");
        // Add the "New Round" button to the round winner panel
        winRoundPanel.add(newRoundButton);
        winRoundPanel.setVisible(false);

        // Set the frame location to be centered on the screen
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(700, 500);
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

        //List<String> cardNames = game.getCurrentPlayerCardNames();
        List<Card> cards = game.getCurrentPlayer().getHand().getCards();
        for (int i = 0; i< cards.size(); i++) {
            // Assuming cards are named with their respective COLOR_RANK.png
            // get the path to the images

            String imagePath;
            ImageIcon icon;
            JButton button;

            imagePath = cards.get(i).getImagePath(game.isCurrentSideLight());
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
        // get the path to the image
        JLabel label2;

        String imagePath = topCard.getImagePath(game.isCurrentSideLight());
        System.out.println(imagePath);
        ImageIcon icon2 = new ImageIcon(getClass().getResource(imagePath));
        label2 = new JLabel(icon2);

        topCardPanel.add(label2);

        // Revalidate and repaint the playerCardsPanel to reflect the changes
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
  
  /**
     * Updates status bar
     */
    public void updateStatusBar(String function, String type) {
        statusBar.setText(game.getCurrentPlayerName() + " "+ function + " " + type);
    }

    /**
     * Displays a dialog for drawing a card, allowing the player to decide whether to play the card or not.
     *
     * @param card The card to be drawn.
     * @param canPlay A boolean indicating whether the player can play the drawn card.
     * @param e The UNOGameEvent associated with the game.
     */
    private void drawCardDialog(Card card, Boolean canPlay, UNOGameEvent e) {
        ImageIcon icon = new ImageIcon(getClass().getResource(card.getImagePath(game.isCurrentSideLight())));
        JLabel cardLabel = new JLabel(icon);

        if (canPlay) {
            int input = JOptionPane.showConfirmDialog(null, cardLabel,
                    "Do you want to play " + card.toString2(game.isCurrentSideLight())
                            + " card?", JOptionPane.YES_NO_OPTION);

            if (input == JOptionPane.YES_OPTION) {
                game.updateTopCard(card);
                displayTopCard();
                handlePlayCard(e);

            } else {
                game.updatePlayerHand(card);
                displayPlayerHand();
            }
        }else{
            JOptionPane.showMessageDialog(null, cardLabel, "You drew a "
                    + card.toString2(game.isCurrentSideLight()), JOptionPane.INFORMATION_MESSAGE);
            game.updatePlayerHand(card);
            displayPlayerHand();
        }
    }

    /**
     * Displays a dialog for the player to choose a color when playing a Wild card.
     * The dialog contains radio buttons for selecting among the colors (BLUE, GREEN, RED, YELLOW).
     * Updates the game with the chosen color.
     */
    public Card.Color darkWildDialog(){
        // Create panel
        JPanel panel = new JPanel();

        //Create Radio Buttons
        JRadioButton radioButton1 = new JRadioButton("PINK");
        JRadioButton radioButton2 = new JRadioButton("TEAL");
        JRadioButton radioButton3 = new JRadioButton("ORANGE");
        JRadioButton radioButton4 = new JRadioButton("PURPLE");

        //add colours to indicate colour
        radioButton1.setBackground(Color.PINK);
        radioButton2.setBackground(Color.CYAN);
        radioButton3.setBackground(Color.ORANGE);
        radioButton4.setBackground(Color.MAGENTA);

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
                game.chooseNewColor(Card.Color.PINK);
                updateStatusBar("played", "WILD, new colour is PINK");
                return Card.Color.PINK;
                //statusBar.setText(game.getCurrentPlayerName() + " played WILD, new colour is PINK");
            } else if (radioButton2.isSelected()) {
                game.chooseNewColor(Card.Color.TEAL);
                updateStatusBar("played", "WILD, new colour is TEAL");
                return Card.Color.TEAL;
                //statusBar.setText(game.getCurrentPlayerName() + " played WILD, new colour is TEAL");
            } else if (radioButton3.isSelected()) {
                game.chooseNewColor(Card.Color.ORANGE);
                updateStatusBar("played", "WILD, new colour is ORANGE");
                return Card.Color.ORANGE;
                //statusBar.setText(game.getCurrentPlayerName() + " played WILD, new colour is ORANGE");
            } else {
                game.chooseNewColor(Card.Color.PURPLE);
                updateStatusBar("played", "WILD, new colour is PURPLE");
                return Card.Color.PURPLE;
                //statusBar.setText(game.getCurrentPlayerName() + " played WILD, new colour is PURPLE");
            }
        } else {
            System.out.println("Dialog canceled");
        }
        return Card.Color.ORANGE;
    }

    /**
     * Displays a dialog for the player to choose a color when playing a Wild card.
     * The dialog contains radio buttons for selecting among the colors (BLUE, GREEN, RED, YELLOW).
     * Updates the game with the chosen color.
     */
    public void lightWildDialog(){
        // Create panel
        JPanel panel = new JPanel();

        //Create Radio Buttons
        JRadioButton radioButton1 = new JRadioButton("BLUE");
        JRadioButton radioButton2 = new JRadioButton("GREEN");
        JRadioButton radioButton3 = new JRadioButton("RED");
        JRadioButton radioButton4 = new JRadioButton("YELLOW");

        //add colours to indicate colour
        radioButton1.setBackground(Color.BLUE);
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
                //statusBar.setText(game.getCurrentPlayerName() + " played WILD, new colour is BLUE");
                updateStatusBar("played", "WILD, colour: BLUE");
            } else if (radioButton2.isSelected()) {
                game.chooseNewColor(Card.Color.GREEN);
                //statusBar.setText(game.getCurrentPlayerName() + " played WILD, new colour is GREEN");
                updateStatusBar("played", "WILD, colour: GREEN");
            } else if (radioButton3.isSelected()) {
                game.chooseNewColor(Card.Color.RED);
                //statusBar.setText(game.getCurrentPlayerName() + " played WILD, new colour is RED");
                updateStatusBar("played", "WILD, colour: RED");
            } else {
                game.chooseNewColor(Card.Color.YELLOW);
                //statusBar.setText(game.getCurrentPlayerName() + " played WILD, new colour is YELLOW");
                updateStatusBar("played", "WILD, colour: YELLOW");
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
        undoItem.setEnabled(true);
        Card card = e.getCard();
        Boolean canPlay = e.canPlay();
        displayPlayerHand();
        isCardDrawn = true;
        if (!game.getCurrentPlayer().isBot()) {
            drawCardDialog(card, canPlay, e);
        }

        updateStatusBar("drew", card.getColor(game.isCurrentSideLight()) + " " + card.getRank(game.isCurrentSideLight()));


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
        undoItem.setEnabled(true);

        String specialCard = game.executeSpecialFunction(e.getCard());
        updateStatusBar("played", (e.getCard().getColor(game.isCurrentSideLight()).toString()) + " " + (e.getCard().getRank(game.isCurrentSideLight()).toString()));

        isCardPlayed = true;
        if(game.getCurrentPlayer().isBot()) {
            undoItem.setEnabled(false);

        }else{
            undoItem.setEnabled(true);
        }


        if(!game.getCurrentPlayer().isBot()) {
            //handle WILD, SKIP, REVERSE, +1, +2 cards if human player
            if (e.getCard().getColor(game.isCurrentSideLight()) == Card.Color.WILD) {
                if (game.isCurrentSideLight()){
                    lightWildDialog();
                } else{
                    Card.Color color = darkWildDialog();
                    if(e.getCard().getRank(false)== Card.Rank.DRAW_COLOR){
                        game.nextPlayerDrawColor(color);
                    }
                }
            }
        }
        if(specialCard!=null && specialCard.equals("FLIP")){
            displayPlayerHand();
            displayTopCard();

        }

        //call UNO button if the current player has only one card left
        if(game.getCurrentPlayer().getHand().isUNO()){
            callUNOButton.setEnabled(true);
            UNOButtonColour();
            if(game.getCurrentPlayer().isBot()){
                callUNOButton.doClick();
            }
        }


        // Check for a round winner and display the round results if the current player has no more cards
        if(game.getCurrentPlayer().getHand().isEmpty()){
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
        game.saveGameState();
        System.out.println("game snapshot saved after turn");

        // Reset UNO call button's state and color
        callUNOButton.setEnabled(false);
        UNOButtonColour();
        //statusBar.setText("");

    }
    /**
     * Handles the UNO call action in the UNO game.
     * @param e The UNOGameEvent associated with the UNO call action.
     */
    @Override
    public void handleCallUNO(UNOGameEvent e) {
        // Display a message indicating that the current player has called UNO
        JOptionPane.showMessageDialog(this, "Player "+game.getCurrentPlayerName()+" called UNO!");
        updateStatusBar("called", "UNO");
        //statusBar.setText(game.getCurrentPlayerName() + " called UNO");
        // Disable the UNO call button and update its color
        callUNOButton.setEnabled(false);
        UNOButtonColour();
    }

    @Override
    public void handleColourUpdate(UNOGameEvent e) {
        updateStatusBar("played", "WILD, colour: " + e.getColor());
    }

    @Override
    public void handleUndo(UNOGameEvent e) {
        System.out.println("reached handleUndo");
        updateStatusBar("Undo move", "is called!");
        drawCardButton.setEnabled(true);

        //case1: card is drawn but not played
            // leave top card as is
            // add last card in hand back to deck
            // remove last card from hand
        // case 2: card is drawn and played
            //change top card to prev
            // add top card back to deck
        // case 3: plays card from hand
            // put current topCard back to currentPlayer's hand
            // change top card to prev

        if(game.lastCard == game.prevTopCard){
            System.out.println("case1");
            displayTopCard();
            game.removeCardFromHand(game.getCurrentPlayer().getHand().getCards().size()-1);
            System.out.println("Display is updated after card is drawn+ played");
        }else if(game.lastCard != game.prevTopCard && !(isCardPlayed)){
            System.out.println("case2");
            game.removeCardFromHand(game.getCurrentPlayer().getHand().getCards().size()-1);
            displayPlayerHand();
        }else{
            System.out.println("case3");
            displayTopCard();
            game.updatePlayerHand(game.prevTopCard);
            System.out.println("Display is updated after card is drawn2");
            displayPlayerHand();
        }

        undoItem.setEnabled(false);
        JOptionPane.showMessageDialog(this, "Move is Undone!");

    }

    @Override
    public void handleUndoCaseOne(UNOGameEvent e) {
        System.out.println("reached handleUndo case1");
        updateStatusBar("Undo move", "is called!");
        drawCardButton.setEnabled(true);

        //case1: card is drawn but not played
        // leave top card as is
        // add last card in hand back to deck
        // remove last card from hand

        System.out.println("case1");
        displayTopCard();
        game.removeCardFromHand(game.getCurrentPlayer().getHand().getCards().size()-1);
        System.out.println("Display is updated after card is drawn+ played");

        undoItem.setEnabled(false);
        JOptionPane.showMessageDialog(this, "Move is Undone!");

    }

    @Override
    public void handleUndoCaseTwo(UNOGameEvent e) {
        // case 2: card is drawn and played
        //change top card to prev
        // add top card back to deck
        System.out.println("reached handleUndo case1");
        updateStatusBar("Undo move", "is called!");
        drawCardButton.setEnabled(true);

        System.out.println("case2");
        game.removeCardFromHand(game.getCurrentPlayer().getHand().getCards().size()-1);
        displayPlayerHand();

        undoItem.setEnabled(false);
        JOptionPane.showMessageDialog(this, "Move is Undone!");

    }

    @Override
    public void handleUndoCaseThree(UNOGameEvent e) {
        // case 3: plays card from hand
        // put current topCard back to currentPlayer's hand
        // change top card to prev
        System.out.println("reached handleUndo case1");
        updateStatusBar("Undo move", "is called!");
        drawCardButton.setEnabled(true);

        System.out.println("case3");
        displayTopCard();
        game.updatePlayerHand(game.prevTopCard);
        System.out.println("Display is updated after card is drawn2");
        displayPlayerHand();

        undoItem.setEnabled(false);
        JOptionPane.showMessageDialog(this, "Move is Undone!");

    }
    @Override
    public void handleEnableUndo(UNOGameEvent e){
        undoItem.setEnabled(true);
    }
}
