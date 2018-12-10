package App;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static javafx.geometry.Pos.CENTER;

/**
 * Klasa odpowiedzialna za okno łącznia oraz pośrednio za uruchamianie gry
 */
public class ClientApp extends Application {

    InstancjaGry instancjaGry;
    private Stage mainWindow;
    private Scene startScene, gameScene, waitScene, playersWaitScene, winScene;
    private CheckBox[] checkbox1 = new CheckBox[6];
    private CheckBox[] checkbox2 = new CheckBox[6];
    private int players, boot;
    public ClientViewer clientViewer;
    TextField adres;

    /**
     * Metoda odpowiedzlna za sprawdzanie poprawnej liczby graczy
     * @param numberOfHuman - liczba graczy
     * @param numberOfBots - liczba botów
     * @return true/false
     */
    public boolean checkPlayersNumber(Integer numberOfHuman, Integer numberOfBots) {
        int sum = numberOfHuman + numberOfBots;
        if (sum == 2 || sum == 3 || sum == 4 || sum == 6) {
            startGame(numberOfHuman, numberOfBots);
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setResizable(false);
            alert.setTitle("Ostrzeżenie");
            alert.setHeaderText("Nieprawidłowa ilość graczy");
            alert.setContentText("Podane wartości nie są zgodne z zasadami. \n Gra jest przeznaczona dla 2,3,4 lub 6 graczy.");
            alert.show();
            return false;
        }
    }

    /**
     * Metoda opowiedzialna za sprawdzanie jakie opcje wybrał gracz do uruchomienia gry:
     * @param number- liczba botów/liczba graczy
     * @param table - table odnosi sie do checkbox1 i checkbox1
     */
    private void setCheckBoxNotSelected(int number, int table) {
        CheckBox[] temp;
        if (table == 1) {
            temp = checkbox1;
            setPlayers(number);
        } else {
            temp = checkbox2;
            setBoot(number);
            number++;
        }
        for (int i = 0; i < 6; i++) {
            if (i == number - 1) continue;
            temp[i].setSelected(false);

        }
    }

    public void setPlayers(int players) { this.players = players; }

    public void setBoot(int boot) { this.boot = boot; }

    public int getPlayers() { return players; }

    public int getBoot() { return boot; }


    /**
     * Metoda odpowiedzialna za uruchomienie gry
     * @param numberOfHuman - przekazujemy liczbę osób grających
     * @param numberOfBots - oraz liczbę botów
     */
    public void startGame(int numberOfHuman, int numberOfBots) {
        startPlayersWaiting();
        clientViewer = new ClientViewer(this, numberOfHuman, numberOfBots, true, adres.getText());
    }

    /**
     * Metoda odpowiedzialna za dołącznie do istniejącej już gry
     */
    public void joinGame() {
        clientViewer = new ClientViewer(this, 0, 0, false, adres.getText());
    }

