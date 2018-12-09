package Tests.Server.Gracze;

import Server.Gracze.Człowiek;
import Server.Gracze.Gracz;
import javafx.scene.paint.Color;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CzłowiekTest {

    @Test
    public void ConstructorCzłowiekTest(){
        Gracz gracz = new Człowiek(2,3,Color.WHEAT);
        assertEquals(false,gracz.bot);
    }
}
