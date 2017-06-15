package engine.helper;

import config.LoggerGenerator;
import engine.GameEngine;
import engine.GameSettings;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.LinkedBlockingQueue;

public class SubmissionHandler {
    /*for debugging*/
    private final Logger logger = LoggerGenerator.submissionHandler;

    /*--
     * constructor
     */
    public SubmissionHandler(GameEngine engine) {
        this.gameEngine = engine;
    }

    /*--
     * attributes
     */
    private GameEngine gameEngine;
    private LinkedBlockingQueue<Submission> submissions = new LinkedBlockingQueue<>();

    /*functions*/
    public void addSubmission(Submission submission) {
        try {
            submissions.put(submission);
        } catch (InterruptedException e) {
            System.out.println("    ERROR: Adding of submission " + submission.toString() + " failed.");
            e.printStackTrace();
        }
    }

    public void dropSubmissions() {
        submissions.clear();
    }

    public void handleSubmissionRequest(int requestCounter) {
        if (requestCounter < GameSettings.INSTANCE.numberOfTries) {
            Submission currentLine = null;
            try {
                currentLine = submissions.take();
            } catch (InterruptedException e) {
                System.out.println("    ERROR: reading of submission #" + requestCounter + " failed.");
                e.printStackTrace();
            }

            if (GameSettings.INSTANCE.loggingEnabled) {
                logger.info("    position = " + requestCounter + ", " + currentLine.toString());
            }
            int nextCounter = requestCounter + 1;
            if (nextCounter < GameSettings.INSTANCE.numberOfTries) {
                if (GameSettings.INSTANCE.loggingEnabled) {
                    logger.info("    calculate submission #" + nextCounter);
                }
                gameEngine.calculateNextSubmission();
            }
            if (GameSettings.INSTANCE.loggingEnabled) {
                logger.info("    After calculating next submission: ");
                logger.info("    position = " + requestCounter + ", " + currentLine.toString());
            }
        }
    }

    public int sumEvolutionSteps() {
        int sum = 0;
        for (Submission subm : submissions) {
            sum += subm.getChromosome().getGeneration();
        }
        return sum;
    }

    /*getter + setter*/
    public LinkedBlockingQueue<Submission> getSubmissions() {
        return submissions;
    }
}
