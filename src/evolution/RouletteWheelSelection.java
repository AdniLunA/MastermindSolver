package evolution;

import config.MersenneTwisterFast;

import java.util.Arrays;

public class RouletteWheelSelection implements ISelection{
    //attributes
    private IPopulation fatherPool;
    private IPopulation motherPool;
    private MersenneTwisterFast randomGenerator = new MersenneTwisterFast(System.nanoTime());

    //functions
    @Override
    public IChromosome[] getParents(IPopulation population) {
        System.out.println("TournamentSelection - getParents");
        splitPopulation(population);
        IChromosome[] parents = new NumChromosome[2];
        parents[0] = selectParents(fatherPool);
        parents[1] = selectParents(motherPool);
        System.out.println("father: "+parents[0].toString()+", mother: "+parents[1].toString());
        System.out.println("fitness of father: "+parents[0].getFitness()+", fitness of mother: "+ parents[1].getFitness());
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

    private IChromosome selectParents(IPopulation populationPool) {
        System.out.println("TournamentSelection - selectParents");
        double totalPopulationFitness = (double) populationPool.getSumPopulationFitness();
        double roulettePointer = randomGenerator.nextDouble(true, false); //incl. 0, excl. 1
        double bottomBoundary = 0.0;
        double topBoundary;

        for(IChromosome chromosome : populationPool.getPopulation()){
            topBoundary = bottomBoundary + (chromosome.getFitness() / totalPopulationFitness);
            if(bottomBoundary <= roulettePointer && roulettePointer < topBoundary ){
                return chromosome;
            } else {
                bottomBoundary = topBoundary;
            }
        }
        IChromosome[] sortedPopulation = populationPool.getPopulationSorted();
        return sortedPopulation[sortedPopulation.length - 1];
    }
}
