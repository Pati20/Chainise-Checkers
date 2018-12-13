package App.Decorators;

public class ClientMessageDecorator extends MessageDecorator {
    /**
     * Metoda odpowiedzialna za dekorowanie wiadomości od klienta.
     *  @param message - wiadomość do udekorowania
     * @return (String) - udekorowana wiadomość
     */
    @Override
    public String log(String message) {
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append("[").append(Message).append("]").append("[Client]  ").append(message).toString();
    }
}
