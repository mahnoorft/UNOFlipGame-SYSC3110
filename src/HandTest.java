import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This is the JUnit test class for Hand.
 * @author Areej Mahmoud 101218260
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
        c1 = new Card(Card.Rank.ONE, Card.Color.YELLOW, Card.Rank.ONE, Card.Color.PURPLE);
        c2 = new Card(Card.Rank.DRAW2, Card.Color.WILD, Card.Rank.DRAW_COLOR, Card.Color.WILD);
        c3 = new Card(Card.Rank.DRAW1, Card.Color.GREEN, Card.Rank.DRAW5, Card.Color.TEAL);
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
        assertEquals(0, hand.calculateTotalPoints(true));

        hand.addCard(c1);
        hand.addCard(c2);
        hand.addCard(c3);

        //ensure correct calculation of light side points
        assertEquals(61, hand.calculateTotalPoints(true));
        //ensure correct calculation of dark side points
        assertEquals(61, hand.calculateTotalPoints(true));

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
    @org.junit.jupiter.api.Test
    public void testSaveAttributesToJSON() throws IOException {
        hand.addCard(c1);
        hand.addCard(c2);
        hand.addCard(c3);

        String actual = hand.saveAttributesToJson().toString();
        System.out.println(actual);
        String expected = "{\"cards\":[{\"rankLight\":\"ONE\",\"colorLight\":\"YELLOW\",\"rankDark\":\"ONE\",\"colorDark\":\"PURPLE\"}," +
                "{\"rankLight\":\"DRAW2\",\"colorLight\":\"WILD\",\"rankDark\":\"DRAW_COLOR\",\"colorDark\":\"WILD\"}," +
                "{\"rankLight\":\"DRAW1\",\"colorLight\":\"GREEN\",\"rankDark\":\"DRAW5\",\"colorDark\":\"TEAL\"}]}";
        assertEquals(expected, actual);
    }
}