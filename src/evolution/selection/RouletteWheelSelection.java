package evolution.selection;

import config.LoggerGenerator;
import config.MersenneTwisterFast;
import engine.GameSettings;
import evolution.IChromosome;
import evolution.NumChromosome;
import evolution.population.PopulationBasics;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class RouletteWheelSelection extends SelectorBasics {
    /*--
     * debugging
     */
    private final Logger logger = LoggerGenerator.rouletteWheelSelection;

    /*--attributes*/
    private MersenneTwisterFast randomGenerator = new MersenneTwisterFast(System.nanoTime());

    /*--functions*/
    @Override
    public IChromosome[] getParents(ArrayList<IChromosome> genePool) {
        super.splitPopulation(genePool);
        IChromosome[] parents = new NumChromosome[2];
        parents[0] = selectParents(super.fatherPool);
        parents[1] = selectParents(super.motherPool);
        if (GameSettings.INSTANCE.loggingEnabled) {
            logger.info("    Father: " + parents[0].toString() + ", mother: " + parents[1].toString());
            logger.info("    Fitness of father: " + parents[0].getSickness() + ", fitness of mother: " + parents[1].getSickness());
        }
        return parents;
    }

    private IChromosome selectParents(PopulationBasics populationPool) {
        double totalPopulationSickness = (double) populationPool.getSumPopulationSickness();
        double roulettePointer = randomGenerator.nextDouble(true, false); /*incl. 0, excl. 1*/
        double bottomBoundary = 0.0;
        double topBoundary;

        for (IChromosome chromosome : populationPool.getGenePool()) {
            topBoundary = bottomBoundary
                    + ((totalPopulationSickness - chromosome.getSickness())
                    / totalPopulationSickness);
            if (bottomBoundary <= roulettePointer && roulettePointer < topBoundary) {
                return chromosome;
            } else {
                bottomBoundary = topBoundary;
            }
        }
        return populationPool.getFittest();
    }
}
