package LocalApp.Board;

import LocalApp.GameInstance;
import LocalApp.PlayersColor;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;

public class BoardField extends Circle {
    public int pawn;
    public int col;
    public int row;
    public int winID;
    GameInstance localboard;
    private BoardField tempRef;
    private int buffer;

    public BoardField(int id, int winID, int col, int row) {
        pawn = id;

        this.localboard = localboard;
        this.col = col;
        this.row = row;
        this.winID = winID;

        tempRef = this;
    }

    public BoardField(GameInstance localboard, int id, int winID, int col, int row) {
        pawn = id;

        this.localboard = localboard;
        this.col = col;
        this.row = row;
        this.winID = winID;

        tempRef = this;

        setFill(PlayersColor.COLOR1.playerscolor(pawn));
        setStroke(Color.ALICEBLUE);
        setStrokeType(StrokeType.INSIDE);
        setStrokeWidth(2);
        setRadius(15);

        setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                localboard.selectPawn(tempRef);
            }
        });
    }
}
