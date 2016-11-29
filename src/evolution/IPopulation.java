package evolution;

public interface IPopulation {


    //functions
    IChromosome getFittest();

    void evolve();

    IChromosome[] getPopulationSorted();

    IChromosome[] getPopulation();

    int getSumPopulationFitness();
}