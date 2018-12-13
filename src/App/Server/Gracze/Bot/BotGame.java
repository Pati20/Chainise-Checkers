package App.Server.Gracze.Bot;

import App.Decorators.BotMessageDecorator;
import App.Decorators.MessageDecorator;
import App.Server.StaraPlansza;

import java.util.List;
import java.util.Random;

import static java.lang.Math.abs;


public class BotGame extends Bot {

    private int ID;
    private int numberOfPlayers;
    private int corner;
    private StaraPlansza plansza;
    private List<Pionek> pawn;
    private List<Pionek> win;

    /**
     * Konstruktor klasy BotGame
     * @param nnumber - numer gracza
     * @param numberOfPlayers - liczba graczy
     * @param plansza - aktualna plansza
     */
    public BotGame(int nnumber, int numberOfPlayers, StaraPlansza plansza) {
        super(nnumber, numberOfPlayers, plansza);
        this.numberOfPlayers = numberOfPlayers;
        this.ID = nnumber;
        this.corner = ID + 1;
        this.plansza = plansza;
        bot = true;
    }

    /**
     * Metoda informaująca o wykonanych akcjach ze strony bota
     * @param message
     */
    public  void log(String message){
        MessageDecorator m = new BotMessageDecorator();
        System.out.println(m.log(message)); }

    /**
     * Metoda odpowiedzialna za ustawienie początkowe bota.
     */
    @Override
    public void gameStart() {
        pawn = setMyPawns(plansza);
        win = setMyPawnsWin(plansza);
    }

    /**
     * Metoda odpowiedzialna za prowadzenia aktualnej tury oraz zwracania ruchów ze strony bota.
     * @return ruch
     */
    @Override
    public String Turn() {
        moves = "";
        Pionek currentPawn;
        Random generator = new Random();
        Boolean notCorrectMove = false;

        int i = generator.nextInt(10);
        Pionek target = win.get(i);

        while (!win.get(i).enabled) { //chosing target field
            i = generator.nextInt(10);
            target = win.get(i);
        }

        for (Pionek p : pawn) {
            p.enabled = true;
            for (Pionek w : win)
                if ((p.row == w.row) && (p.column == w.column)) {
                    p.enabled = false;
                    log("Enable false");
                }
        }

        while (!notCorrectMove) {
            log("In while");

            currentPawn = null;

            for (Pionek p : pawn) { //which is closed
                if (currentPawn == null && p.enabled) currentPawn = p;
                if (currentPawn != null && p.enabled && ((abs(target.column - currentPawn.column) + abs(target.row - currentPawn.row)) < (abs(target.column - p.column) + abs(target.row - p.row)))) {
                    currentPawn = p;
                }

            }

            if (currentPawn == null) {
                notCorrectMove = true;
                moves = "ENDTURN PASS";
            } else {//real move
                int moveCol = 0;
                int moveRow = 0;
                if (target.column - currentPawn.column > 0) {
                    moveCol = 1;
                } else {
                    moveCol = -1;
                }
                if (target.row - currentPawn.row > 0) {
                    moveRow = 1;
                } else {
                    moveRow = -1;
                }
                log("Current " + currentPawn.column + " " + currentPawn.row + " move +- " + moveCol + " " + moveRow + "  choset target " + target.column + " " + target.row);
                if (0 == plansza.localboard.serverBoardFields.get(plansza.localboard.serverBoardFields.indexOf(plansza.localboard.findField(currentPawn.column + moveCol, currentPawn.row + moveRow))).pionek
                        && plansza.localboard.testMove(plansza.localboard.findField(currentPawn.column, currentPawn.row), plansza.localboard.findField(currentPawn.column + moveCol, currentPawn.row + moveRow))) {

                    moves = "ENDTURN " + currentPawn.column + " " + currentPawn.row + " " + (currentPawn.column + moveCol) + " " + (currentPawn.row + moveRow);
                    pawn.get(pawn.indexOf(currentPawn)).row = currentPawn.row + moveRow;
                    pawn.get(pawn.indexOf(currentPawn)).column = currentPawn.column + moveCol;
                    break;
                } else {
                   log("False move " + plansza.localboard.testMove(plansza.localboard.findField(currentPawn.column, currentPawn.row), plansza.localboard.findField(currentPawn.column + moveCol, currentPawn.row + moveRow)));
                    pawn.get(pawn.indexOf(currentPawn)).enabled = false;
                }


            }

        }
        log("Moves" + moves);
        return moves;
    }


}

