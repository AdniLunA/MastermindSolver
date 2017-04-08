package gui;

import engine.GameEngine;
import evolution.FitnessCalculator;
import evolution.IChromosome;
import evolution.NumChromosome;
import engine.Submission;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

public class GUIManager {
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
        System.out.println("GUIManager - getInstance");
        if (guiManager == null) {
            return new GUIManager();
        } else {
            return guiManager;
        }
    }

    public void openConfigurationPage(Stage primaryStage) {
        System.out.println("GUIManager - openConfigurationPage");

        this.primaryStage = primaryStage;

        try {
            Pane configPage = (Pane) FXMLLoader.load(getClass().getResource("configurationPage.fxml"));
            Scene scene = new Scene(configPage);
            primaryStage.setTitle("Mastermind Simulation");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            System.out.println("GUIManager - openSimulationPage: errors in \"configurationPage.fxml\" or corresponding controller");
            e.printStackTrace();
        }
    }

    protected void returnToConfigurationPage() {
        System.out.println("GUIManager - returnToConfigurationPage");
        openConfigurationPage(primaryStage);
    }

    public void startWithPresetCode(int lengthOfCode, int numberOfColors, int numberOfTries, int[] secretCode) {
        System.out.println("GUIManager - startWithPresetCode");
        this.lengthOfCode = lengthOfCode;
        this.numberOfColors = numberOfColors;
        this.numberOfTries = numberOfTries;
        this.code = new NumChromosome(secretCode, numberOfColors);

        System.out.println("GUIManager - startWithPresetCode - starting simulation with values LOC: " + lengthOfCode
                + ", NOC: " + numberOfColors + ", NOT: " + numberOfTries + ", secret code: " + code.toString());

        openSimulationPage();
    }

    public void startWithRandomCode(int lengthOfCode, int numberOfColors, int numberOfTries) {
        System.out.println("GUIManager - startWithRandomCode");
        this.lengthOfCode = lengthOfCode;
        this.numberOfColors = numberOfColors;
        this.numberOfTries = numberOfTries;
        this.code = gameEngine.getRandomCode(lengthOfCode, numberOfColors);

        System.out.println("GUIManager - startWithRandomCode - starting simulation with values LOC: " + lengthOfCode
                + ", NOC: " + numberOfColors + ", NOT: " + numberOfTries + ", secret code: " + code.toString());

        openSimulationPage();
    }

    private void openSimulationPage() {
        this.submissions = new LinkedBlockingQueue<Submission>();

        System.out.println("GUIManager - openSimulationPage");
        //only accept valid code
        if (!code.checkValidity()) {
            /*todo: error message */
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

    public void handleSubmission(Submission submission, int position) {
        System.out.println("GUIManager - handleSubmission");
        try {
            submissions.put(submission);
        } catch (InterruptedException e) {
            System.out.println("    ERROR: Adding of submission "+submission.toString()+" failed.");
            e.printStackTrace();
        }
        System.out.println("    GUIManager: position = " + position + ", " + submission.toString());
    }

    public void handleSubmissionRequest(int requestCounter) {
        System.out.println("GUIManager - handleSubmissionRequest");
        if (requestCounter < numberOfTries) {
            Submission currentLine = null;
            try {
                currentLine = submissions.take();
            } catch (InterruptedException e) {
                System.out.println("    ERROR: reading of submission #"+requestCounter+" failed.");
                e.printStackTrace();
            }
            System.out.println("    GUIManager: position = " + requestCounter + ", " + currentLine.toString());
            int nextCounter = requestCounter + 1;
            if(nextCounter < numberOfTries) {
                System.out.println("    calculate submission #" + nextCounter);
                gameEngine.calculateNextSubmission(nextCounter);
            }
            System.out.println("    After calculating next submission: ");
            System.out.println("    GUIManager: position = " + requestCounter + ", " + currentLine.toString());

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
