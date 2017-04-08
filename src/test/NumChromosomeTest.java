package test;

import config.ConfigurationManager;
import evolution.NumChromosome;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NumChromosomeTest {
    @Test
    public void TestValidity(){
        int defaultNumCol = ConfigurationManager.INSTANCE.DEFAULT_NUMBER_OF_COLORS;
        int[] testSequence1 = {0,1,2,3};
        NumChromosome testChromosome = new NumChromosome(testSequence1, defaultNumCol);
        assertTrue(testChromosome.checkValidity());

        int[] testSequence2 = {0,0,2,3};
        testChromosome = new NumChromosome(testSequence2, defaultNumCol);
        assertFalse(testChromosome.checkValidity());

        int[] testSequence3 = {1,1,4};
        testChromosome = new NumChromosome(testSequence3, defaultNumCol);
        assertFalse(testChromosome.checkValidity());

        int[] testSequence4 = {2,2,1};
        testChromosome = new NumChromosome(testSequence4, defaultNumCol);
        assertFalse(testChromosome.checkValidity());
    }
}
