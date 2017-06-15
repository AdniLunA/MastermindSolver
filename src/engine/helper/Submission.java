package engine.helper;

import evolution.IChromosome;

public class Submission {
    /*constructor*/
    public Submission(IChromosome submittedChromosome, int red, int white) {
        this.submittedChromosome = submittedChromosome;
        this.red = red;
        this.white = white;
    }

    /*--attributes*/
    private IChromosome submittedChromosome;
    private int red;
    private int white;

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
        return "red = " + red +
                ", white = " + white +
                ", sequence = " + submittedChromosome;
    }
}
