package engine.helper;

import engine.GameEngine;
import engine.GameSettings;
import evolution.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    private final Logger logger = LogManager.getLogger(this);

    /*--
     * console
     */
    public CodeSolver(GameEngine engine){
        this.engine = engine;
    }

    /*--
     * attributes
     */
    private GameEngine engine;

    /*--
     * functions
     */
    public void solve(int requestCounter) {
        this.logger.info("");
        IChromosome newSequence = new NumChromosome();
        /*first submission random*/
        if (requestCounter == 0) {
            newSequence.generateRandom();
        } else { /*other submission via evolution*/
            newSequence = solveViaEvolutionaryAlgorithms();
        }
        this.logger.info("CodeSolver: request #" + requestCounter);
        this.logger.info("    CodeSolver: next sequence = " + newSequence.toString());
        engine.resolveSubmission(newSequence, requestCounter);
    }

    private IChromosome solveViaEvolutionaryAlgorithms() {
        /*random gene pool -> population*/
        IPopulation population = new Population();
        /*evolve n times*/
        /*todo: find an intelligent way to choose mutation methods*/
        for (int i = 0; i < GameSettings.INSTANCE.repeatEvolutionNTimes; i++) {
            population.evolve();
            this.logger.info("    Code Solver - population fitness at round #" + i + ": " + population.getSumPopulationFitness());
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
                if (postedSubmission == postedSubmission.getChromosome()) {
                    alreadyPosted = true;
                    population.replaceGene(nextRequest);
                    break;
                }
            }
        }
        while (alreadyPosted);
        this.logger.info("New request has fitness " + nextRequest.getFitness());
        return nextRequest;
    }

    /*--getter + setter*/
}
