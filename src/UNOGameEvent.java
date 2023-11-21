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

    public UNOGameEvent(UNOGameModel model){
        super(model);
    }

    /**
     * Constructs a new UNOGameEvent with references to the UNOGame model, a Card, and a boolean.
     *
     * @param model   The UNOGame model representing the game logic.
     * @param card    The Card associated with the event.
     * @param canPlay    A boolean indicating whether the player can play the card.
     */
    public UNOGameEvent(UNOGameModel model, Card card, Boolean canPlay) {
        super(model);
        this.card = card;
        this.canPlay = canPlay;
    }

    /**
     * Gets the Card associated with this UNOGameEvent.
     *
     * @return The Card associated with the event.
     */
    public Card getCard(){ return card;}

    /**
     * A flag to Check if the player can play the card
     *
     * @return True if the player can play the card, false otherwise.
     */
    public Boolean canPlay(){ return canPlay;}


}
