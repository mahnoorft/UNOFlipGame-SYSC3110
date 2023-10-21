import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * This is the JUnit test class for Hand.
 * @author Areej Mahmoud
 */

public class HandTest {
    Hand hand;
    Card c1,c2,c3;

    /**
     * Sets up the test structure. Called before every test case method.
     */
    @BeforeEach
    void setUp() {
        hand = new Hand();
        c1 = new Card(Card.Rank.ONE, Card.Color.YELLOW);
        c2 = new Card(Card.Rank.DRAW2, Card.Color.WILD);
        c3 = new Card(Card.Rank.DRAW1, Card.Color.GREEN);
    }

    @org.junit.jupiter.api.Test
    public void testAddCard() {
        hand.addCard(c1);
        hand.addCard(c2);
        hand.addCard(c3);
        //ensure cards are added correctly, and size is updated
        assertEquals(3, hand.getCards().size());
        assertEquals(c1, hand.getCards().get(0));
        assertEquals(c2, hand.getCards().get(1));
        assertEquals(c3, hand.getCards().get(2));
    }

    @org.junit.jupiter.api.Test
    public void testRemoveCard() {
        hand.addCard(c1);
        hand.addCard(c2);
        assertEquals(2, hand.getCards().size());
        //ensure cards are removed correctly, and size is updated
        hand.removeCard(1);
        assertEquals(1, hand.getCards().size());
        hand.removeCard(0);
        assertEquals(0, hand.getCards().size());
    }

    @org.junit.jupiter.api.Test
    public void testRemoveAll() {
        hand.addCard(c1);
        hand.addCard(c2);
        hand.addCard(c3);
        //ensure removeAll() removes all cards from the hand
        hand.removeAll();
        assertEquals(0, hand.getCards().size());
    }

    @org.junit.jupiter.api.Test
    public void testCalculateTotalPoints() {
        //test hand is empty case
        assertEquals(0, hand.calculateTotalPoints());
        //ensure correctly calculates points
        hand.addCard(c1);
        hand.addCard(c2);
        hand.addCard(c3);
        assertEquals(61, hand.calculateTotalPoints());
    }

    @org.junit.jupiter.api.Test
    public void testIsUNO() {
        //test true case
        hand.addCard(c1);
        assertEquals(1,hand.getCards().size());
        assertTrue(hand.isUNO());

        //test false case
        hand.addCard(c2);
        assertEquals(2,hand.getCards().size());
        assertFalse(hand.isUNO());
    }

    @org.junit.jupiter.api.Test
    public void testIsEmpty() {
        //test true case
        assertEquals(0,hand.getCards().size());
        assertTrue(hand.isEmpty());

        //test false case
        hand.addCard(c1);
        assertEquals(1,hand.getCards().size());
        assertFalse(hand.isEmpty());
    }
}