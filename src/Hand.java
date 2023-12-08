import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/** This class represents the cards in an UNO player's hands.
 * @author Areej Mahmoud 101218260
 */

public class Hand{

    //list of cards in the hand
    private ArrayList<Card> cards;

    /**Constructor for Class Deck*/
    public Hand() {
        this.cards = new ArrayList<Card>();
    }

    /**Adds a new card to the Hand
     * @param card the card to be added */
    public void addCard(Card card){
        cards.add(card);
    }
    /**remove a card with index cardIndex from the hand
     * @param cardIndex index of the card to be removed*/
    public void removeCard(int cardIndex){
        cards.remove(cardIndex);
    }
    /** Remove all cards from the hand*/
    public void removeAll(){
        cards.clear();
    }
    /** Calculate the sum of points for all cards currently held.
     * @return sum of total points of the cards held */
    public int calculateTotalPoints(boolean currentSideLight){
        int points = 0;
        for(Card c: cards){
            points += c.getPoints(currentSideLight);
        }
        return points;
    }
    /** @return an ArrayList of cards in the Hand */
    public Card getRecentDraw() {
        return this.cards.get(cards.size()-1);
    }
    /** @return an ArrayList of cards in the Hand */
    public ArrayList<Card> getCards() {
        return this.cards;
    }
    /** @return true if only one card is left in the hand.*/
    public boolean isUNO(){
        return cards.size() == 1;
    }

    /** @return true if there are no cards in the hand.*/
    public boolean isEmpty(){
        return cards.isEmpty();
    }
    /** @return a String representation of Hand */
    public String toString(){
        StringBuilder s = new StringBuilder();
        for(int i=0; i< cards.size(); i++){
            s.append(i+1);
            s.append(" "+ cards.get(i).toString() + " \n");
        }
        return s.toString();
    }


    /** Return a JSON object containing the attributes in this class
     * @return JsonObject of the class attributes*/
    public JsonObject saveAttributesToJson(){
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

        for (int i = 0; i < this.cards.size() ; i++){
            JsonObject jsonObject = this.cards.get(i).saveAttributesToJson();
            jsonArrayBuilder.add(i,jsonObject);
        }
        // Build the JSON array
        JsonArray jsonArrayList = jsonArrayBuilder.build();

        JsonObject jsonObject = Json.createObjectBuilder()
                .add("cards", jsonArrayList)
                .build();

        return jsonObject;
    }

    public void saveJsonObjectsToFile(String fileName) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            JsonObject jsonObject = saveAttributesToJson();
            writer.println(jsonObject);
        }
    }


}