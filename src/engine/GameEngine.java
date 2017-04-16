package engine;

import config.CrossoverEnum;
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
import java.util.InputMismatchException;

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
        int k = GameSettings.INSTANCE.kForCrossover;
        int kMax = GameSettings.INSTANCE.MAX_LENGTH_OF_CODE - 1;
        if (GameSettings.INSTANCE.crossoverType == CrossoverEnum.K_POINT && k > kMax) {
            throw new InputMismatchException("ERROR: K-Point Crossover selected in \"Configuratoin\" file. K has to be < "
                    + kMax + ". K is set to: " + k);
        }
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

    public void resolveSubmission(IChromosome chromosome, int position) {
        logger.info("");
        int[] response = validator.calculateResponse(chromosome);
        logger.info("    GameEngine: position = " + position + ", red = " + response[0]
                + ", white = " + response[1] + ", sequence = " + chromosome.toString());
        System.out.printf("  " + chromosome.toString() + "  => sequence #" + position + ", red = " + response[0]
                + ", white = " + response[1] + ". @GameEngine - resolveSubmission." + "\n");
        Submission submission = new Submission(chromosome, response[0], response[1]);

        FitnessCalculator.getInstance().addSubmission(submission);

        submissionHandler.handleSubmission(submission, position);
    }

    public void calculateNextSubmission() {
        logger.info("");
        solver.solve();
    }

}
