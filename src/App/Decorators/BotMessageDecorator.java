package App.Decorators;

public class BotMessageDecorator extends MessageDecorator{

    /**
     * Klasa odpowiedzialna za dekorowanie wiadomości ze strony bota.
     * @param message - wiadomość do udekorowania
     * @return (String) - udekorowana wiadomość
     */
    @Override
    public String log(String message) {
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append("[").append(Message).append("]").append("[Server BOT]  ").append(message).toString();
    }
}
