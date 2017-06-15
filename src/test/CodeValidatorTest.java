package test;

import engine.GameEngine;
import engine.helper.CodeValidator;
import engine.helper.Submission;
import evolution.NumChromosome;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class CodeValidatorTest {
    private GameEngine engine = new GameEngine();

    @Test
    public void validate1() {
        engine.settingsSetLocNocNot(5, 10, 15);
        int[] sampleSequence = {0, 1, 2, 3, 4};
        CodeValidator validator = new CodeValidator(new NumChromosome(sampleSequence));

        int[] testSequence1 = {4, 3, 1, 2, 0};
        Submission testResponse1 = validator.calculateResponse(new NumChromosome(testSequence1));
        /*red 0, white 5*/
        assertTrue((testResponse1.getRed() == 0) && (testResponse1.getWhite() == 5));

        int[] testSequence2 = {0, 3, 5, 4, 1};
        Submission testResponse2 = validator.calculateResponse(new NumChromosome(testSequence2));
        /*red 1, white 3*/
        assertTrue((testResponse2.getRed() == 1) && (testResponse2.getWhite() == 3));

        int[] testSequence3 = {9, 8, 7, 5, 6};
        Submission testResponse3 = validator.calculateResponse(new NumChromosome(testSequence3));
        /*red 0, white 0*/
        assertTrue((testResponse3.getRed() == 0) && (testResponse3.getWhite() == 0));

        int[] testSequence4 = {0, 1, 2, 3, 4};
        Submission testResponse4 = validator.calculateResponse(new NumChromosome(testSequence4));
        /*red 4, white 0*/
        assertTrue((testResponse4.getRed() == 5) && (testResponse4.getWhite() == 0));
    }

    @Test
    public void validate2() {
        engine.settingsSetLocNocNot(2, 4, 15);
        int[] sampleSequence = {0, 1};
        CodeValidator validator = new CodeValidator(new NumChromosome(sampleSequence));

        int[] testSequence1 = {2, 3};
        Submission testResponse1 = validator.calculateResponse(new NumChromosome(testSequence1));
        /*red 0, white 0*/
        assertTrue((testResponse1.getRed() == 0) && (testResponse1.getWhite() == 0));

        int[] testSequence2 = {0, 2};
        Submission testResponse2 = validator.calculateResponse(new NumChromosome(testSequence2));
        /*red 1, white 0*/
        assertTrue((testResponse2.getRed() == 1) && (testResponse2.getWhite() == 0));

        int[] testSequence3 = {0, 1};
        Submission testResponse3 = validator.calculateResponse(new NumChromosome(testSequence3));
        /*red 2, white 0*/
        assertTrue((testResponse3.getRed() == 2) && (testResponse3.getWhite() == 0));

        int[] testSequence4 = {1, 3};
        Submission testResponse4 = validator.calculateResponse(new NumChromosome(testSequence4));
        /*red 1, white 0*/
        assertTrue((testResponse4.getRed() == 0) && (testResponse4.getWhite() == 1));
    }

    @Test
    public void validate3() {
        engine.settingsSetLocNocNot(2, 4, 15);
        int[] sampleSequence = {1, 2};
        CodeValidator validator = new CodeValidator(new NumChromosome(sampleSequence));

        int[] testSequence1 = {0, 2};
        Submission testResponse1 = validator.calculateResponse(new NumChromosome(testSequence1));
        /*red 1, white 0*/
        assertTrue((testResponse1.getRed() == 1) && (testResponse1.getWhite() == 0));
    }
}
