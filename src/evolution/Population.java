package evolution;

import config.Configuration;

public class Population implements IPopulation{
    //constructors
    public Population() {
    }

    public Population(IChromosome[] population) {
        this.population = population;
    }

    //attributes
    private int maxGenerationCounter = 0;
    private IChromosome[] population = new IChromosome[Configuration.INSTANCE.SIZE_OF_POPULATION];
    private IChromosome[] sortedPopulation = new IChromosome[Configuration.INSTANCE.SIZE_OF_POPULATION];

    //functions
    @Override
    public IChromosome getFittest(){
        System.out.println("Population - getFittest");
        return new NumChromosome(1,1);//todo
    }

    @Override
    public void evolve(){
        System.out.println("Population - evolve");
    }

    @Override
    public void sortPopulation(){
        System.out.println("Population - sortPopulation");

    }

    //getter + setter
    public int getMaxGenerationCounter() {
        return maxGenerationCounter;
    }

    public void setMaxGenerationCounter(int maxGenerationCounter) {
        this.maxGenerationCounter = maxGenerationCounter;
    }

    @Override
    public IChromosome[] getPopulation() {
        return population;
    }

    public void setPopulation(IChromosome[] population) {
        this.population = population;
    }

    @Override
    public int getSumPopulationFitness(){
        int sum = 0;
        for (IChromosome chromosome:population) {
            sum += chromosome.getFitness();
        }
        return sum;
    }

}
