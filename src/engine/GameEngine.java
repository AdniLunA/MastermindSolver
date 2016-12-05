package engine;

import evolution.FitnessCalculator;
import evolution.IChromosome;
import evolution.NumChromosome;
import evolution.Submission;

public class GameEngine {
    /*attributes*/
    private static GameEngine gameEngine; /*Singleton Pattern*/

    private NumChromosome codeGenerator;
    private CodeValidator validator;
    private CodeSolver solver;
    private int codeLength;
    private int numColors;
    private int numTries;

    /*functions*/
    public static final GameEngine getInstance(){ /*Singleton Pattern*/
        if (gameEngine == null){
            return new GameEngine();
        } else {
            return gameEngine;
        }
    }

    private GameEngine() {
        gameEngine = this; /*Singleton Pattern*/
    }

    public IChromosome getRandomCode(int codeLength, int numColors){
        System.out.println("GameEngine - getRandomCode");
        codeGenerator = new NumChromosome(codeLength, numColors);
        codeGenerator.generateRandom();
        return codeGenerator;
    }

    public void startGame(int codeLength, int numColors, int numTries, IChromosome code){
        System.out.println("GameEngine - startGame");
        validator = new CodeValidator(code);
        solver = new CodeSolver();
        solver.run();
    }

    public void resolveSubmission(IChromosome chromosome) {
        System.out.println("GameEngine - resolve Submission");
        int[] response = validator.calculateResponse(chromosome);

        FitnessCalculator.getInstance().addSubmission(new Submission(chromosome, response[0], response[1]));
    }

    /*getter + setter*/
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
