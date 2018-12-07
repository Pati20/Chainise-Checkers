package LocalApp.Board;


public class BoardFactory {

    public static Board createLocalBoard(int boardType) {
        switch (boardType) {
            case 61:
                return new Board61();
        }
        return new Board61();
    }
}
