package test;

import engine.GameEngine;
import engine.GameSettings;
import evolution.NumChromosome;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

public class ConstellationTest {
    @Test
    public void TestConstellation1_05H10C15T() {
        assertTrue(testConstellation(5, 10, 10000));
    }

    @Test
    public void TestConstellation2_06H12C15T() {
        assertTrue(testConstellation(6, 12, 20000));
    }

    @Test
    public void TestConstellation3_07H14C15T() {
        assertTrue(testConstellation(7, 14, 30000));
    }

    private boolean testConstellation(int loc, int noc, int repEv) {
        GameEngine engine = new GameEngine();

        engine.settingsSetLocNocNot(loc, noc, 15);
        engine.settingsSetPopulationSize(750);
        engine.settingsSetRepeatEvolution(repEv);
        GameSettings.INSTANCE.setLoggingEnabled(false);

        int[] testSequence = new int[loc];

        for (int i = 0; i < loc; i++) {
            testSequence[i] = i;
        }

        System.out.println("Size of population: " + GameSettings.INSTANCE.sizeOfPopulation +
                ", repetition of evolution: " + GameSettings.INSTANCE.repeatEvolutionNTimes +
                " times.");

        long startTime = System.currentTimeMillis();

        boolean codeSolved = engine.runGameAutomated(new NumChromosome(testSequence));
        if (codeSolved) {
            System.out.println("secret code found!");
        }
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        int min = (int) TimeUnit.MILLISECONDS.toMinutes(totalTime);
        int sec = (int) (TimeUnit.MILLISECONDS.toSeconds(totalTime) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(totalTime)));
        System.out.printf("Runtime: %3d:%2d\n\n\n", min, sec);

        return codeSolved;
    }
}
