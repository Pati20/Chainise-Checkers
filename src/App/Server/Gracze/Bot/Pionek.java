package App.Server.Gracze.Bot;

/**
 * Klasa reprezentująca pionki ze strony serwera/bota.
 */
public class Pionek {
     int column;
     int row;
    boolean enabled;

    public Pionek(int column, int row){
        this.column = column;
        this.row = row;
        this.enabled = true;
    }

}
