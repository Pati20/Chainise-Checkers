package server.Player.Bot;

import javafx.scene.paint.Color;
import server.OldBoard;
import server.ServerBoard.ServerBoardField;

import java.util.List;
import java.util.Random;

import static java.lang.Math.abs;

public class NormalBot extends Bot {

    int ID;
    int numberOfPlayers;
    int corner;
    OldBoard board;
    List<Pawn> pawn;
    List<Pawn> win;

    public NormalBot(int nnumber, int numberOfPlayers, OldBoard board){
        super(nnumber,numberOfPlayers,board);
        this.numberOfPlayers = numberOfPlayers;
        this.ID = nnumber;
        this.corner = ID+1;
        this.board = board;
        bot = true;
    }

  /*  @Override
    public List<String> Turn(){
        moves.clear();
        moves.add("ENDTURN");
        moves.add("PASS");


      return moves;
    }
*/

    @Override
    public void gameStart(){
        pawn = setMyPawns(board);
        win = setMyPawnsWin(board);
    }

    @Override
    public String Turn(){
        moves = "";
        Pawn currentPawn;
        Random generator = new Random();
        Boolean notCorrectMove = false;

        int i=generator.nextInt(10);
        Pawn target = win.get(i);

        while(!win.get(i).enabled){ //chosing target field
            i=generator.nextInt(10);
            target = win.get(i);
        }

        for(Pawn p : pawn) {
            p.enabled = true;
            for(Pawn w : win)
                if((p.row == w.row) && (p.column == w.column)){ p.enabled = false;
                    System.out.println("bot enable false");
                }
        }

        while(! notCorrectMove) {
            System.out.println("bot in while");

            currentPawn = null;

            for (Pawn p : pawn) { //which is closed
                if (currentPawn == null && p.enabled) currentPawn = p;
                if (currentPawn != null && p.enabled && ((abs(target.column - currentPawn.column) + abs(target.row - currentPawn.row)) < (abs(target.column - p.column) + abs(target.row - p.row)))) {
                    currentPawn = p;
                }

            }

            if(currentPawn == null){
                notCorrectMove = true;
               moves = "ENDTURN PASS";
            } else {//real move
                int moveCol = 0;
                int moveRow = 0;
                if(target.column - currentPawn.column > 0){
                    moveCol=1;
                }
                else{
                    moveCol=-1;
                }
                if(target.row - currentPawn.row > 0){
                    moveRow=1;
                }
                else{
                    moveRow=-1;
                }
                System.out.println("BOT current " + currentPawn.column + " " + currentPawn.row + " move +- " + moveCol + " " + moveRow + "  choset target " + target.column + " " + target.row );
               if( 0 == board.localboard.serverBoardFields.get(board.localboard.serverBoardFields.indexOf(board.localboard.findField(currentPawn.column + moveCol, currentPawn.row + moveRow))).pawn
                       && board.localboard.testMove( board.localboard.findField(currentPawn.column, currentPawn.row) , board.localboard.findField(currentPawn.column + moveCol, currentPawn.row + moveRow) ) ){

                    moves = "ENDTURN " + Integer.toString(currentPawn.column) + " " + Integer.toString(currentPawn.row) + " " + Integer.toString(currentPawn.column + moveCol) + " " + Integer.toString(currentPawn.row + moveRow);
                    pawn.get(pawn.indexOf(currentPawn)).row = currentPawn.row + moveRow;
                    pawn.get(pawn.indexOf(currentPawn)).column = currentPawn.column + moveCol;
                    break;
               } else{
                   System.out.println("bot false move "+ board.localboard.testMove( board.localboard.findField(currentPawn.column, currentPawn.row) , board.localboard.findField(currentPawn.column + moveCol, currentPawn.row + moveRow)));
                   pawn.get(pawn.indexOf(currentPawn)).enabled=false;
               }


            }

        }



System.out.println("moves bot " + moves);
        return moves;
    }



}
