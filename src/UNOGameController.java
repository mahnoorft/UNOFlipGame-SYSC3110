import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class UNOGameController implements ActionListener {
    UNOGame model;
    UNOGameFrame view;
    public UNOGameController(UNOGame model, UNOGameFrame view) {
        this.model = model;
        this.view = view;

        // Add action listeners to UI components in the view

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("test");
        String command = e.getActionCommand();
        if (command.equals("draw")){
            model.actionDrawCard();

        }
        else if(command.equals("end")){
            model.actionEndTurn();

        }
        else{
            int index = Integer.parseInt(command);
            System.out.println(model.actionPlayCard(index));
        }

        //action commands for when the button is pressed
        //if buttons are clicked, call model class with action buttons

    }
}
