import java.util.*;

/**
 * This Class represents the UNO Flip game deck, consisting of all playing cards.
 * @author Mahnoor Fatima 101192353
 */
public class Deck {
    private List<Card> deck;

    /**Constructor for Class Deck*/
    public Deck() {
        this.deck = new ArrayList<Card>();
        createDeck();
    }

    /**initialize the number of cards according to UNO rules and add them to the deck*/
//    public void createDeck() {
//        for (int colors = 0; colors < 4; colors++){
//
//            //adding one rank 0
//            deck.add(new Card(Card.Rank.ZERO, Card.Color.values()[colors]));
//
//            //adding two Cards of ranks 1-9 and 3 special cards
//            for(int i =1; i<13; i++){
//                for (int j =0; j<2; j++){
//                    deck.add(new Card(Card.Rank.values()[i], Card.Color.values()[colors]));
//                }
//            }
//        }
//        //adding four Cards of Wild and WildDraw2
//        for(int i =13; i<15; i++){
//            for (int j =0; j<4; j++){
//                deck.add(new Card(Card.Rank.values()[i], Card.Color.WILD));
//            }
//        }
//        this.shuffle();
//    }

    public void createDeck() {
        // Add light side cards
        for (int colorIndex = 0; colorIndex <= 3; colorIndex++) {
            Card.Color color = Card.Color.values()[colorIndex];
            if (color != Card.Color.WILD) {
                // Add one rank 0 card
                deck.add(new Card(Card.Rank.ZERO, color));

                // Add two cards of ranks 1-9 and special cards
                for (int i = 1; i <= 13; i++) {
                    for (int j = 0; j < 2; j++) {
                        deck.add(new Card(Card.Rank.values()[i], color));

                    }
                }

                for (int i = 14; i <= 15; i++) {
                    for (int j = 0; j < 4; j++)
                        deck.add(new Card(Card.Rank.values()[i], Card.Color.WILD));
                }
            }
        }

        // Add dark side cards
        for (int colorIndex = 5; colorIndex <= 8; colorIndex++) {
            Card.Color color = Card.Color.values()[colorIndex];
            if (color != Card.Color.WILD) {
                // Add one rank 0 card
                deck.add(new Card(Card.Rank.ZERO, color));

                // Add two cards of ranks 1-9 and special cards REVERSE, FLIP
                for (int i = 1; i <= 11; i++) {
                    for (int j = 0; j < 2; j++) {
                        deck.add(new Card(Card.Rank.values()[i], color));
                    }
                }
                // Adds 4 cards of skip everyone and draw 5 cards
                for (int i = 17; i <= 18; i++) {
                    for (int j = 0; j < 2; j++) {
                    deck.add(new Card(Card.Rank.values()[i], color));
                    }
                }

            }
        }

        // Add Wild cards
        for (int i = 0; i < 4; i++) {
            deck.add(new Card(Card.Rank.WILD_LIGHT, Card.Color.WILD));
            deck.add(new Card(Card.Rank.WILD_DARK, Card.Color.WILD));
            deck.add(new Card(Card.Rank.WILD_DRAW_COLOR, Card.Color.WILD));
        }

        this.shuffle();
    }

    /** @return ArrayList of all cards in the deck*/
    public List<Card> getDeck(){
        return this.deck;
    }

    /** Shuffles the cards in the deck randomly.*/
    public void shuffle() {
        Collections.shuffle(deck);
    }

    /** Refills the deck from the pile and shuffles the deck
     *  @param pile the pile to refill the deck from*/
    public void refill(List<Card> pile) {
        deck.addAll(pile);
        this.shuffle();
    }

    /** removes one card from the deck to be drawn
     * @return Card drawn from deck*/
    public Card draw() {
        if (!deck.isEmpty()){
            return deck.remove(0);
        }
        return null;
    }
    /** @return the size of the deck*/
    public int size() {
        return deck.size();
    }
    /** @return a String representation of Deck */
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (Card card : deck) {
            sb.append(card.toString()).append(", ");
        }
        return sb.toString();
    }
}
