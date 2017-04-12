package engine;

import evolution.IChromosome;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * calculates red + white response
 * - knows the secret code
 */
public class CodeValidator {
    /*--
     * debugging
     */
    private final Logger logger = LogManager.getLogger(this);

    /*--
     * constructor
     */
    public CodeValidator(IChromosome secretCode) {
        this.secretCode = secretCode;
        this.logger.info("- initialized with " + secretCode.toString());
    }

    /*--
     * attributes
     */
    private final IChromosome secretCode;

    /*--
     * functions
     */
    public int[] calculateResponse(IChromosome sequenceToCheck) {
        int redResponse = 0;
        int whiteResponse = 0;
        this.logger.info("    Secret code: " + secretCode.toString());
        for (int i = 0; i < secretCode.getSequence().length; i++) {
            if (secretCode.getSequence()[i] == sequenceToCheck.getSequence()[i]) {
                redResponse++;
            }
        }

        int[] sortedSecretCode = secretCode.getSequenceSorted();
        int[] sortedSequenceToCheck = sequenceToCheck.getSequenceSorted();
        int[] colorCounter = new int[secretCode.getNumberOfColors()];
        for (int i = 0; i < sortedSecretCode.length; i++) {
            colorCounter[sortedSecretCode[i]]++;
            colorCounter[sortedSequenceToCheck[i]]++;
        }
        for (int colorCount : colorCounter) {
            if (colorCount == 2) {
                whiteResponse++;
            }
        }
        whiteResponse -= redResponse;

        int[] response = {redResponse, whiteResponse};
        this.logger.info("    CodeValidator: red = " + redResponse + ", white = " + whiteResponse + ", code = " + sequenceToCheck.toString());
        return response;
    }

    /*--getter + setter*/
}
