package engine;

import evolution.NumChromosome;

public class GameEngine {
    //attributes
    private static GameEngine gameEngine; //Singleton Pattern

    private NumChromosome codeGenerator;
    private CodeValidator validator;
    private CodeSolver solver;

    //functions
    public static final GameEngine getInstance(){ //Singleton Pattern
        if (gameEngine == null){
            return new GameEngine();
        } else {
            return gameEngine;
        }
    }

    private GameEngine() {
        this.gameEngine = this; //Singleton Pattern
    }

    public int[] getRandomCode(int codeLength, int numColors){
        System.out.println("GameEngine - getRandomCode");
        codeGenerator.generateRandom(codeLength, numColors);
        return codeGenerator.getSequence();
    }

    //getter + setter
}
