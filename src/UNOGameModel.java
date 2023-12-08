import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.List;

//import com.fasterxml.jackson.databind.ObjectMapper;
import javax.json.*;

import java.util.Stack;


/**
 *  This class represents the model for a UNO Flip game.
 *  It manages the game state and logic, including player turns, card actions, and scoring.
 *  The model interacts with the view and controller to update the game state and notify the view of changes.
 *  It follows the Model-View-Controller (MVC) architectural pattern.
 *
 *  @author Areej Mahmoud 101218260
 *  @author Mahnoor Fatima 101192353
 *  @author Eric Cui 101237617
 *  @author Rama Alkhouli 101198025
 */

public class UNOGameModel {
    ArrayList<Player> players;
    Deck deck;
    ArrayList<Card> pile;
    int currentTurn;
    int turnDirection;
    public Card topCard;
    private boolean currentSideLight;
    int canPlayCard;
    int turnSkipped;
    List<UNOGameHandler> view;

    private Stack<UNOGameState> gameStateStack;

    private UNOGameFrame frame;

    Card prevTopCard;

    Card lastDrawnCard;


    Card lastCard;




    /** Constructor for class UNOGame*/
    public UNOGameModel(){
        this.players = new ArrayList<Player>();
        this.deck = new Deck();
        this.pile = new ArrayList<Card>();
        this.currentTurn = 0;
        this.turnDirection = 1;
        this.topCard = null;
        this.prevTopCard = null;
        this.currentSideLight = true;
        this.view = new ArrayList<UNOGameHandler>();
        this.gameStateStack = new Stack<>();

        createPlayers();
        canPlayCard = 2;
        turnSkipped = 0;
    }

    /**
     * Adds a UNOGameHandler view to the list of views.
     * The view will be notified of changes in the game state.
     * @param view The UNOGameHandler view to be added.*/
    public void addUnoGameView(UNOGameHandler view){
        this.view.add(view);
    }

