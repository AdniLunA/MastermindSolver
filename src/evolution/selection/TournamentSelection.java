package evolution.selection;

import evolution.IChromosome;
import evolution.IPopulation;
import evolution.NumChromosome;
import evolution.Population;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

public class TournamentSelection implements ISelection {
    /*--
     * debugging
     */
    private final Logger logger = LogManager.getLogger(this);

    /*--
     * attributes
     */
    private IPopulation fatherPool;
    private IPopulation motherPool;

    /*--
     * functions
     */
    @Override
    public IChromosome[] getParents(IChromosome[] genePool) {
        logger.info("");
        splitPopulation(genePool);
        IChromosome[] parents = new NumChromosome[2];
        parents[0] = selectParents(fatherPool);
        parents[1] = selectParents(motherPool);
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
        return populationPool.getFittest();
    }
}
