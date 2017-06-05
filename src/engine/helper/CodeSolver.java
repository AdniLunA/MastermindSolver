package engine.helper;

import config.*;
import engine.GameEngine;
import engine.GameSettings;
import evolution.IChromosome;
import evolution.IPopulation;
import evolution.NumChromosome;
import evolution.Population;
import evolution.crossover.KPointCrossover;
import evolution.crossover.OnePointCrossover;
import evolution.crossover.TwoPointCrossover;
import evolution.crossover.UniformCrossover;
import evolution.mutation.*;
import evolution.selection.RouletteWheelSelection;
import evolution.selection.TournamentSelection;
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
    private static final Logger logger = LoggerGenerator.codeSolver;

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
            newSequence = solveViaEvolutionaryAlgorithms();
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

        population.refreshSicknessOfGenePool();
        IChromosome fittest = new NumChromosome();
        for (int i = 0; (i < GameSettings.INSTANCE.repeatEvolutionNTimes) && (fittest.getSickness() > 0); i++) {

            if(GameSettings.INSTANCE.dynamicEvolution){
                dynamicEvolution();
            }else {
                population.evolve();
            }

            if (GameSettings.INSTANCE.trackSicknessByEvolving && !GameSettings.INSTANCE.efficiencyAnalysisEnabled) {
                System.out.printf("- Evolution round #%02d, sickness of fittest: %3d, generation of fittest: %3d, sum sickness: %6d\n", i, population.getFittest().getSickness(), population.getFittest().getGeneration(), population.getSumPopulationSickness());
            }
            if (GameSettings.INSTANCE.loggingEnabled) {
                logger.info("    Code Solver - population sickness at round #" + i + ": " + population.getSumPopulationSickness());
            }

            population.getFittest().setGeneration(i);
            fittest = population.getFittest();
        }

        /*get fittest*/
        IChromosome nextRequest = fittest;

        if (GameSettings.INSTANCE.loggingEnabled) {
            this.logger.info("New request has fitness " + nextRequest.getSickness());
        }
        return nextRequest;
    }

    void dynamicEvolution(){
        MersenneTwisterFast generator = new MersenneTwisterFast(System.nanoTime());
        int selectionNum = generator.nextInt(0, 2);
        int crossoverNum = generator.nextInt(0, 4);
        int mutationNum = generator.nextInt(0, 5);
        population.evolve(selectionNum,crossoverNum,mutationNum);
    }

    /*--getter + setter*/

    public int getRequestCounter() {
        return requestCounter;
    }

    public void setRequestCounter(int requestCounter) {
        this.requestCounter = requestCounter;
    }
}
