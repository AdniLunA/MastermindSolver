package evolution;

import config.SelectionEnum;

public interface ISelection {
    public IChromosome[] getParents(IPopulation population);

    void splitPopulation(IPopulation population);

    IChromosome selectParents(IPopulation populationPool);
}
