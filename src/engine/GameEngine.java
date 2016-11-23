package engine;

import evolution.IChromosome;
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

    public IChromosome getRandomCode(int codeLength, int numColors){
        System.out.println("GameEngine - getRandomCode");
        codeGenerator = new NumChromosome(codeLength, numColors);
        codeGenerator.generateRandom();
        return codeGenerator;
    }

    public void startGame(){
        //todo
    }

    //getter + setter
}
