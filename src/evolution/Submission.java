package evolution;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Submission {
    /*--
     * debugging
     */
    private final Logger logger = LogManager.getLogger(this);

    /*constructor*/
    public Submission() {
    }

    public Submission(IChromosome submittedChromosome, int red, int white) {
        setAttributes(submittedChromosome, red, white);
    }

    /*attributes*/
    private IChromosome submittedChromosome;
    private int red;
    private int white;

    /*functions*/
    public void setAttributes(IChromosome submittedChromosome, int red, int white) {
        //logger.info("");
        this.submittedChromosome = submittedChromosome;
        this.red = red;
        this.white = white;
    }

    /*getter + setter*/
    public IChromosome getChromosome() {
        return submittedChromosome;
    }

    public void setSubmittedChromosome(IChromosome submittedChromosome) {
        this.submittedChromosome = submittedChromosome;
    }

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getWhite() {
        return white;
    }

    public void setWhite(int white) {
        this.white = white;
    }
}
