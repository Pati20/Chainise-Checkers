package App.Server.Gracze;

import javafx.scene.paint.Color;



public class Człowiek extends Gracz {
    private int playerIDOnBoard;
    private int clientID;
    private Color color;

    public Człowiek(int cclientID, int pplayerIDOnBoard, Color ccolor) {
        super(cclientID, pplayerIDOnBoard, ccolor);
        this.color = ccolor;
        this.playerIDOnBoard = pplayerIDOnBoard;
        this.clientID = cclientID;
        System.out.println("HUMAN cclietnID " + clientID);
        bot = false;
    }
}