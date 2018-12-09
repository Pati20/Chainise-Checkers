package Server.Gracze;

import javafx.scene.paint.Color;
import javafx.scene.paint.Color;
import Server.StaraPlansza;

public class Gracz {

    public int playerIDOnBoard;
    public int clientID;
    public Color color;
    public Boolean bot;
    public int numberOfPlayers;
    public String moves = "";


    /**
     * Konstruktor dla graczy
     * @param cclientID
     * @param nnumber
     * @param ccolor
     */
    public Gracz(int cclientID, int nnumber, Color ccolor) {
        this.color = ccolor;
        this.playerIDOnBoard = nnumber;
        this.clientID = cclientID;
        System.out.println("HUMAN cclietnID " + clientID);
    }

    /**
     * Konstruktor dla boów
     * @param nnumber
     * @param numberOfPlayers
     * @param staraPlansza
     */
    public Gracz(int nnumber, int numberOfPlayers, StaraPlansza staraPlansza) {
        this.playerIDOnBoard = nnumber;
        this.numberOfPlayers = numberOfPlayers;
    }

    public String Turn() {
        return moves;
    }

    public void gameStart() {}

}
