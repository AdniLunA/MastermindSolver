package evolution;

import config.Configuration;

import java.util.ArrayList;

public class FitnessCalculator {
    /***attributes***/
    private static FitnessCalculator fitnessCalculator; /*Singleton Pattern*/

    private ArrayList<Submission> submissions = new ArrayList<Submission>();

    private FitnessCalculator() {
        fitnessCalculator = this; /*Singleton Pattern*/
    }

    /***functions***/
    public static final FitnessCalculator getInstance() { /*Singleton Pattern*/
        System.out.println("FitnessCalculator - getInstance");
        if (fitnessCalculator == null) {
            return new FitnessCalculator();
        } else {
            return fitnessCalculator;
        }
    }

    public void addSubmission(Submission newSubmission) {
        System.out.println("FitnessCalculator - addSubmission: " + newSubmission.getChromosome().getSequence());
        this.submissions.add(newSubmission);
    }

    public int calculateFitness(IChromosome chromosomeToCheck) {
        System.out.println("FitnessCalculator - calculateFitness");
        /*smaller illness = better fitness*/
        int illness = 0;
        if (!submissions.isEmpty()) {
            /*calculate if the chromosome would fit to the responses of the submissions*/
            for (Submission submission : submissions) {
                /*check red fit*/
                int redFit = 0;
                int[] orignialSequence = chromosomeToCheck.getSequence();
                int[] originalSubmission = submission.getChromosome().getSequence();

                for (int i = 0; i < originalSubmission.length; i++) {
                    if (originalSubmission[i] == orignialSequence[i]) {
                        redFit++;
                    }
                }

                int redDifference = Math.abs(submission.getRed() - redFit);

                /*check white fit*/
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

                int whiteDifference = Math.abs(submission.getWhite() - whiteFit);

                illness += (Configuration.INSTANCE.WEIGHT_OF_RED_DIFFERENCE * redDifference
                            + Configuration.INSTANCE.WEIGHT_OF_WHITE_DIFFERENCE * whiteDifference);
            }
            return getMaxFitness(chromosomeToCheck) - illness;
        } else {
            /*if no submissions has been set, fitness can't be calculated.*/
            return 0;
        }
    }

    private int getMaxFitness(IChromosome chromosomeToCheck){
        int maxDifference = chromosomeToCheck.getNumberOfColors();
        return submissions.size() * maxDifference *(Configuration.INSTANCE.WEIGHT_OF_RED_DIFFERENCE + Configuration.INSTANCE.WEIGHT_OF_WHITE_DIFFERENCE);
    }

    /***getter + setter***/
    public ArrayList<Submission> getSubmissions() {
        return submissions;
    }

}
