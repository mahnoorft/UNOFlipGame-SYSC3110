import java.util.EventObject;
/** This class represents an event causes the UNOGameFrame View to be updated
 * @author Areej Mahmoud 101218260
 * */

public class UNOGameEvent extends EventObject {

    /**
     * Constructs a prototypical Event.
     * @param model the object on which the Event initially occurred
     */
    public UNOGameEvent(UNOGame model, boolean wildPlayed) {
        super(model);
    }

    public UNOGameEvent(UNOGame model, Card card) {
        super(model);
    }
}
