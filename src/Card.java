/**
 * This Class represents a playing card in the UNO Flip game deck
 * @author Eric Cui 101237617
 * */
public class Card {
    public enum Rank{ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, REVERSE, SKIP, DRAW1, DRAW2, SKIP_All, DRAW5, DRAW_COLOR, FLIP, WILD_LIGHT, WILD_DARK}
    public enum Color{RED, YELLOW, GREEN, BLUE, ORANGE, TEAL, PINK, PURPLE, WILD}
    public Rank rankLight;
    public Color colorLight;
    public Rank rankDark;
    public Color colorDark;
    public int points;
    private int pointsDark;

    public Card(Rank rankLight,Color colorLight,Rank rankDark, Color colorDark){
        this.rankLight = rankLight;
        this.colorLight = colorLight;
        this.rankDark = rankDark;
        this.colorDark = colorDark;
        assignPointsLight();
    }

    /**
     * Assigns the points associated with this card according to UNO Flip rules
     */

    private void assignPointsLight(){
        switch (rankLight){
            case ZERO -> points = 0;
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
            case WILD_LIGHT -> points = 40;
            case DRAW2 -> points = 50;
            default -> throw new IllegalArgumentException("CUSTOM EXCEPTION: light card have dark rank");
        }
        switch (rankDark){
            case ZERO -> pointsDark = 0;
            case ONE -> pointsDark = 1;
            case TWO -> pointsDark = 2;
            case THREE -> pointsDark = 3;
            case FOUR -> pointsDark = 4;
            case FIVE -> pointsDark = 5;
            case SIX -> pointsDark = 6;
            case SEVEN -> pointsDark = 7;
            case EIGHT -> pointsDark = 8;
            case NINE -> pointsDark = 9;
            case DRAW5 -> pointsDark = 10;
            case REVERSE,SKIP_All,FLIP -> pointsDark = 20;
            case WILD_DARK -> pointsDark = 40;
            case DRAW_COLOR -> pointsDark = 50;
            default -> throw new IllegalArgumentException("CUSTOM EXCEPTION: dark card have light rank");
        }
    }

    /**
     *  compares this card to another card and returns
     *
     *  @return true if this card can be played according to UNO rules
     */
    public boolean checkValid(Card other,boolean isSideLight){
        if(isSideLight){
        return this.rankLight == other.rankLight || this.colorLight == other.colorLight
                || this.colorLight == Color.WILD || other.colorLight == Color.WILD;
        }else{
            return this.rankDark == other.rankDark || this.colorDark == other.colorDark
                    || this.colorDark == Color.WILD || other.colorDark == Color.WILD;
        }
    }

    /**
     * get the light side color
     * @return color of light side
     */
    //public Color getColorLight() {return colorLight;}
    //public Rank getRankLight() {return rankLight;}
    public Color getColor(boolean sideLight){
        if (sideLight){return colorLight;}
        else{return colorDark;}
    }

    public Rank getRank(boolean currentSideLight){
        if (currentSideLight){return rankLight;}
        else{return rankDark;}
    }

    /**
     * set the side color to the given color
     */
    public void setColorLight(Color colorLight) {this.colorLight = colorLight;}
    public void setColorDark(Color colorDark) {this.colorDark = colorDark;}

    /**
     * get the rank of this card
     * @return  light rank
     */



    /**
     * get the points associated with this card
     * @return points
     */
    public int getPoints(boolean currentSideLight) {
        if(currentSideLight){
            return points;
        }
        return pointsDark;
    }

    /**
     * check if card is a special UNO card
     * @return true if this card is a special UNO card
     */
    public boolean isSpecial(boolean currentSideLight){
        if(currentSideLight){return rankLight.compareTo(Rank.NINE) > 0;}
        else{return rankDark.compareTo(Rank.NINE) > 0;}

    }

    /**
     * return a string representation of this card
     * @return the string
     */
    public String toString2(boolean isSideLight){
        if(isSideLight){
            return this.colorLight+"_"+ this.rankLight;
        }
        return this.colorDark+"_"+ this.rankDark;
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
        if (this.getColor(true) == otherCard.getColor(true) && this.getRank(true) == otherCard.getRank(true)){
            return true;
        }
        return false;
    }

}
