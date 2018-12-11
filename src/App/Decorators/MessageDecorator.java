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


//package App.Decorators;
//
////import App.OutputMessage;
//
//        import App.OutputMessage;
//
//        import java.text.DateFormat;
//        import java.text.SimpleDateFormat;
//        import java.util.Calendar;
//        import java.util.Date;
//
//public abstract class MessageDecorator implements OutputMessage {
//
//    public OutputMessage outputMessage;
//    //    private DateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd:hh:mm:ss");
////    protected String message;
////
//    public MessageDecorator(OutputMessage outputMessage) {
//        this.outputMessage = outputMessage;
//    }
//
//
//    public void setDateFormat() {
//        Date currentDate = new Date();
//        Calendar c = Calendar.getInstance();
//        c.setTime(currentDate);
//        dateFormat.format(currentDate);
//    }
//
//    //@Override
//    public void log(String message) {
//        StringBuilder stringBuilder = new StringBuilder();
//        message = stringBuilder.append("[").append(dateFormat).append("]").append(message).toString();
//    }

//
//}
