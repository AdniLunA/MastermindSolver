package presentation;

import engine.GameEngine;
import engine.helper.Submission;
import evolution.FitnessCalculator;
import evolution.IChromosome;
import evolution.NumChromosome;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

public class GUIManager implements IPresentationManager {
    /*for debugging*/
    private final Logger logger = LogManager.getLogger(this);

    /*constructor*/
    public GUIManager(GameEngine engine) {
        this.gameEngine = engine;
    }

    /*--
     * attributes
     */
    private GameEngine gameEngine;


    private ConfigurationController configPg = new ConfigurationController(this);
    private SimulationController simulationPg = new SimulationController(this);
    private IChromosome code;

    private LinkedBlockingQueue<Submission> submissions;

    private int lengthOfCode;
    private int numberOfColors;
    private int numberOfTries;

    private Stage primaryStage;

    /*--
     * presentation event tracking/settings
     */
    public boolean TRACK_CODE_SETTING = false;
    public boolean DEFAULT_SHOW_BLACKBOX_CONTENT = true;
    public boolean DEFAULT_RUN_AUTOMATED = false;
    public int DEFAULT_SIMULATION_SPEED_MS = 5000; /*between 100 and 5000*/

    /*--
     * functions
     */
    public void openConfigurationPage(Stage primaryStage) {
        logger.info("");

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
        logger.info("");
        openConfigurationPage(primaryStage);
    }

    public void startWithPresetCode(int lengthOfCode, int numberOfColors, int numberOfTries, int[] secretCode) {
        this.lengthOfCode = lengthOfCode;
        this.numberOfColors = numberOfColors;
        this.numberOfTries = numberOfTries;
        this.code = new NumChromosome(secretCode, numberOfColors);

        logger.info(" - starting simulation with values LOC: " + lengthOfCode
                + ", NOC: " + numberOfColors + ", NOT: " + numberOfTries + ", secret code: " + code.toString());

        openSimulationPage();
    }

    @Override
    public void startWithRandomCode(int lengthOfCode, int numberOfColors, int numberOfTries) {
        this.lengthOfCode = lengthOfCode;
        this.numberOfColors = numberOfColors;
        this.numberOfTries = numberOfTries;
        this.code = gameEngine.getRandomCode(lengthOfCode, numberOfColors);

        logger.info(" - starting simulation with values LOC: " + lengthOfCode
                + ", NOC: " + numberOfColors + ", NOT: " + numberOfTries + ", secret code: " + code.toString());
        System.out.printf("GUIManager startWithRandomCode - starting simulation with values LOC: " + lengthOfCode
                + ", NOC: " + numberOfColors + ", NOT: " + numberOfTries + ", secret code: \n* " + code.toString() + " *\n");

        openSimulationPage();
    }

    private void openSimulationPage() {
        this.submissions = new LinkedBlockingQueue<Submission>();

        logger.info("");
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
            FitnessCalculator.getInstance().dropForNextGame();
            gameEngine.startGame(lengthOfCode, numberOfColors, numberOfTries, code);
        }
    }

    @Override
    public void handleSubmission(Submission submission, int position) {
        logger.info("");
        try {
            submissions.put(submission);
        } catch (InterruptedException e) {
            System.out.println("    ERROR: Adding of submission " + submission.toString() + " failed.");
            e.printStackTrace();
        }
        logger.info("    GUIManager: position = " + position + ", " + submission.toString());
    }

    @Override
    public void handleSubmissionRequest(int requestCounter) {
        logger.info("");
        if (requestCounter < numberOfTries) {
            Submission currentLine = null;
            try {
                currentLine = submissions.take();
            } catch (InterruptedException e) {
                System.out.println("    ERROR: reading of submission #" + requestCounter + " failed.");
                e.printStackTrace();
            }
            logger.info("    GUIManager: position = " + requestCounter + ", " + currentLine.toString());
            int nextCounter = requestCounter + 1;
            if (nextCounter < numberOfTries) {
                logger.info("    calculate submission #" + nextCounter);
                gameEngine.calculateNextSubmission(nextCounter);
            }
            logger.info("    After calculating next submission: ");
            logger.info("    GUIManager: position = " + requestCounter + ", " + currentLine.toString());

            simulationPg.setNextSubmission(currentLine);
        }
    }

    /*--
     * getter + setter
     */
    @Override
    public GameEngine getGameEngine() {
        return gameEngine;
    }

    @Override
    public void setGameEngine(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    protected int getLengthOfCode() {
        return lengthOfCode;
    }

    protected int getNumberOfTries() {
        return numberOfTries;
    }

    protected int[] getSecretCode() {
        return code.getSequence();
    }

}
