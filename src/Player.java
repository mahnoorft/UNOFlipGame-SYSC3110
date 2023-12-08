import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.json.Json;
import javax.json.JsonObject;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * THis class represents a player in an UNO Fip Game
 * @author Rama Alkhouli 101198025
 */
public class  Player {
    @JsonProperty("name")
    private String name;
    @JsonProperty("hand")
    protected Hand hand;
    @JsonProperty("score")
    private int score;
    @JsonProperty("isBot")
    private boolean isBot;

    public Player(String name, boolean isBot) {
        this.name = name;
        this.hand = new Hand();
        this.score = 0;
        this.isBot = isBot;

    }

    public Player(){}

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
            if (cardToPlay.checkValid(topCard, isSideLight)) {
                // Print the card that is being played
                System.out.println(name + " played " + cardToPlay.toString2(isSideLight));
                hand.removeCard(index);
                return cardToPlay;
            }
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
        return isBot;
    }

    /** Return a JSON object containing the attributes in this class
     * @return JsonObject of the class attributes*/
    public JsonObject saveAttributesToJson() {
        JsonObject jsonObject = Json.createObjectBuilder()
                .add("name", name)
                .add("hand", hand.saveAttributesToJson())
                .add("score", score)
                .add("isBot", isBot)
                .build();
        return jsonObject;
    }
    public void saveJsonObjectsToFile(String fileName) throws IOException {
        try(PrintWriter writer = new PrintWriter(new FileWriter(fileName))){
            JsonObject jsonObject = saveAttributesToJson();
            writer.println(jsonObject);
        }
    }
}