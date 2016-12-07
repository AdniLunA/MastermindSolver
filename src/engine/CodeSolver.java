package engine;

import evolution.IChromosome;
import evolution.NumChromosome;
import evolution.Submission;
import gui.GUIManager;

import java.util.ArrayList;

public class CodeSolver {
    /***constructor***/
    public CodeSolver() {
        newSequence = new NumChromosome(engine.getCodeLength(),engine.getNumColors());
    }
    /***attributes***/
    private IChromosome newSequence;
    GameEngine engine = GameEngine.getInstance();

    /***functions***/
    public void run(int requestCounter){
        System.out.println("CodeSolver - run");
        /*first submission random*/
        if(requestCounter == 0) {
            newSequence.generateRandom();
        } else { /*other submission via evolution*/
            /*todo*/
            newSequence.generateRandom();
        }
        System.out.println("request #"+requestCounter);
        submitSequence();
    }

    private void submitSequence(){
        System.out.println("CodeSolver - submitSequence");
        engine.resolveSubmission(newSequence);
    }

    /*public void handleResponse(int[] response) {
        System.out.println("CodeSolver - handleResponse");
    }*/

    /***getter + setter***/
}
