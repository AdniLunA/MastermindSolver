package evolution.selection;

import evolution.IChromosome;
import evolution.NumChromosome;
import evolution.population.PopulationBasics;

import java.util.ArrayList;

public class TournamentSelection extends SelectorBasics {
    /*--
     * functions
     */
    @Override
    public IChromosome[] getParents(ArrayList<IChromosome> genePool) {
        super.splitPopulation(genePool);
        IChromosome[] parents = new NumChromosome[2];
        parents[0] = selectParents(fatherPool);
        parents[1] = selectParents(motherPool);
        return parents;
    }

    private IChromosome selectParents(PopulationBasics populationPool) {
        return populationPool.getFittest();
    }
}
