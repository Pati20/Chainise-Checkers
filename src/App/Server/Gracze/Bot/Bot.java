package App.Server.Gracze.Bot;

import App.Server.Gracze.Gracz;
import App.Server.ServerPlansza.ServerPlanszaPola;
import App.Server.StaraPlansza;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasa reprezentująca poczynania bota
 */
public class Bot extends Gracz {

    int liczbaGraczy;
    int ID;
    int narożnik;
    StaraPlansza plansza;

    /** Konstruktor klasy ustawia wszystkie podana parametry */
    public Bot(int nnumber, int liczbaGraczy, StaraPlansza plansza) {
        super(nnumber, liczbaGraczy,plansza);
        this.liczbaGraczy = liczbaGraczy;
        this.ID = nnumber;
        this.narożnik = ID+1;
        this.plansza = plansza;
        bot = true;

    }

    public List<Pionek> setMyPawns(StaraPlansza plansza){
        List<Pionek> pawn = new ArrayList<>();
        for(ServerPlanszaPola field : plansza.localboard.serverBoardFields){
            if(field.pionek == narożnik) pawn.add(new Pionek(field.kolumna, field.row));
        }

        return pawn;
    }

    public List<Pionek> setMyPawnsWin(StaraPlansza plansza){
        List<Pionek> pawn = new ArrayList<>();
        for( ServerPlanszaPola field : plansza.localboard.serverBoardFields){
            if(field.winID == narożnik) pawn.add(new Pionek(field.kolumna, field.row));
        }

        return pawn;
    }


}
