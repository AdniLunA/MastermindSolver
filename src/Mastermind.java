import engine.GameEngine;
import gui.GUIManager;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Mastermind extends Application{
    //attributes

    //functions


    @Override

    public void start (Stage primaryStage) throws IOException {
        System.out.println("Mastermind - start");

        GameEngine gameEngine = new GameEngine();
        GUIManager gui = new GUIManager(gameEngine);
        gui.setStage(primaryStage);

        /*
            Pane testPage = (Pane) FXMLLoader.load(getClass().getResource("gui/test.fxml"));
            Scene scene = new Scene(testPage);
            primaryStage.setTitle("Page 1");
            primaryStage.setScene(scene);
            primaryStage.show();
        */
    }


    //MAIN
    public static void main (String ... args) throws IOException {
        System.out.println("Mastermind - main");

        launch(args);
    }
}