    /** Initialize player names and add players to the players ArrayList*/
    private void createPlayers(){
        CreatePlayersView view = new CreatePlayersView();

        //create human players with the given names in playerNames
        for(String name: view.getNameList()){
            Player player = new Player(name, false);
            players.add(player);
        }
        //create the specified number of AI players
        for(int i=1; i<=view.getNumAIPlayers(); i++){
            PlayerAI playerAI = new PlayerAI("AI Player "+i, true);
            players.add(playerAI);
        }
    }
    /** Distribute playing cards and draw the first card from deck*/
    public void initializeGame(){
        deck = new Deck();
        pile = new ArrayList<Card>();
        currentSideLight = true; // start game on light side
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
                score += h.calculateTotalPoints(currentSideLight);
            }
        }
        players.get(currentTurn).incrementScore(score);
        return score;
    }

    /** Execute the special card function based on the rank
     * @param card the special card played*/
    public String executeSpecialFunction(Card card){
        switch (card.getRank(currentSideLight)){
            case REVERSE:
                this.reverseTurn();
                return "reverse";
            case SKIP:
                this.skipTurn();
                return "skip";
            case SKIP_All:
                turnSkipped = -1;
                return "Skipped all";
            case WILD_LIGHT:
                return "Wild Light";
            case WILD_DARK:
                return "Wild Dark";
            case DRAW1:
                this.nextPlayerDrawCard(1);
                this.skipTurn();
                return "draw 1";
            case DRAW5:
                this.nextPlayerDrawCard(5);
                this.skipTurn();
                return "draw 5";
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

    public String nextPlayerDrawColor(Card.Color color){
        int i = 0;
        while(true){
            i++;
            Card card = players.get(getNextPlayerIndex()).drawCard(deck);
            System.out.println(card.toString2(currentSideLight));
            if(card.getColor(currentSideLight) == color){
                break;
            }
        }

        this.skipTurn();
        System.out.println("WILD draw color; Player "+players.get(getNextPlayerIndex()).getName()+" draw " + i +" cards");
        return "WILD draw color; Player "+players.get(getNextPlayerIndex()).getName()+" draw " + i +" cards";
    }

    /** Return the index of the next player
     * @return index of the next player*/
    private int getNextPlayerIndex(){
        int index = currentTurn + turnDirection;
        if (index < 0){index += players.size();}
        else if (index>(players.size()-1)) {index -= players.size();}
        return index;
    }

    /**
     * Checks if the current side is the light side.
     * @return True if the current side is the light side, false otherwise.*/
    public boolean isCurrentSideLight() {return currentSideLight;}

    /**
     * Updates the player's hand by adding a card.
     * @param card The card to be added to the player's hand.*/
    public void updatePlayerHand(Card card){
        players.get(currentTurn).getHand().addCard(card);
    }

    public void removeCardFromHand(int index){
        players.get(currentTurn).getHand().removeCard(index);
    }

    /**
     * Updates the top card on the pile.
     * @param card The new top card on the pile.*/
    public void updateTopCard(Card card){
        topCard = card;
    }

    /**
     * Retrieves the name of the current player.
     * @return The name of the current player.*/
    public String getCurrentPlayerName(){
        return players.get(currentTurn).getName();
    }

    /**
     * Retrieves the current player object.
     * @return The current player object.*/
    public Player getCurrentPlayer(){
        return players.get(currentTurn);
    }

    /**
     * Updates the turn by setting the canPlayCard value to 2. */
    public void updateTurn(){
        canPlayCard = 2;
    }



    // New method to restore the game state
    private void restoreGameState(UNOGameState gameState) {
        players = gameState.getPlayers();
        deck = gameState.getDeck();
        pile = gameState.getPile();
        currentTurn = gameState.getCurrentTurn();
        turnDirection = gameState.getTurnDirection();
        topCard = gameState.getTopCard();
        currentSideLight = gameState.isCurrentSideLight();
        canPlayCard = gameState.getCanPlayCard();
        turnSkipped = gameState.getTurnSkipped();
    }

    // New method to save the current game state to the stack
    void saveGameState() {
        UNOGameState gameState = new UNOGameState(this);
        gameStateStack.push(gameState);
    }

    /**
     * Plays card at index
     * @param index the index of the card that is being played in the current player's hand
     * @return the card that is being played
     */
    public Card actionPlayCard(int index){
        if(canPlayCard == -1){
            JOptionPane.showMessageDialog(null, "This is AI's turn",
                    "Error!", JOptionPane.ERROR_MESSAGE);
            return null;
        }
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
        saveGameState();
        Card c = player.playCard(index,topCard,currentSideLight );

        //update top card
        if (c!= null){
            topCard = c;
            pile.add(c);
        }
        canPlayCard = 0;


        for (UNOGameHandler view: view){
            view.handlePlayCard(new UNOGameEvent(this, c, false));
        }

        return c;
    }

    /**
     * Handles the action of a player drawing a card from the deck.
     * @return True if the card can be played, false otherwise.
     */
    public boolean actionDrawCard(){
        saveGameState();
        Card c = deck.draw();
        if(c.checkValid(topCard,currentSideLight)){
            canPlayCard = 1;
            // Store the drawn card in a variable for potential undo
            lastDrawnCard = c;
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
    /**
     * Handles the action of a player ending their turn.
     */
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
        if(players.get(currentTurn).isBot()){
            botPlayCard();
        }
    }

    public boolean isCardPlayedAfterDrawing() {
//    Player currentPlayer = getCurrentPlayer();
//    Hand playerHand = currentPlayer.getHand();
//    //Card drawnCard = playerHand.getRecentDraw();
//    lastCard = getCurrentPlayer().getHand().getLastCard();
        return lastDrawnCard == topCard;
    }

    public boolean isDrawnCardNotPlayed(){
        Player currentPlayer = getCurrentPlayer();
        Hand playerHand = currentPlayer.getHand();
        Card drawnCard = playerHand.getRecentDraw();

        if(playerHand.getCards().contains(drawnCard)) {
                return true;
        }
        return false;
    }


    // New method to undo the last move
    public void actionUndo() {

        UNOGameState unoGameState = gameStateStack.pop();
        System.out.println("<<<");
        System.out.println("TopCard");
        System.out.println(unoGameState.getTopCard().toString2(true));
        System.out.println("Current player");
        System.out.println(unoGameState.getPlayers().get(unoGameState.getCurrentTurn()).getName());
        System.out.println("hand size");
        System.out.println(unoGameState.getPlayers().get(unoGameState.getCurrentTurn()).getHand().getCards().size());
        System.out.println(">>>");
        System.out.println(unoGameState.topCard.toString2(true));
        this.pile = unoGameState.getPile();
        this.topCard = unoGameState.getTopCard();
        this.players = unoGameState.getPlayers();
        this.deck = unoGameState.getDeck();
        this.currentTurn = unoGameState.getCurrentTurn();
        this.currentSideLight = unoGameState.isCurrentSideLight();
        this.canPlayCard = 2;
        this.turnSkipped = 0;
        System.out.println("<<<<");
        System.out.println("TopCard");
        System.out.println(this.topCard.toString2(true));
        System.out.println("Current player");
        System.out.println(this.players.get(currentTurn).getName());
        System.out.println("hand size");
        System.out.println(this.players.get(currentTurn).getHand().getCards().size());
        System.out.println(">>>>");
        for (UNOGameHandler view : view) {
            view.handleUndo(new UNOGameEvent(this));
        }
        /**
        lastCard = getCurrentPlayer().getHand().getLastCard();
        if (!gameStateStack.isEmpty()) {
            // if undo action is called and card is drawn from deck and is played
            if (canPlayCard == 1 && isCardPlayedAfterDrawing()) {
                System.out.println("this is current top card" + topCard);
                prevTopCard = topCard;
                System.out.println("this is prevTop card which equals drawnCard" + prevTopCard);
                deck.putBackCard(prevTopCard);
                //getCurrentPlayer().getHand().removeCard(getCurrentPlayer().getHand().getCards().size()-1);
                UNOGameState gameState = gameStateStack.pop();
                System.out.println("Popped: " + gameState);
                restoreGameState(gameState);
                System.out.println("case1, drawn and played");


                for (UNOGameHandler view : view) {
                    view.handleUndoCaseOne(new UNOGameEvent(this));
                    System.out.println("called handler in view Case 1");
                }
            }else if (canPlayCard == 2  && !(isCardPlayedAfterDrawing())){ // if card is drawn but not played
                System.out.println("This is last card in hand"+ lastCard);
                deck.putBackCard(lastCard);
                System.out.println("card is put back to deck!");
                getCurrentPlayer().getHand().removeCard(getCurrentPlayer().getHand().getCards().size()-1);
                UNOGameState gameState = gameStateStack.pop();
                System.out.println("Popped: " + gameState);
                restoreGameState(gameState);
                System.out.println("case 2 drawn but not played");


                for (UNOGameHandler view : view) {
                    view.handleUndoCaseTwo(new UNOGameEvent(this));
                    System.out.println("called handler in view Case 2");
                }
            }else {
                System.out.println("Normal Case!");
                prevTopCard = topCard;
                UNOGameState gameState = gameStateStack.pop();
                System.out.println("Popped: " + gameState);
                restoreGameState(gameState);
                System.out.println("restored game state!!! Normal case");



                for (UNOGameHandler view : view) {
                    view.handleUndoCaseThree(new UNOGameEvent(this));
                    System.out.println("called handler in view Case 3");
                }
            }

            saveGameState();
//            for (UNOGameHandler view : view) {
//                // Update the UI to reflect the restored game state
//                // Provide visual feedback for successful undo
//                view.handleUndo(new UNOGameEvent(this));
//                System.out.println("called handler in view");
//            }
        }
         */
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
        topCard.setColor(color, currentSideLight);
    }

    /**
     * Plays a card or draws a card for a bot player.
     */
    public void botPlayCard(){
        canPlayCard = -1;
        PlayerAI bot = (PlayerAI)players.get(currentTurn);
        Card c = bot.getBestPlay(topCard,isCurrentSideLight());
        if(c != null){
            System.out.println("Bot Played a card " + c.toString2(currentSideLight));
            //frame.updateStatusBar("played", c.getColor(isCurrentSideLight()) + " " + c.getRank(isCurrentSideLight()));
            topCard = c;
            pile.add(c);

            for (UNOGameHandler view: view){
                view.handlePlayCard(new UNOGameEvent(this, c, false));
            }
            if(c.getColor(currentSideLight) == Card.Color.WILD){
                Card.Color wildColour = bot.getBestColor(currentSideLight);
                chooseNewColor(wildColour);
                if(c.getRank(currentSideLight) == Card.Rank.DRAW_COLOR){
                    nextPlayerDrawColor(wildColour);
                }
                for (UNOGameHandler view: view){
                    view.handleColourUpdate(new UNOGameEvent(this, wildColour));
                }
            }

        }else{
            System.out.println("Bot Drew a card");
            c = deck.draw();
            //frame.updateStatusBar("drew", c.getColor(isCurrentSideLight()) + " " + c.getRank(isCurrentSideLight()));

            if(c.checkValid(topCard,currentSideLight)){
                System.out.println("Bot Played the drawn card " + c.toString2(currentSideLight));
                topCard = c;
                pile.add(c);
                for (UNOGameHandler view: view){
                    view.handlePlayCard(new UNOGameEvent(this, c, false));
                }
                if(c.getColor(currentSideLight) == Card.Color.WILD){
                    Card.Color wildColour = bot.getBestColor(currentSideLight);
                    chooseNewColor(wildColour);
                    if(c.getRank(currentSideLight) == Card.Rank.DRAW_COLOR){
                        nextPlayerDrawColor(wildColour);
                    }
                    for (UNOGameHandler view: view){
                        view.handleColourUpdate(new UNOGameEvent(this, wildColour));
                    }
                }
            }else{
                bot.getHand().addCard(c);
                for (UNOGameHandler view: view){
                    view.handleDrawCard(new UNOGameEvent(this, c, false));
                }
                //frame.updateStatusBar("called", "UNO");
            }

        }

    }

    /** Return a JSON object containing the attributes in this class
     * @return JsonObject of the class attributes*/
    public JsonObject saveAttributesToJson(){
        //create players jsonArray
        JsonArrayBuilder jsonPlayersArrayBuilder = Json.createArrayBuilder();

        for (int i = 0; i < this.players.size() ; i++){
            JsonObject jsonObject = this.players.get(i).saveAttributesToJson();
            jsonPlayersArrayBuilder.add(i,jsonObject);
        }
        //create pile jsonArray
        JsonArrayBuilder jsonPileArrayBuilder = Json.createArrayBuilder();

        for (int i = 0; i < this.pile.size() ; i++){
            JsonObject jsonObject = this.pile.get(i).saveAttributesToJson();
            jsonPileArrayBuilder.add(i,jsonObject);
        }

        // Build the JSON arrays
        JsonArray jsonPlayersArrayList = jsonPlayersArrayBuilder.build();
        JsonArray jsonPileArrayList = jsonPileArrayBuilder.build();

        //save all attributes to a json object
        JsonObject jsonObject = Json.createObjectBuilder()
                .add("players", jsonPlayersArrayList)
                .add("deck", deck.saveAttributesToJson())
                .add("pile", jsonPileArrayList)
                .add("currentTurn", currentTurn)
                .add("turnDirection", turnDirection)
                .add("topCard", topCard.saveAttributesToJson())
                .add("currentSideLight", currentSideLight)
                .add("canPlayCard", canPlayCard)
                .add("turnSkipped", turnSkipped)
                .build();

        return jsonObject;
    }

    /**Saves JSON object to a new JSON file called fileName
     * @param fileName name of the file to save JSON*/
    public void saveJsonObjectsToFile(String fileName) throws IOException {
        try(PrintWriter writer = new PrintWriter(new FileWriter(fileName))){
            JsonObject jsonObject = saveAttributesToJson();
            writer.println(jsonObject);
        }
    }

}