import java.util.ArrayList;

public class UNOGameState {
    private ArrayList<Player> players;
    private Deck deck;
    private ArrayList<Card> pile;
    private int currentTurn;
    private int turnDirection;
    Card topCard;

    private boolean currentSideLight;
    private int canPlayCard;
    private int turnSkipped;

    public UNOGameState(UNOGameModel game ) {

        deck = game.deck.cloneClass();
        pile = game.pile;
        currentTurn= game.currentTurn;
        turnDirection = game.turnDirection;
        topCard = game.topCard;
        currentSideLight = game.isCurrentSideLight();
        canPlayCard = game.canPlayCard;
        turnSkipped = game.turnSkipped;
        players = new ArrayList<Player>();
        for(Player player: game.getAllPlayers()){
            players.add(player.cloneClass());
        }
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
