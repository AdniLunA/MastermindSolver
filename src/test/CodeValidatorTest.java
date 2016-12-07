package test;

import engine.CodeValidator;
import evolution.IChromosome;
import evolution.NumChromosome;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class CodeValidatorTest {
    @Test
    public void validate1() {
        int sampleNOC = 10;
        int[] sampleSequence = {0, 1, 2, 3, 4};
        CodeValidator validator = new CodeValidator(new NumChromosome(sampleSequence, sampleNOC));

        int[] testSequence1 = {4, 3, 1, 2, 0};
        int[] testResponse1 = validator.calculateResponse(new NumChromosome(testSequence1, sampleNOC));
        /*red 0, white 5*/
        assertTrue((testResponse1[0] == 0) && (testResponse1[1] == 5));

        int[] testSequence2 = {0, 3, 5, 4, 1};
        int[] testResponse2 = validator.calculateResponse(new NumChromosome(testSequence2, sampleNOC));
        /*red 1, white 3*/
        assertTrue((testResponse2[0] == 1) && (testResponse2[1] == 3));

        int[] testSequence3 = {9, 8, 7, 5, 6};
        int[] testResponse3 = validator.calculateResponse(new NumChromosome(testSequence3, sampleNOC));
        /*red 0, white 0*/
        assertTrue((testResponse3[0] == 0) && (testResponse3[1] == 0));

        int[] testSequence4 = {0, 1, 2, 3, 4};
        int[] testResponse4 = validator.calculateResponse(new NumChromosome(testSequence4, sampleNOC));
        /*red 4, white 0*/
        assertTrue((testResponse4[0] == 5) && (testResponse4[1] == 0));
    }

    @Test
    public void validate2(){
        int sampleNOC = 4;
        int[] sampleSequence = {0, 1};
        CodeValidator validator = new CodeValidator(new NumChromosome(sampleSequence, sampleNOC));

        int[] testSequence1 = {2, 3};
        int[] testResponse1 = validator.calculateResponse(new NumChromosome(testSequence1, sampleNOC));
        /*red 0, white 0*/
        assertTrue((testResponse1[0] == 0) && (testResponse1[1] == 0));

        int[] testSequence2 = {0, 2};
        int[] testResponse2 = validator.calculateResponse(new NumChromosome(testSequence2, sampleNOC));
        /*red 1, white 0*/
        assertTrue((testResponse2[0] == 1) && (testResponse2[1] == 0));

        int[] testSequence3 = {0, 1};
        int[] testResponse3 = validator.calculateResponse(new NumChromosome(testSequence3, sampleNOC));
        /*red 2, white 0*/
        assertTrue((testResponse3[0] == 2) && (testResponse3[1] == 0));

        int[] testSequence4 = {1, 3};
        int[] testResponse4 = validator.calculateResponse(new NumChromosome(testSequence4, sampleNOC));
        /*red 1, white 0*/
        assertTrue((testResponse4[0] == 0) && (testResponse4[1] == 1));
    }

    @Test
    public void validate3() {
        int sampleNOC = 4;
        int[] sampleSequence = {1, 2};
        CodeValidator validator = new CodeValidator(new NumChromosome(sampleSequence, sampleNOC));

        int[] testSequence1 = {0, 2};
        int[] testResponse1 = validator.calculateResponse(new NumChromosome(testSequence1, sampleNOC));
        /*red 1, white 0*/
        assertTrue((testResponse1[0] == 1) && (testResponse1[1] == 0));
    }
}
