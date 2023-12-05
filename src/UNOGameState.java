import java.util.ArrayList;

public class UNOGameState {
    private ArrayList<Player> players;
    private Deck deck;
    private ArrayList<Card> pile;
    private int currentTurn;
    private int turnDirection;
    private Card topCard;
    private boolean currentSideLight;
    private int canPlayCard;
    private int turnSkipped;

    public UNOGameState(UNOGameModel game ) {
        players = game.players;
        deck = game.deck;
        pile = game.pile;
        currentTurn= game.currentTurn;
        turnDirection = game.turnDirection;
        topCard = game.topCard;
        currentSideLight = game.isCurrentSideLight();
        canPlayCard = game.canPlayCard;
        turnSkipped = game.turnSkipped;
    }

    // Getter methods for all fields
    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Deck getDeck() {
        return deck;
    }

    public ArrayList<Card> getPile() {
        return pile;
    }

    public int getCurrentTurn() {
        return currentTurn;
    }

    public int getTurnDirection() {
        return turnDirection;
    }

    public Card getTopCard() {
        return topCard;
    }

    public boolean isCurrentSideLight() {
        return currentSideLight;
    }

    public int getCanPlayCard() {
        return canPlayCard;
    }

    public int getTurnSkipped() {
        return turnSkipped;
    }
}
