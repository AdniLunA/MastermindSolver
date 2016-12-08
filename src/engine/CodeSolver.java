package engine;

import config.Configuration;
import evolution.*;

import java.util.ArrayList;

public class CodeSolver {
    /***
     * attributes
     ***/
    private GameEngine engine = GameEngine.getInstance();

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
            newSequence = solveViaEvolutionaryAlgorithms();
        }
        System.out.println("CodeSolver: request #" + requestCounter);
        System.out.println("    CodeSolver: next sequence = " + newSequence.toString());
        engine.resolveSubmission(newSequence, requestCounter);
    }

    private IChromosome solveViaEvolutionaryAlgorithms() {
        //Configuration.INSTANCE.REPEAT_EVOLUTION_N_TIMES
        /*random gene pool -> population*/
        IPopulation population = new Population();
        /*evolve n times*/
        /*todo: find an intelligent way to choose mutation methods*/
        for (int i = 0; i < Configuration.INSTANCE.REPEAT_EVOLUTION_N_TIMES; i++) {
            population.evolve();
            System.out.println("    Code Solver - population fitness at round #"+i+": "+population.getSumPopulationFitness());
        }
        /*get fittest*/
        IChromosome nextRequest;
        FitnessCalculator checkSubmissions = FitnessCalculator.getInstance();
        boolean alreadyPosted = false;
        do {
            nextRequest = population.getFittest();
            ArrayList<Submission> postedSubmissions = checkSubmissions.getSubmissions();
            for (Submission postedSubmission : postedSubmissions) {
                if(postedSubmission == postedSubmission.getChromosome()){
                    alreadyPosted = true;
                    population.replaceGene(nextRequest);
                    break;
                }
            }
        }
        while (alreadyPosted);
        System.out.println("New request has fitness "+nextRequest.getFitness());
        return nextRequest;
    }


    /*public void handleResponse(int[] response) {
        System.out.println("CodeSolver - handleResponse");
    }*/

    /***getter + setter***/
}
