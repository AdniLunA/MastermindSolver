package evolution.population;

import config.*;
import engine.GameSettings;
import evolution.IChromosome;
import evolution.NumChromosome;
import evolution.crossover.*;
import evolution.mutation.*;
import evolution.selection.ISelection;
import evolution.selection.RouletteWheelSelection;
import evolution.selection.TournamentSelection;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.InputMismatchException;

public class Population extends PopulationBasics {
    /*--
     * debugging
     */
    private final Logger logger = LoggerGenerator.population;

    /*--
     * constructors
     */
    public Population() {
        populationSize = GameSettings.INSTANCE.sizeOfPopulation;
        genePool.clear();
        setGenePoolRandom(populationSize);
    }

    /*--
     * attributes
     */
    private int generationCounter = 0;
    private ISelection select;
    private ICrossover crossover;
    private IMutation mutate;
    private MersenneTwisterFast generator = new MersenneTwisterFast(System.nanoTime());

    /*--
     * functions
     */
    public void evolve() {
        evolve(GameSettings.INSTANCE.selectionType, GameSettings.INSTANCE.crossoverType, GameSettings.INSTANCE.mutationType);
    }

    private void evolve(SelectionEnum chooseSelection, CrossoverEnum chooseCrossover, MutationEnum chooseMutation) {
        generationCounter++;
        instantiateHelpers(chooseSelection, chooseCrossover, chooseMutation);
        doEvolution();
    }

    public void evolve(int selection, int crossover, int mutation) {
        generationCounter++;
        instantiateHelpers(selection, crossover, mutation);
        doEvolution();
    }

    private void doEvolution() {
        /*selection*/
        IChromosome[] parents = select.getParents(genePool);

        /*crossParents*/
        IChromosome[] children = crossover.crossParents(parents);
        for (int i = 0; i < children.length; i++) {
            if (GameSettings.INSTANCE.loggingEnabled) {
                logger.info("    -- Child #" + i + " is valid: " + children[i].checkValidity());
            }
            if (!children[i].checkValidity()) {
                throw new InputMismatchException("Population crossover: ERROR - got invalid child " + children[i].toString());
            }
        }

        /*mutation*/
        genePool = mutate.mutateGenes(genePool);

        replaceWeakestWithNewGenes(children);

        if (GameSettings.INSTANCE.loggingEnabled) {
            logger.info("A new generation was born! #" + generationCounter);
        }
    }

    private IChromosome createNonDuplicateRandomChromosome() {
        NumChromosome randomChromosome;
        int countTries = 0;
        do {
            if (countTries > GameSettings.INSTANCE.MAX_TRY_AGAIN_NEW_RANDOM_CHR) {
                throw new RuntimeException("Population - removeAlreadyRequestedCodes: " +
                        "Tried to often to generate new code that hasn't been submitted yet. " +
                        "Stopped to prevent infinite loop.");
            }
            randomChromosome = new NumChromosome(generator);
            countTries++;
        } while (genePool.contains(randomChromosome));
        return randomChromosome;
    }

    public void refreshSicknessOfGenePool() {
        for (IChromosome gene : genePool) {
            gene.calculateSickness();
        }
    }

    private void replaceWeakestWithNewGenes(IChromosome[] newGenes) {
        boolean isGene0Known = genePool.contains(newGenes[0]);
        boolean isGene1Known = genePool.contains(newGenes[1]);
        ArrayList<IChromosome> improvedGenePool = getPopulationSorted();
        /*add some random genes*/
        genePool.remove(improvedGenePool.get(improvedGenePool.size()-1));
        genePool.remove(improvedGenePool.get(improvedGenePool.size()-2));
        genePool.add(createNonDuplicateRandomChromosome());
        genePool.add(createNonDuplicateRandomChromosome());

        /*add new genes*/
        if (!isGene0Known || !isGene1Known) {
            genePool.remove(improvedGenePool.get(improvedGenePool.size() - 3));
            if (!isGene0Known && !isGene1Known) {
                genePool.remove(improvedGenePool.get(improvedGenePool.size() - 4));
            }

            if (!isGene0Known) {
                genePool.add(newGenes[0]);
            }
            if (!isGene1Known) {
                genePool.add(newGenes[1]);
            }
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

    private void instantiateHelpers(int chooseSelection, int chooseCrossover, int chooseMutation) {
        switch (chooseSelection) {
            case 0:
                select = new RouletteWheelSelection();
                break;
            case 1:
                select = new TournamentSelection();
                break;
        }

        switch (chooseCrossover) {
            case 0:
                if (GameSettings.INSTANCE.kForCrossover > GameSettings.INSTANCE.lengthOfCode) {
                    String errorMessage = "   Population - instantiateHelpers: chooseCrossover ERROR: configured k ="
                            + GameSettings.INSTANCE.kForCrossover + " while code length is " + GameSettings.INSTANCE.lengthOfCode;
                    throw new IndexOutOfBoundsException(errorMessage);
                }
                crossover = new KPointCrossover();
                break;
            case 1:
                if (GameSettings.INSTANCE.lengthOfCode < 2) {
                    String errorMessage = "   Population - instantiateHelpers: chooseCrossover ERROR: one-point crossParents while code length is " + GameSettings.INSTANCE.lengthOfCode;
                    throw new IndexOutOfBoundsException(errorMessage);
                }
                crossover = new OnePointCrossover();
                break;
            case 2:
                if (GameSettings.INSTANCE.lengthOfCode < 3) {
                    String errorMessage = "   Population - instantiateHelpers: chooseCrossover ERROR: two-point crossParents while code length is " + GameSettings.INSTANCE.lengthOfCode;
                    throw new IndexOutOfBoundsException(errorMessage);
                }
                crossover = new TwoPointCrossover();
                break;
            case 3:
                crossover = new UniformCrossover();
                break;
        }

        switch (chooseMutation) {
            case 0:
                mutate = new DisplacementMutation();
                break;
            case 1:
                mutate = new ExchangeMutation();
                break;
            case 2:
                mutate = new InsertionMutation();
                break;
            case 3:
                mutate = new InversionMutation();
                break;
            case 4:
                mutate = new ScrambleMutation();
                break;
        }
    }
}
