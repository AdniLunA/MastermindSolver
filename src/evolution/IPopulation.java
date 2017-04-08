package evolution;

import config.CrossoverEnum;
import config.MutationEnum;
import config.SelectionEnum;

public interface IPopulation {

    IChromosome getFittest();

    void evolve();

    void evolve(SelectionEnum chooseSelection, CrossoverEnum chooseCrossover, MutationEnum chooseMutation);

    IChromosome[] getPopulationSorted();

    IChromosome[] getGenePool();

    int getSumPopulationFitness();

    void replaceGene(IChromosome geneToRemove);
}