package evolution;

public class Submission {
    //constructor
    public Submission() {
    }

    public Submission(IChromosome submittedChromosome, int red, int white) {
        setAttributes(submittedChromosome, red, white);
    }

    //attributes
    private IChromosome submittedChromosome;
    private int red;
    private int white;

    //functions
    public void setAttributes(IChromosome submission, int red, int white){
        this.submittedChromosome = submission;
        this.red = red;
        this.white = white;
    }

    //getter + setter
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