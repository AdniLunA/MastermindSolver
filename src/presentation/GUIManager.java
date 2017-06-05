package presentation;

import config.LoggerGenerator;
import engine.GameEngine;
import engine.helper.SubmissionHandler;
import evolution.IChromosome;
import evolution.NumChromosome;
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
        simulationPg = new SimulationController(this, submissionHandler);
    }

    /*--
     * attributes
     */
    private GameEngine gameEngine;

    private ConfigurationController configPg;
    private SimulationController simulationPg;
    private IChromosome code;

    private Stage primaryStage;

    /*--
     * presentation event tracking/settings
     */
    public boolean TRACK_CODE_SETTING = false;
    public boolean DEFAULT_SHOW_BLACKBOX_CONTENT = true;
    public boolean DEFAULT_RUN_AUTOMATED = false;

    /*--
     * functions
     */
    public void openConfigurationPage(Stage primaryStage) {
//        logger.info("");

        configPg = new ConfigurationController(this);

        this.primaryStage = primaryStage;

        try {
            Pane configPage = (Pane) FXMLLoader.load(getClass().getResource("configurationPage.fxml"));
            Scene scene = new Scene(configPage);
            primaryStage.setTitle("application.InitializeGui Simulation");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            System.out.println("GUIManager - openSimulationPage: errors in \"configurationPage.fxml\" or corresponding controller");
            e.printStackTrace();
        }
    }

    protected void returnToConfigurationPage() {
//        logger.info("");
        openConfigurationPage(primaryStage);
    }

    public void setSelectedSecretCode(int lengthOfCode, int numberOfColors, int numberOfTries, int[] secretCode) {
        gameEngine.settingsSetLocNocNot(lengthOfCode, numberOfColors, numberOfTries);
        this.code = new NumChromosome(secretCode);

        logger.info(" - starting simulation with values LOC: " + lengthOfCode
                + ", NOC: " + numberOfColors + ", NOT: " + numberOfTries + ", secret code: " + code.toString());

        openSimulationPage();
    }

    public void setRandomSecretCode(int lengthOfCode, int numberOfColors, int numberOfTries) {
        gameEngine.settingsSetLocNocNot(lengthOfCode, numberOfColors, numberOfTries);
        this.code = new NumChromosome();

        logger.info(" - starting simulation with values LOC: " + lengthOfCode
                + ", NOC: " + numberOfColors + ", NOT: " + numberOfTries + ", secret code: " + code.toString());

        openSimulationPage();
    }

    private void openSimulationPage() {

//        logger.info("");
        //only accept valid code
        if (!code.checkValidity()) {
            /*todo: GUI error message invalid code*/
        } else {
            try {
                Pane simulationPage = (Pane) FXMLLoader.load(getClass().getResource("simulationPage.fxml"));
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
    public GameEngine getGameEngine() {
        return gameEngine;
    }

    protected int[] getSecretCode() {
        return code.getSequence();
    }

}
