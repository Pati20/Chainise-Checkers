package App.Plansza;

import javafx.scene.paint.Color;
import org.junit.Assert;
import org.junit.Test;

public class KoloryModeliTest {

    @Test
    public void CheckCorrrectColor(){

        Assert.assertEquals(KoloryModeli.Kolor.Kolory(0),Color.LIGHTGRAY);
        Assert.assertEquals(KoloryModeli.Kolor.Kolory(1),Color.RED);
        Assert.assertEquals(KoloryModeli.Kolor.Kolory(2),Color.GREEN);
        Assert.assertEquals(KoloryModeli.Kolor.Kolory(3),Color.BLUE);
        Assert.assertEquals(KoloryModeli.Kolor.Kolory(4),Color.CYAN);
        Assert.assertEquals(KoloryModeli.Kolor.Kolory(5),Color.MAGENTA);
        Assert.assertEquals(KoloryModeli.Kolor.Kolory(6),Color.YELLOW);
        Assert.assertEquals(KoloryModeli.Kolor.Kolory(7),Color.ORANGE);
        Assert.assertEquals(KoloryModeli.Kolor.Kolory(71),Color.BLACK);
        Assert.assertEquals(KoloryModeli.Kolor.Kolory(711),Color.BLACK);
        Assert.assertEquals(KoloryModeli.Kolor.Kolory(9),Color.BLACK);
    }
}
