public class PlayerAI extends Player{

    public PlayerAI(String name, boolean isBot) {
        super(name, isBot);
    }

    public Card getBestPlay(Card topCard, boolean isSideLight){
        for(int i=0;i<this.hand.getCards().size();i++){
            if(this.hand.getCards().get(i).checkValid(topCard,isSideLight)){
                return playCard(i,topCard,isSideLight);

            }
        }
        return null;
    }

    public Card.Color getBestColor(boolean isLightSide){
        for(Card card:this.hand.getCards()){
            Card.Color curr = card.getColor(isLightSide);
            if(curr!= Card.Color.WILD){
                return curr;
            }
        }
        //Default color choice is RED/ORANGE
        if(isLightSide) {
            return Card.Color.RED;
        }else{
            return Card.Color.ORANGE;
        }
    }

}
