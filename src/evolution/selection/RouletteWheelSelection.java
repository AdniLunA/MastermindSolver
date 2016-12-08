package evolution.selection;

import config.MersenneTwisterFast;
import evolution.*;

import java.util.Arrays;

public class RouletteWheelSelection implements ISelection {
    /***attributes***/
    private IPopulation fatherPool;
    private IPopulation motherPool;
    private MersenneTwisterFast randomGenerator = new MersenneTwisterFast(System.nanoTime());

    /***functions***/
    @Override
    public IChromosome[] getParents(IChromosome[] genePool) {
        System.out.println("RouletteWheelSelection - getParents");
        splitPopulation(genePool);
        IChromosome[] parents = new NumChromosome[2];
        parents[0] = selectParents(fatherPool);
        parents[1] = selectParents(motherPool);
        System.out.println("RouletteWheelSelection - getParents: ");
        System.out.println("    Father: " + parents[0].toString() + ", mother: " + parents[1].toString());
        System.out.println("    Fitness of father: " + parents[0].getFitness() + ", fitness of mother: " + parents[1].getFitness());
        return parents;
    }

    private void splitPopulation(IChromosome[] genePool) {
        System.out.println("RouletteWheelSelection - splitPopulation");
        /*copyOfRange: original [], inclusive from, exclusive to*/
        fatherPool = new Population(Arrays.copyOfRange(genePool,
                0, genePool.length / 2));
        motherPool = new Population(Arrays.copyOfRange(genePool,
                genePool.length / 2, genePool.length));
    }

    private IChromosome selectParents(IPopulation populationPool) {
        System.out.println("RouletteWheelSelection - selectParents");
        double totalPopulationFitness = (double) populationPool.getSumPopulationFitness();
        double roulettePointer = randomGenerator.nextDouble(true, false); /*incl. 0, excl. 1*/
        double bottomBoundary = 0.0;
        double topBoundary;

        for (IChromosome chromosome : populationPool.getGenePool()) {
            topBoundary = bottomBoundary + (chromosome.getFitness() / totalPopulationFitness);
            if (bottomBoundary <= roulettePointer && roulettePointer < topBoundary) {
                return chromosome;
            } else {
                bottomBoundary = topBoundary;
            }
        }
        IChromosome[] sortedPopulation = populationPool.getPopulationSorted();
        return sortedPopulation[sortedPopulation.length - 1];
    }
}
