package engine;

import evolution.IChromosome;

public class Submission {
    //constructor
    public Submission() {
    }

    public Submission(IChromosome submission, int red, int white) {
        setAttributes(submission, red, white);
    }

    //attributes
    private IChromosome submission;
    private int red;
    private int white;

    //functions
    public void setAttributes(IChromosome submission, int red, int white){
        this.submission = submission;
        this.red = red;
        this.white = white;
    }

    //getter + setter
    public IChromosome getSubmission() {
        return submission;
    }

    public void setSubmission(IChromosome submission) {
        this.submission = submission;
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
