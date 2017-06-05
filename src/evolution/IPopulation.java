package evolution;

import config.CrossoverEnum;
import config.MutationEnum;
import config.SelectionEnum;

import java.util.ArrayList;

public interface IPopulation {

    IChromosome getFittest();

    void evolve();

    void evolve(SelectionEnum chooseSelection, CrossoverEnum chooseCrossover, MutationEnum chooseMutation);

    void refreshSicknessOfGenePool();

    ArrayList<IChromosome> getPopulationSorted();

    ArrayList<IChromosome> getGenePool();

    int getSumPopulationSickness();
}