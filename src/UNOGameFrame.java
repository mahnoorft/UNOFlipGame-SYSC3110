import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class UNOGameFrame extends JFrame{
    UNOGame game;
    UNOGameController controller;
    JPanel mainPanel, playerCardsPanel, topCard, buttonPanel;
    JButton drawCardButton, nextTurnButton;
    JMenuBar menuBar;
    JMenu gameMenu;

    public UNOGameFrame()  {
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
        playerCardsPanel.add(new JTextField("Player cards"));
        JList<Player> playerList= new JList<>();
        topCard = new JPanel();
        topCard.add(new JTextField("top card"));
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        //add components to main Panel
        mainPanel.add(playerCardsPanel,BorderLayout.SOUTH);
        mainPanel.add(topCard,BorderLayout.CENTER);
        mainPanel.add(buttonPanel,BorderLayout.EAST);
        mainPanel.add(playerList,BorderLayout.WEST);
        JTextField playerNameField = new JTextField("Player name");
        mainPanel.add(playerNameField,BorderLayout.NORTH);

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
    public ArrayList<String> createPlayersView(){
        //display player selection dialogue and get number of players
        Object[] possibilities = {2, 3, 4};
        int numPlayers = (int)JOptionPane.showInputDialog(
                this,
                "Welcome to UNO!\n"
                        + "Choose the number of players:",
                "Select Players",
                JOptionPane.PLAIN_MESSAGE,null,
                possibilities,
                2);

        //create panel with JTextFields to set player names based on numPlayers
        JPanel namePanel = new JPanel();
        namePanel.setLayout(new GridLayout(numPlayers,0));
        for (int i=1; i<=numPlayers; i++){
            namePanel.add(new JLabel("Player "+i+" name:"));
            namePanel.add(new JTextField(10));
        }

        //display input dialogue to set player names
        int result = JOptionPane.showConfirmDialog(this, namePanel,
                "Create Players", JOptionPane.OK_CANCEL_OPTION);

        //store player names in ArrayList and return ArrayList
        ArrayList<String> nameList = new ArrayList<>();
        if (result == JOptionPane.OK_OPTION) {
            for(Component c: namePanel.getComponents()){
                JTextField field = (JTextField) c;
                nameList.add(field.getText());
            }
        }
        return nameList;
    }
    public static void main(String[] args) {
        UNOGameFrame f = new UNOGameFrame();
        f.createPlayersView();
    }
}
