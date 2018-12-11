package App.Decorators;

public class BotMessageDecorator extends MessageDecorator{

    @Override
    public String log(String message) {
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append("[").append(Message).append("]").append("[App.Server BOT]  ").append(message).toString();
    }
}
