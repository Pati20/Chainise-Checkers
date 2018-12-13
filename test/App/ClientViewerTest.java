package App;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sun.nio.ch.Interruptible;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

public class ClientViewerTest {
    @Mock
    Socket socket;
    @Mock
    PrintWriter out;
    @Mock
    BufferedReader in;
    @Mock
    List<String> comand;
    @Mock
    ClientApp clientapp;
    @Mock
    Thread threadQ;
    @Mock
    Runnable target;
    @Mock
    ThreadGroup group;
    @Mock
    ClassLoader contextClassLoader;
    @Mock
    Object parkBlocker;
    @Mock
    Interruptible blocker;
    @Mock
    Object blockerLock;
    @Mock
    Thread.UncaughtExceptionHandler uncaughtExceptionHandler;
    @Mock
    Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler;
    @InjectMocks
    ClientViewer clientViewer;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testLog()   {
        clientViewer.log("message");
    }

    @Test
    public void testSocketListener() throws Exception {

        clientViewer.SocketListener();
        Assert.assertFalse(clientViewer.getGoodConnection());

    }

    @Test
    public void testConect() throws Exception {
        clientViewer.conect();
    }

    @Test
    public void testBegin() throws Exception {
        clientViewer.begin();
    }

    @Test
    public void testRun() throws Exception {
        clientViewer.run();
    }

    @Test
    public void testGetGames() throws Exception {
        clientViewer.getGames();
    }

    @Test
    public void testConectToGame() throws Exception {
        clientViewer.conectToGame(0, 0);
    }

    @Test
    public void testTerminateServer() throws Exception {
        clientViewer.terminateServer();
    }

    @Test
    public void testGame() throws Exception {
        clientViewer.game();
    }

    @Test
    public void testParserOfCommand() throws Exception {
        List result = clientViewer.parserOfCommand("line");
        Assert.assertEquals(Arrays.asList("String"), result);
    }
}

