import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UNOGameFrame extends JFrame {
    UNOGame game;
    UNOGameController controller;
    JPanel mainPanel, playerCardsPanel, topCardPanel, buttonPanel, winRoundPanel;
    JButton drawCardButton, endTurnButton, newRoundButton;
    JLabel winRoundMessage,winRoundMessagePoints;
    JMenuBar menuBar;
    JMenu gameMenu;
    ArrayList<ImageIcon> iconImages;
    Card card;
    private static final String IMAGES_FOLDER_PATH = "src/images/"; // Path to the images folder

    public UNOGameFrame( UNOGame game) {
        super("UNO Flip Game!");
        this.game = game;

        //initialize menu and menu item
        menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);
        gameMenu = new JMenu("Game");
        menuBar.add(gameMenu);
        JMenuItem newGame = new JMenuItem("New Game");
        gameMenu.add(newGame);

        //set up the content pane
        mainPanel = new JPanel(new BorderLayout());
        this.add(mainPanel);
        playerCardsPanel = new JPanel(new FlowLayout());
        //playerCardsPanel = new JPanel(new GridLayout(1, 0));
        JList<Player> playerList = new JList<>();
        topCardPanel = new JPanel();
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(playerCardsPanel);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);


        //add components to main Panel
        mainPanel.add(scrollPane, BorderLayout.SOUTH);
        mainPanel.add(topCardPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.EAST);
        mainPanel.add(playerList, BorderLayout.WEST);
        JTextField playerNameField = new JTextField("Player name");
        mainPanel.add(playerNameField, BorderLayout.NORTH);

        game.initializeGame();
        displayPlayerHand();
        displayTopCard();


        //initialize and add buttons to buttonPanel
        drawCardButton = new JButton("Draw Card");
        endTurnButton = new JButton("End Turn");
        buttonPanel.add(drawCardButton);
        buttonPanel.add(endTurnButton);


        //add ActionListeners and initialize controller
        controller = new UNOGameController(game, this);
        drawCardButton.addActionListener(controller);
        endTurnButton.addActionListener(controller);
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
        newRoundButton = new JButton("New Round");
        newRoundButton.setBounds(300,500,200,80);
        newRoundButton.setFont(new Font("Monospaced Bold", Font.PLAIN, 24));

        winRoundPanel.add(newRoundButton);
        winRoundPanel.setVisible(false);

        //Edit the visuals of buttons
        //create action commands for when button is clicked

        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 800);
        this.setVisible(true);


    }

    private void displayPlayerHand() {
        playerCardsPanel.removeAll();

        List<String> cardNames = game.getCurrentPlayerCardNames();

        for (String cardName : cardNames) {
            // Assuming cards are named with their respective COLOR_RANK.png
            String imagePath = IMAGES_FOLDER_PATH + cardName;
            ImageIcon icon = new ImageIcon(imagePath);
            JLabel label = new JLabel(icon);
            playerCardsPanel.add(label);
        }

        // Revalidate and repaint the playerCardsPanel to reflect the changes
        playerCardsPanel.revalidate();
        playerCardsPanel.repaint();
    }

    private void displayTopCard() {

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
    }

    public static void main(String[] args) {
        UNOGame game = new UNOGame();
        UNOGameFrame unoGameFrame = new UNOGameFrame(game);
        Scanner input = new Scanner(System.in);
        while(true){
            String x = input.nextLine();
            switch (x){

                case "winRound":
                    unoGameFrame.winRoundScreen("test player",200,4);
                    break;
                case "game":
                    unoGameFrame.restartRoundScreen();
                    break;
                case "winGame":
                    unoGameFrame.winGameScreen("test player",569);
                    break;
                default:
                    System.out.println("Invalid Input");
            }
        }

    }
}
