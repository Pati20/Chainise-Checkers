package Server.Gracze.Bot;

import java.util.ArrayList;
import java.util.List;


import Server.Gracze.Gracz;
import Server.ServerPlansza.ServerPlanszaPola;
import Server.StaraPlansza;

public class Bot extends Gracz {

    int liczbaGraczy;
    int ID;
    int narożnik;
    StaraPlansza plansza;

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
