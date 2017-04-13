package application;

import engine.GameEngine;
import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import presentation.GUIManager;

import java.io.IOException;

public class InitializeGui extends Application {
    /*--
     * debugging
     */
    private final Logger logger = LogManager.getLogger(this);

    /*--functions*/
    @Override
    public void start(Stage primaryStage) throws IOException {
        logger.info("");

        new GameEngine(primaryStage);
    }
}


