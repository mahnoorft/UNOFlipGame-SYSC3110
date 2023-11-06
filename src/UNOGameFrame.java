import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class UNOGameFrame extends JFrame {
    UNOGame game;
    UNOGameController controller;
    JPanel mainPanel, playerCardsPanel, topCard, buttonPanel;
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
        JList<Player> playerList = new JList<>();
        topCard = new JPanel();
        topCard.add(new JTextField("top card"));
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        //add components to main Panel
        mainPanel.add(playerCardsPanel, BorderLayout.SOUTH);
        mainPanel.add(topCard, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.EAST);
        mainPanel.add(playerList, BorderLayout.WEST);
        JTextField playerNameField = new JTextField("Player name");
        mainPanel.add(playerNameField, BorderLayout.NORTH);

        System.out.println("Start");
        displayPlayerHand();
        System.out.println("end");


        //initialize and add buttons to buttonPanel
        drawCardButton = new JButton("Draw Card");
        endTurnButton = new JButton("End Turn");
        buttonPanel.add(drawCardButton);
        buttonPanel.add(endTurnButton);

       String imagePath = IMAGES_FOLDER_PATH + "BLUE_TWO.png";
        ImageIcon cardImage = new ImageIcon(imagePath);
        JLabel cardLabel = new JLabel(cardImage);
        playerCardsPanel.add(cardLabel);


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
        game.initializeGame();
        List<String> cardNames = game.getCurrentPlayerCardNames();

        for (String cardName : cardNames) {
            String imagePath = IMAGES_FOLDER_PATH + cardName; // Assuming cards are named with their respective COLOR_RANK.png
            ImageIcon icon = new ImageIcon(imagePath);
            JLabel label = new JLabel(icon);
            playerCardsPanel.add(label);
        }

        // Revalidate and repaint the playerCardsPanel to reflect the changes
        playerCardsPanel.revalidate();
        playerCardsPanel.repaint();
    }


    public static void main(String[] args) {
        UNOGame game = new UNOGame();
        new UNOGameFrame(game);

    }
}
