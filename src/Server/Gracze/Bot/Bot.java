package Server.Gracze.Bot;

import java.util.ArrayList;
import java.util.List;


import Server.Gracze.Gracz;
import Server.ServerPlansza.ServerPlanszaPola;
import Server.StaraPlansza;

public class Bot extends Gracz {

    int numberOfPlayers;
    int ID;
    int corner;
    StaraPlansza plansza;

    public Bot(int nnumber, int numberOfPlayers,StaraPlansza plansza) {
        super(nnumber, numberOfPlayers,plansza);
        this.numberOfPlayers = numberOfPlayers;
        this.ID = nnumber;
        this.corner = ID+1;
        this.plansza = plansza;
        bot = true;

    }

    public List<Pionek> setMyPawns(StaraPlansza plansza){
        List<Pionek> pawn = new ArrayList<>();
        for(ServerPlanszaPola field : plansza.localboard.serverBoardFields){
            if(field.pionek == corner) pawn.add(new Pionek(field.col, field.row));
        }

        return pawn;
    }

    public List<Pionek> setMyPawnsWin(StaraPlansza plansza){
        List<Pionek> pawn = new ArrayList<>();
        for( ServerPlanszaPola field : plansza.localboard.serverBoardFields){
            if(field.winID == corner) pawn.add(new Pionek(field.col, field.row));
        }

        return pawn;
    }


}
