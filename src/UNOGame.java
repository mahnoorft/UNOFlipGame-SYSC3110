import java.util.*;
import java.util.Scanner;
/** This class initializes a text-based interface for an UNO game and
 * executes the UNO Game functions until a winner is announced*/

public class UNOGame{
    private ArrayList<Player> players;
    private Deck deck;
    private ArrayList<Card> pile;
    private int currentTurn;
    private int turnDirection;
    private Card topCard;
    private boolean currentSideLight;
    private boolean gameActive;
    private Scanner input; //not used with GUI anymore

    /** Constructor for class UNOGame*/
    public UNOGame(){
        this.players = new ArrayList<Player>();
        this.deck = new Deck();
        this.pile = new ArrayList<Card>();
        this.currentTurn = 0;
        this.turnDirection = 1;
        this.topCard = null;
        this.currentSideLight = true;
        this.input = new Scanner(System.in);
        createPlayers();

    }

    /** Initializes the game and keeps the game running until a winner is announced*/
    public void play(){
        initializeGame();
        gameActive = true;
        while(gameActive){
            takeTurn();
            currentTurn = getNextPlayerIndex();
        }
    }
    /** Initialize player names and add players to the players ArrayList*/
    private void createPlayers(){
        //CHANGE TO WORK CORRECTLY FOR MVC
        CreatePlayersView view = new CreatePlayersView();

        //create a players with the given names in playerNames
        for(String name: view.getNameList()){
            Player player = new Player(name);
            players.add(player);
        }
    }
    /** Distribute playing cards and draw the first card from deck*/
    private void initializeGame(){
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
        //Assume it is okay for starting card to be Wild
        topCard = deck.draw();
        this.pile.add(topCard);

    }

    /** Perform all functionalities for taking a turn in UNO*/
    private void takeTurn() {
        int userInput = -1;

        Player player = players.get(currentTurn);

        //Initial display
        System.out.println(player.getName() + "'s Turn: ");
        System.out.println("Current side: " + (currentSideLight ? "Light" : "Dark"));
        System.out.println("Your cards: " + "\n" + player.getHand());
        System.out.println("Top Card: " + topCard);

        System.out.println("Enter card index to play or 0 to draw a card:");

        while(true){
            try{
                userInput = Integer.parseInt(input.nextLine());
                if(userInput>=0 && userInput <= player.getHand().getCards().size()){
                    if(userInput == 0){
                        Card c = deck.draw();
                        System.out.println("You drew a " + c.toString());
                        if(c.checkValid(topCard)){
                            System.out.println("enter 1 to play card, press other to end turn");
                            if(input.nextLine().equals("1")){
                                topCard = c;
                                pile.add(c);
                                userInput = -2;
                            }else{
                                player.getHand().addCard(c);
                            }
                        }else{
                            System.out.println("Enter any to continue");
                            player.getHand().addCard(c);
                            input.nextLine();
                        }
                        break;
                    }else{
                        Card c = player.playCard(userInput-1,topCard);
                        if(c != null){
                            topCard = c;
                            break;
                        }
                    }
                }
                System.out.println("Enter card index to play or 0 to draw a card:");
            }catch(NumberFormatException e){
                System.out.println("Please enter a valid number");
            }
        }

        //update top card with the card last played
        if(userInput != 0 && topCard.getColorLight()== Card.Color.WILD){
            this.chooseNewColor();
        }

        //Announce winner if no cards remaining
        if (player.getHand().isEmpty()) {
            winRound();
        }

        //check if card's special, if it is executeSpecialFunction
        if (userInput != 0 && topCard.isSpecial()) {
            executeSpecialFunction(topCard);
        } else {
            System.out.println(player.getName() + "'s Turn Finished");
        }

        //call UNO if on last card
        if (player.getHand().isUNO()){
            System.out.println(player.getName().toUpperCase() + " CALLS UNO");
        }


    }

    /** Skip the turn of the next player*/
    private void skipTurn(){
        currentTurn = getNextPlayerIndex();
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
    /** Get input on new color for WILD cards*/
    private void chooseNewColor(){
        while(topCard.getColorLight()== Card.Color.WILD){
            System.out.print("Please enter your choice of color: ");
            String i = input.nextLine().toUpperCase();
            switch (i) {
                case "RED" -> topCard.setColorLight(Card.Color.RED);
                case "YELLOW" -> topCard.setColorLight((Card.Color.YELLOW));
                case "GREEN" -> topCard.setColorLight((Card.Color.GREEN));
                case "BLUE" -> topCard.setColorLight((Card.Color.BLUE));
                default -> System.out.println("Please choose a valid color (red, yellow, green, blue)");
            }
        }
    }
    /** Draws the number of cards specified in numCards for the next Player
     * @param numCards number of cards to draw  */
    private void nextPlayerDrawCard(int numCards){

        System.out.println("Player " + players.get(getNextPlayerIndex()).getName()+" draw "+numCards+" cards");
        for(int i=0;i<numCards;i++){
            players.get(getNextPlayerIndex()).drawCard(deck);
        }
    }

    /** Declare round winner when a player plays all the cards in hand*/
    private void winRound() {
        Player winner = players.get(currentTurn);
        calculateWinnerScore();
        System.out.println("Winner: " + winner.getName() + " Scored: " + winner.getScore() + " points this round.");
        if(winner.getScore()<500){
            play();
        }else
            gameActive = false;
    }
    /** Return the score of the player by adding up the cards points held by other players.
     * @return score of winner for this round*/
    private int calculateWinnerScore(){
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
    private void executeSpecialFunction(Card card){
        switch (card.rankLight){
            case REVERSE:
                this.reverseTurn();
                break;
            case SKIP:
                this.skipTurn();
                break;
            case WILD:
                break;
            case DRAW1:
                this.nextPlayerDrawCard(1);
                this.skipTurn();
                break;
            case DRAW2:

                this.nextPlayerDrawCard(2);
                this.skipTurn();
                break;
        }
    }
    /** Return the index of the next player
     * @return index of the next player*/
    private int getNextPlayerIndex(){
        int index = currentTurn + turnDirection;
        if (index < 0){index += players.size();}
        else if (index>(players.size()-1)) {index -= players.size();}
        return index;
    }
}