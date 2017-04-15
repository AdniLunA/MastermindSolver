package engine.helper;

import engine.GameEngine;
import engine.GameSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.LinkedBlockingQueue;

public class SubmissionHandler {
    /*for debugging*/
    private final Logger logger = LogManager.getLogger(this);

    /*--
     * constructor
     */
    public SubmissionHandler(GameEngine engine){
        this.gameEngine = engine;
    }

    /*--
     * attributes
     */
    private GameEngine gameEngine;
    private LinkedBlockingQueue<Submission> submissions = new LinkedBlockingQueue<>();;

    public void handleSubmission(Submission submission, int position) {
        logger.info("");
        try {
            submissions.put(submission);
        } catch (InterruptedException e) {
            System.out.println("    ERROR: Adding of submission " + submission.toString() + " failed.");
            e.printStackTrace();
        }
        logger.info("    position = " + position + ", " + submission.toString());
    }

    public void handleSubmissionRequest(int requestCounter) {
        logger.info("");
        if (requestCounter < GameSettings.INSTANCE.numberOfTries) {
            Submission currentLine = null;
            try {
                currentLine = submissions.take();
            } catch (InterruptedException e) {
                System.out.println("    ERROR: reading of submission #" + requestCounter + " failed.");
                e.printStackTrace();
            }
            logger.info("    position = " + requestCounter + ", " + currentLine.toString());
            int nextCounter = requestCounter + 1;
            if (nextCounter < GameSettings.INSTANCE.numberOfTries) {
                logger.info("    calculate submission #" + nextCounter);
                gameEngine.calculateNextSubmission(nextCounter);
            }
            logger.info("    After calculating next submission: ");
            logger.info("    position = " + requestCounter + ", " + currentLine.toString());

        }
    }
}
