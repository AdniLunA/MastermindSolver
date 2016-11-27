package engine;

import evolution.IChromosome;
import evolution.NumChromosome;
import evolution.Submission;

import java.util.ArrayList;

public class CodeSolver {
    //attributes

    //functions
    public void run(){
        System.out.println("CodeSolver - run");
    }

    private void submitSequence(){
        System.out.println("CodeSolver - submitSequence");
        IChromosome chromosome = new NumChromosome();
        Submission newSumbmission = GameEngine.getInstance().resolveSubmission(chromosome);
    }

    public void handleResponse(int[] response) {
        System.out.println("CodeSolver - handleResponse");
    }

    //getter + setter
}
