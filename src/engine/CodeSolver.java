package engine;

import evolution.IChromosome;
import evolution.NumChromosome;

public class CodeSolver {
    /***
     * constructor
     ***/
    public CodeSolver() {

    }

    /***
     * attributes
     ***/
    GameEngine engine = GameEngine.getInstance();

    /***
     * functions
     ***/
    public void solve(int requestCounter) {
        System.out.println("CodeSolver - solve");
        IChromosome newSequence = new NumChromosome(engine.getCodeLength(), engine.getNumColors());
        /*first submission random*/
        if (requestCounter == 0) {
            newSequence.generateRandom();
        } else { /*other submission via evolution*/
            /*todo*/
            newSequence.generateRandom();
        }
        System.out.println("CodeSolver: request #" + requestCounter);
        System.out.println("    CodeSolver: next sequence = " + newSequence.toString());
        engine.resolveSubmission(newSequence, requestCounter);
    }


    /*public void handleResponse(int[] response) {
        System.out.println("CodeSolver - handleResponse");
    }*/

    /***getter + setter***/
}
