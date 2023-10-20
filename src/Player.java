import java.util.*;

/**
 * THis class represents a player in a Flip UNO Game
 * Written by: Rama Alkhouli 101198025
 *
 */
public class  Player {

    private String name;
    private Hand hand;
    private int score;

    public Player(String name) {
        this.name = name;
        this.hand = new Hand();
        this.score = 0;
    }

    // draw card from deck and add to hand
    public void drawCard(Deck deck) {
        Card drawnCard = deck.draw();
        hand.addCard(drawnCard);
    }

    public void incrementScore(int addedScore) {
        this.score += addedScore;
    }

    public int getScore() {
        return score;
    }
    public String getName() {
        return name;
    }

    public Hand getHand() {
        return hand;
    }

    // play one of the player's cards
    public Card playCard(int index, Card topCard) {
        if (index >= 0 && index < hand.getCards().size()) {
            Card cardToPlay = hand.getCards().get(index);
            if (cardToPlay.checkValid(topCard)) {
                // Print the card that is being played
                System.out.println(name + " played " + cardToPlay);
                hand.removeCard(index);
                return cardToPlay;
            } else {
                System.out.println("The selected card cannot be played.");
            }
        } else {
            System.out.println("Invalid index. Please choose a valid index to play.");
        }
        return null;
    }
}