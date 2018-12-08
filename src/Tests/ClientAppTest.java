package Tests;

import App.ClientApp;

import App.ClientViewer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class ClientAppTest {

    ClientApp app;

    @Before
    public void setEverythink() {
        app = new ClientApp();
        app.setPlayers(5);
        app.setBoot(2);
    }

    @Test
    public void TestsetCheckBoxNotSelected() {
        assertEquals(5, app.getPlayers());
        assertEquals(2, app.getBoot());
    }

    @Test
    public void TestcheckPlayersNumber() {
        assertTrue(app.checkPlayersNumber(2, 2));
        assertTrue(app.checkPlayersNumber(1, 3));
        assertTrue(app.checkPlayersNumber(1, 1));
        assertNotNull(app.checkPlayersNumber(3, 3));
    }

    @Test
    public void TestonExit(){
        ClientViewer clientCommunicator = createMockedClientViewer();
        clientCommunicator.activityOfClient = true;
        assertTrue(app.onExit());
    }

    @Test
    public void TestonExit2(){
        ClientApp cv = createMockedClientApp();
        assertEquals(false,cv.onExit());

        //clientCommunicator = new ClientViewer(this, 0, 0, false,addressField.getText());
    }

    protected ClientViewer createMockedClientViewer() {
        return mock(ClientViewer.class);
    }

    protected ClientApp createMockedClientApp() {
        return mock(ClientApp.class);
    }
}

