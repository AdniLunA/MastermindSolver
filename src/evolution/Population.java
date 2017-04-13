package evolution;

import config.CrossoverEnum;
import config.MutationEnum;
import config.SelectionEnum;
import engine.GameEngine;
import engine.GameSettings;
import evolution.crossover.*;
import evolution.mutation.*;
import evolution.selection.ISelection;
import evolution.selection.RouletteWheelSelection;
import evolution.selection.TournamentSelection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;

public class Population implements IPopulation {
    /*--
     * debugging
     */
    private final Logger logger = LogManager.getLogger(this);

    /*--
     * constructors
     */
    public Population() {
        IChromosome[] populationArray = breedRandomPopulation(GameSettings.INSTANCE.sizeOfPopulation);
        genePool = transformToList(populationArray);
    }

    public Population(IChromosome[] genePool) {
        this.genePool = transformToList(genePool);
    }

    /*--
     * attributes
     */
    private int maxGenerationCounter = 0;
    private ArrayList<IChromosome> genePool = new ArrayList<IChromosome>();
    private ISelection selector;
    private ICrossover crosser;
    private IMutation mutator;
    private int idCounter = 0;

    /*--
     * functions
     */
    @Override
    public void evolve() {
        evolve(GameSettings.INSTANCE.selectionType, GameSettings.INSTANCE.crossoverType, GameSettings.INSTANCE.mutationType);
    }

    @Override
    public void evolve(SelectionEnum chooseSelection, CrossoverEnum chooseCrossover, MutationEnum chooseMutation) {
        logger.info("");
        maxGenerationCounter++;
        instantiateHelpers(chooseSelection, chooseCrossover, chooseMutation);
        /*selection*/
        logger.info("Start selection");
        IChromosome[] parents = selector.getParents(Arrays.copyOf(transformToArray(genePool), genePool.size()));

        /*crossover*/
        logger.info("Start crossover");
        IChromosome[] newGeneration = crosser.crossover(parents);
        for (int i = 0; i < 2; i++) {
            logger.info("    -- Child #" + i + " is valid: " + newGeneration[i].checkValidity());
            if (!newGeneration[i].checkValidity()) {
                throw new InputMismatchException("Population evolve crossover: ERROR - got invalid child " + newGeneration[i].toString());
            }
        }
        newGeneration[0].setGeneration(maxGenerationCounter + 1);
        newGeneration[1].setGeneration(maxGenerationCounter + 1);
        replaceWeakestWithNewGenes(newGeneration);

        /*mutation*/
        logger.info("Start mutation");
        IChromosome[] mutatedGeneration = newGeneration;
        mutatedGeneration = mutator.mutateGenes(Arrays.copyOf(transformToArray(genePool), genePool.size()));
        this.genePool = transformToList(mutatedGeneration);

        logger.info("A new generation has been born! #" + maxGenerationCounter);
    }

    @Override
    public IChromosome[] getPopulationSorted() {
        logger.info("");
        IChromosome[] sortedPopulation = Arrays.copyOf(transformToArray(genePool), genePool.size());
        Arrays.sort(sortedPopulation);
        return sortedPopulation;
    }

    @Override
    public int getSumPopulationFitness() {
        logger.info("");
        int sum = 0;
        for (IChromosome chromosome : genePool) {
            sum += chromosome.getFitness();
        }
        return sum;
    }

    @Override
    public void replaceGene(IChromosome geneToReplace) {
        genePool.remove(geneToReplace);
        GameEngine engine = GameEngine.getInstance();
        IChromosome replacer = new NumChromosome(engine.getCodeLength(), engine.getNumColors());
        replacer.generateRandom();
        genePool.add(replacer);
    }

    @Override
    public IChromosome getFittest() {
        logger.info("");
        IChromosome fittest = getPopulationSorted()[genePool.size() - 1];
        /*todo check*/
        logger.info("*****Fitness of fittest: " + fittest.getFitness());
        logger.info("*****Fitness of weakest: " + getPopulationSorted()[0].getFitness());

        return fittest;
    }

