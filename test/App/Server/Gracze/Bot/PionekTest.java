package App.Server.Gracze.Bot;

import org.junit.Assert;
import org.junit.Test;

public class PionekTest {
    Pionek p = new Pionek(5,10);

    @Test
    public void CheckCorrectConstructor(){
        Assert.assertTrue(p instanceof Pionek);
        Assert.assertEquals(5,p.column);
        Assert.assertEquals(10,p.row);
    }
}
