package App.Decorators;


public class ServerMessageDecorator extends MessageDecorator {


    @Override
    public String log(String message) {
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append("[").append(Message).append("]").append("[App.Server]  ").append(message).toString();
    }
}
