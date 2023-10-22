import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * This Class is to test PLayer class for the UNO Flip game
 * @author Rama Alkhouli 101198025
 */
class PlayerTest {

    Deck deck;
    Player player;


    // Set up method that will run before every test in this class.
    @org.junit.jupiter.api.BeforeEach
    public void setUp() {
        deck = new Deck();
        player = new Player("Sam");

    }

    @org.junit.jupiter.api.Test
    public void TestDrawCard() {
        int handSizeBefore = player.getHand().getCards().size();
        player.drawCard(deck);
        int handSizeAfter = player.getHand().getCards().size();
        // Assert that the player's hand has one more card after drawing
        assertEquals(handSizeBefore + 1, handSizeAfter);
    }

    @org.junit.jupiter.api.Test
    public void TestAddCard() {
        Card card = new Card(Card.Rank.ONE, Card.Color.YELLOW);
        player.addCard(card);
        // assert that the player's hand has one more card after adding
        assertEquals(1, player.getHand().getCards().size());
        // Ensure the added card is in the player's hand
        assertTrue(player.getHand().getCards().contains(card));
    }

    @org.junit.jupiter.api.Test
    public void TestIncrementScore() {
        int scoreBefore = player.getScore();
        player.incrementScore(20);
        int scoreAfter = player.getScore();
        //assert that the player's score is incremented by 20
        assertEquals(scoreBefore + 20, scoreAfter);

    }

    @org.junit.jupiter.api.Test
    public void TestGetScore() {
        int score = player.getScore();
        //assert that the player's initial score is 0'
        assertEquals(0, score);
    }
    @org.junit.jupiter.api.Test
    public void TestGetName() {
        String name = player.getName();
        //assert that the player's name is the same name that was set in setUp()
        assertEquals("Sam", name);
    }

    @org.junit.jupiter.api.Test
    public void TestGetHand() {
        Hand hand = player.getHand();
        // Assert that the player's hand isn't null
        assertNotNull(hand);
    }

    @org.junit.jupiter.api.Test
    public void TestPlayCard() {
        Card topCard = new Card(Card.Rank.SIX, Card.Color.YELLOW);
        Card cardToPlay = new Card(Card.Rank.THREE, Card.Color.YELLOW);
        player.addCard(cardToPlay);

        int initialHandSize = player.getHand().getCards().size();
        Card playedCard = player.playCard(0, topCard);
        int finalHandSize = player.getHand().getCards().size();

        // Ensure that the correct card is played
        assertEquals(cardToPlay, playedCard);
        // Assert that the player's hand has one less card after playing
        assertEquals(initialHandSize - 1, finalHandSize);

    }

    @org.junit.jupiter.api.Test
    public void TestClearHand() {
        Card card1 = new Card(Card.Rank.SEVEN, Card.Color.WILD);
        Card card2 = new Card(Card.Rank.EIGHT, Card.Color.YELLOW);
        Card card3 = new Card(Card.Rank.REVERSE, Card.Color.WILD);

        player.addCard(card1);
        player.addCard(card2);
        player.addCard(card3);

        player.clearHand();
        int finalHandSize = player.getHand().getCards().size();

        // Assert that the player's hand is empty after clearing
        assertEquals(0, finalHandSize);

    }
}
