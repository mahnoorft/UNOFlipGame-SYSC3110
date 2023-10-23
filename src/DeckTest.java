import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
/**
 * This Class is to test Deck class for the UNO Flip game
 * @author Mahnoor Fatima
 */
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
    @Test
    void testCreateDeck() {
        //checks if deck is initialized with the number of cards expected
        assertEquals(108, deck.size());

        //for loop for all colors excluding WILD, for loop for ranks (1-9, and SKIP, REVERSE, DRAW1)
        for (int colors = 0; colors < 4; colors++){
            for(int ranks = 0; ranks < 13; ranks++){
                Card cardToCheck = new Card(Card.Rank.values()[ranks], Card.Color.values()[colors]);
                assertTrue(deck.getDeck().contains(cardToCheck));
            }
        }

        //check that the WILD colour exists, and its two ranks WILD, DRAW2
        for(int ranks = 13; ranks < 15; ranks++){
            Card cardToCheck = new Card(Card.Rank.values()[ranks], Card.Color.WILD);
            assertTrue(deck.getDeck().contains(cardToCheck));
        }
    }

    @Test
    void testRefill() {
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
    void testDraw() {
        //1 - After one card is drawn, deck size should decrement by one
        Card drawnCard = deck.draw();
        assertNotNull(drawnCard);
        assertEquals(107, deck.size());

        //2 - if deck is empty shouldn't return a card
        assertNull(deck2.draw());
    }

    @Test
    void testSize() {
        //1 - checks if deck at the start is the number of cards expected
        assertEquals(108, deck.size());

        //2 - after one card is draw deck size should decrement by one
        deck.draw();
        assertEquals(107, deck.size());
    }
}