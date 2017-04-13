package presentation;

import engine.GameEngine;
import engine.helper.Submission;
import evolution.FitnessCalculator;
import evolution.IChromosome;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.LinkedBlockingQueue;

public class ConsoleManager implements IPresentationManager {
    /*for debugging*/
    private final Logger logger = LogManager.getLogger(this);

    /*--
     * attributes
     */
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

        logger.info(" - starting simulation with values LOC: " + lengthOfCode
                + ", NOC: " + numberOfColors + ", NOT: " + numberOfTries + ", secret code: " + code.toString());
        System.out.printf("GUIManager startWithRandomCode - starting simulation with values LOC: " + lengthOfCode
                + ", NOC: " + numberOfColors + ", NOT: " + numberOfTries + ", secret code: \n* " + code.toString() + " *\n");

        FitnessCalculator.getInstance().dropForNextGame();
        gameEngine.startGame(lengthOfCode, numberOfColors, numberOfTries, code);
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
                logger.info("handleSubmissionRequest", "    calculate submission #" + nextCounter);
                gameEngine.calculateNextSubmission(nextCounter);
            }
            logger.info("    After calculating next submission: ");
            logger.info("    GUIManager: position = " + requestCounter + ", " + currentLine.toString());

            if (currentLine.getRed() == lengthOfCode) {
                logger.info("secret code found!!!");
            }
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
