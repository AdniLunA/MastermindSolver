package engine;

import engine.helper.CodeSolver;
import engine.helper.CodeValidator;
import engine.helper.Submission;
import engine.helper.SubmissionHandler;
import evolution.SicknessCalculator;
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

        SicknessCalculator.INSTANCE.dropForNextGame();
        validator = new CodeValidator(code);
        System.out.printf("starting simulation with values LOC: "
                + GameSettings.INSTANCE.lengthOfCode + ", NOC: " + GameSettings.INSTANCE.numberOfColors + ", NOT: "
                + GameSettings.INSTANCE.numberOfTries + ", secret code: \n* " + code.toString() + " *\n");
        /*initialize solver*/
        solver = new CodeSolver(this);
        calculateNextSubmission();
    }

    public void runGameAutomated(IChromosome code) {
        startGame(code);

        while (solver.getRequestCounter() < GameSettings.INSTANCE.numberOfTries) {
            try {
                Thread.sleep(GameSettings.INSTANCE.simulationSpeedInMs);
                calculateNextSubmission();
            } catch (InterruptedException e) {
                System.out.println("GameEngine runGameAutomated: Thread Exception");
                e.printStackTrace();
            }
            logger.info("");
        }
    }

    public void resolveSubmission(IChromosome chromosome) {
        logger.info("");
        Submission newSubmission = validator.calculateResponse(chromosome);
        this.logger.info("red = " + newSubmission.getRed() + ", white = " + newSubmission.getWhite() + ", code = "
                + chromosome.toString());
        System.out.printf("  " + chromosome.toString() + "  => sequence #%02d, red = %2d, white = %2d, sickness = %4d, generation = %3d. @GameEngine - resolveSubmission.\n"
                , (solver.getRequestCounter() - 1), newSubmission.getRed(), newSubmission.getWhite(), chromosome.getSickness(), chromosome.getGeneration());

        /*if solution is found, break loop by setting currentLine to max*/
        if (newSubmission.getRed() == GameSettings.INSTANCE.lengthOfCode) {
            System.out.println("secret code found!!!");
            solver.setRequestCounter(GameSettings.INSTANCE.numberOfTries);
        }

        SicknessCalculator.INSTANCE.addSubmission(newSubmission);

        submissionHandler.addSubmission(newSubmission);
    }

    public void calculateNextSubmission() {
        logger.info("");
        solver.solve();
    }

}
