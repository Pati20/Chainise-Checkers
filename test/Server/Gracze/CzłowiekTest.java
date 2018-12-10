package Server.Gracze;

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
