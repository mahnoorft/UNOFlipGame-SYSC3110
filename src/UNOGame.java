import java.util.*;
import java.util.Scanner;
/** This class executes the UNO Game functions until a winner is announced*/

public class UNOGame{
    private ArrayList<Player> players;
    private Deck deck;
    private ArrayList<Card> pile;
    private int currentTurn;
    private int turnDirection;
    private Card topCard;
    private boolean currentSideLight;
    private Scanner input;

    public UNOGame(){
        this.players = new ArrayList<Player>();
        this.deck = new Deck();
        this.pile = new ArrayList<Card>();
        this.currentTurn = 0;
        this.turnDirection = 1;
        this.topCard = null;
        this.currentSideLight = true;
        this.input = new Scanner(System.in);

    }
    public void play(){
        initializeGame();
        while(true){
            takeTurn();
            currentTurn++;
            if(currentTurn>=players.size()){
                currentTurn = 0;
            }
        }

    }
    private void initializeGame(){
        System.out.println("Welcome to UNO!");
        //Get the number of players
        System.out.print("Enter the number of players (2-4): ");
        int numPlayers = input.nextInt();
        input.nextLine();

        // initialize all player names and distribute 7 cards to each
        for(int i = 1; i <= numPlayers; i++) {
            System.out.print("Enter name of player " + i + ": ");
            Player player = new Player(input.nextLine());
            for(int j=0; j<7; j++){
                player.drawCard(this.deck);
            }
            this.players.add(player);
        }
        //draw first card from deck to start the game
        //Assume it is okay for starting card to be Wild
        topCard = deck.draw();
        this.pile.add(topCard);

    }
    private void takeTurn() {
        boolean isDigit; //verify user input
        Card isValid = null; //verify valid card input
        int value = 0; //inital value

        Player player = players.get(currentTurn);

        //Initial display
        System.out.println(player.getName() + "'s Turn: ");
        System.out.println("Current side: " + (currentSideLight ? "Light" : "Dark"));
        System.out.println("Your cards: " + player.getHand());
        System.out.println("Top Card: " + topCard);

        System.out.println("Enter card index to play or 0 to draw a card: ");
        String userInput = input.nextLine();

        //repeat until a valid card has been inputted
        while (isValid != null) {

            //Verify User inputs a digit
            try {
                value = Integer.parseInt(userInput);
            } catch (NumberFormatException e) {
                // This is thrown when the String
                // contains characters other than digits
                System.out.println("Input isn't a numerical value");
                System.out.println("Enter card index to play or 0 to draw a card: ");
                isValid = null;
                break;
            }
            isValid = player.playCard(value, topCard);
            if (isValid != null) {
                System.out.println("Enter card index to play or 0 to draw a card: ");
            }
            userInput = input.nextLine();
        }

        //update top card with the card last played
        topCard = isValid;

        //Announce winner if no cards remaining
        if (player.getHand().isEmpty()) {
            winRound();
        }

        //check if card's special, if it is executeSpecialFunction
        if (topCard.isSpecial()) {
            executeSpecialFunction(topCard);
        } else {
            System.out.println(player.getName() + "'s Turn Finished");
        }

        //call UNO if on last card
        if (player.getHand().getCards().size() == 1){
            System.out.println(player.getName() + " calls UNO");
        }
    }

    private void skipTurn(){
        currentTurn += turnDirection;
        if(currentTurn <0){
            currentTurn += players.size();
        }else if(currentTurn > (players.size()-1)){
            currentTurn -=players.size();
        }
    }
    private void reverseTurn(){turnDirection *= -1;}
    private void chooseNewColor(){
        while(topCard.getColorLight()== Card.Color.WILD){
            System.out.print("Please enter your choice of color: ");
            String i = input.nextLine().toUpperCase();
            switch (i){
                case "RED":
                    topCard.setColorLight(Card.Color.RED);
                    break;
                case "YELLOW":
                    topCard.setColorLight((Card.Color.YELLOW));
                    break;
                case "GREEN":
                    topCard.setColorLight((Card.Color.GREEN));
                    break;
                case "BLUE":
                    topCard.setColorLight((Card.Color.BLUE));
                    break;
                default:
                    System.out.println("Please choose a valid color (red, yellow, green, blue)");
            }
        }
    }
    private void nextPlayerDrawCard(int numCards){
        int target = currentTurn + turnDirection;
        if(target <0){
            target += players.size();
        }else if(target > (players.size() - 1)){
            target -= players.size();
        }
        for(int i=0;i<numCards;i++){
            players.get(target).drawCard(deck);
        }
    }

    // declare round winner when a player plays all the cards in hand
    private void winRound() {
        Player winner = players.get(currentTurn);
        calculateWinnerScore();
        System.out.println("Winner: " + winner.getName() + "Scored: " + winner.getScore() + "points this round.");
    }
    // calculate the score of the player by adding up the cards points held by other players.
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
    private void executeSpecialFunction(Card card){
        switch (card.rankLight){
            case REVERSE:
                this.reverseTurn();;
            case SKIP:
                this.skipTurn();
            case WILD:
                this.chooseNewColor();
            case DRAW1:
                this.nextPlayerDrawCard(1);
                this.skipTurn();
            case WILD_DRAW2:
                this.chooseNewColor();
                this.nextPlayerDrawCard(2);
                this.skipTurn();
        }
    }
    public static void main(String[] args) {
        UNOGame unoGame = new UNOGame();
        unoGame.play();
    }
}