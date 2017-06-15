package test;

import engine.GameEngine;
import evolution.NumChromosome;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NumChromosomeTest {
    @Test
    public void TestValidity() {
        GameEngine engine = new GameEngine();
        engine.settingsSetLocNocNot(4, 4, 15);
        NumChromosome testChromosome = new NumChromosome(new int[]{0, 1, 2, 3});
        assertTrue(testChromosome.checkValidity());

        testChromosome = new NumChromosome(new int[]{0, 0, 2, 3});
        assertFalse(testChromosome.checkValidity());

        engine.settingsSetLocNocNot(3, 5, 15);
        NumChromosome testChromosome2 = new NumChromosome(new int[]{1, 1, 4});
        assertFalse(testChromosome2.checkValidity());

        testChromosome2 = new NumChromosome(new int[]{2, 2, 1});
        assertFalse(testChromosome2.checkValidity());

        testChromosome2 = new NumChromosome(new int[]{0, 1, 2});
        assertTrue(testChromosome2.checkValidity());

        boolean throwsException = false;
        try {
            testChromosome2 = new NumChromosome(new int[]{0, 1, 10});
            assertFalse(testChromosome2.checkValidity());
        } catch (ArrayIndexOutOfBoundsException e) {
            throwsException = true;
        }
        assertTrue(throwsException);
    }
}
