import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    Deck deck;
    Deck deck2;
    ArrayList<Card> cards;

    @BeforeEach
    void setUp() {
        //full deck
        deck = new Deck();

        //empty deck
        deck2 = new Deck();
        for (int i = 0; i < 108; i++){
            deck2.draw();
        }
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createDeck() {
        //1 - size is as expected

        //for loop for all colours, for loop for ranks


    }

    @Test
    void shuffle() {
        //create pile
        List<Card> pile = new ArrayList<>();
        pile.add(new Card(Card.Rank.ONE, Card.Color.RED));
        pile.add(new Card(Card.Rank.TWO, Card.Color.BLUE));
        pile.add(new Card(Card.Rank.THREE, Card.Color.RED));
        pile.add(new Card(Card.Rank.REVERSE, Card.Color.GREEN));
        pile.add(new Card(Card.Rank.SKIP, Card.Color.RED));
        pile.add(new Card(Card.Rank.FIVE, Card.Color.BLUE));
        pile.add(new Card(Card.Rank.SIX, Card.Color.WILD));
        pile.add(new Card(Card.Rank.SKIP, Card.Color.BLUE));

        System.out.println(pile);

        //create empty deck
        Deck deck3 = new Deck();
        for (int i = 0; i < 108; i++){
            deck3.draw();
        }
    }

    @Test
    void refill() {
        //Create a pile of cards
        List<Card> pile = new ArrayList<>();
        pile.add(new Card(Card.Rank.ONE, Card.Color.RED));
        pile.add(new Card(Card.Rank.TWO, Card.Color.BLUE));

        //verify initial size of deck2 is empty
        assertEquals(0, deck2.size());

        //refill the two cards in pile
        deck2.refill(pile);

        //verify size of deck has been updated
        assertEquals(2, deck2.size());
    }

    @Test
    void draw() {
        //1 - After one card is drawn, deck size should decrement by one
        Card drawnCard = deck.draw();
        assertNotNull(drawnCard);
        assertEquals(107, deck.size());

        //2 - if deck is empty shouldn't return a card
        assertNull(deck2.draw());
    }

    @Test
    void size() {
        //1 - checks if deck at the start is the number of cards expected
        assertEquals(108, deck.size());

        //2 - after one card is draw deck size should decrement by one
        deck.draw();
        assertEquals(107, deck.size());

    }
}