package engine.helper;

import engine.GameEngine;
import engine.GameSettings;
import evolution.IChromosome;
import evolution.IPopulation;
import evolution.NumChromosome;
import evolution.Population;
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
    public CodeSolver(GameEngine engine) {
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
        //this.logger.info("");
        if (requestCounter >= GameSettings.INSTANCE.maxNumberOfTries) {
            logger.error("maxNumberOfTries reached. No further submissions are calculated.");
            throw new IndexOutOfBoundsException("maxNumberOfTries reached. No further submissions are calculated.");
        }
        IChromosome newSequence = new NumChromosome();
        /*first submission random, others via EA*/
        if (requestCounter != 0) {

            long timeStart = System.currentTimeMillis();

            newSequence = solveViaEvolutionaryAlgorithms();

            System.out.println("Solve Via Evolutionary Algorithms: "+ (System.currentTimeMillis() - timeStart));
        }
        if (GameSettings.INSTANCE.loggingEnabled) {
            this.logger.info("CodeSolver: request #" + requestCounter);
            this.logger.info("    CodeSolver: next sequence = " + newSequence.toString());
        }
        requestCounter++;

        alreadyPostedRequests.add(newSequence);
        engine.resolveSubmission(newSequence);
    }

    private IChromosome solveViaEvolutionaryAlgorithms() {
        /*random gene pool -> population*/
        /*evolve n times*/
        /*todo: find an intelligent way to choose mutation methods*/
        /*todo: fortfahren mit error tracking*/
        long timeStart = System.currentTimeMillis();
        int lastNumber = 0;
        long lastTime = 0;
        for (int i = 0; (i < GameSettings.INSTANCE.repeatEvolutionNTimes) && (population.getFittest().getSickness() > 0); i++) {

            population.evolve();

            if (GameSettings.INSTANCE.trackSicknessByEvolving && !GameSettings.INSTANCE.efficiencyAnalysisEnabled) {
                System.out.printf("- Evolution round #%02d, sickness of fittest: %3d, generation of fittest: %3d, sum sickness: %6d\n", i, population.getFittest().getSickness(), population.getFittest().getGeneration(), population.getSumPopulationSickness());
            }
            if (GameSettings.INSTANCE.loggingEnabled) {
                this.logger.info("    Code Solver - population sickness at round #" + i + ": " + population.getSumPopulationSickness());
            }

            /*remove already posted sequences in a copy to not to disturb creation of similar requests*/
            IPopulation copyOfPopulation = new Population(population.getGenePool());
            copyOfPopulation.removeAlreadyRequestedCodes(alreadyPostedRequests);

            copyOfPopulation.getFittest().setGeneration(i);

            long passedTime = System.currentTimeMillis() - timeStart;
            if(passedTime%1000 < 6){
                System.out.println(i - lastNumber+" evolution steps in "+ (passedTime/1000 - lastTime) +"sec");
                lastNumber = i;
                lastTime = (passedTime/1000);
            }
        }

        /*get fittest*/
        IChromosome nextRequest = population.getFittest();

        if (GameSettings.INSTANCE.loggingEnabled) {
            this.logger.info("New request has fitness " + nextRequest.getSickness());
        }
        return nextRequest;
    }

    /*--getter + setter*/

    public int getRequestCounter() {
        return requestCounter;
    }

    public void setRequestCounter(int requestCounter) {
        this.requestCounter = requestCounter;
    }
}
