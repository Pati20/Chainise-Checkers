package App.Decorator;

import App.Decorators.ClientMessageDecorator;
import App.Decorators.MessageDecorator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ClientMessageDecoratorTest {

    @Mock
    MessageDecorator messageDecorator;

    @Test
    public void TestingCorrectMessageClient(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd:hh:mm:ss");
        Date currentDate = new Date();

        when(messageDecorator.getDate()).thenReturn(dateFormat.format(currentDate));

        //System.out.println(messageDecorator.getDate());
        MessageDecorator mess = new ClientMessageDecorator();

        String dateExpected = mess.getDate();
        assertEquals(dateExpected,dateFormat.format(currentDate)); //napis
        assertNotSame(dateExpected,dateFormat.format(currentDate)); //obiekt

        String testMessage = "Test message";
        String result = mess.log(testMessage);
        StringBuilder s = new StringBuilder();

        String expected = s.append("[").append(messageDecorator.getDate()).append("]").append("[Client]  ").append(testMessage).toString();
        assertEquals(expected,result);

    }
}
