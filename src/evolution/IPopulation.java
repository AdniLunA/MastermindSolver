package evolution;

public interface IPopulation {
    IChromosome getFittest();

    void evolve();

    IChromosome[] getPopulationSorted();

    IChromosome[] getPopulation();

    int getSumPopulationFitness();
}