    private void replaceWeakestWithNewGenes(IChromosome[] newGenes) {
        IChromosome[] improvedGenePool = getPopulationSorted();
        int lastPos = improvedGenePool.length - 1;
        improvedGenePool[lastPos - 1] = newGenes[0];
        improvedGenePool[lastPos] = newGenes[1];
        this.genePool = transformToList(improvedGenePool);
    }

    private IChromosome[] breedRandomPopulation(int size) {
        IChromosome[] randomPopulationPool = new IChromosome[size];
        GameEngine engine = GameEngine.getInstance();
        NumChromosome breeder = new NumChromosome(engine.getCodeLength(), engine.getNumColors());
        for (int i = 0; i < randomPopulationPool.length; i++) {
            breeder.generateRandom();
            randomPopulationPool[i] = new NumChromosome(Arrays.copyOf(breeder.getSequence(), engine.getCodeLength()));
            randomPopulationPool[i].setGeneration(0);
        }
        return randomPopulationPool;
    }

    private void instantiateHelpers(SelectionEnum chooseSelection, CrossoverEnum chooseCrossover, MutationEnum chooseMutation) {
        switch (chooseSelection) {
            case ROULETTE_WHEEL:
                selector = new RouletteWheelSelection();
                break;
            case TOURNAMENT:
                selector = new TournamentSelection();
                break;
        }

        switch (chooseCrossover) {
            case K_POINT:
                if (GameSettings.INSTANCE.kForCrossover > GameEngine.getInstance().getCodeLength()) {
                    String errorMessage = "   Population - instantiateHelpers: chooseCrossover ERROR: configured k ="
                            + GameSettings.INSTANCE.kForCrossover + " while code length is " + GameEngine.getInstance().getCodeLength();
                    throw new IndexOutOfBoundsException(errorMessage);
                }
                crosser = new KPointCrossover();
                break;
            case ONE_POINT:
                if (GameEngine.getInstance().getCodeLength() < 2) {
                    String errorMessage = "   Population - instantiateHelpers: chooseCrossover ERROR: one-point crossover while code length is " + GameEngine.getInstance().getCodeLength();
                    throw new IndexOutOfBoundsException(errorMessage);
                }
                crosser = new OnePointCrossover();
                break;
            case TWO_POINT:
                if (GameEngine.getInstance().getCodeLength() < 3) {
                    String errorMessage = "   Population - instantiateHelpers: chooseCrossover ERROR: two-point crossover while code length is " + GameEngine.getInstance().getCodeLength();
                    throw new IndexOutOfBoundsException(errorMessage);
                }
                crosser = new TwoPointCrossover();
                break;
            case UNIFORM:
                crosser = new UniformCrossover();
                break;
        }

        switch (chooseMutation) {
            case DISPLACEMENT:
                mutator = new DisplacementMutation();
                break;
            case EXCHANGE:
                mutator = new ExchangeMutation();
                break;
            case INSERTION:
                mutator = new InsertionMutation();
                break;
            case INVERSION:
                mutator = new InversionMutation();
                break;
            case SCRAMBLE:
                mutator = new ScrambleMutation();
                break;
        }
    }

    private IChromosome[] transformToArray(ArrayList<IChromosome> list) {
        IChromosome[] geneArray = new IChromosome[list.size()];
        for (int i = 0; i < list.size(); i++) {
            geneArray[i] = list.get(i);
        }
        return geneArray;
    }

    private ArrayList<IChromosome> transformToList(IChromosome[] array) {
        ArrayList<IChromosome> list = new ArrayList<IChromosome>();
        for (int i = 0; i < array.length; i++) {
            list.add(array[i]);
        }
        return list;
    }

    /*--
     * getter + setter
     */
    public int getMaxGenerationCounter() {
        return maxGenerationCounter;
    }

    public void setMaxGenerationCounter(int maxGenerationCounter) {
        this.maxGenerationCounter = maxGenerationCounter;
    }

    @Override
    public IChromosome[] getGenePool() {
        return transformToArray(genePool);
    }

    public void setGenePool(IChromosome[] genePool) {
        this.genePool = transformToList(genePool);
    }


}
