package engine.helper;

import config.LoggerGenerator;
import engine.GameSettings;
import evolution.IChromosome;
import org.apache.logging.log4j.Logger;

/**
 * calculates red + white response
 * - knows the secret code
 */
public class CodeValidator {

    /*--
     * constructor
     */
    public CodeValidator(IChromosome secretCode) {
        this.secretCode = secretCode;
        if (GameSettings.INSTANCE.loggingEnabled) {
            Logger logger = LoggerGenerator.codeValidator;
            logger.info("- initialized with " + secretCode.toString());
        }
    }

    /*--
     * attributes
     */
    private final IChromosome secretCode;

    /*--
     * functions
     */
    public Submission calculateResponse(IChromosome sequenceToCheck) {
        int redResponse = 0;
        int whiteResponse = 0;
        for (int i = 0; i < sequenceToCheck.getSequence().length; i++) {
            if (secretCode.getChromosomeAtPos(i) == sequenceToCheck.getChromosomeAtPos(i)) {
                redResponse++;
            }
        }

        int[] colorCounter = new int[GameSettings.INSTANCE.numberOfColors];
        for (int i = 0; i < secretCode.getSequence().length; i++) {
            colorCounter[secretCode.getSequence()[i]]++;
            colorCounter[sequenceToCheck.getSequence()[i]]++;
        }
        for (int colorCount : colorCounter) {
            if (colorCount == 2) {
                whiteResponse++;
            }
        }
        whiteResponse -= redResponse;

        return new Submission(sequenceToCheck, redResponse, whiteResponse);
    }
}
