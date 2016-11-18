package engine;

import evolution.IChromosome;
import evolution.NumChromosome;

import java.util.ArrayList;

public class CodeSolver {
    //attributes
    private ArrayList<Submission> pastSubmissions;

    //functions
    public IChromosome submitSequence(){
        System.out.println("CodeSolver - submitSequence");
        return new NumChromosome();
    }

    public void handleResponse(int[] response) {
        System.out.println("CodeSolver - handleResponse");
    }

    //getter + setter

}
