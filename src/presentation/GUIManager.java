package presentation;

import config.LoggerGenerator;
import config.MersenneTwisterFast;
import engine.GameEngine;
import engine.helper.SubmissionHandler;
import evolution.IChromosome;
import evolution.NumChromosome;
import evolution.SicknessCalculator;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class GUIManager {
    /*for debugging*/
    private final Logger logger = LoggerGenerator.guiManager;

    /*constructor*/
    public GUIManager(GameEngine engine, SubmissionHandler submissionHandler) {
        this.gameEngine = engine;
        this.submissionHandler = submissionHandler;
        this.generator = new MersenneTwisterFast(System.nanoTime());
    }

    /*--
     * attributes
     */
    private GameEngine gameEngine;
    private MersenneTwisterFast generator;

    private SubmissionHandler submissionHandler;
    private IChromosome code;

    private Stage primaryStage;

    /*--
     * presentation event tracking/settings
     */
    boolean TRACK_CODE_SETTING = false;
    boolean DEFAULT_SHOW_BLACKBOX_CONTENT = true;
    boolean DEFAULT_RUN_AUTOMATED = false;

    /*--
     * functions
     */
    public void openConfigurationPage(Stage primaryStage) {
        ConfigurationController configPg = new ConfigurationController(this);

        this.primaryStage = primaryStage;

        try {
            FXMLLoader configPgLoader = new FXMLLoader(getClass().getResource("configurationPage.fxml"));
            configPgLoader.setController(configPg);
            Pane configPage = configPgLoader.load();

            Scene scene = new Scene(configPage);
            primaryStage.setTitle("application.InitializeGui Simulation");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            System.out.println("GUIManager - openSimulationPage: errors in \"configurationPage.fxml\" or corresponding controller");
            e.printStackTrace();
        }
    }

    void returnToConfigurationPage() {
        openConfigurationPage(primaryStage);
    }

    void setSelectedSecretCode(int lengthOfCode, int numberOfColors, int numberOfTries, int[] secretCode) {
        submissionHandler.dropSubmissions();
        SicknessCalculator.INSTANCE.dropForNextGame();
        gameEngine.settingsSetLocNocNot(lengthOfCode, numberOfColors, numberOfTries);
        this.code = new NumChromosome(secretCode);

        logger.info(" - starting simulation with values LOC: " + lengthOfCode
                + ", NOC: " + numberOfColors + ", NOT: " + numberOfTries + ", secret code: " + code.toString());

        openSimulationPage();
    }

    void setRandomSecretCode(int lengthOfCode, int numberOfColors, int numberOfTries) {
        submissionHandler.dropSubmissions();
        SicknessCalculator.INSTANCE.dropForNextGame();
        gameEngine.settingsSetLocNocNot(lengthOfCode, numberOfColors, numberOfTries);
        this.code = new NumChromosome(generator);

        logger.info(" - starting simulation with values LOC: " + lengthOfCode
                + ", NOC: " + numberOfColors + ", NOT: " + numberOfTries + ", secret code: " + code.toString());

        openSimulationPage();
    }

    private void openSimulationPage() {
        /*only accept valid code*/
        if (code.checkValidity()) {
            try {
                SimulationController simulationPg = new SimulationController(this, submissionHandler);
                FXMLLoader simulationPgLoader = new FXMLLoader(getClass().getResource("simulationPage.fxml"));
                simulationPgLoader.setController(simulationPg);
                Pane simulationPage = (simulationPgLoader.load());

                Scene scene = new Scene(simulationPage);
                primaryStage.setScene(scene);
                primaryStage.show();
            } catch (IOException e) {
                System.out.println("GUIManager - openSimulationPage: errors in \"simulationPage.fxml\" or the corresponding controller");
                e.printStackTrace();
            }
            gameEngine.startGame(code);
        }
    }

    /*--
     * getter + setter
     */
    int[] getSecretCode() {
        return code.getSequence();
    }

}
