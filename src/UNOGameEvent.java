import java.util.EventObject;
/** This class represents an event causes the UNOGameFrame View to be updated
 * @author Areej Mahmoud 101218260
 * */

public class UNOGameEvent extends EventObject {

    Card card;
    Boolean canPlay;

    /**
     * Constructs a prototypical Event.
     * @param model the object on which the Event initially occurred
     */

    public UNOGameEvent(UNOGame model){
        super(model);
    }
    public UNOGameEvent(UNOGame model, Card card, Boolean bool) {
        super(model);
        this.card = card;
        this.canPlay = bool;
    }

    public Card getCard(){ return card;}

    public Boolean canPlay(){ return canPlay;}


}
