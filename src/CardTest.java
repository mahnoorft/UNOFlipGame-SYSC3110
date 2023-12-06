import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
/**This Class is to test Card class for the UNO Flip game
 * @author Eric Cui 101237617*/
public class CardTest {

    Card card1,card2,card3,card4,card5;

    /**
     * Sets up the test structure. Called before every test case method.
     */
    @org.junit.jupiter.api.Test
    //Test that the points are assigned correctly to cards
    public void testAssignPoint() {
        card1 = new Card(Card.Rank.ONE, Card.Color.RED, Card.Rank.ONE, Card.Color.ORANGE);
        card2 = new Card(Card.Rank.REVERSE, Card.Color.GREEN,Card.Rank.REVERSE, Card.Color.TEAL);
        card3 = new Card(Card.Rank.DRAW2, Card.Color.BLUE, Card.Rank.DRAW_COLOR, Card.Color.PINK);

        assertEquals(1, card1.getPoints(true));
        assertEquals(20, card2.getPoints(true));
        assertEquals(50, card3.getPoints(true));

        assertEquals(1, card1.getPoints(false));
        assertEquals(20, card2.getPoints(false));
        assertEquals(50, card3.getPoints(false));
    }

    @org.junit.jupiter.api.Test
    //Test if a card can be played is correctly checking
    public void testCheckValid() {
        card1 = new Card(Card.Rank.ONE, Card.Color.RED,Card.Rank.ONE, Card.Color.ORANGE);
        card2 = new Card(Card.Rank.ONE, Card.Color.GREEN,Card.Rank.ONE, Card.Color.TEAL);
        card3 = new Card(Card.Rank.FIVE, Card.Color.RED,Card.Rank.FIVE, Card.Color.ORANGE);
        card4 = new Card(Card.Rank.DRAW2, Card.Color.WILD,Card.Rank.DRAW_COLOR, Card.Color.WILD);
        card5 = new Card(Card.Rank.NINE, Card.Color.BLUE,Card.Rank.NINE, Card.Color.PINK);

        assertTrue(card1.checkValid(card2,true));
        assertTrue(card1.checkValid(card3,true));
        assertTrue(card1.checkValid(card4,true));
        assertFalse(card1.checkValid(card5,true));

        assertTrue(card1.checkValid(card2,false));
        assertTrue(card1.checkValid(card3,false));
        assertTrue(card1.checkValid(card4,false));
        assertFalse(card1.checkValid(card5,false));
    }



    @org.junit.jupiter.api.Test
    public void testToString2() {
        card1 = new Card(Card.Rank.ONE, Card.Color.RED,Card.Rank.ONE, Card.Color.ORANGE);
        card2 = new Card(Card.Rank.ONE, Card.Color.GREEN,Card.Rank.ONE, Card.Color.TEAL);
        card3 = new Card(Card.Rank.REVERSE, Card.Color.YELLOW,Card.Rank.REVERSE, Card.Color.PURPLE);
        card4 = new Card(Card.Rank.DRAW2, Card.Color.WILD,Card.Rank.DRAW_COLOR, Card.Color.WILD);
        card5 = new Card(Card.Rank.NINE, Card.Color.BLUE,Card.Rank.NINE, Card.Color.PINK);

        assertEquals("RED_ONE",card1.toString2(true));
        assertEquals("GREEN_ONE",card2.toString2(true));
        assertEquals("YELLOW_REVERSE",card3.toString2(true));
        assertEquals("WILD_DRAW2",card4.toString2(true));
        assertEquals("BLUE_NINE",card5.toString2(true));

        assertEquals("ORANGE_ONE",card1.toString2(false));
        assertEquals("TEAL_ONE",card2.toString2(false));
        assertEquals("PURPLE_REVERSE",card3.toString2(false));
        assertEquals("WILD_DRAW_COLOR",card4.toString2(false));
        assertEquals("PINK_NINE",card5.toString2(false));
    }

    @org.junit.jupiter.api.Test
    public void testSaveAttributesToJSON() throws IOException {
        card1 = new Card(Card.Rank.ONE, Card.Color.RED, Card.Rank.ONE, Card.Color.ORANGE);
        card1.saveJsonObjectsToFile("testJson.json");
    }
}
