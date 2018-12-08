package Server.ServerPlansza;

public class ServerPlanszaPola {
    public int pionek;
    public int col;
    public int row;
    public int winID;


    public ServerPlanszaPola(int id, int winID, int col, int row) {

        pionek = id;
        this.col = col;
        this.row = row;
        this.winID = winID;

    }
}
