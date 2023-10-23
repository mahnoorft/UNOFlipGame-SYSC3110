/**
 * This Class represents a playing card in the UNO Flip game deck
 * @author Eric Cui
 * */
public class Card {
    public enum Rank{ZERO,ONE,TWO,THREE,FOUR,FIVE,SIX,SEVEN,EIGHT,NINE,REVERSE,SKIP,DRAW1,WILD,DRAW2,FLIP}
    public enum Color{RED,YELLOW,GREEN,BLUE,WILD}
    public Rank rankLight;
    public Color colorLight;
    public int points;

    public Card(Rank rankLight,Color colorLight){
        this.rankLight = rankLight;
        this.colorLight = colorLight;
        assignPointsLight();
    }

    /**
     * Assigns the points associated with this card according to UNO Flip rules
     */

    private void assignPointsLight(){
        switch (rankLight){
            case ONE -> points = 1;
            case TWO -> points = 2;
            case THREE -> points = 3;
            case FOUR -> points = 4;
            case FIVE -> points = 5;
            case SIX -> points = 6;
            case SEVEN -> points = 7;
            case EIGHT -> points = 8;
            case NINE -> points = 9;
            case DRAW1 -> points = 10;
            case REVERSE,SKIP,FLIP -> points = 20;
            case WILD -> points = 40;
            case DRAW2 -> points = 50;
        }
    }

    /**
     *  compares this card to another card and returns
     *  true if the card can be played according to UNO rules
     * @return true if this card can be played according to UNO rules
     */
    public boolean checkValid(Card other){
        //if(unoGame.getFlip() == UNOGame.Flip.LIGHT){
        return this.rankLight == other.rankLight || this.colorLight == other.colorLight
                || this.colorLight == Color.WILD || other.colorLight == Color.WILD;
        //}
    }

    /**
     * get the light side color
     * @return color of light side
     */
    public Color getColorLight() {return colorLight;}

    /**
     * set the side color to the given color
     */
    public void setColorLight(Color colorLight) {this.colorLight = colorLight;}

    /**
     * get the rank of this card
     * @return  light rank
     */
    public Rank getRankLight() {return rankLight;}

    /**
     * get the points associated with this card
     * @return points
     */
    public int getPoints() {return points;}

    /**
     * check if card is a special UNO card
     * @return true if this card is a special UNO card
     */
    public boolean isSpecial(){
        return rankLight.compareTo(Rank.NINE) > 0;
    }

    /**
     * return a string representation of this card
     * @return the string
     */
    public String toString(){
        return "["+this.colorLight+" "+ this.rankLight+"]";
    }

    /**
     * checks if obj equals this card
     * @param obj the object being compared
     * @return true if obj equals this card
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true; // If the objects are the same reference, they are equal.
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false; // If the other object is null or not of the same class, they are not equal.
        }

        Card otherCard = (Card) obj; // Cast the object to a Card.

        // Compare the individual attributes to check for equality.
        if (this.getColorLight() == otherCard.getColorLight() && this.getRankLight() == otherCard.getRankLight()){
            return true;
        }
        return false;
    }


}
