import java.util.*;

/** This class represents the cards in an UNO player's hands.
 * @author Areej Mahmoud 101218260
 */

public class Hand{

    //list of cards in the hand
    private ArrayList<Card> cards;

    public Hand() {
        this.cards = new ArrayList<Card>();
    }
    //Adds a new card to the Hand
    public void addCard(Card card){
        cards.add(card);
    }
    //remove a card with index cardIndex from the hand
    public void removeCard(int cardIndex){
        cards.remove(cardIndex);
    }
    //remove all cards from the hand
    public void removeAll(){
        cards.clear();
    }
    //calculate the sum of points for all cards currently held.
    public int calculateTotalPoints(){
        int points = 0;
        for(Card c: cards){
            points += c.getPoints();
        }
        return points;
    }
    //returns an ArrayList of cards in the Hand
    public ArrayList<Card> getCards() {
        return this.cards;
    }
    //returns true if only one card is left in the hand.
    public boolean isUNO(){
        return cards.size() == 1;
    }
    //returns true if there are no cards in the hand.
    public boolean isEmpty(){
        return cards.isEmpty();
    }

    public String toString(){
        StringBuilder s = new StringBuilder();
        for(int i=0; i< cards.size(); i++){
            s.append(i+1);
            s.append(" "+ cards.get(i).toString() + " \n");
        }
        return s.toString();
    }

}