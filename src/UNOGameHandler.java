public interface UNOGameHandler {
    public void handleDrawCard(UNOGameEvent e);
    public void handlePlayCard(UNOGameEvent e);
    public void handleWinRound(UNOGameEvent e);
    public void handleNextTurn(UNOGameEvent e);

}
