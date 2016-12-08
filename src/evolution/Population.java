package evolution;

import config.Configuration;
import config.CrossoverEnum;
import config.MutationEnum;
import config.SelectionEnum;
import engine.GameEngine;
import evolution.crossover.*;
import evolution.mutation.*;
import evolution.selection.ISelection;
import evolution.selection.RouletteWheelSelection;
import evolution.selection.TournamentSelection;

import java.util.Arrays;
import java.util.InputMismatchException;

public class Population implements IPopulation {
    /***
     * constructors
     ***/
    public Population() {
        this.genePool = breedRandomPopulation(Configuration.INSTANCE.SIZE_OF_POPULATION);
    }

    public Population(IChromosome[] genePool) {
        this.genePool = genePool;
    }

    /***
     * attributes
     ***/
    private int maxGenerationCounter = 0;
    private IChromosome[] genePool = new IChromosome[Configuration.INSTANCE.SIZE_OF_POPULATION];
    private ISelection selector;
    private ICrossover crosser;
    private IMutation mutator;

    /***
     * functions
     ***/
    @Override
    public void evolve(){
        evolve(Configuration.INSTANCE.SELECTION_TYPE, Configuration.INSTANCE.CROSSOVER_TYPE, Configuration.INSTANCE.MUTATION_TYPE);
    }

    @Override
    public void evolve(SelectionEnum chooseSelection, CrossoverEnum chooseCrossover, MutationEnum chooseMutation) {
        System.out.println();
        System.out.println("Population - evolve");
        maxGenerationCounter++;
        instantiateHelpers(chooseSelection,chooseCrossover,chooseMutation);
        /*selection*/
        System.out.println("Population - evolve: Start selection");
        IChromosome[] parents = selector.getParents(Arrays.copyOf(genePool, genePool.length));

        /*crossover*/
        System.out.println("Population - evolve: Start crossover");
        IChromosome[] newGeneration = crosser.crossover(parents);
        for (int i = 0; i < 2; i++) {
            System.out.println("    -- Child #"+i+" is valid: " +newGeneration[i].checkValidity());
            if(!newGeneration[i].checkValidity()){
                throw new InputMismatchException("Population evolve crossover: ERROR - got invalid child "+newGeneration[i].toString());
            }
        }
        newGeneration[0].setGeneration(maxGenerationCounter+1);
        newGeneration[1].setGeneration(maxGenerationCounter+1);
        replaceWeakestWithNewGenes(newGeneration);

        /*mutation*/
        System.out.println("Population - evolve: Start mutation");
        IChromosome[] mutatedGeneration = newGeneration;
        mutatedGeneration = mutator.mutateGenes(Arrays.copyOf(genePool, genePool.length));
        this.genePool = mutatedGeneration;

        System.out.println("- Population - evolve: A new generation has been born! #" + maxGenerationCounter);
    }

    @Override
    public IChromosome[] getPopulationSorted() {
        System.out.println("Population - getPopulationSorted");
        IChromosome[] sortedPopulation = Arrays.copyOf(genePool, genePool.length);
        Arrays.sort(sortedPopulation);
        return sortedPopulation;
    }

    @Override
    public int getSumPopulationFitness() {
        System.out.println("Population - getSumPopulationFitness");
        int sum = 0;
        for (IChromosome chromosome : genePool) {
            sum += chromosome.getFitness();
        }
        return sum;
    }

    @Override
    public IChromosome[] getSortedPopulation() {
        IChromosome[] sortedPopulation = Arrays.copyOf(genePool, genePool.length);
        Arrays.sort(sortedPopulation);
        return sortedPopulation;
    }

    @Override
    public IChromosome getFittest() {
        System.out.println("Population - getFittest");
        IChromosome fittest = getSortedPopulation()[genePool.length - 1];
        /*todo check*/
        System.out.println("*****Fitness of expected fittest: " + fittest.getFitness());
        System.out.println("*****Fitness of expected weakest: " + getSortedPopulation()[0].getFitness());

        return fittest;
    }

    private void replaceWeakestWithNewGenes(IChromosome[] newGenes){
        IChromosome[] improvedGenePool = getSortedPopulation();
        int lastPos = improvedGenePool.length-1;
        improvedGenePool[lastPos-1] = newGenes[0];
        improvedGenePool[lastPos] = newGenes[1];
        this.genePool = improvedGenePool;
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
        switch (chooseSelection){
            case ROULETTE_WHEEL:
                selector = new RouletteWheelSelection();
                break;
            case TOURNAMENT :
                selector = new TournamentSelection();
                break;
        }

        switch (chooseCrossover){
            case K_POINT :
                crosser = new KPointCrossover();
                break;
            case ONE_POINT:
                crosser = new OnePointCrossover();
                break;
            case TWO_POINT :
                crosser = new TwoPointCrossover();
                break;
            case UNIFORM :
                crosser = new UniformCrossover();
                break;
        }

        switch (chooseMutation){
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

    /***
     * getter + setter
     ***/
    public int getMaxGenerationCounter() {
        return maxGenerationCounter;
    }

    public void setMaxGenerationCounter(int maxGenerationCounter) {
        this.maxGenerationCounter = maxGenerationCounter;
    }

    @Override
    public IChromosome[] getGenePool() {
        return genePool;
    }

    public void setGenePool(IChromosome[] genePool) {
        this.genePool = genePool;
    }


}
