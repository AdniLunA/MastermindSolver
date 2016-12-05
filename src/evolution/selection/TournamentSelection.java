package evolution.selection;

import evolution.*;

import java.util.Arrays;

public class TournamentSelection implements ISelection {
    //attributes
    private IPopulation fatherPool;
    private IPopulation motherPool;

    //functions
    @Override
    public IChromosome[] getParents(IPopulation population) {
        System.out.println("TournamentSelection - getParents");
        splitPopulation(population);
        IChromosome[] parents = new NumChromosome[2];
        parents[0] = selectParents(fatherPool);
        parents[1] = selectParents(motherPool);
        return parents;
    }

    private void splitPopulation(IPopulation population){
        System.out.println("TournamentSelection - splitPopulation");
        //copyOfRange: original [], inclusive from, exclusive to
        fatherPool = new Population(Arrays.copyOfRange(population.getPopulation(),
                0,                                     population.getPopulation().length / 2));
        motherPool = new Population(Arrays.copyOfRange(population.getPopulation(),
                population.getPopulation().length / 2, population.getPopulation().length));
    }

    private IChromosome selectParents(IPopulation populationPool){
        return populationPool.getFittest();
    }
}
