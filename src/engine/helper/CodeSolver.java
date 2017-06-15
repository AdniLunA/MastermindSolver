package engine.helper;

import config.LoggerGenerator;
import config.MersenneTwisterFast;
import engine.GameEngine;
import engine.GameSettings;
import evolution.IChromosome;
import evolution.NumChromosome;
import evolution.population.Population;
import org.apache.logging.log4j.Logger;

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
    public CodeSolver(GameEngine engine, MersenneTwisterFast generator) {
        this.engine = engine;
        this.requestCounter = 0;
        this.population = new Population();
        this.generator = generator;
    }

    /*--
     * attributes
     */
    private GameEngine engine;
    private int requestCounter;
    private Population population;
    private MersenneTwisterFast generator;

    /*--
     * functions
     */
    public void solve() {
        if (requestCounter >= GameSettings.INSTANCE.maxNumberOfTries) {
            logger.error("maxNumberOfTries reached. No further submissions are calculated.");
            throw new IndexOutOfBoundsException("maxNumberOfTries reached. No further submissions are calculated.");
        }
        IChromosome newSequence = new NumChromosome(generator);
        /*first submission random, others via EA*/
        if (requestCounter != 0) {
            newSequence = solveViaEvolutionaryAlgorithms();
        }
        if (GameSettings.INSTANCE.loggingEnabled) {
            logger.info("CodeSolver: request #" + requestCounter);
            logger.info("    CodeSolver: next sequence = " + newSequence.toString());
        }
        requestCounter++;

        /*alreadyPostedRequests.add(newSequence);*/
        engine.resolveSubmission(newSequence);
    }

    private IChromosome solveViaEvolutionaryAlgorithms() {
        /*random gene pool -> population*/
        /*evolve n times*/
        /*todo: find an intelligent way to choose mutation methods*/
        /*todo: fortfahren mit error tracking*/

        population.refreshSicknessOfGenePool();
        IChromosome fittest = new NumChromosome(generator);
        for (int i = 0; (i < GameSettings.INSTANCE.repeatEvolutionNTimes) && (fittest.getSickness() > 0); i++) {

            if (GameSettings.INSTANCE.dynamicEvolution) {
                dynamicEvolution();
            } else {
                population.evolve();
            }

            if (GameSettings.INSTANCE.trackSicknessByEvolving && !GameSettings.INSTANCE.efficiencyAnalysisEnabled) {
                System.out.printf("- Evolution round #%02d, sickness of fittest: %3d, generation of fittest: %3d, sum sickness: %6d\n", i, population.getFittest().getSickness(), population.getFittest().getGeneration(), population.getSumPopulationSickness());
            }
            if (GameSettings.INSTANCE.loggingEnabled) {
                logger.info("    Code Solver - population sickness at round #" + i + ": " + population.getSumPopulationSickness());
            }

            fittest = population.getFittest();
            fittest.setGeneration(i);
        }

        /*get fittest*/
        IChromosome nextRequest = fittest;

        if (GameSettings.INSTANCE.loggingEnabled) {
            logger.info("New request has fitness " + nextRequest.getSickness());
        }
        return nextRequest;
    }

    private void dynamicEvolution() {
        MersenneTwisterFast generator = new MersenneTwisterFast(System.nanoTime());
        int selectionNum = generator.nextInt(0, 1);
        int crossoverNum = generator.nextInt(0, 3);
        int mutationNum = generator.nextInt(0, 4);
        population.evolve(selectionNum, crossoverNum, mutationNum);
    }

    /*--getter + setter*/

    public int getRequestCounter() {
        return requestCounter;
    }

    public void setRequestCounter(int requestCounter) {
        this.requestCounter = requestCounter;
    }
}
