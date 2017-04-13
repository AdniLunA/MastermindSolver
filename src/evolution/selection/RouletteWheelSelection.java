package evolution.selection;

import config.MersenneTwisterFast;
import evolution.IChromosome;
import evolution.IPopulation;
import evolution.NumChromosome;
import evolution.Population;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

public class RouletteWheelSelection implements ISelection {
    /*--
     * debugging
     */
    private final Logger logger = LogManager.getLogger(this);

    /*--attributes*/
    private IPopulation fatherPool;
    private IPopulation motherPool;
    private MersenneTwisterFast randomGenerator = new MersenneTwisterFast(System.nanoTime());

    /*--functions*/
    @Override
    public IChromosome[] getParents(IChromosome[] genePool) {
        logger.info("");
        splitPopulation(genePool);
        IChromosome[] parents = new NumChromosome[2];
        parents[0] = selectParents(fatherPool);
        parents[1] = selectParents(motherPool);
        logger.info("    Father: " + parents[0].toString() + ", mother: " + parents[1].toString());
        logger.info("    Fitness of father: " + parents[0].getFitness() + ", fitness of mother: " + parents[1].getFitness());
        return parents;
    }

    private void splitPopulation(IChromosome[] genePool) {
        logger.info("");
        /*copyOfRange: original [], inclusive from, exclusive to*/
        fatherPool = new Population(Arrays.copyOfRange(genePool,
                0, genePool.length / 2));
        motherPool = new Population(Arrays.copyOfRange(genePool,
                genePool.length / 2, genePool.length));
    }

    private IChromosome selectParents(IPopulation populationPool) {
        logger.info("");
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