    private BackgroundImage setImmage(String path){
        BackgroundImage myBI = null;
        try {
            myBI = new BackgroundImage(new Image(new FileInputStream(path)),
                    BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                    BackgroundSize.DEFAULT);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return myBI;
    }
    /**
     * Motoda która uruchamia Instancję gry
     * @param playerID
     * @param numberOfPlayers
     */
    void startLocalGame(int playerID, int numberOfPlayers) {
        instancjaGry = new InstancjaGry(this, playerID, numberOfPlayers);
        Platform.runLater(new Runnable() {
            /**
             * Wewnętrzna metoda odpowiedzialna za zmianę wyglądu okna podczas działania gry
             */
            public void run() {

                BackgroundImage myBI= setImmage("Fotos/Game_background.png");
                instancjaGry.getVbox().setBackground(new Background(myBI));
                gameScene = new Scene(instancjaGry.getVbox(), 500, 544);
                mainWindow.setScene(waitScene);
                mainWindow.setMinHeight(634);
                mainWindow.setMinWidth(600);
                mainWindow.setMaxHeight(834);
                mainWindow.setMaxWidth(800);
                mainWindow.setResizable(false);
                System.out.println("Rozpoczęto lokalną grę.");
            }
        });
    }

    /**
     * Metoda, uruchamiana w momencie kiedy dany gracz zakączył swój ruch
     */
    public void startPlayersWaiting() {
        Platform.runLater(new Runnable() {
            public void run() {
                mainWindow.setScene(playersWaitScene);
            }
        });
    }

    public void startWaiting() {
        Platform.runLater(new Runnable() {
            public void run() {
                mainWindow.setScene(waitScene);
            }
        });
    }

    public void stopWaiting() {
        Platform.runLater(new Runnable() {
            public void run() {
                mainWindow.setScene(gameScene);
            }
        });
    }

    public void winScreen() {
        Platform.runLater(new Runnable() {
            public void run() {
                mainWindow.setScene(gameScene);
            }
        });
    }

    public boolean onExit() {
        try {
            clientViewer.activityOfClient = false;
            clientViewer.terminateServer();
            clientViewer.interrupt();
        } catch (Exception e) {
            System.out.println("Kliient nie połączył się z serwerem");
            return true;
        }
        Platform.exit();
        return false;
    }

    /**
     * Główna obsługa okienek pojawijących się w grze
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        mainWindow = primaryStage;
        mainWindow.setTitle("Chińskie warcaby ");
        mainWindow.setOnCloseRequest(e -> onExit());

        VBox vbox = new VBox(15);
        vbox.setPadding(new Insets(30));

        Label label1 = new Label("Liczba graczy");
        label1.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD,15));
        label1.setTextFill(Color.web("FFFFFF"));
        Label label2 = new Label("Liczba botów");
        label2.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD ,15));
        label2.setTextFill(Color.web("FFFFFF"));

        for (int i = 0; i < 6; i++) {
            int j = i + 1;
            int k = i;
            checkbox1[i] = new CheckBox("" + (i + 1));
            checkbox1[i].setTextFill(Color.web("FFFFFF"));
            checkbox1[i].setOnMouseClicked(event -> setCheckBoxNotSelected(j, 1));
            checkbox2[i] = new CheckBox("" + (i));
            checkbox2[i].setTextFill(Color.web("FFFFFF"));
            checkbox2[i].setOnMouseClicked(event -> setCheckBoxNotSelected(k, 2));
        }
        HBox hboxOfPlayers = new HBox(8);
        hboxOfPlayers.getChildren().addAll(checkbox1);
        HBox hboxOfBoot = new HBox(8);
        hboxOfBoot.getChildren().addAll(checkbox2);

        Button button1 = new Button("Nowa gra");
        button1.setMinSize(100,25);
        button1.setOnMouseClicked(event -> checkPlayersNumber(getPlayers(), getBoot()));

        Label label3 = new Label("Adres serwera");
        label3.setFont(new Font("Arial",20));
        label3.setTextFill(Color.web("FFFFFF"));
        adres = new TextField();
        adres.setText("localhost");


        Button button2 = new Button("Dołącz do gry");
        button2.setOnMouseClicked(event -> joinGame());

        HBox hBox3 = new HBox(10);
        hBox3.getChildren().addAll(button1,button2);
        hBox3.setAlignment(CENTER);

        vbox.getChildren().addAll(label3, adres, new Separator(), label1, hboxOfPlayers, label2, hboxOfBoot, new Separator(),hBox3);

        Image image = new Image(new FileInputStream("Fotos/Connection_of_players.png"));
        ImageView imageView = new ImageView(image);
        Group root = new Group(imageView);
        playersWaitScene = new Scene(root, 400, 450);

        Image image2 = new Image(new FileInputStream("Fotos/Waitinf_for_players.png"));
        ImageView imageView2 = new ImageView(image2);
        Group root2 = new Group(imageView2);
        waitScene = new Scene(root2, 500, 544);

        Label winLabel = new Label("Wygrałeś! Gratulacje.");
        winLabel.setFont(Font.font("Verdana", 30));
        winLabel.setTextAlignment(TextAlignment.CENTER);
        winLabel.setAlignment(CENTER);
        winScene = new Scene(winLabel, 500, 544);

        BackgroundImage myBI= setImmage("Fotos/first_background.png");
        vbox.setBackground(new Background(myBI));

        startScene = new Scene(vbox, 400, 400);
        mainWindow.setScene(startScene);
        mainWindow.setMinHeight(400);
        mainWindow.setMinWidth(400);
        mainWindow.setMaxHeight(400);
        mainWindow.setMaxWidth(400);
        mainWindow.show();
    }
}
