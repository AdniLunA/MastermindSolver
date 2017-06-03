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

import java.util.*;

public class Population implements IPopulation {
    /*--
     * debugging
     */
    private final Logger logger = LogManager.getLogger(this);

    /*--
     * constructors
     */
    public Population() {
        this.populationSize=GameSettings.INSTANCE.sizeOfPopulation;
        genePool = new ArrayList<>();
        genePool.ensureCapacity(populationSize);
        setGenePoolRandom(populationSize);
    }

    public Population(ArrayList<IChromosome> genePool) {
        this.genePool = (ArrayList<IChromosome>) genePool.clone();
        this.populationSize=genePool.size();
    }

    /*--
     * attributes
     */
    private int generationCounter = 0;
    private ArrayList<IChromosome> genePool;
    private ISelection select;
    private ICrossover crossover;
    private IMutation mutate;
    private int populationSize;

    /*--
     * functions
     */
    @Override
    public void evolve() {
        evolve(GameSettings.INSTANCE.selectionType, GameSettings.INSTANCE.crossoverType, GameSettings.INSTANCE.mutationType);
    }

    @Override
    public void evolve(SelectionEnum chooseSelection, CrossoverEnum chooseCrossover, MutationEnum chooseMutation) {
        //logger.info("");
        generationCounter++;
        instantiateHelpers(chooseSelection, chooseCrossover, chooseMutation);
        /*selection*/
        //logger.info("Start selection");
        /*todo: fortfahren mit error tracking*/
        IChromosome[] parents = select.getParents(genePool);

        /*crossParents*/
        //logger.info("Start crossover");
        IChromosome[] children = crossover.crossParents(parents);
        for (int i = 0; i < children.length; i++) {
            if(GameSettings.INSTANCE.loggingEnabled) {
                logger.info("    -- Child #" + i + " is valid: " + children[i].checkValidity());
            }
            if (!children[i].checkValidity()) {
                throw new InputMismatchException("Population crossover: ERROR - got invalid child " + children[i].toString());
            }
        }
        replaceWeakestWithNewGenes(children);

        /*mutation*/
        //logger.info("Start mutation");
        genePool = mutate.mutateGenes(genePool);

        if(GameSettings.INSTANCE.loggingEnabled) {
            logger.info("A new generation was born! #" + generationCounter);
        }
    }

    @Override
    public ArrayList<IChromosome> getPopulationSorted() {
        ArrayList<IChromosome> sortedCopy = (ArrayList<IChromosome>) genePool.clone();
        sortedCopy.sort((c1, c2) -> c1.compare(c1,c2));
        return sortedCopy;
    }

    @Override
    public int getSumPopulationSickness() {
        //logger.info("");
        int sum = 0;
        for (IChromosome chromosome : genePool) {
            sum += chromosome.getSickness();
        }
        return sum;
    }

    private IChromosome createNonDuplicateRandomChromosome(){
        NumChromosome randomChromosome;
        int countTries = 0;
        do {
            if(countTries > GameSettings.INSTANCE.MAX_NO_OF_TRIES_TO_GENERATE_NEW_REQUESTS){
                throw new RuntimeException("Population - removeAlreadyRequestedCodes: "+
                        "Tried to often to generate new code that hasn't been submitted yet. "+
                        "Stopped to prevent infinite loop.");
            }
            randomChromosome = new NumChromosome();
            countTries++;
        } while (genePool.contains(randomChromosome));
        return randomChromosome;
    }

    @Override
    public void removeAlreadyRequestedCodes(ArrayList<IChromosome> alreadyRequestedCodes){
        genePool.removeAll(alreadyRequestedCodes);
        while(genePool.size() < populationSize){
            genePool.add(createNonDuplicateRandomChromosome());
        }
    }

    @Override
    public IChromosome getFittest() {
        for (IChromosome candidate :
                genePool) {
            if(candidate.getSickness() == 0){
                return candidate;
            }
        }
        return genePool.get(0);
    }

    private void replaceWeakestWithNewGenes(IChromosome[] newGenes) {
        boolean isGene0Known = genePool.contains(newGenes[0]);
        boolean isGene1Known = genePool.contains(newGenes[1]);
        if(!isGene0Known || !isGene1Known) {
            ArrayList<IChromosome> improvedGenePool = getPopulationSorted();
            int lastPos = improvedGenePool.size() - 1;

            genePool.remove(improvedGenePool.get(lastPos));
            if(!isGene0Known && !isGene1Known) {
                genePool.remove(improvedGenePool.get(lastPos - 1));
            }
        }
        if(isGene0Known){
            genePool.add(newGenes[0]);
        }
        if(isGene1Known){
            genePool.add(newGenes[1]);
        }
    }

    private void setGenePoolRandom(int size) {
        genePool = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            genePool.add(createNonDuplicateRandomChromosome());
        }
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

    /*--
     * getter + setter
     */
    @Override
    public ArrayList<IChromosome> getGenePool(){
        return genePool;
    }
}
