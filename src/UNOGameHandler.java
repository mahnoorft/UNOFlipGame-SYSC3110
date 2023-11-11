public interface UNOGameHandler {
    public void handleDrawCard(UNOGameEvent e);
    public void handlePlayCard(UNOGameEvent e);
    public void handleNextTurn(UNOGameEvent e);
    public void handleCallUNO(UNOGameEvent e);

}
