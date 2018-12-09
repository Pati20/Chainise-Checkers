package Tests;

import App.ClientApp;
import App.ClientViewer;
import Server.Server;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ClientViewerTest {

    /**
     * Dopisać ten test bo coś mi nie idzie
     */
    @Test
    public void parserOfCommandTestTimes(){

        ClientApp clientApp = mock(ClientApp.class);
        ClientViewer clientViewer = new ClientViewer(clientApp,2,3,true,"localhost");

        doNothing().when(clientViewer).SocketListener();

        clientViewer.parserOfCommand("JEDEN DWA");
    }
}
