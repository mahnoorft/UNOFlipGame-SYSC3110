import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class UNOGameFrame extends JFrame {
    UNOGame game;
    UNOGameController controller;
    JPanel mainPanel, playerCardsPanel, topCardPanel, buttonPanel;
    JButton drawCardButton, endTurnButton;
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
            //String imagePath = IMAGES_FOLDER_PATH + cardName;
            ImageIcon icon = new ImageIcon(cardName);
            JLabel label = new JLabel(icon);
            playerCardsPanel.add(label);
        }

        // Revalidate and repaint the playerCardsPanel to reflect the changes
        playerCardsPanel.revalidate();
        playerCardsPanel.repaint();
    }

    private void displayTopCard() {

        Card topCard = game.topCard;
        String imagePath = topCard.getColorLight().name() + "_"+ topCard.getRankLight().name() + ".png";
        ImageIcon icon2 = new ImageIcon(imagePath);
        JLabel label2 = new JLabel(icon2);
        topCardPanel.add(label2);

        // Revalidate and repaint the playerCardsPanel to reflect the changes
        topCardPanel.revalidate();
        topCardPanel.repaint();
    }

    public static void main(String[] args) {
        UNOGame game = new UNOGame();
        new UNOGameFrame(game);

    }
}
