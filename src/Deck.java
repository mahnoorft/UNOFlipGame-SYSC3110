import java.util.*;

/**
 * This Class represents the UNO Flip game deck, consisting of all playing cards.
 * @author Mahnoor Fatima
 */
public class Deck {
    private List<Card> deck;

    public Deck() {
        this.deck = new ArrayList<Card>();
        createDeck();
    }
    //initialize the number of cards according to UNO rules and add them to the deck
    public void createDeck() {
        for (int colors = 0; colors < 4; colors++){

            //adding one rank 0
            deck.add(new Card(Card.Rank.ZERO, Card.Color.values()[colors]));

            //adding two of ranks 1-9 and 3 special cards
            for(int i =1; i<13; i++){
                for (int j =0; j<2; j++){
                    deck.add(new Card(Card.Rank.values()[i], Card.Color.values()[colors]));
                }
            }
        }
        //adding four of Wild Cards
        for(int i =12; i<15; i++){
            for (int j =0; j<4; j++){
                deck.add(new Card(Card.Rank.values()[i], Card.Color.WILD));
            }
        }
        this.shuffle();
    }
    // Shuffles the cards in the deck randomly.
    public void shuffle() {
        Collections.shuffle(deck);
    }

    //refills the deck from the pile and shuffles the deck
    public void refill(List<Card> pile) {
        deck.addAll(pile);
        this.shuffle();
    }

    //removes one card from the deck to be drawn
    public Card draw() {
        return deck.remove(1);
    }
    //returns the size of the deck (used for testing)
    public int size() {
        return deck.size();
    }
}
