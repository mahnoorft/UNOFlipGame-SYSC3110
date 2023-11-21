import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/** This class represents the controller for UNO game and is responsible for
 * processing the GUI input through ActionListeners and performing
 * model and view calls
 * */

public class UNOGameController implements ActionListener {
    UNOGameModel model;
    UNOGameFrame view;

    /**
     * Constructs a new UNOGameController with references to the model and view.
     *
     * @param model The UNOGame model representing the game logic.
     * @param view  The UNOGameFrame view representing the  view or user interface.
     */
    public UNOGameController(UNOGameModel model, UNOGameFrame view) {
        this.model = model;
        this.view = view;
    }

    /**
     * Handles the action performed when a button is clicked.
     * If buttons are clicked, calls the model class with corresponding action buttons.
     *
     * @param e The ActionEvent representing the button click.
     */

    @Override
    public void actionPerformed(ActionEvent e) {
        //action commands for when the button is pressed
        //if buttons are clicked, call model class with action buttons
        String command = e.getActionCommand();
        if (command.equals("draw")){
            model.actionDrawCard();
        }
        else if(command.equals("end")){
            model.actionEndTurn();
        }
        else if(command.equals("call")){
            // Call the view's handleCallUNO method with a new UNOGameEvent
            view.handleCallUNO(new UNOGameEvent(model));
        }
        else if (command.equals("new")) {
            // Start a new round
            System.out.println("NEW ROUND");
            model.initializeGame();
            view.restartRoundScreen();
        } else if (command.equals("newGame")) {
            // Start a new game
            view.dispose();
            UNOGameModel game = new UNOGameModel();
            UNOGameFrame unoGameFrame = new UNOGameFrame(game);
        } else{
            // If the command is a number, parse it and call model's actionPlayCard method
            int index = Integer.parseInt(command);
            model.actionPlayCard(index);
        }

    }
}
