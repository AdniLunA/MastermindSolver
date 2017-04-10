package evolution;

import config.ConfigurationManager;
import de.bean900.logger.Logger;
import engine.GameEngine;
import engine.Submission;

import java.util.ArrayList;

public class FitnessCalculator {
    /*--
     * debugging
     */
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    /*--
     * attributes
     */
    private static FitnessCalculator fitnessCalculator; /*Singleton Pattern*/

    private ArrayList<Submission> submissions = new ArrayList<Submission>();

    private FitnessCalculator() {
        fitnessCalculator = this; /*Singleton Pattern*/
    }

    /*--
     * functions
     */
    public static final FitnessCalculator getInstance() { /*Singleton Pattern*/
        /*System.out.println("FitnessCalculator - getInstance");*/
        if (fitnessCalculator == null) {
            return new FitnessCalculator();
        } else {
            return fitnessCalculator;
        }
    }

    public void addSubmission(Submission newSubmission) {
        logger.info("addSubmission", newSubmission.getChromosome().toString());
        this.submissions.add(newSubmission);
    }

    public int calculateFitness(IChromosome chromosomeToCheck) {
        /*System.out.println("FitnessCalculator - calculateFitness of " + chromosomeToCheck.toString());*/
        /*smaller illness = better fitness*/
        int illness = 0;
        if (!submissions.isEmpty()) {
            /*calculate if the chromosome would fit to the responses of the submissions*/
            for (Submission submission : submissions) {
                /*check red fit*/
                int redFit = 0;

                int[] originalSequence = chromosomeToCheck.getSequence();
                int[] originalSubmission = submission.getChromosome().getSequence();

                for (int i = 0; i < originalSubmission.length; i++) {
                    if(originalSequence[i] > GameEngine.getInstance().getNumColors() ||
                            originalSubmission[i] >= GameEngine.getInstance().getNumColors()){
                        String errormessage = "FitnessCalculator - calculateFitness ERROR: color > number of Colors.\n" +
                                "Tried to compare sequence "+chromosomeToCheck.toString()+" with submission "+submission.getChromosome().toString();
                        throw new IndexOutOfBoundsException(errormessage);
                    } else {
                        if (originalSubmission[i] == originalSequence[i]) {
                            redFit++;
                        }
                    }
                }

                int redDifference = Math.abs(submission.getRed() - redFit);

                /*check white fit*/
                int whiteFit = 0;
                int[] sortedSequence = chromosomeToCheck.getSequenceSorted();
                int[] sortedSubmission = submission.getChromosome().getSequenceSorted();
                int[] colorCounter = new int[GameEngine.getInstance().getNumColors()];
                for (int i = 0; i < sortedSequence.length; i++) {
                    if(sortedSequence[i] >= GameEngine.getInstance().getNumColors() ||
                            sortedSubmission[i] > GameEngine.getInstance().getNumColors()){
                        String errormessage = "FitnessCalculator - calculateFitness ERROR: color > number of Colors.\n" +
                                "Tried to compare sequence "+chromosomeToCheck.toString()+" with submission "+submission.getChromosome().toString();
                        throw new IndexOutOfBoundsException(errormessage);
                    } else {
                        colorCounter[sortedSequence[i]]++;
                        colorCounter[sortedSubmission[i]]++;
                    }
                }
                for (int colorCount : colorCounter) {
                    if (colorCount == 2) {
                        whiteFit++;
                    }
                }
                whiteFit -= redFit;

                int whiteDifference = Math.abs(submission.getWhite() - whiteFit);

                illness += (ConfigurationManager.INSTANCE.WEIGHT_OF_RED_DIFFERENCE * redDifference
                        + ConfigurationManager.INSTANCE.WEIGHT_OF_WHITE_DIFFERENCE * whiteDifference);
            }
            return getMaxFitness(chromosomeToCheck) - illness;
        } else {
            /*if no submissions has been set, fitness can't be calculated.*/
            return 0;
        }
    }

    private int getMaxFitness(IChromosome chromosomeToCheck) {
        int maxDifference = chromosomeToCheck.getNumberOfColors();
        return submissions.size() * maxDifference * (ConfigurationManager.INSTANCE.WEIGHT_OF_RED_DIFFERENCE + ConfigurationManager.INSTANCE.WEIGHT_OF_WHITE_DIFFERENCE);
    }

    public void dropForNextGame(){
        submissions.removeAll(submissions);
    }

    /*--
     * getter + setter
     */
    public ArrayList<Submission> getSubmissions() {
        return submissions;
    }

}
