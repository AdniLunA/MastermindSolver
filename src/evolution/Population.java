package evolution;

import config.CrossoverEnum;
import config.MutationEnum;
import config.SelectionEnum;
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
        genePool = generateRandomPopulation(GameSettings.INSTANCE.sizeOfPopulation);
    }

    public Population(IChromosome[] genePool) {
        this.genePool = transformToList(genePool);
    }

    /*--
     * attributes
     */
    private int maxGenerationCounter = 0;
    private ArrayList<IChromosome> genePool = new ArrayList<>();
    private ISelection select;
    private ICrossover crossover;
    private IMutation mutate;
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
        IChromosome[] parents = select.getParents(getGenePoolArray());

        /*crossParents*/
        logger.info("Start crossover");
        IChromosome[] children = crossover.crossParents(parents);
        for (int i = 0; i < children.length; i++) {
            logger.info("    -- Child #" + i + " is valid: " + children[i].checkValidity());
            if (!children[i].checkValidity()) {
                throw new InputMismatchException("Population crossover: ERROR - got invalid child " + children[i].toString());
            }
        }
        children[0].setGeneration(maxGenerationCounter + 1);
        children[1].setGeneration(maxGenerationCounter + 1);
        replaceWeakestWithNewGenes(children);

        /*mutation*/
        logger.info("Start mutation");
        IChromosome[] mutatedGeneration = mutate.mutateGenes(getGenePoolArray());
        this.genePool = transformToList(mutatedGeneration);

        logger.info("A new generation has been born! #" + maxGenerationCounter);
    }

    @Override
    public IChromosome[] getPopulationSorted() {
        logger.info("");
        IChromosome[] sortedPopulation = getGenePoolArray();
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
        IChromosome replacer = new NumChromosome();
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

    private ArrayList<IChromosome> generateRandomPopulation(int size) {
        ArrayList<IChromosome> randomPopulationPool = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            randomPopulationPool.add(new NumChromosome());
        }
        return randomPopulationPool;
    }

    private void instantiateHelpers(SelectionEnum chooseSelection, CrossoverEnum chooseCrossover, MutationEnum chooseMutation) {
        switch (chooseSelection) {
            case ROULETTE_WHEEL:
                select = new RouletteWheelSelection();
                break;
            case TOURNAMENT:
                select = new TournamentSelection();
                break;
        }

        switch (chooseCrossover) {
            case K_POINT:
                if (GameSettings.INSTANCE.kForCrossover > GameSettings.INSTANCE.lengthOfCode) {
                    String errorMessage = "   Population - instantiateHelpers: chooseCrossover ERROR: configured k ="
                            + GameSettings.INSTANCE.kForCrossover + " while code length is " + GameSettings.INSTANCE.lengthOfCode;
                    throw new IndexOutOfBoundsException(errorMessage);
                }
                crossover = new KPointCrossover();
                break;
            case ONE_POINT:
                if (GameSettings.INSTANCE.lengthOfCode < 2) {
                    String errorMessage = "   Population - instantiateHelpers: chooseCrossover ERROR: one-point crossParents while code length is " + GameSettings.INSTANCE.lengthOfCode;
                    throw new IndexOutOfBoundsException(errorMessage);
                }
                crossover = new OnePointCrossover();
                break;
            case TWO_POINT:
                if (GameSettings.INSTANCE.lengthOfCode < 3) {
                    String errorMessage = "   Population - instantiateHelpers: chooseCrossover ERROR: two-point crossParents while code length is " + GameSettings.INSTANCE.lengthOfCode;
                    throw new IndexOutOfBoundsException(errorMessage);
                }
                crossover = new TwoPointCrossover();
                break;
            case UNIFORM:
                crossover = new UniformCrossover();
                break;
        }

        switch (chooseMutation) {
            case DISPLACEMENT:
                mutate = new DisplacementMutation();
                break;
            case EXCHANGE:
                mutate = new ExchangeMutation();
                break;
            case INSERTION:
                mutate = new InsertionMutation();
                break;
            case INVERSION:
                mutate = new InversionMutation();
                break;
            case SCRAMBLE:
                mutate = new ScrambleMutation();
                break;
        }
    }

    private ArrayList<IChromosome> transformToList(IChromosome[] array) {
        ArrayList<IChromosome> list = new ArrayList<>();
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

    @Override
    public IChromosome[] getGenePoolArray() {
        IChromosome[] geneArray = new IChromosome[genePool.size()];
        for (int i = 0; i < genePool.size(); i++) {
            geneArray[i] = genePool.get(i);
        }
        return geneArray;
    }

    public void setGenePool(IChromosome[] genePool) {
        this.genePool = transformToList(genePool);
    }


}
