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
        String command = e.getActionCommand();


    }
}
