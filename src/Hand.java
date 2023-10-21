import java.util.*;

/** This class represents the cards in an UNO player's hands.
 * Written by: Areej Mahmoud 101218260
 * Date: 10/13/2023
 */

public class Hand{
    private List<Card> cards;

    public Hand() {
        this.cards = new ArrayList<Card>();
    }
    public void addCard(Card card){
        cards.add(card);
    }
    public void removeCard(int cardIndex){
        cards.remove(cardIndex);
    }
    public void removeAll(){
        cards.clear();
    }

    public int calculateTotalPoints(){
        int points = 0;
        for(Card c: cards){
            points += c.getPoints();
        }
        return points;
    }

    public List<Card> getCards() {
        return this.cards;
    }

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