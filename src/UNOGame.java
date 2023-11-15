import javax.swing.*;
import java.util.*;

/** This class represents the model for UNO game and is responsible for
 * executes the UNO Game functions and updating the game logic*/

public class UNOGame{
    private ArrayList<Player> players;
    private Deck deck;
    private ArrayList<Card> pile;
    private int currentTurn;
    private int turnDirection;
    public Card topCard;
    private boolean currentSideLight;
    private int canPlayCard;
    private int turnSkipped;
    List<UNOGameHandler> view;


    /** Constructor for class UNOGame*/
    public UNOGame(){
        this.players = new ArrayList<Player>();
        this.deck = new Deck();
        this.pile = new ArrayList<Card>();
        this.currentTurn = 0;
        this.turnDirection = 1;
        this.topCard = null;
        this.currentSideLight = true;
        this.view = new ArrayList<UNOGameHandler>();
        createPlayers();
        canPlayCard = 2;
        turnSkipped = 0;
    }

    public void addUnoGameView(UNOGameHandler view){
        this.view.add(view);
    }

    /** Initialize player names and add players to the players ArrayList*/
    private void createPlayers(){
        CreatePlayersView view = new CreatePlayersView();

        //create a players with the given names in playerNames
        for(String name: view.getNameList()){
            Player player = new Player(name);
            players.add(player);
        }
    }
    /** Distribute playing cards and draw the first card from deck*/
    public void initializeGame(){
        deck = new Deck();
        pile = new ArrayList<Card>();
        // initialize all player names and distribute 7 cards to each
        for(int i = 0; i < players.size(); i++) {
            players.get(i).clearHand();
            for(int j=0; j<7; j++){
                players.get(i).drawCard(this.deck);
            }
        }
        //draw first card from deck to start the game
        //Switch top card if it's wild
        topCard = deck.draw();
        while (topCard.getColor(currentSideLight) == Card.Color.WILD){
            pile.add(topCard);
            topCard = deck.draw();
        }
        this.pile.add(topCard);

    }

    /** Skip the turn of the next player*/
    private void skipTurn(){
        turnSkipped = 1;
        System.out.println("Skipped player "+players.get(currentTurn).getName());
    }
    /** Reverse the turn direction*/
    private void reverseTurn(){
        if(players.size()==2){
            skipTurn();
        }
        else {
            turnDirection *= -1;
            System.out.println("Reversed the direction of the turn to " + turnDirection);
        }
    }

    /**
     * Draws the number of cards specified in numCards for the next Player
     * @param numCards    number of cards to draw
     */
    private void nextPlayerDrawCard(int numCards){

        System.out.println("Player " + players.get(getNextPlayerIndex()).getName()+" draw "+numCards+" cards");
        for(int i=0;i<numCards;i++){
            players.get(getNextPlayerIndex()).drawCard(deck);
        }
    }


    /** Return the score of the player by adding up the cards points held by other players.
     * @return score of winner for this round*/
    public int calculateWinnerScore(){
        int score = 0;
        for (int i=0; i<players.size(); i++) {
            // checks whether the otherPlayer is not the same as the player for whom the score is being calculated
            if (i != currentTurn) {
                Hand h = players.get(i).getHand();
                score += h.calculateTotalPoints();
            }
        }
        players.get(currentTurn).incrementScore(score);
        return score;
    }
    /** Execute the spaecial card function based on the rank
     * @param card the special card played*/
    public String executeSpecialFunction(Card card){
        switch (card.rankLight){
            case REVERSE:
                this.reverseTurn();
                return "reverse";
            case SKIP:
                this.skipTurn();
                return "skip";
            case SKIP_All:
                turnSkipped = -1;
                return "Skipped all";
            case WILD:
                return "wild";
            case DRAW1:
                this.nextPlayerDrawCard(1);
                this.skipTurn();
                return "draw 1";
            case DRAW2:
                this.nextPlayerDrawCard(2);
                this.skipTurn();
                return "WILD draw 2";

            case FLIP:
                currentSideLight = !currentSideLight;
                return "FLIP";
        }
        return null;
    }
    /** Return the index of the next player
     * @return index of the next player*/
    private int getNextPlayerIndex(){
        int index = currentTurn + turnDirection;
        if (index < 0){index += players.size();}
        else if (index>(players.size()-1)) {index -= players.size();}
        return index;
    }

