package engine;

import evolution.IChromosome;
import evolution.NumChromosome;

import java.awt.color.ICC_ColorSpace;
import java.util.ArrayList;

public class CodeSolver {
    //attributes
    private ArrayList<Submission> pastSubmissions;

    //functions
    public void run(){
        System.out.println("CodeSolver - run");
    }

    private void submitSequence(){
        System.out.println("CodeSolver - submitSequence");
        IChromosome chromosome = new NumChromosome();
        Submission newSumbmission = GameEngine.getInstance().resolveSubmission(chromosome);
        pastSubmissions.add(newSumbmission);
    }

    public void handleResponse(int[] response) {
        System.out.println("CodeSolver - handleResponse");
    }

    //getter + setter
}
