package evolution;

public interface IPopulation {


    //functions
    IChromosome getFittest();

    void evolve();

    void sortPopulation();

    IChromosome[] getPopulation();

    int getSumPopulationFitness();
}