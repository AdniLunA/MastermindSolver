package gui;

import de.bean900.logger.Logger;
import engine.GameEngine;
import engine.Submission;
import evolution.FitnessCalculator;
import evolution.IChromosome;
import evolution.NumChromosome;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

public class GUIManager {
    /*for debugging*/
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    /*constructor*/
    private GUIManager() {
        guiManager = this; /*Singleton Pattern*/
    }

    /*--
     * attributes
     */
    private static GUIManager guiManager; /*Singleton Pattern*/

    private ConfigurationController configPg = new ConfigurationController();
    private SimulationController simulationPg = new SimulationController();
    private GameEngine gameEngine = GameEngine.getInstance();
    private IChromosome code;

    private LinkedBlockingQueue<Submission> submissions;

    private int lengthOfCode;
    private int numberOfColors;
    private int numberOfTries;

    private Stage primaryStage;

    private SimulationController subscriber;

    /*--
     * functions
     */
    public static final GUIManager getInstance() { /*Singleton Pattern*/
        Logger.getLogger("GUIManager").info("getInstance", "");
        if (guiManager == null) {
            return new GUIManager();
        } else {
            return guiManager;
        }
    }

    public void openConfigurationPage(Stage primaryStage) {
        logger.info("openConfigurationPage", "");

        this.primaryStage = primaryStage;

        try {
            Pane configPage = (Pane) FXMLLoader.load(getClass().getResource("configurationPage.fxml"));
            Scene scene = new Scene(configPage);
            primaryStage.setTitle("Mastermind Simulation");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            logger.error("GUIManager - openSimulationPage: errors in \"configurationPage.fxml\" or corresponding controller", e);
            e.printStackTrace();
        }
    }

    protected void returnToConfigurationPage() {
        logger.info("returnToConfigurationPage", "");
        openConfigurationPage(primaryStage);
    }

    public void startWithPresetCode(int lengthOfCode, int numberOfColors, int numberOfTries, int[] secretCode) {
        this.lengthOfCode = lengthOfCode;
        this.numberOfColors = numberOfColors;
        this.numberOfTries = numberOfTries;
        this.code = new NumChromosome(secretCode, numberOfColors);

        logger.info("startWithPresetCode", " - starting simulation with values LOC: " + lengthOfCode
                + ", NOC: " + numberOfColors + ", NOT: " + numberOfTries + ", secret code: " + code.toString());

        openSimulationPage();
    }

    public void startWithRandomCode(int lengthOfCode, int numberOfColors, int numberOfTries) {
        this.lengthOfCode = lengthOfCode;
        this.numberOfColors = numberOfColors;
        this.numberOfTries = numberOfTries;
        this.code = gameEngine.getRandomCode(lengthOfCode, numberOfColors);

        logger.info("startWithRandomCode", " - starting simulation with values LOC: " + lengthOfCode
                + ", NOC: " + numberOfColors + ", NOT: " + numberOfTries + ", secret code: " + code.toString());

        openSimulationPage();
    }

    private void openSimulationPage() {
        this.submissions = new LinkedBlockingQueue<Submission>();

        logger.info("openSimulationPage", "");
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
                logger.error("GUIManager - openSimulationPage: errors in \"simulationPage.fxml\" or the corresponding controller", e);
                e.printStackTrace();
            }
            FitnessCalculator.getInstance().dropForNextGame();
            gameEngine.startGame(lengthOfCode, numberOfColors, numberOfTries, code);
        }
    }

    public void handleSubmission(Submission submission, int position) {
        logger.info("handleSubmission", "");
        try {
            submissions.put(submission);
        } catch (InterruptedException e) {
            logger.error("    ERROR: Adding of submission "+submission.toString()+" failed.", e);
            e.printStackTrace();
        }
        logger.info("handleSubmission", "    GUIManager: position = " + position + ", " + submission.toString());
    }

    public void handleSubmissionRequest(int requestCounter) {
        logger.info("handleSubmissionRequest", "");
        if (requestCounter < numberOfTries) {
            Submission currentLine = null;
            try {
                currentLine = submissions.take();
            } catch (InterruptedException e) {
                logger.error("    ERROR: reading of submission #"+requestCounter+" failed.", e);
                e.printStackTrace();
            }
            logger.info("handleSubmissionRequest", "    GUIManager: position = " + requestCounter + ", " + currentLine.toString());
            int nextCounter = requestCounter + 1;
            if(nextCounter < numberOfTries) {
                System.out.println("    calculate submission #" + nextCounter);
                gameEngine.calculateNextSubmission(nextCounter);
            }
            logger.info("handleSubmissionRequest", "    After calculating next submission: ");
            logger.info("handleSubmissionRequest", "    GUIManager: position = " + requestCounter + ", " + currentLine.toString());

            subscriber.setNextSubmission(currentLine);
        }
    }

    /*--
     * getter + setter
     */
    public ConfigurationController getConfigPg() {
        return configPg;
    }

    public void setConfigPg(ConfigurationController configPg) {
        this.configPg = configPg;
    }

    public SimulationController getSimulationPg() {
        return simulationPg;
    }

    public void setSimulationPg(SimulationController simulationPg) {
        this.simulationPg = simulationPg;
    }

    public GameEngine getGameEngine() {
        return gameEngine;
    }

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

    public void setSubscriber(SimulationController subscriber) {
        this.subscriber = subscriber;
    }
}
