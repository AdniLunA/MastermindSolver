package presentation;

import engine.GameEngine;
import engine.helper.Submission;

public interface IPresentationManager {
    /*--
     * functions
     */
    void startWithRandomCode(int lengthOfCode, int numberOfColors, int numberOfTries);

    void handleSubmission(Submission submission, int position);

    void handleSubmissionRequest(int requestCounter);

    /*--
     * getter + setter
     */
    GameEngine getGameEngine();

    void setGameEngine(GameEngine gameEngine);

}
