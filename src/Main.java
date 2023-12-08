import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        UNOGameModel game = new UNOGameModel();
        UNOGameFrame unoGameFrame = new UNOGameFrame(game);
        //game.restoreJsonObjectsFromFile("saveUNO.json");
    }
}