import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerAITest {
    PlayerAI ai;
    Card c1,c2,c3, topCard;

    @BeforeEach
    void setUp() {
        ai = new PlayerAI("AI", true);
        c1 = new Card(Card.Rank.ONE, Card.Color.YELLOW, Card.Rank.ONE, Card.Color.PURPLE);
        c2 = new Card(Card.Rank.DRAW2, Card.Color.WILD, Card.Rank.DRAW_COLOR, Card.Color.WILD);
        c3 = new Card(Card.Rank.DRAW1, Card.Color.GREEN, Card.Rank.DRAW5, Card.Color.TEAL);
        ai.getHand().addCard(c1);
        ai.getHand().addCard(c2);
        ai.getHand().addCard(c3);
        topCard = new Card(Card.Rank.THREE, Card.Color.GREEN, Card.Rank.THREE, Card.Color.TEAL);
    }

    @Test
    void getBestPlay() {
        //check light side case
        assertEquals(c2, ai.getBestPlay(topCard, true));

        //check dark side case
        assertEquals(c3, ai.getBestPlay(topCard, false));
    }

    @Test
    void getBestColor() {
        //check light side case
        assertEquals(Card.Color.YELLOW, ai.getBestColor(true));

        //check dark side case
        assertEquals(Card.Color.PURPLE, ai.getBestColor(false));

        //check default cases (when all cards in hand are WILD)
        ai.getHand().removeCard(0);
        ai.getHand().removeCard(1);

        assertEquals(Card.Color.RED, ai.getBestColor(true));
        assertEquals(Card.Color.ORANGE, ai.getBestColor(false));
    }
}