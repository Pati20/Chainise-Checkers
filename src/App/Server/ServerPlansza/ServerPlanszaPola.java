package App.Server.ServerPlansza;

public class ServerPlanszaPola {
    public int pionek;
    public int kolumna;
    public int row;
    public int winID;

    /**
     * Klasa odpowiedzialna za tworzenie startowych pionków po stronie servera
     * @param id - identyfikator narożnika
     * @param winID - identyfikator narożnika docelowego
     * @param kolumna -kolumny
     * @param row - wiersze
     */

    public ServerPlanszaPola(int id, int winID, int kolumna, int row) {

        pionek = id;
        this.kolumna = kolumna;
        this.row = row;
        this.winID = winID;

    }
}
