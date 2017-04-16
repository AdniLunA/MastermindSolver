package engine;

import engine.helper.CodeSolver;
import engine.helper.CodeValidator;
import engine.helper.Submission;
import engine.helper.SubmissionHandler;
import evolution.FitnessCalculator;
import evolution.IChromosome;
import evolution.NumChromosome;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import presentation.GUIManager;

import java.io.IOException;

public class GameEngine {
    /*--
     * debugging
     */
    private final Logger logger = LogManager.getLogger(this);

    /*--
     * constructors
     */
    /* Basic settings */
    public GameEngine() {
        submissionHandler = new SubmissionHandler(this);
        GameSettings.INSTANCE.loadDefaultSettings();
    }

    /*Simulate Mastermind via GUI*/
    public GameEngine(Stage primaryStage) throws IOException {
        this();
        logger.info("Mastermind with GUI");
        GUIManager gui = new GUIManager(this, submissionHandler);
        gui.openConfigurationPage(primaryStage);
    }

    /*--
     * attributes
     */
    private NumChromosome codeGenerator;
    private CodeValidator validator;
    private CodeSolver solver;
    private SubmissionHandler submissionHandler;

    /*--
     * functions
     */

    public void settingsSetLocNocNot(int lengthOfCode, int numberOfColors, int numberOfTries){
        GameSettings.INSTANCE.setLengthOfCode(lengthOfCode);
        GameSettings.INSTANCE.setNumberOfColors(numberOfColors);
        GameSettings.INSTANCE.setNumberOfTries(numberOfTries);
    }

    public void startGame(IChromosome code) {
        logger.info("");

        FitnessCalculator.INSTANCE.dropForNextGame();
        validator = new CodeValidator(code);
        /*initialize solver*/
        solver = new CodeSolver(this);
        calculateNextSubmission();
    }

    public void runGame(IChromosome code) {
        startGame(code);

        while (solver.getRequestCounter() < GameSettings.INSTANCE.numberOfTries) {
            try {
                Thread.sleep(GameSettings.INSTANCE.simulationSpeedInMs);
                calculateNextSubmission();
            } catch (InterruptedException e) {
                System.out.println("GameEngine runGame: Thread Exception");
                e.printStackTrace();
            }
            logger.info("");
        }
    }

    public void resolveSubmission(IChromosome chromosome) {
        logger.info("");
        Submission newSubmission = validator.calculateResponse(chromosome);
        System.out.printf("  " + chromosome.toString() + "  => sequence #" + (solver.getRequestCounter() - 1)
                + ", red = " + newSubmission.getRed() + ", white = " + newSubmission.getWhite()
                + ". @GameEngine - resolveSubmission." + "\n");

        FitnessCalculator.INSTANCE.addSubmission(newSubmission);

        submissionHandler.addSubmission(newSubmission);
    }

    public void calculateNextSubmission() {
        logger.info("");
        solver.solve();
    }

}
