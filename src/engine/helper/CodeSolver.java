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
        this.requestCounter = 0;
        this.population = new Population();
    }

    /*--
     * attributes
     */
    private GameEngine engine;
    private int requestCounter;
    private Population population;
    private ArrayList<IChromosome> alreadyPostedRequests = new ArrayList<>();

    /*--
     * functions
     */
    public void solve() {
        this.logger.info("");
        if (requestCounter >= GameSettings.INSTANCE.maxNumberOfTries){
            logger.error("maxNumberOfTries reached. No further submissions are calculated.");
            throw new IndexOutOfBoundsException("maxNumberOfTries reached. No further submissions are calculated.");
        }
        IChromosome newSequence = new NumChromosome();
        /*first submission random, others via EA*/
        if (requestCounter != 0) {
            newSequence = solveViaEvolutionaryAlgorithms();
        }
        this.logger.info("CodeSolver: request #" + requestCounter);
        this.logger.info("    CodeSolver: next sequence = " + newSequence.toString());
        requestCounter++;

        alreadyPostedRequests.add(newSequence);
        engine.resolveSubmission(newSequence);
    }

    private IChromosome solveViaEvolutionaryAlgorithms() {
        /*random gene pool -> population*/
        /*evolve n times*/
        /*todo: find an intelligent way to choose mutation methods*/
        /*todo: fortfahren mit error tracking*/
        //for (int count = 0; count < GameSettings.INSTANCE.repeatEvolutionNTimes; count++) {
        int count = 0;
        while(population.getFittest().getSickness()>1){
            population.evolve();
            if(GameSettings.INSTANCE.trackSicknessByEvolving){
                System.out.printf("- Evolution round #%02d, sickness of fittest: %3d, generation of fittest: %3d, sum sickness: %6d\n", count, population.getFittest().getSickness(), population.getFittest().getGeneration(), population.getSumPopulationSickness());
            }
            /*remove already posted sequences*/
            population.removeAlreadyRequestedCodes(alreadyPostedRequests);
            this.logger.info("    Code Solver - population fitness at round #" + count + ": " + population.getSumPopulationSickness());
            count++;
        }
        /*get fittest*/
        IChromosome nextRequest = population.getFittest();
        System.out.println("Set request after "+count+" evolutionary steps");
        /*
        prevent duplicate submission - old method
        *//*
        boolean alreadyPosted = false;
        do {
            nextRequest = population.getFittest();
            ArrayList<Submission> postedSubmissions = SicknessCalculator.INSTANCE.getSubmissions();
            for (Submission postedSubmission : postedSubmissions) {
                if (postedSubmission == postedSubmission.getChromosome()) {
                    alreadyPosted = true;
                    population.replaceGene(nextRequest);
                    break;
                }
            }
        }
        while (alreadyPosted);
        */
        this.logger.info("New request has fitness " + nextRequest.getSickness());
        return nextRequest;
    }

    /*--getter + setter*/

    public int getRequestCounter() {
        return requestCounter;
    }
}
