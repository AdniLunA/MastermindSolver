package engine.helper;

import config.LoggerGenerator;
import evolution.IChromosome;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 */
public class Submission {
    /*constructor*/
    public Submission() {
    }

    public Submission(IChromosome submittedChromosome, int red, int white) {
        setAttributes(submittedChromosome, red, white);
    }

    /*--attributes*/
    private IChromosome submittedChromosome;
    private int red;
    private int white;

    /*--functions*/
    public void setAttributes(IChromosome submittedChromosome, int red, int white) {
        this.submittedChromosome = submittedChromosome;
        this.red = red;
        this.white = white;
    }

    /*--getter + setter*/
    public IChromosome getChromosome() {
        return submittedChromosome;
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

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("red = ");
        buffer.append(red);
        buffer.append(", white = ");
        buffer.append(white);
        buffer.append(", sequence = ");
        buffer.append(submittedChromosome);
        return buffer.toString();
    }
}
