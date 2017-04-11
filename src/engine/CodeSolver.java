package engine;

import config.ConfigurationManager;
import evolution.*;
import de.bean900.logger.Logger;

import java.util.ArrayList;

/**
 * Handle request for next code submission
 * - At first request, returns random code
 * - Later codes are calculated with the evolutionary algorithms
 */
public class CodeSolver {
    /*--
     * debugging
     */
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    /*--
     * attributes
     */
    private GameEngine engine = GameEngine.getInstance();

    /*--
     * functions
     */
    public void solve(int requestCounter) {
        this.logger.info("solve", "");
        IChromosome newSequence = new NumChromosome(engine.getCodeLength(), engine.getNumColors());
        /*first submission random*/
        if (requestCounter == 0) {
            newSequence.generateRandom();
        } else { /*other submission via evolution*/
            newSequence = solveViaEvolutionaryAlgorithms();
        }
        this.logger.info("solve", "CodeSolver: request #" + requestCounter);
        this.logger.info("solve", "    CodeSolver: next sequence = " + newSequence.toString());
        engine.resolveSubmission(newSequence, requestCounter);
    }

    private IChromosome solveViaEvolutionaryAlgorithms() {
        /*random gene pool -> population*/
        IPopulation population = new Population();
        /*evolve n times*/
        /*todo: find an intelligent way to choose mutation methods*/
        for (int i = 0; i < ConfigurationManager.INSTANCE.REPEAT_EVOLUTION_N_TIMES; i++) {
            population.evolve();
            this.logger.info("solveViaEvolutionaryAlgorithms", "    Code Solver - population fitness at round #"+i+": "+population.getSumPopulationFitness());
        }
        /*get fittest*/
        IChromosome nextRequest;
        FitnessCalculator checkSubmissions = FitnessCalculator.getInstance();
        /*
        prevent duplicate submission
         */
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
        this.logger.info("solveViaEvolutionaryAlgorithms", "New request has fitness "+nextRequest.getFitness());
        return nextRequest;
    }

    /*--getter + setter*/
}
