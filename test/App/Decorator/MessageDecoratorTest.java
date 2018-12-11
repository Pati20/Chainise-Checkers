package App.Decorator;

import App.Decorators.MessageDecorator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static junit.framework.TestCase.assertSame;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MessageDecoratorTest {

    @Mock
    MessageDecorator messageDecorator;

    @Mock
    MessageDecorator messageDecorator2;

    @Test
    public void MessageDecoratorTestData(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd:hh:mm:ss");
        Date currentDate = new Date();
        Calendar c = Calendar.getInstance();

        when(messageDecorator.getDate()).thenReturn(dateFormat.format(currentDate));
        assertSame(messageDecorator.getDate(),messageDecorator.getDate());

        verify(messageDecorator,times(2)).getDate();

        String dat1 = messageDecorator.getDate();
        String data2 = messageDecorator2.getDate();


        assertNotSame(dat1,data2);

    }
}
