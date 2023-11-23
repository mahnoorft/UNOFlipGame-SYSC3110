/** This class represents the UNOGame Handler Interface. The class
 * has function declarations for handling UNO Game events where the view is updated.
 * @author Areej Mahmoud 101218260
 * */
public interface UNOGameHandler {
    public void handleDrawCard(UNOGameEvent e);

    public void handlePlayCard(UNOGameEvent e);
    public void handleNextTurn(UNOGameEvent e);
    public void handleCallUNO(UNOGameEvent e);

}
