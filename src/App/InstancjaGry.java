package App;

import App.Plansza.KoloryModeli;
import App.Plansza.PlanszaFabryka;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import java.util.ArrayList;
import java.util.List;
import static java.lang.Math.abs;


import App.Plansza.Plansza;
import App.Plansza.PlanszaPola;
import static javafx.geometry.Pos.CENTER;

/**
 * Klasa odpowiedzialna za tworzenie rozgrywki
 */
public class InstancjaGry {

    public ArrayList<PlanszaPola> polaPlanszy;
    public PlanszaPola wybranyPionek = null;
    public PlanszaPola ruszonyPionek;
    public Boolean aktywnyNaPlanszy = false;
    public List<String> moveRegister = new ArrayList<>();
    public Plansza board;
    int playerID;

    /**
     * Deklaracja Gui elementów
     */
    GridPane gridpane;
    VBox vbox;
    ClientApp clientapp;

    public InstancjaGry(ClientApp clientapp, int playerID, int numberOfPlayers) {
        this.clientapp = clientapp;
        this.playerID = playerID + 1;
        polaPlanszy = PlanszaFabryka.stworzLokalnaPlansze(61).stworzPlansze(this, numberOfPlayers);
        generateGUI();
    }

    //create fields of board on ArrayList
    public void unlockGame() {
        aktywnyNaPlanszy = true;
        wybranyPionek = null;
        ruszonyPionek = null;
        clientapp.stopWaiting();
        if (checkWin()) lockGame();
    }

    public void lockGame() {
        aktywnyNaPlanszy = false;
        clientapp.startWaiting();
    }

    public Boolean getAktywnyNaPlanszy() {
        return aktywnyNaPlanszy;
    }

    public Boolean checkWin() {
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
    void generateGUI() {

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
        Button buttonHowToPlay = new Button("Jak grać?");
        buttonHowToPlay.setOnMouseClicked(event -> showHowToPlay());
        Button buttonAboutUs = new Button("O programie");
        buttonAboutUs.setOnMouseClicked(event -> showAboutUs());
        Label colorLabel = new Label("Twój kolor: ");
        Circle circle = new Circle();
        circle.setRadius(10);
        circle.setFill(KoloryModeli.Kolor.KoloryModeli(playerID));
        circle.setStroke(Color.GRAY);
        circle.setStrokeType(StrokeType.INSIDE);
        circle.setStrokeWidth(2);
        ToolBar toolbar = new ToolBar(buttonEndTurn, new Separator(), buttonHowToPlay, buttonAboutUs, new Separator(),colorLabel,circle);


        //this.gotowaGra = true;
        //create vbox with button bar and table (board)
        vbox = new VBox();
        vbox.getChildren().addAll(toolbar, gridpane);
        vbox.setVgrow(gridpane, Priority.ALWAYS);

    }

    //show information about authors
    void showAboutUs() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText("Autorzy programu");
        alert.setContentText("Albert Piekielny, Patrycja Paradowska");
        alert.show();
    }

    //show information about game rules
    void showHowToPlay() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText("Skrócona instrukcja");
        alert.setContentText("Celem gry jest ustawienie wszystkich \n swoich pionów w przeciwległym promieniu.\n " +
                "Podczas tury można poruszyć się jednym pionem \nna sąsiadujące pole lub przeskoczyć dowolną ilość innych pionów.");
        alert.show();
    }

    //select or deselect pawn in gui, draw stroke
    public void selectPawn(PlanszaPola pos) {
        if (wybranyPionek != null) {
            wybranyPionek.setStroke(Color.GRAY);
            wybranyPionek.setStrokeWidth(2);
            moveAndSendPawn(wybranyPionek, pos);
            wybranyPionek = null;
        } else {
            if ((pos.pionek) == playerID) {
                wybranyPionek = pos;
                wybranyPionek.setStroke(Color.ORANGE);
                wybranyPionek.setStrokeWidth(5);
            }
        }
    }

    //move pawn from oldPos to newPos
    public boolean movePawn(PlanszaPola oldPos, PlanszaPola newPos) {
        if (testMove(oldPos, newPos)) {
            newPos.pionek = oldPos.pionek;
            oldPos.pionek = 0;
            newPos.setFill(KoloryModeli.Kolor.KoloryModeli(newPos.pionek));
            oldPos.setFill(KoloryModeli.Kolor.KoloryModeli(oldPos.pionek));
            ruszonyPionek = newPos;
            return true;
        }
        return false;
    }

    //move pawn by server_do_not_use //by Akageneko
    public void movePawnServer(PlanszaPola oldPos, PlanszaPola newPos) {
        newPos.pionek = oldPos.pionek;
        oldPos.pionek = 0;
        newPos.setFill(KoloryModeli.Kolor.KoloryModeli(newPos.pionek));
        oldPos.setFill(KoloryModeli.Kolor.KoloryModeli(oldPos.pionek));
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

    //return field with specified column and wiersz
    public PlanszaPola findField(int col, int row) {
        for (PlanszaPola field : polaPlanszy) {
            if (field.kolumna == col && field.wiersz == row) {
                return field;
            }
        }
        return null;
    }

    //check correctness of move
    public boolean testMove(PlanszaPola oldPos, PlanszaPola newPos) {
        if (newPos.pionek == 0 && oldPos != newPos) {
            if (ruszonyPionek == null) {
                if ((abs(oldPos.kolumna - newPos.kolumna) <= 2) && (abs(oldPos.wiersz - newPos.wiersz) <= 1)) {
                    return true;
                }
            }
            if (ruszonyPionek == null || ruszonyPionek == oldPos) {
                if (oldPos.wiersz == newPos.wiersz) {
                    //right
                    if (newPos.kolumna == oldPos.kolumna + 4) {
                        if (findField(oldPos.kolumna + 2, oldPos.wiersz).pionek > 0) {
                            return true;
                        }
                    }
                    //left
                    if (newPos.kolumna == oldPos.kolumna - 4) {
                        if (findField(oldPos.kolumna - 2, oldPos.wiersz).pionek > 0) {
                            return true;
                        }
                    }
                }

                if (newPos.wiersz == oldPos.wiersz + 2) {
                    //right up
                    if (newPos.kolumna == oldPos.kolumna + 2) {
                        if (findField(oldPos.kolumna + 1, oldPos.wiersz + 1).pionek > 0) {
                            return true;
                        }
                    }
                    //left up
                    if (newPos.kolumna == oldPos.kolumna - 2) {
                        if (findField(oldPos.kolumna - 1, oldPos.wiersz + 1).pionek > 0) {
                            return true;
                        }
                    }
                }

                if (newPos.wiersz == oldPos.wiersz - 2) {
                    //right down
                    if (newPos.kolumna == oldPos.kolumna + 2) {
                        if (findField(oldPos.kolumna + 1, oldPos.wiersz - 1).pionek > 0) {
                            return true;
                        }
                    }
                    //left down
                    if (newPos.kolumna == oldPos.kolumna - 2) {
                        if (findField(oldPos.kolumna - 1, oldPos.wiersz - 1).pionek > 0) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
