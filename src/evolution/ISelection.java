package evolution;

import config.SelectionEnum;

public interface ISelection {
    public IChromosome[] selectParents(IPopulation population);

    void splitPopulation(IPopulation population);

    IChromosome doTournamentSelection(IPopulation populationPool);
}
