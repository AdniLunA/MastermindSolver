package test;

import engine.GameSettings;
import evolution.NumChromosome;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NumChromosomeTest {
    @Test
    public void TestValidity() {
        int defaultNumCol = GameSettings.INSTANCE.numberOfColors;
        int[] testSequence1 = {0, 1, 2, 3};
        NumChromosome testChromosome = new NumChromosome(testSequence1);
        assertTrue(testChromosome.checkValidity());

        int[] testSequence2 = {0, 0, 2, 3};
        testChromosome = new NumChromosome(testSequence2);
        assertFalse(testChromosome.checkValidity());

        int[] testSequence3 = {1, 1, 4};
        testChromosome = new NumChromosome(testSequence3);
        assertFalse(testChromosome.checkValidity());

        int[] testSequence4 = {2, 2, 1};
        testChromosome = new NumChromosome(testSequence4);
        assertFalse(testChromosome.checkValidity());
    }
}
