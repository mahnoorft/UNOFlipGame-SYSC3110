import java.util.*;

public class Deck {
    private List<Card> deck;

    public Deck() {
        this.deck = new ArrayList<Card>();
    }

    public void createDeck() {
        for (int colors = 0; colors < 4; colors++){

            //adding one rank 0
            deck.add(new Card(Card.Rank.ZERO, Card.Color.values()[colors]));

            //adding two of ranks 1-9 and 3 special cards
            for(int i =0; i<12; i++){
                for (int j =0; j<2; j++){
                    deck.add(new Card(Card.Rank.values()[i], Card.Color.values()[colors]));
                }
            }
        }
        //adding four of Wild Cards
        for(int i =12; i<14; i++){
            for (int j =0; j<4; j++){
                deck.add(new Card(Card.Rank.values()[i], Card.Color.WILD));
            }
        }
        this.shuffle();
    }

    public void shuffle() {
        Collections.shuffle(deck);
    }

    public void refill(List<Card> pile) {
        deck.addAll(pile);
        this.shuffle();
    }

    public Card draw() {
        return deck.remove(0);
    }

    public int size() {
        return deck.size();
    }
}
