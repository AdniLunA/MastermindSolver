package test;

import config.CrossoverEnum;
import config.MutationEnum;
import config.SelectionEnum;
import de.bean900.logger.Logger;
import engine.GameEngine;
import engine.GameSettings;
import evolution.IChromosome;
import evolution.NumChromosome;

import java.io.IOException;

public class EfficiencyTest {

    private EfficiencyTest() {
        this.NUMBER_OF_TESTS = 50;
        int[] sequence = {0, 1, 2};
        this.TEST_CODE = new NumChromosome(sequence);
        int numColors = (sequence.length * 2 > GameSettings.INSTANCE.MAX_NUMBER_OF_COLORS) ? GameSettings.INSTANCE.MAX_NUMBER_OF_COLORS : sequence.length * 2;
        int[] locNocNot = {sequence.length, numColors, 15};
        this.LOC_NOC_NOT = locNocNot;
        LOG.info("***NEW EfficiencyTest***", "Code to solve: " + TEST_CODE);
    }

    private final Logger LOG = Logger.getLogger("EfficiencyTest");
    private final int NUMBER_OF_TESTS;
    private final IChromosome TEST_CODE;
    private final int[] LOC_NOC_NOT;

    private void countPositives(String description, GameEngine engine) {
        engine.settingsSetLocNocNot(LOC_NOC_NOT[0], LOC_NOC_NOT[1], LOC_NOC_NOT[2]);
        engine.settingsSetSimulationSpeedInMs(0);
        engine.settingsSetAnalysingMode(true);

        int countPositive = 0;
        for (int i = NUMBER_OF_TESTS; i > 0; i--) {
            boolean codeSolved = engine.runGameAutomated(TEST_CODE);
            if (codeSolved) {
                countPositive++;
            }
        }

        LOG.info(description, countPositive + " of " + NUMBER_OF_TESTS + " tests successful (" + countPositive * 100 / NUMBER_OF_TESTS + "%)");
    }

    public static void main(String... args)  {
        /*
        settingsSetEvolutionTypes
        */
        /*select roulette*/
        GameEngine engineRoulette = new GameEngine();
        engineRoulette.settingsSetEvolutionTypes(SelectionEnum.ROULETTE_WHEEL, null, null);
        /*select tournament*/
        GameEngine engineTournament = new GameEngine();
        engineTournament.settingsSetEvolutionTypes(SelectionEnum.TOURNAMENT, null, null);
        /*crossover K*/
        GameEngine engineK = new GameEngine();
        engineK.settingsSetEvolutionTypes(null, CrossoverEnum.K_POINT, null);
        /*crossover One*/
        GameEngine engineOne = new GameEngine();
        engineOne.settingsSetEvolutionTypes(null, CrossoverEnum.ONE_POINT, null);
        /*crossover Two*/
        GameEngine engineTwo = new GameEngine();
        engineTwo.settingsSetEvolutionTypes(null, CrossoverEnum.TWO_POINT, null);
        /*crossover Uni*/
        GameEngine engineUni = new GameEngine();
        engineUni.settingsSetEvolutionTypes(null, CrossoverEnum.UNIFORM, null);
        /*mutation Displacement*/
        GameEngine engineDisplacement = new GameEngine();
        engineDisplacement.settingsSetEvolutionTypes(null, null, MutationEnum.DISPLACEMENT);
        /*mutation Exchange*/
        GameEngine engineExchange = new GameEngine();
        engineExchange.settingsSetEvolutionTypes(null, null, MutationEnum.EXCHANGE);
        /*mutation Insertion*/
        GameEngine engineInsertion = new GameEngine();
        engineInsertion.settingsSetEvolutionTypes(null, null, MutationEnum.INSERTION);
        /*mutation Inversion*/
        GameEngine engineInversion = new GameEngine();
        engineInversion.settingsSetEvolutionTypes(null, null, MutationEnum.INVERSION);
        /*mutation Scramble*/
        GameEngine engineScramble = new GameEngine();
        engineScramble.settingsSetEvolutionTypes(null, null, MutationEnum.SCRAMBLE);

        /*
        test settingsSetPopulationSizePop
        */
        /*25*/
        GameEngine engine25p = new GameEngine();
        engine25p.settingsSetPopulationSizePop(25);
        /*50*/
        GameEngine engine50p = new GameEngine();
        engine50p.settingsSetPopulationSizePop(50);
        /*100*/
        GameEngine engine100p = new GameEngine();
        engine100p.settingsSetPopulationSizePop(100);

        /*
        test settingsSetRepeatEvolution
        */
        /*3*/
        GameEngine engine3r = new GameEngine();
        engine3r.settingsSetRepeatEvolution(3);
        /*10*/
        GameEngine engine10r = new GameEngine();
        engine10r.settingsSetRepeatEvolution(10);
        /*25*/
        GameEngine engine25r = new GameEngine();
        engine25r.settingsSetRepeatEvolution(25);

        /***Begin with tests***/
        EfficiencyTest test = new EfficiencyTest();

        /*test.countPositives("Test SELECT    via Roulette    ", engineRoulette);//best results
        test.countPositives("Test SELECT    via Tournament  ", engineTournament);*/

       /* test.countPositives("Test CROSSOVER via OnePoint    ", engineOne);
        test.countPositives("Test CROSSOVER via TwoPoint    ", engineTwo);
        test.countPositives("Test CROSSOVER via K-Point     ", engineK);
        test.countPositives("Test CROSSOVER via Uniform     ", engineUni);//best results.*/

      /*  test.countPositives("Test MUTATION  via Displacement", engineDisplacement);//100%
        test.countPositives("Test MUTATION  via Exchange    ", engineExchange);
        test.countPositives("Test MUTATION  via Insertion   ", engineInsertion);//96&
        test.countPositives("Test MUTATION  via Inversion   ", engineInversion);
        test.countPositives("Test MUTATION  via Scramble    ", engineScramble);*/

        test.countPositives("Test POPULATION SIZE  with 25  ", engine25p);
        test.countPositives("Test POPULATION SIZE  with 50  ", engine50p);
        test.countPositives("Test POPULATION SIZE  with 100 ", engine100p);
        test.countPositives("Test EVOLUTION REPEAT with 3   ", engine3r);
        test.countPositives("Test EVOLUTION REPEAT with 10  ", engine10r);
        test.countPositives("Test EVOLUTION REPEAT with 25  ", engine25r);
        System.out.println("-----------------------------");
    }
}