    /** Get the current player's cards in the format COLOR_RANK
     * @return List of strings containing the cards in the format COLOR_RANK */
    public List<String> getCurrentPlayerCardNames() {
        List<String> cardNames = new ArrayList<>();
        Hand currentPlayerHand = players.get(currentTurn).getHand();
        List<Card> cards = currentPlayerHand.getCards();

        for (Card card : cards) {
            String cardName = card.getColor(currentSideLight).name() + "_" + card.getRank(currentSideLight).name() +".png";
            cardNames.add(cardName);
        }
        return cardNames;
    }

    public boolean isCurrentSideLight() {return currentSideLight;}


    public void updatePlayerHand(Card card){
        players.get(currentTurn).getHand().addCard(card);
    }

    public void updateTopCard(Card card){
        topCard = card;
    }

    public String getCurrentPlayerName(){
        return players.get(currentTurn).getName();
    }
    public Player getCurrentPlayer(){
        return players.get(currentTurn);
    }
    public void updateTurn(){
        canPlayCard = 2;
    }

    /**
     * Plays card at index
     * @param index the index of the card that is being played in the current player's hand
     * @return the card that is being played
     */
    public Card actionPlayCard(int index){
        if(canPlayCard == 0){
            JOptionPane.showMessageDialog(null, "You have already played a card in this turn",
                    "Error!", JOptionPane.ERROR_MESSAGE);
            return null;}
        Player player = players.get(currentTurn);
        if(canPlayCard == 1 && index != player.getHand().getCards().size()-1){
            JOptionPane.showMessageDialog(null, "Attempting to play other cards when a card is drawn",
                    "Error!", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        if(!player.getHand().getCards().get(index).checkValid(topCard,currentSideLight)){
            JOptionPane.showMessageDialog(null, "Attempting to play an illegal card",
                    "Error!", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        Card c = player.playCard(index,topCard,currentSideLight);

        //update top card
        if (c!= null){
            topCard = c;
        }
        canPlayCard = 0;

        for (UNOGameHandler view: view){
            view.handlePlayCard(new UNOGameEvent(this, c, false));
        }

        return c;
    }

    public boolean actionDrawCard(){
        Card c = deck.draw();
        if(c.checkValid(topCard,currentSideLight)){
            canPlayCard = 1;
            for (UNOGameHandler view: view){
                view.handleDrawCard(new UNOGameEvent(this, c, true));
            }
            return true;
        }
        for (UNOGameHandler view: view){
            view.handleDrawCard(new UNOGameEvent(this, c, false));
        }

        return false;
    }
    public void actionEndTurn(){
        if(turnSkipped !=-1){
            currentTurn = getNextPlayerIndex();
            if(turnSkipped == 1){
                currentTurn = getNextPlayerIndex();
            }
        }
        turnSkipped = 0;


        canPlayCard = 2;



        for (UNOGameHandler view: view){
            view.handleNextTurn(new UNOGameEvent(this));
        }

    }

    /** Apply the penalty for the player who did not call UNO*/
    public void applyCallPenalty(){
        int index = currentTurn - 1;

        System.out.println("Player " + players.get(index).getName()+" draw "+2+" cards");
        for(int i=0;i<2;i++){
            players.get(index).drawCard(deck);
        }
    }

    /** Update top card to the new chosen color
     * @param color the chosen color
     * */
    public void chooseNewColor(Card.Color color){
        topCard.setColorLight(color);
    }

}