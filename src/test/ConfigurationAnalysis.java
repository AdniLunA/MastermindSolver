package test;

import config.CrossoverEnum;
import config.MersenneTwisterFast;
import config.MutationEnum;
import config.SelectionEnum;
import de.bean900.logger.Logger;
import engine.GameEngine;
import engine.GameSettings;
import evolution.IChromosome;
import evolution.NumChromosome;

public class ConfigurationAnalysis {
    private static final Logger logTime = Logger.getLogger("ConfigAnalysis");
    private static GameEngine engine = new GameEngine();
    private static IChromosome codeToSolve;

    public static void main(String... args) {
        engine.settingsSetLocNocNot(6, 8, 15);
        codeToSolve = new NumChromosome(new MersenneTwisterFast(System.nanoTime()));

        engine.settingsSetRepeatEvolution(10000);
        engine.settingsSetPopulationSize(700);
        GameSettings.INSTANCE.loggingEnabled=false;
        GameSettings.INSTANCE.simulationSpeedInMs = 0;

        logTime.info("Settings", "LOC: " + GameSettings.INSTANCE.lengthOfCode
                + ", NOC: " + GameSettings.INSTANCE.numberOfColors
                + ", NOT: " + GameSettings.INSTANCE.numberOfTries
                + ", Population Size: " + GameSettings.INSTANCE.sizeOfPopulation
                + ", repeat evolution max: " + GameSettings.INSTANCE.repeatEvolutionNTimes);


        GameSettings.INSTANCE.dynamicEvolution = false;
        GameSettings.INSTANCE.setUniformMixingRatio(0.25f);
        solveWithParams(1, SelectionEnum.TOURNAMENT, CrossoverEnum.UNIFORM, MutationEnum.SCRAMBLE, 0.0001);
        solveWithParams(2, SelectionEnum.ROULETTE_WHEEL, CrossoverEnum.K_POINT, MutationEnum.SCRAMBLE, 0.0005);
        solveWithParams(3, SelectionEnum.TOURNAMENT, CrossoverEnum.UNIFORM, MutationEnum.SCRAMBLE, 0.001);
        solveWithParams(4, SelectionEnum.ROULETTE_WHEEL, CrossoverEnum.K_POINT, MutationEnum.SCRAMBLE, 0.003);
        solveWithParams(5, SelectionEnum.TOURNAMENT, CrossoverEnum.K_POINT, MutationEnum.SCRAMBLE, 0.005);
        solveWithParams(6, SelectionEnum.ROULETTE_WHEEL, CrossoverEnum.UNIFORM, MutationEnum.EXCHANGE, 0.005);
        solveWithParams(7, SelectionEnum.TOURNAMENT, CrossoverEnum.K_POINT, MutationEnum.DISPLACEMENT, 0.005);
        GameSettings.INSTANCE.setUniformMixingRatio(0.35f);
        solveWithParams(8, SelectionEnum.ROULETTE_WHEEL, CrossoverEnum.UNIFORM, MutationEnum.INVERSION, 0.005);
        solveWithParams(9, SelectionEnum.TOURNAMENT, CrossoverEnum.K_POINT, MutationEnum.INSERTION, 0.005);
        solveWithParams(10, SelectionEnum.ROULETTE_WHEEL, CrossoverEnum.ONE_POINT, MutationEnum.SCRAMBLE, 0.005);
        solveWithParams(11, SelectionEnum.TOURNAMENT, CrossoverEnum.TWO_POINT, MutationEnum.SCRAMBLE, 0.005);
        solveWithParams(12, SelectionEnum.ROULETTE_WHEEL, CrossoverEnum.UNIFORM, MutationEnum.SCRAMBLE, 0.005);
        solveWithParams(13, SelectionEnum.TOURNAMENT, CrossoverEnum.UNIFORM, MutationEnum.SCRAMBLE, 0.005);
        solveWithParams(14, SelectionEnum.ROULETTE_WHEEL, CrossoverEnum.K_POINT, MutationEnum.SCRAMBLE, 0.005);

        GameSettings.INSTANCE.dynamicEvolution = true;
        for(int i = 1; i < 21; i++) {
            solveWithParams(i, SelectionEnum.ROULETTE_WHEEL, CrossoverEnum.K_POINT, MutationEnum.SCRAMBLE, 0.005);
        }
    }

    private static void solveWithParams(int nr, SelectionEnum sType, CrossoverEnum cType, MutationEnum mType, double mRatio) {
        engine.settingsSetEvolutionTypes(sType, cType, mType);
        GameSettings.INSTANCE.setMutationRatio(mRatio);

        logTime.info("Configuration #" + nr,
                "Selection: " + GameSettings.INSTANCE.selectionType
                + ", Crossover: " + GameSettings.INSTANCE.crossoverType
                + ", Uniform Ratio: " + GameSettings.INSTANCE.uniformMixingRatio
                + ", Mutation: " + GameSettings.INSTANCE.mutationType
                + ", Mutation Ratio: " + GameSettings.INSTANCE.mutationRatio);

        long startTime = System.currentTimeMillis();
        boolean codeSolved = engine.runGameAutomated(codeToSolve);
        if (codeSolved) {
            System.out.println("secret code found!");
        }
        long endTime = System.currentTimeMillis();
        logTime.info("Configuration #" + nr, "Code solved: " + codeSolved + ", Evolutions: "+engine.getSumEvolutions()+", Runtime: " + (endTime - startTime));
    }
}
