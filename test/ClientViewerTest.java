

import App.ClientApp;
import App.ClientViewer;
import org.junit.Test;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

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
