package engine;

import config.Configuration;
import evolution.IChromosome;
import evolution.IPopulation;
import evolution.NumChromosome;
import evolution.Population;
import evolution.crossover.ICrossover;
import evolution.mutation.IMutation;
import evolution.selection.ISelection;
import evolution.selection.RouletteWheelSelection;
import evolution.selection.TournamentSelection;

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
        /*evolve*/
        population.evolve();
        /*get fittest*/
        return population.getFittest();
    }


    /*public void handleResponse(int[] response) {
        System.out.println("CodeSolver - handleResponse");
    }*/

    /***getter + setter***/
}
