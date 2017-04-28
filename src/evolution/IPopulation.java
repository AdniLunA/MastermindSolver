package evolution;

import config.CrossoverEnum;
import config.MutationEnum;
import config.SelectionEnum;

import java.util.ArrayList;

public interface IPopulation {

    IChromosome getFittest();

    void evolve();

    void evolve(SelectionEnum chooseSelection, CrossoverEnum chooseCrossover, MutationEnum chooseMutation);

    void removeAlreadyRequestedCodes(ArrayList<IChromosome> alreadyRequestedCodes);

    IChromosome[] getPopulationSorted();

    IChromosome[] getGenePoolArray();

    int getSumPopulationSickness();

    void replaceGene(IChromosome geneToRemove);
}