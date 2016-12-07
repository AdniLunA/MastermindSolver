package test;

import engine.CodeValidator;
import evolution.NumChromosome;
import evolution.Submission;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class SubmissionTest {
    @Test
    public void generateSubmission(){
        int sampleNOC = 4;
        int[] sampleSequence = {0, 1};
        CodeValidator validator = new CodeValidator(new NumChromosome(sampleSequence, sampleNOC));

        int[] testSequence1 = {2, 3};
        int[] testResponse1 = validator.calculateResponse(new NumChromosome(testSequence1, sampleNOC));
        /*red 0, white 0*/
        assertTrue((testResponse1[0] == 0) && (testResponse1[1] == 0));

        Submission submission = new Submission(new NumChromosome(testSequence1, sampleNOC), testResponse1[0], testResponse1[1]);

        assertTrue(submission.getRed() == 0 && submission.getWhite() == 0);

        /*
        int[] testSequence2 = {0, 2};
        int[] testResponse2 = validator.calculateResponse(new NumChromosome(testSequence2, sampleNOC));
        /*red 1, white 0/
        assertTrue((testResponse2[0] == 1) && (testResponse2[1] == 0));

        int[] testSequence3 = {0, 1};
        int[] testResponse3 = validator.calculateResponse(new NumChromosome(testSequence3, sampleNOC));
        /*red 2, white 0/
        assertTrue((testResponse3[0] == 2) && (testResponse3[1] == 0));

        int[] testSequence4 = {1, 3};
        int[] testResponse4 = validator.calculateResponse(new NumChromosome(testSequence4, sampleNOC));
        /*red 0, white 1/
        assertTrue((testResponse4[0] == 0) && (testResponse4[1] == 1));
        */
    }
}
