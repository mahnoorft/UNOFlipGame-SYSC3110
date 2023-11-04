import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class UNOGameFrame extends JFrame{
    UNOGame game;
    UNOGameController controller;
    JPanel playerCardsPanel, topCard, buttonPanel;
    JButton drawCardButton, nextTurnButton;
    JMenuBar menuBar;
    JMenu gameMenu;

    public UNOGameFrame()  {
        super("UNO Flip Game!");
        this.game = game; //NEED TO ADD UNOGame game as parameter to the constructor
        //initialize menu and menu item
        menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);
        gameMenu = new JMenu("Game");
        menuBar.add(gameMenu);
        JMenuItem newGame = new JMenuItem("New Game");
        gameMenu.add(newGame);

        //set up the content pane
        this.setLayout(new BorderLayout());
        playerCardsPanel = new JPanel(new FlowLayout());
        playerCardsPanel.add(new JTextField("Player cards"));
        JList<Player> playerList= new JList<>();
        topCard = new JPanel();
        topCard.add(new JTextField("top card"));
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        //add components to main Panel
        add(playerCardsPanel,BorderLayout.SOUTH);
        add(topCard,BorderLayout.CENTER);
        add(buttonPanel,BorderLayout.EAST);
        add(playerList,BorderLayout.WEST);
        JTextField playerNameField = new JTextField("Player name");
        add(playerNameField,BorderLayout.NORTH);

        //initialize and add buttons to buttonPanel
        drawCardButton = new JButton("Draw Card");
        nextTurnButton = new JButton("Next Turn");
        buttonPanel.add(drawCardButton);
        buttonPanel.add(nextTurnButton);

        //add ActionListeners and initialize controller
        controller = new UNOGameController(game, this);
        drawCardButton.addActionListener(controller);
        drawCardButton.setActionCommand("draw");
        nextTurnButton.addActionListener(controller);
        nextTurnButton.setActionCommand("next");
        newGame.addActionListener(controller);
        newGame.setActionCommand("new");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800,800);
        this.setVisible(true);

    }

    public static void main(String[] args) {new UNOGameFrame();}
}
