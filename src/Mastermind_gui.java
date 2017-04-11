import config.ConfigurationManager;
import config.CrossoverEnum;
import de.bean900.logger.Logger;
import presentation.GUIManager;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.InputMismatchException;

public class Mastermind_gui extends Application {
    /*--
     * debugging
     */
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    /*--functions*/
    @Override
    public void start(Stage primaryStage) throws IOException {
        logger.info("start", "");

        /*GameEngine gameEngine = GameEngine.getInstance();*/
        GUIManager gui = GUIManager.getInstance();
        gui.openConfigurationPage(primaryStage);

        /*
            Pane testPage = (Pane) FXMLLoader.load(getClass().getResource("presentation/xtest.fxml"));
            Scene scene = new Scene(testPage);
            primaryStage.setTitle("Page 1");
            primaryStage.setScene(scene);
            primaryStage.show();
        */
    }

    /*MAIN*/
    public static void main(String... args) throws IOException {
        Logger.getLogger("Mastermind_gui").info("main", "");

        int k = ConfigurationManager.INSTANCE.K_FOR_CROSS_OVER;
        int kMax = ConfigurationManager.INSTANCE.MAX_LENGTH_OF_CODE - 1;
        if (ConfigurationManager.INSTANCE.CROSSOVER_TYPE == CrossoverEnum.K_POINT && k > kMax) {
            throw new InputMismatchException("ERROR: K-Point Crossover selected in \"Configuratoin\" file. K has to be < "
                    + kMax + ". K is set to: " + k);
        }

        /*start application in console*/
        launch(args);
    }
}


