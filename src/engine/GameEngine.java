package engine;

import evolution.FitnessCalculator;
import evolution.IChromosome;
import evolution.NumChromosome;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import presentation.GUIManager;

public class GameEngine {
    /*--
     * debugging
     */
    private final Logger logger = LogManager.getLogger(this);

    /*--
     * attributes
     */
    private static GameEngine gameEngine; /*Singleton Pattern*/

    private NumChromosome codeGenerator;
    private CodeValidator validator;
    private CodeSolver solver;
    private int codeLength;
    private int numColors;
    private int numTries;

    /*--
     * functions
     */
    public static final GameEngine getInstance() { /*Singleton Pattern*/
        if (gameEngine == null) {
            return new GameEngine();
        } else {
            return gameEngine;
        }
    }

    private GameEngine() {
        gameEngine = this; /*Singleton Pattern*/
    }

    public IChromosome getRandomCode(int codeLength, int numColors) {
        logger.info("");
        codeGenerator = new NumChromosome(codeLength, numColors);
        codeGenerator.generateRandom();
        return codeGenerator;
    }

    public void startGame(int codeLength, int numColors, int numTries, IChromosome code) {
        logger.info("");
        this.codeLength = codeLength;
        this.numColors = numColors;
        this.numTries = numTries;

        validator = new CodeValidator(code);
        /*initialize solver*/
        solver = new CodeSolver();
        solver.solve(0);
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

        GUIManager.getInstance().handleSubmission(submission, position);
    }

    public void calculateNextSubmission(int requestCounter) {
        logger.info("");
        solver.solve(requestCounter);
    }

    /*--
     * getter + setter
     */
    public int getNumTries() {
        return numTries;
    }

    public int getNumColors() {
        return numColors;
    }

    public int getCodeLength() {
        return codeLength;
    }
}
