package Server.ServerPlansza;

public class ServerPlanszaPola {
    public int pionek;
    public int col;
    public int row;
    public int winID;

    /**
     * Klasa odpowiedzialna za tworzenie startowych pionków po stronie servera
     * @param id - identyfikator narożnika
     * @param winID - identyfikator narożnika docelowego
     * @param col -kolumny
     * @param row - wiersze
     */

    public ServerPlanszaPola(int id, int winID, int col, int row) {

        pionek = id;
        this.col = col;
        this.row = row;
        this.winID = winID;

    }
}
