import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
/** This class represents the Create Players dialogue view that prompts
 * users to select the number of players and enter player names.
 * @author Areej Mahmoud 101218260
 * */

public class CreatePlayersView {

    //list of player names inputted by user
    private ArrayList<String> nameList;
    private int numHumanPlayers, numAIPlayers;

    /** Constructor for class CreatePlayersView. Displays Game set-up dialogs
     * and prompts user to select the number of human and AI players.*/
    public CreatePlayersView() {
        this.nameList = new ArrayList<>();
        boolean includeAI = false;
        //display welcome message and get AI player preference
        int choice = JOptionPane.showConfirmDialog(null,
                "Welcome to UNO Flip Game!\n" + "Include AI players?", "UNO FLip!", JOptionPane.YES_NO_OPTION);
        if(choice==JOptionPane.YES_OPTION) {
            includeAI= true;
            //display player selection dialogue and get number of AI players
            Object[] aiChoices = {1, 2, 3, 4, 5}; // least number of AI players is 1
            numAIPlayers = (int) JOptionPane.showInputDialog(
                    null,
                    "Welcome to UNO!\n"
                            + "Select the number of AI players:",
                    "AI Players",
                    JOptionPane.PLAIN_MESSAGE, null,
                    aiChoices,
                    1);
        }
        Object[]  noAIchoices = {2, 3, 4, 5}; // least number of human players is 2
        Object[]  yesAIchoices = {1, 2, 3, 4, 5}; // least number of human players is 1
        //display player selection dialogue and get number of human players
        numHumanPlayers = (int) JOptionPane.showInputDialog(
                null,
                "Select the number of human players:",
                "Human Players",
                JOptionPane.PLAIN_MESSAGE, null,
                includeAI ? yesAIchoices:noAIchoices,
                2);

        //display the set player names dialog
        setPlayerNamesDialog();
    }
    private void setPlayerNamesDialog(){
        //create panel with JTextFields to set player names based on numHumanPlayers
        JPanel namePanel = new JPanel();
        namePanel.setLayout(new GridLayout(numHumanPlayers, 0));
        ArrayList<JTextField> fieldList = new ArrayList<>();
        for (int i = 1; i <= numHumanPlayers; i++) {
            namePanel.add(new JLabel("Player " + i + " name:"));
            JTextField f = new JTextField(10);
            namePanel.add(f);
            fieldList.add(f);
        }

        boolean validInput = false;
        while (!validInput) {
            //display input dialogue to set player names
            int result = JOptionPane.showConfirmDialog(null, namePanel,
                    "Create Players", JOptionPane.OK_CANCEL_OPTION);

            //check that all names are valid and store names in ArrayList
            validInput = true;
            if (result == JOptionPane.OK_OPTION) {
                for (JTextField f : fieldList) {
                    String input = f.getText();
                    //if any field is empty, show error message and break
                    if (input.isBlank() || input.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Please enter a name for all players!",
                                "Invalid Input", JOptionPane.ERROR_MESSAGE);
                        validInput = false;
                        break;
                    } else {
                        nameList.add(input);
                    }
                }
            }
        }
    }
    /**returns list of the names of human players
     * @return list of names that user has inputted*/
    public ArrayList<String> getNameList(){
        return this.nameList;
    }

    /**returns the number of AI players
     * @return number of AI players*/
    public int getNumAIPlayers() {
        return numAIPlayers;
    }
}
