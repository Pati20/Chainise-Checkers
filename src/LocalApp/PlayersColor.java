package LocalApp;

import javafx.scene.paint.Color;

public enum PlayersColor {

    COLOR1;


    public Color playerscolor(int i) {
        switch (i) {
            case 0:
                return Color.LIGHTGRAY;
            case 1:
                return Color.RED;
            case 2:
                return Color.GREEN;
            case 3:
                return Color.BLUE;
            case 4:
                return Color.CYAN;
            case 5:
                return Color.MAGENTA;
            case 6:
                return Color.YELLOW;
            case 7:
                return Color.ORANGE;
            default:
                return Color.BLACK;
        }
    }

}
