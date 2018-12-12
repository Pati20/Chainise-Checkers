package App;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

import App.Plansza.KoloryModeli;
import App.Plansza.PlanszaFabryka;
import App.Plansza.Plansza;
import App.Plansza.PlanszaPola;

import static javafx.geometry.Pos.CENTER;

/**
 * Klasa odpowiedzialna za tworzenie rozgrywki
 */
public class InstancjaGry {

    private ArrayList<PlanszaPola> polaPlanszy;
    private PlanszaPola wybranyPionek = null;
    private PlanszaPola ruszonyPionek;
    private Boolean aktywnyNaPlanszy = false;
    List<String> moveRegister = new ArrayList<>();
    public Plansza board;
    int playerID;
    /**
     * Deklaracja Gui elementów
     */
    private GridPane gridpane;
    private VBox vbox;
    private ClientApp clientapp;

    InstancjaGry(ClientApp clientapp, int playerID, int numberOfPlayers) {
        this.clientapp = clientapp;
        this.playerID = playerID + 1;
        polaPlanszy = PlanszaFabryka.stworzLokalnaPlansze(61).stworzPlansze(this, numberOfPlayers);
        generateGUI();
    }

    VBox getVbox() {
        return this.vbox;
    }

    //create fields of board on ArrayList
    void unlockGame() {
        aktywnyNaPlanszy = true;
        wybranyPionek = null;
        ruszonyPionek = null;
        clientapp.stopWaiting();
        if (checkWin()) lockGame();
    }

    void lockGame() {
        aktywnyNaPlanszy = false;
        clientapp.startWaiting();
    }

    Boolean getAktywnyNaPlanszy() {
        return aktywnyNaPlanszy;
    }

    Boolean checkWin() {
        for (PlanszaPola field : polaPlanszy) {
            if (field.pionek == playerID && field.winID != playerID) {
                return false;
            }
        }
        clientapp.winScreen();
        return true;
    }

    /**
     * Metoda odpowada za stworzenie planszy do rozgrywki
     */
    private void generateGUI() {

        //create new table in window to put fields
        gridpane = new GridPane();
        gridpane.setAlignment(CENTER);
        for (int i = 0; i < 25; i++) {
            ColumnConstraints column = new ColumnConstraints();
            //full symmetry with MinWidth=17 (multiplier 0.87), but 1-1 aspect ratio looks better
            column.setMinWidth(20);
            column.setPrefWidth(20);
            column.setMaxWidth(30);
            column.setHgrow(Priority.ALWAYS);
            gridpane.getColumnConstraints().add(column);
        }
        for (int i = 0; i < 17; i++) {
            RowConstraints row = new RowConstraints();
            row.setMinHeight(30);
            row.setPrefHeight(30);
            row.setMaxHeight(45);
            row.setVgrow(Priority.ALWAYS);
            gridpane.getRowConstraints().add(row);
        }

        //declare positions of fields in gui table
        for (PlanszaPola i : polaPlanszy) {
            GridPane.setConstraints(i, i.kolumna, i.wiersz);
        }

        //add all fields to gui table
        gridpane.getChildren().addAll(polaPlanszy);

        //set margins of whole table
        gridpane.setPadding(new Insets(30));

        //create button bar
        Button buttonEndTurn = new Button("Zakończ turę");
        buttonEndTurn.setOnMouseClicked(event -> lockGame());
        Button buttonAboutUs = new Button("O programie");
        buttonAboutUs.setOnMouseClicked(event -> showAutors());
        Label colorLabel = new Label("Twój kolor: ");
        Circle circle = new Circle();
        circle.setRadius(10);
        circle.setFill(KoloryModeli.Kolor.Kolory(playerID));
        circle.setStroke(Color.GRAY);
        circle.setStrokeType(StrokeType.INSIDE);
        circle.setStrokeWidth(2);
        //   HBox hBox = new HBox(10);
        //   hBox.getChildren().addAll(buttonEndTurn, new Separator(), buttonAboutUs, new Separator(),colorLabel,circle);
        //   hBox.setAlignment(CENTER);
        ToolBar toolbar = new ToolBar(buttonEndTurn, new Separator(), buttonAboutUs, new Separator(), colorLabel, circle);
        //  ToolBar toolbar = new ToolBar(hBox);
        toolbar.getOrientation();

        vbox = new VBox();
        vbox.setAlignment(CENTER);
        vbox.getChildren().addAll(toolbar, gridpane);
        vbox.setVgrow(gridpane, Priority.ALWAYS);

    }

