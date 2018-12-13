package App.Plansza;

import App.InstancjaGry;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;

/**
 * Klasa odpowiedzialna za implementację wyglądu planszy - stworzenie 61 kółek
 */
public class PlanszaPola extends Circle {
    public int pionek;
    public int kolumna;
    public int wiersz;
    public int winID;
    InstancjaGry instancjaGry;
    private PlanszaPola tempRef;

    public PlanszaPola(int id, int winID, int kolumna, int wiersz) {

        this.pionek = id;
        this.instancjaGry = instancjaGry;
        this.kolumna = kolumna;
        this.wiersz = wiersz;
        this.winID = winID;

        tempRef = this;
    }

    /**
     * Konstruktor odpowiedzialny za przechowywanie informacji na temat aktualnego piona, w konkretnej grze
     * @param instancjaGry - aktualna gra
     * @param id - nasz obecny narożnik, nasze położenie
     * @param winID - narożnik do którego zdążamy
     * @param kolumna - kolumna w której znajduje sie pion
     * @param wiersz - wiersz w której znajduje sie pion
     */
    public PlanszaPola(InstancjaGry instancjaGry, int id, int winID, int kolumna, int wiersz) {

        this.instancjaGry = instancjaGry;
        this.pionek = id;
        this.winID = winID;
        this.kolumna = kolumna;
        this.wiersz = wiersz;

        tempRef = this;

        setFill(KoloryModeli.Kolor.Kolory(pionek)); //Kolorowanie pionu
        setStroke(Color.ALICEBLUE);
        setStrokeType(StrokeType.INSIDE);
        setStrokeWidth(2);
        setRadius(15);

        setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
            //    instancjaGry.selectPawn(tempRef);
            }
        });
    }
}
