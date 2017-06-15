package evolution;

import config.LoggerGenerator;
import engine.GameSettings;
import engine.helper.CodeValidator;
import engine.helper.Submission;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public enum SicknessCalculator {
    INSTANCE;

    /*--
     * debugging
     */
    private final Logger logger = LoggerGenerator.sicknessCalculator;

    /*--
     * attributes
     */
    private ArrayList<Submission> submissions = new ArrayList<>();
    private ArrayList<CodeValidator> submissionComparer = new ArrayList<>();

    /*--
     * functions
     */
    public void addSubmission(Submission newSubmission) {
        if (GameSettings.INSTANCE.loggingEnabled) {
            logger.info(newSubmission.getChromosome().toString());
        }
        this.submissions.add(newSubmission);
        this.submissionComparer.add(new CodeValidator(newSubmission.getChromosome()));
    }

    public boolean checkUpToDate(int nKnownSubmissions) {
        return nKnownSubmissions == submissions.size();
    }

    public int getNSubmissions() {
        return submissions.size();
    }

    public int calculateSickness(IChromosome chromosomeToCheck) {
        /*smaller sickness = greater fitness*/
        int sickness = 0;
        if (!submissions.isEmpty()) {
            /*calculate if it is probable that the chromosome is the secret code due to the results of past requests*/
            for (int count = 0; count < submissions.size(); count++) {
                /*check red fit*/
                Submission validationByCurrentSubmission = submissionComparer.get(count).calculateResponse(chromosomeToCheck);
                int redSimilarity = validationByCurrentSubmission.getRed();
                int whiteSimilarity = validationByCurrentSubmission.getWhite();

                int redDifference = Math.abs(submissions.get(count).getRed() - redSimilarity);
                int whiteDifference = Math.abs(submissions.get(count).getWhite() - whiteSimilarity);

                sickness += (GameSettings.INSTANCE.weightOfRedDifference * redDifference
                        + GameSettings.INSTANCE.weightOfWhiteDifference * whiteDifference);
            }
            return sickness;
        } else {
            /*if no submissions has been set, probability can't be determined.*/
            return 0;
        }
    }

    public void dropForNextGame() {
        submissions.clear();
        submissionComparer.clear();
    }
}