    //show information about authors
    private void showAutors() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText("Autorzy programu");
        alert.setContentText("Albert Piekielny, Patrycja Paradowska");
        alert.show();
    }



    //move pawn from oldPos to newPos
    public boolean movePawn(PlanszaPola oldPos, PlanszaPola newPos) {
        if (testMove(oldPos, newPos)) {
            newPos.pionek = oldPos.pionek; //podmieniamy pionki
            oldPos.pionek = 0;
            newPos.setFill(KoloryModeli.Kolor.Kolory(newPos.pionek));  //wypełniamy
            oldPos.setFill(KoloryModeli.Kolor.Kolory(oldPos.pionek));
            ruszonyPionek = newPos;
            return true;
        }
        return false;
    }

    //move pawn by server_do_not_use //by Akageneko
    public void movePawnServer(PlanszaPola oldPos, PlanszaPola newPos) {
        newPos.pionek = oldPos.pionek;
        oldPos.pionek = 0;
        newPos.setFill(KoloryModeli.Kolor.Kolory(newPos.pionek));
        oldPos.setFill(KoloryModeli.Kolor.Kolory(oldPos.pionek));
    }

    //move pawn from oldPos to newPos
    public boolean moveAndSendPawn(PlanszaPola oldPos, PlanszaPola newPos) {
        if (testMove(oldPos, newPos)) {
            movePawn(oldPos, newPos);
            moveRegister.add(Integer.toString(oldPos.kolumna));
            moveRegister.add(Integer.toString(oldPos.wiersz));
            moveRegister.add(Integer.toString(newPos.kolumna));
            moveRegister.add(Integer.toString(newPos.wiersz));
            return true;
        }
        return false;
    }



    /**
     * Metoda pomicnicza przy sprawdzaniu poprawnościu ruch
     * @param col - kolumna nowego piona
     * @param row - wiersz nowego piona
     * @return - szukany pion
     */
    public PlanszaPola findField(int col, int row) {
        for (PlanszaPola field : polaPlanszy) {
            if (field.kolumna == col && field.wiersz == row) {
                return field;
            }
        }
        return null;
    }

    public void badMove(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Niepoprawny ruch");
        alert.setHeaderText("Informacja");
        alert.setContentText("Nie możesz opuszczać docelowego narożnika \n wchodząc do niego.");
        alert.show();
    }

    /**
     * Metoda odpowiedzialna za prawadzanie popranościu ruchu.
     * @param oldPos - stara pozycja na której jesteśmy
     * @param newPos - nowa pozycja na którą chemy się dostać
     * @return true or false
     */
    public boolean testMove(PlanszaPola oldPos, PlanszaPola newPos) {
        if (newPos.pionek == 0 && oldPos != newPos) {//jeśli wybrany pionek nie jest pionkiem gracza,tylko planszowym
            if (ruszonyPionek == null) {//jeśli to 1 przeskok
                if ((oldPos.pionek == oldPos.winID && newPos.winID == 0))  {badMove(); return false;} // nie można opuszczać zwycięskiego trójkąta
                    if ((abs(oldPos.kolumna - newPos.kolumna) <= 2) && (abs(oldPos.wiersz - newPos.wiersz) <= 1)) {
                        return true;
                    }

            }
            if (ruszonyPionek == null || ruszonyPionek == oldPos) {
                if ((oldPos.pionek == oldPos.winID &&  newPos.winID == 0))  {badMove(); return false;}
                if (oldPos.wiersz == newPos.wiersz) {
                    //prawo
                    if (newPos.kolumna == oldPos.kolumna + 4) {
                        if (findField(oldPos.kolumna + 2, oldPos.wiersz).pionek > 0) {
                            return true;
                        }
                    }
                    //lewo
                    if (newPos.kolumna == oldPos.kolumna - 4) {
                        if (findField(oldPos.kolumna - 2, oldPos.wiersz).pionek > 0) {
                            return true;
                        }
                    }
                }

                if (newPos.wiersz == oldPos.wiersz + 2) {
                    //prawy góra
                    if (newPos.kolumna == oldPos.kolumna + 2) {
                        if (findField(oldPos.kolumna + 1, oldPos.wiersz + 1).pionek > 0) {
                            return true;
                        }
                    }
                    //lewy góra
                    if (newPos.kolumna == oldPos.kolumna - 2) {
                        if (findField(oldPos.kolumna - 1, oldPos.wiersz + 1).pionek > 0) {
                            return true;
                        }
                    }
                }

                if (newPos.wiersz == oldPos.wiersz - 2) {
                    //prawy dół
                    if (newPos.kolumna == oldPos.kolumna + 2) {
                        if (findField(oldPos.kolumna + 1, oldPos.wiersz - 1).pionek > 0) {
                            return true;
                        }
                    }
                    //lewy dół
                    if (newPos.kolumna == oldPos.kolumna - 2) {
                        return findField(oldPos.kolumna - 1, oldPos.wiersz - 1).pionek > 0;
                    }
                }
            }
        }
        return false;
    }
}
