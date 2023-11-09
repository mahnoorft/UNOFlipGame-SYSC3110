import java.util.EventObject;
/** This class represents an event causes the UNOGameFrame View to be updated
 * @author Areej Mahmoud 101218260
 * */

public class UNOGameEvent extends EventObject {

    Card card;
    Boolean canPlay;

    Boolean isWild;
    /**
     * Constructs a prototypical Event.
     * @param model the object on which the Event initially occurred
     */
    public UNOGameEvent(UNOGame model, boolean wildPlayed) {
        super(model);
        this.isWild = wildPlayed;
    }

    public UNOGameEvent(UNOGame model, Card card, Boolean bool) {
        super(model);
        this.card = card;
        this.canPlay = bool;
    }

    public UNOGameEvent(UNOGame model, Integer turn) {
        super(model);
    }

    public Card getCard(){ return card;}

    public Boolean canPlay(){ return canPlay;}

    public Boolean isWild(){ return isWild;}


}
