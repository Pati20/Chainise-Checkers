package App.Decorators;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Abstrakcyjna klasa - dekorator wiadomości
 */
public abstract class MessageDecorator {

     String Message;

    /**
     * Ustawia message na obecną datę.
     */
    public MessageDecorator(){
        this.Message = getDate();
    }

    public String getDate( ){
        DateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd:hh:mm:ss");
        Date currentDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        return dateFormat.format(currentDate);
    }

    /**
     * Metoda do implementacji w klasach pokrewnych.
     * @param message - udekorowana wiadomość
     * @return - String message
     */
    public abstract String log(String message);
}

