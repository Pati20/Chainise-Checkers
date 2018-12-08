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
    public int col;
    public int row;
    public int winID;
    InstancjaGry instancjaGry;
    private PlanszaPola tempRef;
    private int buffer;

    public PlanszaPola(int id, int winID, int col, int row) {
        pionek = id;

        this.instancjaGry = instancjaGry;
        this.col = col;
        this.row = row;
        this.winID = winID;

        tempRef = this;
    }

    public PlanszaPola(InstancjaGry instancjaGry, int id, int winID, int col, int row) {
        pionek = id;

        this.instancjaGry = instancjaGry;
        this.col = col;
        this.row = row;
        this.winID = winID;

        tempRef = this;

        setFill(KoloryModeli.Kolor.KoloryModeli(pionek));
        setStroke(Color.ALICEBLUE);
        setStrokeType(StrokeType.INSIDE);
        setStrokeWidth(2);
        setRadius(15);

        setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                instancjaGry.selectPawn(tempRef);
            }
        });
    }
}
