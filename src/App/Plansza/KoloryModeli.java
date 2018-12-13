
package App.Plansza;
import javafx.scene.paint.Color;

/**
 * Typ wyliczeniowy, aby w łatwy sposób móc modyfikować kolory modeli
 */
public enum KoloryModeli {

    Kolor;

    /**
     * Metoda odpowiedzialna za kolorowanie modeli
     * @param i - numer koloru
     * @return color
     */
    public Color Kolory(int i) {
        switch (i) {
            case 0:
                return Color.LIGHTGRAY;
            case 1:
                return Color.RED;
            case 2:
                return Color.GREEN;
            case 3:
                return Color.BLUE;
            case 4:
                return Color.CYAN;
            case 5:
                return Color.MAGENTA;
            case 6:
                return Color.YELLOW;
            case 7:
                return Color.ORANGE;
            default:
                return Color.BLACK;
        }
    }

}
