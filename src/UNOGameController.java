import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/** This class represents the controller for UNO game and is responsible for
 * processing the GUI input through ActionListeners and performing
 * model and view calls
 * */

public class UNOGameController implements ActionListener {
    UNOGame model;
    UNOGameFrame view;
    public UNOGameController(UNOGame model, UNOGameFrame view) {
        this.model = model;
        this.view = view;
    }

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
            view.handleCallUNO(new UNOGameEvent(model));
        }
        else if (command.equals("new")) {
            System.out.println("NEW ROUND");
            view.restartRoundScreen();
        } else{
            int index = Integer.parseInt(command);
            model.actionPlayCard(index);
        }

    }
}
