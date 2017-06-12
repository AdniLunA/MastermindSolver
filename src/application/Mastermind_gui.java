package application;

import engine.GameEngine;
import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class Mastermind_gui extends Application {
    /*--functions*/
    @Override
    public void start(Stage primaryStage) throws IOException {
        new GameEngine(primaryStage);
    }

    /*MAIN*/
    public static void main(String... args) {
        try {
        /*start application in gui*/
            launch(args);
        } catch (Exception e) {
            LogManager.getLogger(Mastermind_gui.class).error("Exception : \n                        " + e);
            e.printStackTrace();
        }

    }
}
