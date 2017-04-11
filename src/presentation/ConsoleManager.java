package presentation;

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

public class ConsoleManager implements  IPresentationManager{
    /*for debugging*/
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    /*--
     * attributes
     */
    private ConfigurationController configPg = new ConfigurationController();
    private GameEngine gameEngine = GameEngine.getInstance();
    private IChromosome code;

    private LinkedBlockingQueue<Submission> submissions = new LinkedBlockingQueue<>();

    private int lengthOfCode;
    private int numberOfColors;
    private int numberOfTries;

    /*--
     * functions
     */
    @Override
    public void startWithRandomCode(int lengthOfCode, int numberOfColors, int numberOfTries) {
        this.lengthOfCode = lengthOfCode;
        this.numberOfColors = numberOfColors;
        this.numberOfTries = numberOfTries;
        this.code = gameEngine.getRandomCode(lengthOfCode, numberOfColors);

        logger.info("startWithRandomCode", " - starting simulation with values LOC: " + lengthOfCode
                + ", NOC: " + numberOfColors + ", NOT: " + numberOfTries + ", secret code: " + code.toString());
        System.out.printf("GUIManager startWithRandomCode - starting simulation with values LOC: " + lengthOfCode
                + ", NOC: " + numberOfColors + ", NOT: " + numberOfTries + ", secret code: \n* " + code.toString()+" *\n");

        FitnessCalculator.getInstance().dropForNextGame();
        gameEngine.startGame(lengthOfCode, numberOfColors, numberOfTries, code);
    }

    @Override
    public void handleSubmission(Submission submission, int position) {
        logger.info("handleSubmission", "");
        try {
            submissions.put(submission);
        } catch (InterruptedException e) {
            System.out.println("    ERROR: Adding of submission "+submission.toString()+" failed.");
            e.printStackTrace();
        }
        logger.info("handleSubmission", "    GUIManager: position = " + position + ", " + submission.toString());
    }

    @Override
    public void handleSubmissionRequest(int requestCounter) {
        logger.info("handleSubmissionRequest", "");
        if (requestCounter < numberOfTries) {
            Submission currentLine = null;
            try {
                currentLine = submissions.take();
            } catch (InterruptedException e) {
                System.out.println("    ERROR: reading of submission #"+requestCounter+" failed.");
                e.printStackTrace();
            }
            logger.info("handleSubmissionRequest", "    GUIManager: position = " + requestCounter + ", " + currentLine.toString());
            int nextCounter = requestCounter + 1;
            if(nextCounter < numberOfTries) {
                logger.info("handleSubmissionRequest", "    calculate submission #" + nextCounter);
                gameEngine.calculateNextSubmission(nextCounter);
            }
            logger.info("handleSubmissionRequest", "    After calculating next submission: ");
            logger.info("handleSubmissionRequest", "    GUIManager: position = " + requestCounter + ", " + currentLine.toString());

            if (currentLine.getRed() == lengthOfCode) {
                logger.info("setNextSubmission", "secret code found!!!");
            }
        }
    }

    /*--
     * getter + setter
     */
    @Override
    public ConfigurationController getConfigPg() {
        return configPg;
    }

    @Override
    public void setConfigPg(ConfigurationController configPg) {
        this.configPg = configPg;
    }

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
