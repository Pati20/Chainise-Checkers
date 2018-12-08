package Tests;

import App.Plansza.KoloryModeli;
import javafx.scene.paint.Color;
import org.junit.Assert;
import org.junit.Test;

public class KoloryModeliTest {

    @Test
    public void CheckCorrrectColor(){

        Assert.assertEquals(KoloryModeli.Kolor.KoloryModeli(0),Color.LIGHTGRAY);
        Assert.assertEquals(KoloryModeli.Kolor.KoloryModeli(1),Color.RED);
        Assert.assertEquals(KoloryModeli.Kolor.KoloryModeli(2),Color.GREEN);
        Assert.assertEquals(KoloryModeli.Kolor.KoloryModeli(3),Color.BLUE);
        Assert.assertEquals(KoloryModeli.Kolor.KoloryModeli(4),Color.CYAN);
        Assert.assertEquals(KoloryModeli.Kolor.KoloryModeli(5),Color.MAGENTA);
        Assert.assertEquals(KoloryModeli.Kolor.KoloryModeli(6),Color.YELLOW);
        Assert.assertEquals(KoloryModeli.Kolor.KoloryModeli(7),Color.ORANGE);
        Assert.assertEquals(KoloryModeli.Kolor.KoloryModeli(71),Color.BLACK);
        Assert.assertEquals(KoloryModeli.Kolor.KoloryModeli(711),Color.BLACK);
        Assert.assertEquals(KoloryModeli.Kolor.KoloryModeli(9),Color.BLACK);
    }
}
