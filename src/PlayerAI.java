import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.HashMap;

/**
 * This Class is to represent an AI Player class for the UNO Flip game.
 * AI players generate the best move based on the card with
 * the highest point in their hand, and choose WILD colors based on the first
 *  held color or the default.
 * @author Eric Cui & Areej Mahmoud
 */

public class PlayerAI extends Player{

    public PlayerAI(String name, boolean isBot) {

        super(name, isBot);
    }

    public PlayerAI(){}

    /** Return and plays the valid Card with the highest points to play.
     * @param topCard  the current top card
     * @param isSideLight the current side of the playing cards
     * */
    public Card getBestPlay(Card topCard, boolean isSideLight){
        //HashMap<points, cardIndex> to store all valid cards and their points
        HashMap<Integer, Integer> map = new HashMap<>();

        //iterate through hand and store valid cards.
        //HashMap does not allow duplicate keys, so if 2 cards are valid
        // and have same points, stores higher index. Solves problem of same points.
        for(int i=0;i<this.hand.getCards().size();i++){
            if(this.hand.getCards().get(i).checkValid(topCard,isSideLight)){
                map.put(this.hand.getCards().get(i).getPoints(isSideLight),i);
            }
        }
        if(map.isEmpty()){
            return null; //no valid cards
        }
        int maxPoints = (Collections.max(map.keySet()));
        System.out.println(maxPoints);
        System.out.println(map.get(maxPoints));
        return playCard(map.get(maxPoints),topCard,isSideLight);
    }

    /** Return the non-WILD color of the first card held,
     *  or the default based on playing side.
     * @param isSideLight the current side of the cards
     * */
    public Card.Color getBestColor(boolean isSideLight){
        for(Card card:this.hand.getCards()){
            Card.Color curr = card.getColor(isSideLight);
            if(curr!= Card.Color.WILD){
                return curr;
            }
        }
        //Default color choice is RED/ORANGE
        if(isSideLight) {
            return Card.Color.RED;
        }else{
            return Card.Color.ORANGE;
        }
    }

}
