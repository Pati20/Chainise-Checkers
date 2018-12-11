package App.Decorators;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public abstract class MessageDecorator {

     String Message;

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

    public abstract String log(String message);
}

