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

    /** Constructor for class CreatePlayersView*/
    public CreatePlayersView() {
        //display player selection dialogue and get number of players
        Object[] possibilities = {2, 3, 4};
        int numPlayers = (int) JOptionPane.showInputDialog(
                null,
                "Welcome to UNO!\n"
                        + "Choose the number of players:",
                "Select Players",
                JOptionPane.PLAIN_MESSAGE, null,
                possibilities,
                2);

        //create panel with JTextFields to set player names based on numPlayers
        JPanel namePanel = new JPanel();
        namePanel.setLayout(new GridLayout(numPlayers, 0));
        ArrayList<JTextField> fieldList = new ArrayList<>();
        for (int i = 1; i <= numPlayers; i++) {
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
            this.nameList = new ArrayList<>();
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
    //returns list of names that user has inputted
    public ArrayList<String> getNameList(){
        return this.nameList;
    }
    public static void main(String[] args) {new CreatePlayersView();}
}
