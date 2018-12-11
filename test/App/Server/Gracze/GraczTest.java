package App.Server.Gracze;

import App.Server.StaraPlansza;
import javafx.scene.paint.Color;
import org.junit.Assert;
import org.junit.Test;

import static org.mockito.Mockito.mock;


public class GraczTest {

    Gracz gracz = new Gracz(1, 2, Color.RED);

    @Test
    public void testCorrectConstruction()  {
        Assert.assertEquals(Color.RED,gracz.color);
        Assert.assertEquals(1,gracz.clientID);
        Assert.assertEquals(2,gracz.playerIDOnBoard);
    }

    @Test
    public void testGameStart() {
        gracz.moves = "MoveToDiffrentField";
        String expected = gracz.moves;
        Assert.assertEquals(expected,gracz.Turn());
    }
    @Test
    public void testCorrecConstructorForBot(){
        Gracz gracz = new Gracz(2,4,createMockedStaraPlansza());
        Assert.assertEquals(2,gracz.playerIDOnBoard);
        Assert.assertEquals(4,gracz.numberOfPlayers);

    }
    protected StaraPlansza createMockedStaraPlansza() {
        return mock(StaraPlansza.class);
    }

}
