import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The UNOGameController class is the controller in the Model-View-Controller (MVC) architecture for a UNO game.
 * It handles user input and updates the game state and user interface accordingly. It implements the ActionListener
 * interface to handle GUI button click events. The actionPerformed method is the main method that handles the events
 * triggered by the GUI buttons. It serves as the bridge between the GUI view events and the underlying game model,
 * ensuring that the game state and user interface stay synchronized.
 *
 *  @author Areej Mahmoud 101218260
 *  @author Mahnoor Fatima 101192353
 *  @author Eric Cui 101237617
 *  @author Rama Alkhouli 101198025
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
        if (command.equals("draw")) {
            model.actionDrawCard();
        } else if (command.equals("end")) {
            model.actionEndTurn();
        } else if (command.equals("Undo")){
            model.actionUndo();
            System.out.println("controller called actionUndo");
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
