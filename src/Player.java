import java.util.*;

/**
 * THis class represents a player in an UNO Fip Game
 * @author Rama Alkhouli 101198025
 */
public class  Player {

    private String name;
    private Hand hand;
    private int score;
    private boolean bot;

    public Player(String name) {
        this.name = name;
        this.hand = new Hand();
        this.score = 0;
        bot = name.contains("bot");
        if(bot)
        System.out.println("added a bot");
    }

    /**
     * draw card from deck and add to hand
     */
    public Card drawCard(Deck deck) {
        Card drawnCard = deck.draw();
        hand.addCard(drawnCard);
        return drawnCard;
    }


    /**
     * update the player's score
     */

    public void incrementScore(int addedScore) {
        this.score += addedScore;
    }

    /**
     *  update the player's score
     * @return the new score
     */
    public int getScore() {
        return score;
    }

    /**
     * get the name of the player
     * @return the player's name
     */
    public String getName() {
        return name;
    }

    /**
     * get the player's hand
     * @return the player's hand
     */
    public Hand getHand() {
        return hand;
    }

    /**
     * play one of the player's cards
     * @return the card that is being played
     */
    public Card playCard(int index, Card topCard, boolean isSideLight) {
        // check if card index is valid
        if (index >= 0 && index < hand.getCards().size()) {
            Card cardToPlay = hand.getCards().get(index);
            if (cardToPlay.checkValid(topCard,isSideLight)) {
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

    /**
     *  remove all cards from the player's hand
     */
    public void clearHand(){
        hand.removeAll();
    }

    public boolean isBot(){
        return bot;
    }
    public int checkBestMove(Card topCard,boolean isSideLight){
        ArrayList<Card> availablePlay = new ArrayList<>();
        for(int i=0;i<hand.getCards().size();i++){
            if(hand.getCards().get(i).checkValid(topCard,isSideLight)){
                return i;
            }
        }
        return -1;
    }

    public Card getBestPlay(Card topCard, boolean isSideLight){
        int bestPlayIndex=0;
        for(int i=0;i<hand.getCards().size();i++){
            if(hand.getCards().get(i).checkValid(topCard,isSideLight)){
                return playCard(i,topCard,isSideLight);

            }
        }
        return null;
    }

    public Card.Color getBestColor(boolean isLightSide){
        for(Card card:hand.getCards()){
            if(card.getColor(isLightSide)!= Card.Color.WILD){
                return hand.getCards().get(0).getColor(isLightSide);
            }
        }
        return Card.Color.RED;
    }

}