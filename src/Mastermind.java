import config.Configuration;
import config.CrossoverEnum;
import config.MersenneTwisterFast;
import engine.GameEngine;
import gui.GUIManager;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.InputMismatchException;

public class Mastermind extends Application{
    //attributes

    //functions

    @Override
    public void start (Stage primaryStage) throws IOException {
        System.out.println("Mastermind - start");

        GameEngine gameEngine = GameEngine.getInstance();
        GUIManager gui = GUIManager.getInstance();
        gui.openConfigurationPage(primaryStage);

        /*
            Pane testPage = (Pane) FXMLLoader.load(getClass().getResource("gui/xtest.fxml"));
            Scene scene = new Scene(testPage);
            primaryStage.setTitle("Page 1");
            primaryStage.setScene(scene);
            primaryStage.show();
        */
    }


    //MAIN
    public static void main (String ... args) throws IOException {
        System.out.println("Mastermind - main");

        int k = Configuration.INSTANCE.K_FOR_CROSS_OVER;
        if(Configuration.INSTANCE.CROSSOVER_TYPE == CrossoverEnum.K_POINT && k > Configuration.INSTANCE.MAX_LENGTH_OF_CODE - 1){
            throw new InputMismatchException("ERROR: K-Point Crossover selected. K has to be < 20. K is set to: "+k);
        }

        launch(args);
    }
}


