package evolution;

import config.Configuration;

import java.util.ArrayList;

public class FitnessCalculator {
    //attributes
    private static FitnessCalculator fitnessCalculator; //Singleton Pattern

    private ArrayList<Submission> submissions;

    private FitnessCalculator() {
        this.fitnessCalculator = this; //Singleton Pattern
    }

    //functions
    public static final FitnessCalculator getInstance() { //Singleton Pattern
        if (fitnessCalculator == null) {
            return new FitnessCalculator();
        } else {
            return fitnessCalculator;
        }
    }

    public void addSubmission(Submission newSubmission) {
        System.out.println("FitnessCalculator - addSubmission: " + newSubmission);
        this.submissions.add(newSubmission);
    }

    public int calculateFitness(IChromosome chromosomeToCheck) {
        System.out.println("FitnessCalculator - calculateFitness");
        //if no submissions has been set, fitness can't be guessed.
        int fitness = 0;
        if (!submissions.isEmpty()) {
            //calculate if the chromosome would fit to the responses of the submissions
            for (Submission submission : submissions) {
                //check red fit
                int redFit = 0;
                int[] orignialSequence = chromosomeToCheck.getSequence();
                int[] originalSubmission = submission.getChromosome().getSequence();

                for (int i = 0; i < originalSubmission.length; i++) {
                    if (originalSubmission[i] == orignialSequence[i]) {
                        redFit++;
                    }
                }

                int redFitDifference = Math.abs(submission.getRed() - redFit);

                //check white fit
                int whiteFit = 0;
                int[] sortedSequence = chromosomeToCheck.getSequenceSorted();
                int[] sortedSubmission = submission.getChromosome().getSequenceSorted();
                int[] colorCounter = new int[chromosomeToCheck.getNumberOfColors()];
                for (int i = 0; i < sortedSequence.length; i++) {
                    colorCounter[sortedSequence[i]]++;
                    colorCounter[sortedSubmission[i]]++;
                }
                for (int colorCount : colorCounter) {
                    if (colorCount == 2) {
                        whiteFit++;
                    }
                }
                whiteFit -= redFit;

                int whiteFitDifference = Math.abs(submission.getWhite() - whiteFit);

                fitness += Configuration.INSTANCE.WEIGHT_OF_RED_FIT * redFit + Configuration.INSTANCE.WEIGHT_OF_WHITE_FIT;
            }
            return fitness;
        } else {
            return Integer.MAX_VALUE;
        }
    }

    //getter + setter
    public ArrayList<Submission> getSubmissions() {
        return submissions;
    }
}
