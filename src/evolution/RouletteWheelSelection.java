package evolution;

import java.util.Arrays;

public class RouletteWheelSelection implements ISelection{
    //attributes
    private IPopulation fatherPool;
    private IPopulation motherPool;

    //functions
    @Override
    public IChromosome[] selectParents(IPopulation population) {
        System.out.println("TournamentSelection - selectParents");
        splitPopulation(population);
        IChromosome[] parents = new NumChromosome[2];
        parents[0] = doTournamentSelection(fatherPool);
        parents[1] = doTournamentSelection(motherPool);
        return parents;
    }

    @Override
    public void splitPopulation(IPopulation population){
        System.out.println("TournamentSelection - splitPopulation");
        //copyOfRange: original [], inclusive from, exclusive to
        fatherPool = new Population(Arrays.copyOfRange(population.getPopulation(),
                0,                                     population.getPopulation().length / 2));
        motherPool = new Population(Arrays.copyOfRange(population.getPopulation(),
                population.getPopulation().length / 2, population.getPopulation().length));
    }

    @Override
    public IChromosome doTournamentSelection(IPopulation populationPool) {
        int totalPopulationFitness = populationPool.getSumPopulationFitness();


    }
}
