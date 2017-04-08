package evolution.selection;

import evolution.*;

import java.util.Arrays;

public class TournamentSelection implements ISelection {
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
        System.out.println("TournamentSelection - getParents");
        splitPopulation(genePool);
        IChromosome[] parents = new NumChromosome[2];
        parents[0] = selectParents(fatherPool);
        parents[1] = selectParents(motherPool);
        return parents;
    }

    private void splitPopulation(IChromosome[] genePool) {
        System.out.println("TournamentSelection - splitPopulation");
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
