package evolution.selection;

import evolution.IChromosome;
import evolution.IPopulation;
import evolution.NumChromosome;
import evolution.Population;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;

public class TournamentSelection extends SelectorBasics {
    /*--
     * debugging
     */
    private final Logger logger = LogManager.getLogger(this);

    /*--
     * functions
     */
    @Override
    public IChromosome[] getParents(ArrayList<IChromosome> genePool) {
        //logger.info("");
        super.splitPopulation(genePool);
        IChromosome[] parents = new NumChromosome[2];
        parents[0] = selectParents(fatherPool);
        parents[1] = selectParents(motherPool);
        return parents;
    }

    private IChromosome selectParents(IPopulation populationPool) {
        return populationPool.getFittest();
    }
}
